package org.example;

import java.util.HashMap;
import java.util.Map;

public class Spacecraft {

    private Vector center;
    private float angle;
    private Vector speed;

    private Map<String, Vector> vertices = new HashMap<>();

    public Spacecraft(Vector center, Vector speed, float angle) {
        this.center = center;
        this.angle = angle;
        this.speed = speed;
    }

    public Map<String, Vector> getVertices() {
        return vertices;
    }

    private void setAngle(float angle) {
        this.angle = angle;
    }

    public void draw() {
        var point1 = new Vector(this.center.getX(), this.center.getY() - 40);
        var point2 = new Vector(this.center.getX() - 15, this.center.getY() + 10);
        var point3 = new Vector(this.center.getX() + 15, this.center.getY() + 10);
        Utils.rotateVector(point1, this.center, this.angle);
        Utils.rotateVector(point2, this.center, this.angle);
        Utils.rotateVector(point3, this.center, this.angle);
        Sketch.drawingTool.line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
        Sketch.drawingTool.line(point2.getX(), point2.getY(), point3.getX(), point3.getY());
        Sketch.drawingTool.line(point3.getX(), point3.getY(), point1.getX(), point1.getY());
        vertices.put("point1", point1);
        vertices.put("point2", point2);
        vertices.put("point3", point3);
    }

    public void move() {
        Utils.move(this.center, this.speed);
        Utils.setLimits(this.center);
    }

    public void accelerate() {
        var acceleration = new Vector(0, -0.5F);
        Utils.rotateVector(acceleration, new Vector(0, 0), this.angle);
        this.speed.setX(this.speed.getX() + acceleration.getX());
        this.speed.setY(this.speed.getY() + acceleration.getY());
    }

    public void rotate(float da) {
        this.setAngle(this.angle + da);
    }

    public Rocket fire() {
        var point = this.vertices.get("point1");
        return new Rocket(new Vector(point.getX(), point.getY()), this.angle);
    }
}
