package com.epiklp.game.Functional;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.characters.FlameDemon;
import com.epiklp.game.actors.characters.Hero;


public class MapBuilder {
    public static Hero parseHeroFromObjectLayer(MapObjects objects){
        MapObject hero = objects.get("hero");
        if (hero instanceof RectangleMapObject) {
            RectangleMapObject rect = ((RectangleMapObject) hero);
            return new Hero(rect.getRectangle().x* Cave.SCALE, rect.getRectangle().y* Cave.SCALE);
        }
        return new Hero(0, 0);
    }

    public static Array<Enemy> parseEnemiesFromObjectLayer(MapObjects objects){
        Array<Enemy> enemies = new Array<Enemy>();
        for(MapObject object : objects){
            if(object instanceof RectangleMapObject){
                RectangleMapObject en = ((RectangleMapObject) object);
                if(Utils.equalsWithNulls(object.getName(), "FlameDemon")){
                    enemies.add(new FlameDemon(en.getRectangle().x* Cave.SCALE, en.getRectangle().y* Cave.SCALE));
                }
            }

        }
        return enemies;
    }
    public static Array<Body> parseTiledObjectLayer(World world, MapObjects objects) {
        Array<Body> bodies = new Array<Body>();
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyLine((PolylineMapObject) object);
            } else if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject) object);
                if(Utils.equalsWithNulls(object.getName(), "CLIMBING_WALL")){
                    bodies.add(TheBox.createStaticBodyForMapBuild(shape, "CLIMBING_WALL"));
                    shape.dispose();
                    continue;
                }
            } else {
                continue;
            }

            bodies.add(TheBox.createStaticBodyForMapBuild(shape, MapBuilder.class.getSimpleName()));
            shape.dispose();
        }
        return bodies;
    }


    private static ChainShape createPolyLine(PolylineMapObject polyline) {
        float[] verticle = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVerticle = new Vector2[verticle.length / 2];
        for (int i = 0; i < verticle.length / 2; ++i) {
            worldVerticle[i] = new Vector2();
            worldVerticle[i].x = verticle[i * 2] / Cave.PPM * Cave.SCALE;
            worldVerticle[i].y = verticle[i * 2 + 1] / Cave.PPM * Cave.SCALE;
        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVerticle);
        return cs;
    }

    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / Cave.PPM * Cave.SCALE,
                (rectangle.y + rectangle.height * 0.5f) / Cave.PPM * Cave.SCALE);
        polygon.setAsBox(rectangle.width * 0.5f / Cave.PPM * Cave.SCALE,
                rectangle.height * 0.5f / Cave.PPM * Cave.SCALE,
                size,
                0.0f);
        return polygon;
    }
}
