package org.example;

public class Utils {
    static public void move(Vector center, Vector speed) {
        center.setX(center.getX() + speed.getX());
        center.setY(center.getY() + speed.getY());

    }

    static public void setLimits(Vector center) {
        if (center.getX() > Sketch.width) {
            center.setX(center.getX() - Sketch.width);
        } else if (center.getX() < 0) {
            center.setX(center.getX() + Sketch.width);
        }

        if (center.getY() > Sketch.height) {
            center.setY(center.getY() - Sketch.height);
        } else if (center.getY() < 0) {
            center.setY(center.getY() + Sketch.height);
        }
    }

    static public void rotateVector(Vector point, Vector center, float da) {
        float dx = point.getX() - center.getX();
        float dy = point.getY() - center.getY();
        float r = Sketch.drawingTool.sqrt(dx*dx + dy*dy);
        float a = Sketch.drawingTool.atan2(dy, dx);
        a -= da / 180 * Sketch.drawingTool.PI;
        point.setX(center.getX() + r * Sketch.drawingTool.cos(a));
        point.setY(center.getY() + r * Sketch.drawingTool.sin(a));
    }
}
