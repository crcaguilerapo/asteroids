package org.example;

import processing.core.PApplet;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Sketch extends PApplet
{
    static PApplet drawingTool;
    Spacecraft spacecraft;
    ArrayList<Asteroid> asteroids = new ArrayList<>();

    ArrayList<Rocket> rockets = new ArrayList<>();

    static int height = 480;
    static int width = 640;

    public static float getRandomDecimal(float b) {
        Random rand = new Random();
        return rand.nextFloat() * b;
    }

    void showStatisticsText() {
        fill(0, 0, 0);
        textSize(15);
        textAlign(LEFT, TOP);
        text("| Asteroids: " + this.asteroids.size() + " | Rockets: " + this.rockets.size(), 10, 10);
    }

    void showGameOverText() {
        fill(0, 0, 0);
        textSize(24);
        textAlign(CENTER, CENTER);
        text("GAME OVER", this.width / 2, this.height / 2);
    }

    void showWinText() {
        fill(0, 0, 0);
        textSize(24);
        textAlign(CENTER, CENTER);
        text("YOU WIN", this.width / 2, this.height / 2);
    }

    void generateAsteroids() {
        for (int i = 0; i < 10; i++) {
            var x = getRandomDecimal(this.width);
            while (x > this.width * 0.3 && x <  this.width * 0.7) {
                x = getRandomDecimal(this.width);
            }
            var y = getRandomDecimal(this.height);
            while (y > this.height * 0.3 && y <  this.height * 0.7) {
                y = getRandomDecimal(this.height);
            }
            this.asteroids.add(
                    new Asteroid(
                            new Vector(x, y),
                            new Vector(getRandomDecimal(1) - 0.5F, getRandomDecimal(0.5F) - 0.5F),
                            0
                    )
            );
        }
    }

    void createSpacecraft() {
        this.spacecraft = new Spacecraft(
                new Vector(this.width / 2, this.height / 2),
                new Vector(0, 0),
                0
        );
    }

    void setupAsteroids() {
        for (Asteroid asteroid : this.asteroids) {
            asteroid.move();
            asteroid.draw();
        }

        for (Asteroid asteroid : this.asteroids) {
            if (asteroid.isCollision(this.spacecraft)) {
                noLoop();
                showGameOverText();
            }
        }
    }

    void setupRockets() {
        for (Rocket rocket : this.rockets) {
            rocket.move();
            rocket.draw();
        }

        for (Rocket rocket : this.rockets) {
            if (rocket.isOut()) {
                this.rockets = (ArrayList<Rocket>) this.rockets.clone();
                this.rockets.remove(rocket);
            }
        }

        for (Rocket rocket : this.rockets) {
            for (Asteroid asteroid : this.asteroids) {
                if (rocket.isCollision(asteroid)) {
                    this.rockets = (ArrayList<Rocket>) this.rockets.clone();
                    this.rockets.remove(rocket);
                    this.asteroids = (ArrayList<Asteroid>) this.asteroids.clone();
                    this.asteroids.remove(asteroid);
                    asteroid.explode(this.asteroids);
                    break;
                }
            }
        }
    }

    void setupSpacecraft() {
        this.spacecraft.move();
        this.spacecraft.draw();
    }

    void clearSpace() {
        this.asteroids.clear();
        this.rockets.clear();
    }

    @Override
    public void settings() {
        size(this.width, this.height);
    }

    @Override
    public void setup() {
        drawingTool = this;
        clearSpace();
        createSpacecraft();
        generateAsteroids();
    }

    @Override
    public void draw() {
        background(255);
        setupAsteroids();
        setupRockets();
        setupSpacecraft();
        showStatisticsText();
        if (this.asteroids.isEmpty()) {
            showWinText();
            noLoop();
        }
    }

    @Override
    public void keyPressed() {
        if (keyCode == KeyEvent.VK_RIGHT) {
            this.spacecraft.rotate(-10);
        } else if (keyCode == KeyEvent.VK_LEFT) {
            this.spacecraft.rotate(10);
        } else if (keyCode == KeyEvent.VK_UP) {
            this.spacecraft.accelerate();
        } else if (keyCode == KeyEvent.VK_ENTER) {
            this.setup();
            loop();
        } else if (keyCode == KeyEvent.VK_SPACE) {
            this.rockets.add(this.spacecraft.fire());
        }
    }

    public static void main(String[] args )
    {
        PApplet.main("org.example.Sketch");
    }
}
