package com.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TankGame extends JFrame {
    private GamePanel gamePanel;
    private Tank playerTank;
    private List<Tank> otherTanks;
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;
    private static final int PORT = 5000;
    private Timer gameTimer;

    public TankGame() {
        setTitle("Танчики");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        otherTanks = new ArrayList<>();
        clients = new ArrayList<>();
        
        playerTank = new Tank(400, 300); // Начальная позиция игрока
        gamePanel = new GamePanel(playerTank, otherTanks);
        
        add(gamePanel);
        addKeyListener(new GameKeyListener());
        setFocusable(true);
        
        startServer();
        startGameLoop();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Сервер запущен на порту " + PORT);
                
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket);
                    clients.add(clientHandler);
                    new Thread(clientHandler).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startGameLoop() {
        gameTimer = new Timer(16, e -> {
            playerTank.updateBullets();
            for (Tank tank : otherTanks) {
                tank.updateBullets();
            }
            gamePanel.repaint();
        });
        gameTimer.start();
    }

    private class GameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    playerTank.moveUp();
                    break;
                case KeyEvent.VK_S:
                    playerTank.moveDown();
                    break;
                case KeyEvent.VK_A:
                    playerTank.moveLeft();
                    break;
                case KeyEvent.VK_D:
                    playerTank.moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    playerTank.shoot();
                    break;
            }
            gamePanel.repaint();
            broadcastGameState();
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private void broadcastGameState() {
        String gameState = playerTank.getX() + "," + playerTank.getY() + "," + playerTank.getDirection();
        for (ClientHandler client : clients) {
            client.sendMessage(gameState);
        }
    }

    public void updateOtherTank(int x, int y, int direction) {
        // Создаем или обновляем позицию другого танка
        Tank otherTank = new Tank(x, y);
        otherTank.setDirection(direction);
        if (!otherTanks.contains(otherTank)) {
            otherTanks.add(otherTank);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TankGame().setVisible(true);
        });
    }
} 