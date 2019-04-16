package nl.saxion.playground.template.lib;

import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import java.io.Serializable;
import java.util.ArrayList;


public class GameModel implements Serializable {

    /**
     * Width and height of the virtual view. This allows for some relatively easy resolution
     * independence.
     */
    public float virtualWidth, virtualHeight;

    // The ordered list of active game entities.
    private SafeTreeSet<Entity> entities = new SafeTreeSet<>();

    /**
     * The list of current touches. For each update, `Entity.handleTouch` is called.
     * But `Entity.tick` (and others) are free to manually inspect (or even edit)
     * this list as well.
     */
    public transient ArrayList<Touch> touches = new ArrayList<>();

    // The time in ms at which the tick() methods were last invoked.
    private transient double lastTickTime;

    // The currently watching listener/observer.
    private transient Listener listener;

    // A canvas `Paint` to fill the black bars outside the virtual screen with.
    private Paint blackPaint = new Paint();

    /**
     * Override to set ticks per second.
     * @return Number of times per second the `tick` methods should be called.
     * This can be zero (for instance when updates made directly from event handlers,
     * such as may be the case for non-animated board games), in which case
     * `GameModel.event("updated")` should be called when appropriate.
     */
    public int ticksPerSecond() { return 180; }

    /**
     * Override to disable continous redraws.
     * @return When true, the canvas will be redrawn 60 times per second, or as fast as the
     * device can manage. If not, `event("updated")`` should be called manually. This is more
     * appropriate for games without (fluent) animations that respond mostly on touch events.
     */
    public boolean continuousRedraw() { return true; }

    /**
     * Set the game event `Listener`. Setting a listener unpauses the game and immediately
     * fires a "new" event to trigger initial updates.
     * @param listener The event listener. May be null.
     */
    public void setListener(Listener listener) {
        this.listener = listener;
        setPaused(listener==null);
        event("new");
    }

    /**
     * Let the `Listener` (usually an `Activity`) know something happened (changed) within the
     * `GameModel`.
     * @param name Name of the event. "updated" is fired automatically when `continousRedraw`
     *             is enabled. "new" is fired when a new Listener is set. Game objects may
     *             make up any other events to communicate changes to the Activity.
     */
    public void event(String name) {
        if (listener!=null) listener.onGameEvent(name);
    }

    public interface Listener {
        void onGameEvent(String name);
    }

    /**
     * @param virtualWidth The GameView will scale, translate and crop to make the specified
     *                     virtual width fit on the actual screen.
     * @param virtualHeight The GameView will scale, translate and crop to make the specified
     *                      virtual height fit on the actual screen.
     */
    public GameModel(float virtualWidth, float virtualHeight) {
        this.virtualWidth = virtualWidth;
        this.virtualHeight = virtualHeight;

        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Add a game entity to the list.
     * @param entity The entity to be added.
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Remove a game entity from the list.
     * @param entity The entity to be removed.
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Get an `ArrayList` of `Entity`s of the specified type. Eg:
     * `ArrayList<MyHero> heroes = game.getEntities(MyHero.class);`
     * @param type A class object to search for in the list of entities.
     * @return The list of entities of the specified class.
     */
    public <T extends Entity> ArrayList<T> getEntities(Class<T> type) {
        ArrayList<T> results = new ArrayList<>();
        for(Entity obj : entities) {
            if (type.isInstance(obj)) {
                results.add(type.cast(obj));
            }
        }
        return results;
    }

    /**
     * Get an `Entity` of the specified type.
     * @param type A class object to search for in the list of entities.
     * @return A single entity (the first) of the specified type, or null (if none are found).
     */
    public <T extends Entity> T getEntity(Class<T> type) {
        for(Entity obj : entities) {
            if (type.isInstance(obj)) {
                return type.cast(obj);
            }
        }
        return null;
    }

    /**
     * Pause or unpause the game. This happens automatically from `setListener`.
     * @param paused When true, stop ticks. When false, enable ticks.
     */
    public void setPaused(boolean paused) {
        lastTickTime = paused ? 0 : System.currentTimeMillis();
    }

    // Based on the current system time, call the tick() methods an appropriate
    // number of times.
    private void emitTicks() {
        if (lastTickTime == 0) return; // the game is paused

        double now = System.currentTimeMillis();
        final double updateInterval = 1000f / ticksPerSecond();
        while(lastTickTime < now) {
            lastTickTime += updateInterval;
            for(Entity go : entities) go.tick();
        }
    }

    // Used by the GameCanvas to draw the current game state.
    void draw(GameView gv) {
        // Make sure the game state is up-to-date with the system time.
        emitTicks();

        // We'll iterate using first/higher, as it will allow the TreeSet
        // to be modified while iterating it.
        for(Entity go : entities) go.draw(gv);

        // If we're in continuous redraw mode, schedule the next redraw immediately.
        // Android will limit redraws to 60 times per second.
        if (continuousRedraw()) event("updated");
    }

    /**
     * Called by the GameCanvas shortly before `draw()` is called. At this point,
     * the canvas will have scaling and translations applied, but the clipping
     * (in case the virtual screen does not have the same aspect ratio as the
     * physical screen) has not been set yet. This allows one to clear the entire
     * canvas (or to draw something more interesting in the borders).
     *
     * Override this to draw something other than black bars in case the aspect ratio of the
     * virtual screen differs from the actual screen.
     *
     * @param gv The `GameView` to paint to.
     */
    void drawBeforeClip(GameView gv) {
        gv.getCanvas().drawPaint(blackPaint);
    }

    public void handleTouch(MotionEvent me) {
        // First fire any outstanding ticks, to make sure objects are in their current place before
        // using their coordinates.
        emitTicks();

        // See if any game objects are interested in handling this click immediately.
        // They may also choose to scan for ongoing touches in their tick() methods.
        Touch touch = getTouch(me);
        for(Entity go : entities) {
            go.handleTouch(touch, me);
        }
    }

    private Touch getTouch(MotionEvent me) {
        int action = me.getActionMasked();
        int actionIndex = me.getActionIndex();
        int pointerId = me.getPointerId(actionIndex);
        float x = me.getX(actionIndex);
        float y = me.getY(actionIndex);

        if (action==MotionEvent.ACTION_POINTER_DOWN) action = MotionEvent.ACTION_DOWN;
        if (action==MotionEvent.ACTION_POINTER_UP) action = MotionEvent.ACTION_UP;

        for (Touch touch : touches) {
            if (touch.pointerId == pointerId) {
                touch.setXY(x, y);
                touch.lastAction = action;
                if (action == MotionEvent.ACTION_UP) {
                    touches.remove(touch);
                }
                return touch;
            }
        }

        // New touch
        Touch touch = new Touch(pointerId, x, y, action);
        touches.add(touch);
        return touch;
    }


    public class Touch {
        public int pointerId;
        public float x, y;
        public float startX, startY;
        public float deltaY, deltaX;
        public long startTime;
        public int lastAction;

        Touch(int pointerId, float startX, float startY, int lastAction) {
            this.pointerId = pointerId;
            this.lastAction = lastAction;
            this.startTime = System.currentTimeMillis();
            this.x = this.startX = startX;
            this.y = this.startY = startY;
        }

        public long getDuration() {
            return System.currentTimeMillis()-startTime;
        }

        void setXY(float x, float y) {
            this.deltaX = x - this.x;
            this.x = x;
            this.deltaY = y - this.y;
            this.y = y;
        }
    }
}
