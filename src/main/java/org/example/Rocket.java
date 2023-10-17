package org.example;

import java.util.HashMap;
import java.util.Map;

public class Rocket {
    private Vector center;
    private float angle;
    private Vector speed;

    private HashMap<String, Vector> vertices = new HashMap<>();

    public Rocket(Vector center, float angle) {
        this.center = center;
        this.speed = new Vector(0, -5);
        this.angle = angle;
        Utils.rotateVector(speed, new Vector(0, 0), angle);
    }

    public void draw() {
        var point1 = new Vector(0, -5);
        var point2 = new Vector(0, 5);
        Utils.rotateVector(point1, new Vector(0, 0), this.angle);
        Utils.rotateVector(point2, new Vector(0, 0), this.angle);
        Sketch.drawingTool.line(
                point1.getX() + this.center.getX(),
                point1.getY() + this.center.getY(),
                point2.getX() + this.center.getX(),
                point2.getY() + this.center.getY()
        );
        point1.setX(point1.getX() + this.center.getX());
        point1.setY(point1.getY() + this.center.getY());
        point2.setX(point2.getX() + this.center.getX());
        point2.setY(point2.getY() + this.center.getY());
        this.vertices.put("point1", point1);
        this.vertices.put("point2", point2);
    }

    public void move() {
        Utils.move(this.center, this.speed);
    }

    public boolean isOut() {
        return  this.center.getX() > Sketch.width    ||
                this.center.getX() < 0               ||
                this.center.getY() > Sketch.height   ||
                this.center.getY() < 0;
    }

    public boolean isCollision(Asteroid asteroid) {
        for (Map.Entry<String, Vector> point : this.vertices.entrySet()) {
            if (asteroid.isInside(point.getValue())) return true;
        }
        return false;
    }
}
