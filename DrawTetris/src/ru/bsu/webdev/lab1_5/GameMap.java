package ru.bsu.webdev.lab1_5;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = -6839529341001166798L;
	public static int WIDTH = 600;
	public static int HEIGHT = 500;
	public static int H = 20;
	public static int M = 20;
	public static int N = 10;
	protected int heroX = 5;
	protected int heroY = 7;

	public GameMap() {
		System.out.println("GameMap instance created...");
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int y = 0;
		for (int j = 1; j <= M; j++) { // up->down

			y = j;
			for (int i = 1; i <= N; i++) { // left->right
				int x = i;
				drawSquare(g, x, y, Color.YELLOW, Color.BLACK);
			}
		}
		drawSquare(g, heroX, heroY, Color.RED, Color.BLACK);
	}

	protected void drawSquare(Graphics g, int x, int y, Color lineColor, Color fillColor) {
		g.setColor(lineColor);
		g.fillRect(x * H, y * H, H, H);
		g.setColor(fillColor);
		g.drawRect(x * H, y * H, H, H);
	}

	public void keyTyped(KeyEvent e) {
		// char c = e.getKeyChar();
		// System.out.println("keyTyped: " + c);
	}
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		int code = e.getKeyCode();
		String s = "keyPressed: [" + c + "] (" + String.valueOf(code) + ")";
		System.out.println(s);

		switch (code) {
		case KeyEvent.VK_W: // up
			if (heroY > 1) this.heroY--;
			break;
		case KeyEvent.VK_A: // left
			if (heroX > 1) this.heroX--;
			break;
		case KeyEvent.VK_S: // down
			if (heroY < M) this.heroY++;
			break;
		case KeyEvent.VK_D: // right
			if (heroX < N) this.heroX++;
			break;
		}
		this.repaint();
	}
	public void keyReleased(KeyEvent e) {
		// char c = e.getKeyChar();
		// System.out.println("keyReleased: " + c);
	}
}

