## Rendering library

To allow students to get games up-and-running quickly, and to provide solutions for some aspects that are especially unintuitive when starting out with Android game programming, a small library and some example uses have been provided. They can be used as a base for your work. You're very much encouraged to make changes to the library to fit your needs! Or at least, make sure you have a basic understanding of what is happening.  

This document describes how to use this library.


### About this document

This document is written in Markdown using PlantUML for diagrams. To view and edit it from within Android Studio, install the 'Markdown Navigator' plugin.


### Features

The provided library and examples demonstrate how to:

- Draw scaled, rotated and semi-transparent images.
- Animate fluently at 60 frames per second.
- Maintain game speed when the hardware is not capable of achieving the full frame rate. 
- Render on any screen resolution, without much effort.
- Sensibly organize your game logic into independent Entity instances.
- Handle touch events.


### Overview

The state of a game is represented by a `GameModel` object, that contains a list of objects extending `Entity`. Each `Entity` represents a visible entity or actor in the game. It can handle events like a time tick passing or user input. Also, it can output a visual representation of itself to a `DrawView`

A `DrawView` is an Android custom view. It collaborates with `GameModel` (and its `Entity`s) to render the graphical representation of the game state. `DrawView`s offer methods for easily drawing scaled, rotated and semi-transparent bitmaps, and makes it easy to work in a resolution independent way.

It's also possible to manually reflect the `GameModel` state through regular Android Views (such as `TextView`s and `CheckBox`es). If you're planning to do this exclusively (and thus not use the `DrawView`) this library probably won't be of much use.


### Class diagram

This UML class diagram shows the use of the base library with a simple example game. Many details have been intentionally left out.

```plantuml

skinparam class {
    BackgroundColor<< lib >> white
}

class MyGameModel {
    - score: int
    + incrScore(int amount)
}

class MainActivity {
    + onGameEvent(String)
}
note left of MainActivity
    Implements GameModel.Listener
    to receive events.
end note

class Hero {
    + draw(DrawView)
    + tick()
    + handleTouch(Touch)
}

class Bullet {
    + draw(DrawView)
    + tick()
}

class Entity<<lib>> {
    + draw(DrawView)
    + tick()
    + handleTouch(Touch)
    + getLayer(): int
}

class GameModel<<lib>> {
    + virtualWidth: float
    + virtualHeight: float
    
    + event(String)
    + setListener(Listener)
    + setPaused(boolean)
    
    + addEntityEntity)
    + removeEntity(Entity)
    + getEntities(X.class): List<X>
}

class DrawView<<lib>> {
    + show(GameModel)
    + drawBitmap(...)
    + getCanvas(): Canvas
}


GameModel o-> "*" Entity
MainActivity "listener" <-- GameModel

MyGameModel -|> GameModel
MyGameModel *-- Hero
note on link
    Hero references back to MyGameModel to
    call incrScore() and to add Bullet entities.
end note


Hero -> "*" Bullet

Entity <|-- Hero
Entity <|-- Bullet

MainActivity --> MyGameModel
MainActivity -> DrawView


class android.View {
    + onDraw()
}
class android.Activity {
    + onCreate()
    + onResume()
    + onPause()
}
android.Activity <|-- MainActivity 
android.View <|-- DrawView

```


### Class reference

#### Entity

Although Model View Control (MVC) is often a good idea, it's use is not really convenient for games. In this library, the central concept is the `Entity`, which you can think of as a thing that is visible in the game, or changing the state of the game. Examples: the hero, a cloud, a bullet, an enemy cruiser, and the planet shattering megaboss. But HUD elements (like your health, inventory or buttons) and non-visible elements (like a victory checker, a monster spawner or a collision checker) may also be game objects.

You can create a new kind of game object by extending from `Entity`. `Entity`s need to be added to the `GameModel` by calling `GameMode.addEntity(entity)`. Similarly, entities can be removed using `GameModel.removeEntity(entity)`.

There are four methods on `Entity` that you may want to override:
- `draw(DrawView)`. Called each time the screen is refreshed (usually up to 60 times per second). If the entity wants to display something, it will need to do that here onto the given DrawView (discussed later).
- `tick()`. Called on every game object 180 times per second (configurable by overriding `GameLoop.ticksPerSecond()`). This method is to update the game state one step. So a bullet may want to move forward and check if it is hitting something. The reason the tick rate is (by default) higher than the maximum frame rate, is that this makes things like collision detection more accurate. Also, when the full frame rate cannot be met, smaller tick steps make for a more fluent experience.
- `getLayer()`. Returns the layer this entity is on. The draw and tick methods are usually called in the order the entities were created. In case this is not desirable, you can put game objects on a different layer. Let's say you want to create a new cloud, but that cloud should be drawn underneath (thus before) the hero, you can give clouds a lower layer number. The default layer number is 0, so the clouds could be put on layer -1.
- `handleTouch(Game.Touch, MotionEvent)`. Used to act on touch events such as `ACTION_UP`, `ACTION_DOWN` or `ACTION_MOVE`. The `Touch` object provides coordinates (translated to the virtual screen), movement, time, etc.


#### GameModel

The `GameModel` class is meant to be subclassed by your own class that contains specifics about your game.

A `GameModel` object represents the state of a single game, by way of a list of `Entity`s.

When creating a `GameModel`, you need to specify the width and height of the virtual screen. Any `DrawView` displaying this game, will have to scale proportionally to make this fit exactly on the actual device. To prevent black bars on the sides or on the top and bottom, you should make sure the virtual screen size you specify matches the aspect ratio (the width/height ratio) of the actual screen.

The `GameModel` can have an associated `GameModel.Listener`, to which it can send 'events' that consist of just a string indicating what kind of change was made to the model. The `Listener`, which may well just be an `Activity`, is responsible for updating the appropriate views when this happens.

In the simplest case, there is just one View, which is a `DrawView`. In this case the `Listener.onGameEvent()` should just a `DrawView.show(gameModel)` to display the changed model. In more complicated cases, your `GameModel` or `Entities` may fire more specific events (using `GameModel.event(String)`, causing specific `View`s to be updated. For instance a `"score"` event can be sent when the `scoreTextView` should be updated. Events may also be used to trigger other behaviour, such as sound effects or vibrations. It is up to the Listener to implement these behaviours (or to delegate to objects implementing these behaviours).

`GameModel`s are `serializable`, meaning the entire game state can easily be converted to and from a `String`. This can be used in the Android Activity to, for instance, resume a game after it has been moved to the background and stopped by the operating system. 

Methods on `Game` you may want to call:

- `setListener(listener)` sets a (new) listener and unpauses the game. The listener will immediately receive a `"new"` event, which is may want to use to update any state to the initial value.
- `setPaused(boolean)` will disable (true) or enable(false) the tick() events. You can use this to freeze the game state while in the settings menu, for instance.
- addEntity(Entity) will add an `Entity` to the game.
- removeEntity(Entity) will remove an `Entity` from the game.
- `getEntities(Class)` returns a list of `Entity`s that are of type `Class`. For instance:
  ```java
  List<Car> cars = Game.getObjects(Car.class);
  ````
- `event(String name)` will send an event with this name to `Listener.onGameEvent(name)`.

Methods you may want to override:

- `ticksPerSecond()`, which returns the number of `tick()` call that are performed per second. If your game doesn't use ticks (for instance when it only changes immediately based on touch events), you can set this to 0. It defaults to 180.
- `continousRedraw()`, which returns a boolean indicating if you want `event("updated")` to fire continuously, causing 60 DrawView updates per second. Defaults to `true`. If your game doesn't use fluent animation but only updates immediately based on touch events, you should set this to `false` and manually call `event("updated")` to save device battery. 


#### DrawView

This Android custom View uses the canvas to display graphics. This is done by passing it a reference to this class to the `draw(DrawView)` method of all `Entity`s in the `GameModel`. 

From there the entity can...
- Call `DrawView.getCanvas()` to get access to the Android Canvas object, on which the usual methods like `drawRect(..)` are available.
- Use the helper method `DrawView.drawBitmap(...)`, to easily copy a `Bitmap` image to a specific position on the canvas, while optionally rotating and scaling it, and adding translucency. A `Bitmap` can be created from an Android resource using the `DrawView.getBitmapFromResource(int resourceId)` helper method.

In aby cases, the `DrawView` will arrange for the `Canvas` to be in such a state that it is `GameModel.virtualWidth` wide and `GameModel.virtualHeight` high.

