package com.game;

import java.awt.*;

public class Bullet {
    private int x, y;
    private int direction;
    private static final int SPEED = 10;
    private static final int SIZE = 8;
    private boolean active;

    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.active = true;
    }

    public void move() {
        switch (direction) {
            case 0: // вверх
                y -= SPEED;
                break;
            case 1: // вправо
                x += SPEED;
                break;
            case 2: // вниз
                y += SPEED;
                break;
            case 3: // влево
                x -= SPEED;
                break;
        }

        // Деактивируем пулю, если она вышла за пределы экрана
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            active = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return new Rectangle(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
    }
} 