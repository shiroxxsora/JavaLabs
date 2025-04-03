package com.game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tank {
    private int x, y;
    private int direction; // 0 - вверх, 1 - вправо, 2 - вниз, 3 - влево
    private static final int SPEED = 5;
    private static final int SIZE = 40;
    private List<Bullet> bullets;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        this.direction = 0;
        this.bullets = new ArrayList<>();
    }

    public void moveUp() {
        y -= SPEED;
        direction = 0;
    }

    public void moveDown() {
        y += SPEED;
        direction = 2;
    }

    public void moveLeft() {
        x -= SPEED;
        direction = 3;
    }

    public void moveRight() {
        x += SPEED;
        direction = 1;
    }

    public void shoot() {
        bullets.add(new Bullet(x + SIZE/2, y + SIZE/2, direction));
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, SIZE, SIZE);
        
        // Рисуем пушку
        g.setColor(Color.DARK_GRAY);
        switch (direction) {
            case 0: // вверх
                g.fillRect(x + SIZE/2 - 2, y - 10, 4, 20);
                break;
            case 1: // вправо
                g.fillRect(x + SIZE - 10, y + SIZE/2 - 2, 20, 4);
                break;
            case 2: // вниз
                g.fillRect(x + SIZE/2 - 2, y + SIZE - 10, 4, 20);
                break;
            case 3: // влево
                g.fillRect(x - 10, y + SIZE/2 - 2, 20, 4);
                break;
        }

        // Рисуем пули
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
    }

    public void updateBullets() {
        bullets.removeIf(bullet -> !bullet.isActive());
        bullets.forEach(Bullet::move);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getDirection() { return direction; }

    public void setDirection(int direction) {
        this.direction = direction;
    }
} 