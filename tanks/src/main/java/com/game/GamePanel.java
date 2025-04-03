package com.game;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private Tank playerTank;
    private List<Tank> otherTanks;

    public GamePanel(Tank playerTank, List<Tank> otherTanks) {
        this.playerTank = playerTank;
        this.otherTanks = otherTanks;
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Рисуем сетку
        g.setColor(new Color(50, 50, 50));
        for (int i = 0; i < getWidth(); i += 40) {
            g.drawLine(i, 0, i, getHeight());
        }
        for (int i = 0; i < getHeight(); i += 40) {
            g.drawLine(0, i, getWidth(), i);
        }

        // Рисуем танки
        playerTank.draw(g);
        for (Tank tank : otherTanks) {
            tank.draw(g);
        }
    }
} 