package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Asteroid {

    private Vector center;
    private float angle;
    private Vector speed;
    float radius;
    private final List<Vector> vertices = new ArrayList<>();
    public Asteroid(Vector center, Vector speed, float angle) {
        this.center = center;
        this.angle = angle;
        this.speed = speed;
        this.radius = 25;
        generatePoints();
    }

    public Asteroid(Vector center, Vector speed, float angle, float radius) {
        this.center = center;
        this.angle = angle;
        this.speed = speed;
        this.radius = radius;
        generatePoints();
    }

    public void draw() {
        Sketch.drawingTool.fill(204, 0, 102);
        for (int i = 1; i < this.vertices.size(); i++) {
            Sketch.drawingTool.line(
                    vertices.get(i - 1).getX() + this.center.getX(),
                    vertices.get(i - 1).getY() + this.center.getY(),
                    vertices.get(i).getX() + this.center.getX(),
                    vertices.get(i).getY() + this.center.getY()
            );
        }
        Sketch.drawingTool.line(
                vertices.get(this.vertices.size() - 1).getX() + this.center.getX(),
                vertices.get(this.vertices.size() - 1).getY() + this.center.getY(),
                vertices.get(0).getX() + this.center.getX(),
                vertices.get(0).getY() + this.center.getY()
        );
    }

    public void generatePoints() {
        for (int i = 0; i < 12; i++) {
            var point = new Vector(
                    0,
                    this.radius + Sketch.getRandomDecimal(this.radius * 0.5F) - this.radius * 0.25F
            );
            Utils.rotateVector(point, new Vector(0, 0), 30F * i);
            vertices.add(point);
        }
    }

    public boolean isCollision(Spacecraft spacecraft) {
        for (Map.Entry<String, Vector> point : spacecraft.getVertices().entrySet()) {
            if (isInside(point.getValue())) return true;
        }
        return  false;
    }

    public boolean isInside(Vector point) {
        var dx = this.center.getX() - point.getX();
        var dy = this.center.getY() - point.getY();
        var result = Sketch.drawingTool.sqrt(dx*dx + dy*dy) < radius;
        return result;
    }

    public void explode(List<Asteroid> asteroids) {
        if (radius > 20) {
            for (int i = 0; i < 4; i++) {
                var center = new Vector(this.center.getX(), this.center.getY());
                var speed = new Vector(
                        this.speed.getX() + Sketch.getRandomDecimal(1F) - 0.5F,
                        this.speed.getY() + Sketch.getRandomDecimal(1F) - 0.5F
                );
                asteroids.add(new Asteroid(center, speed, 0, 10));
            }
        }
    }

    public void move() {
        Utils.move(this.center, this.speed);
        Utils.setLimits(this.center);
    }
}
