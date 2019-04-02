package nl.saxion.playground.template.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class DrawView extends View implements View.OnTouchListener {

    // While in an `onDraw` method, this references the `Canvas` to be painted to.
    private Canvas canvas;

    // The `GameModel` we're currently showing and relaying touch events to.
    private GameModel gameModel;

    // The `Matrix` representing scale and translate operations to apply on the canvas
    // in order to fit the virtual screen on the actual screen.
    private Matrix viewMatrix;

    // The clipping rectangle, such that drawing outside of the virtual screen is not visible.
    // Set to null in case clipping is not required, when the virtual screen fills the actual
    // screen.
    private RectF clipRect;

    // The inverse of `viewMatrix`, allowing us to map touch event coordinates to virtual
    // screen coordinates.
    private Matrix touchMatrix;

    // These are used to log the FPS counter every 3s.
    private int frameCount;
    private double lastFpsLogTime;

    // Used by `drawBitmap` to paint translucent images.
    private Paint alphaPaint = new Paint();


    public DrawView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    /**
     * Trigger drawing the given `gameModel` in this View. The actual drawing will happen
     * asynchronously, when Android decides to call our `onDraw` method.
     * This also cause future touch events on this DrawView to be dispatched to `gameModel`.
     * @param gameModel The `GameModel` to show.
     */
    public void show(GameModel gameModel) {
        if (this.gameModel != gameModel) {
            this.gameModel = gameModel;
            viewMatrix = null; // needs to be recalculated
        }
        invalidate();
    }

    /**
     * Obtain a reference to the Canvas, to manipulate it without the use of `drawBitmap`.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    public void drawBitmap(Bitmap bitmap, float left, float top) {
        drawBitmap(bitmap, left, top, -1, -1, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width) {
        drawBitmap(bitmap, left, top, width, -1, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height) {
        drawBitmap(bitmap, left, top, width, height, 0, 255);
    }

    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height, float angle) {
        drawBitmap(bitmap, left, top, width, height, angle, 255);
    }

    /**
     * Draw a bitmap to the GameCanvas canvas.
     * @param left Distance from the left of the canvas in virtual pixels.
     * @param top Distance from the top of the canvas in virtual pixels.
     * @param width Width in virtual pixels. When -1, the width is derived from the height, or from the natural size of the bitmap.
     * @param height Height in virtual pixels. When -1, the height is derived from the width.
     * @param angle Angle in degrees (0-360).
     * @param alpha Opacity to draw with (0 is fully transparent, 255 is fully visible).
     */
    public void drawBitmap(Bitmap bitmap, float left, float top, float width, float height, float angle, int alpha) {

        if (width < 0) width = bitmap.getWidth() * (height < 0 ? 1 : (height / bitmap.getHeight()));
        if (height < 0) height = bitmap.getHeight() * (width / bitmap.getWidth());

        canvas.save();

        if (angle!=0) {
            canvas.rotate(angle, left+width/2, top+height/2);
        }

        canvas.translate(left, top);

        canvas.scale(width / bitmap.getWidth(), height / bitmap.getHeight());

        if (alpha<255) {
            alphaPaint.setAlpha(alpha);
        }
        canvas.drawBitmap(bitmap, 0, 0, alpha<255 ? alphaPaint : null);

        canvas.restore();
    }


    /*
     * Load/decode an image. This is pretty slow.
     * For instance: `Bitmap b = gameModel.getBitmapFromResource(R.drawable.my_image);`
     */
    public Bitmap getBitmapFromResource(int resourceId) {
        return BitmapFactory.decodeResource(getContext().getResources(), resourceId);
    }


    // Called when the actual screen dimensions change. For instance on orientation change.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewMatrix = null;
    }

    // Calculates how to scale the virtual viewport (as defined in Platformer), such that it
    // fits nicely in the center of the actual screen.
    private void calculateMatrices() {
        viewMatrix = new Matrix();
        clipRect = null;

        float actualWidth = getWidth();
        float actualHeight = getHeight();

        float scale = Math.max(gameModel.virtualWidth / actualWidth, gameModel.virtualHeight / actualHeight);
        if (scale < 0.99 || scale > 1.01) {
            viewMatrix.postScale(1f/scale,1f/scale);

            float extraW = actualWidth*scale - gameModel.virtualWidth;
            float extraH = actualHeight*scale - gameModel.virtualHeight;

            if (extraW > 1f || extraH > 1f) {
                viewMatrix.postTranslate(extraW/scale / 2f, extraH/scale / 2f);
                clipRect = new RectF(0, 0, gameModel.virtualWidth, gameModel.virtualHeight);
            }
        }

        // Invert the view matrix, to obtain a matrix that can be used for translating
        // touch events back to virtual coordinates.
        touchMatrix = new Matrix(viewMatrix);
        touchMatrix.invert(touchMatrix);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (gameModel == null) return false;
        // Translate actual coordinates to virtual coordinates:
        event.transform(touchMatrix);
        gameModel.handleTouch(event);
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (gameModel == null) return;

        // The first draw (after orientation changes) we need to calculate the viewMatrix and clipRect.
        if (viewMatrix == null) calculateMatrices();

        // Allow draw methods to access our canvas
        this.canvas = canvas;

        // Based on these calculations, we can configure the canvas.
        canvas.setMatrix(viewMatrix);
        gameModel.drawBeforeClip(this);
        if (clipRect != null) canvas.clipRect(clipRect);

        // Draw a frame!
        gameModel.draw(this);

        // After this, nobody should be drawing to the canvas anymore.
        this.canvas = null;

        // Log FPS counter and hardware acceleration status.
        frameCount++;
        double now = System.currentTimeMillis();
        if (lastFpsLogTime==0d) {
            lastFpsLogTime = now;
            Log.i("GameCanvas", "hardware acceleration: "+(canvas.isHardwareAccelerated() ? "enabled" : "*DISABLED*"));
        }
        double delta = (now-lastFpsLogTime) / 1000d;
        if (delta >= 3) {
            Log.i("GameCanvas", String.format("%.1f fps", frameCount / delta));
            frameCount = 0;
            lastFpsLogTime = now;
        }
    }
}
