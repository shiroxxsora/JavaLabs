package ru.bsu.webdev.tetris;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class GameMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = -6839529341001166798L;
	public static int WIDTH = 600;
	public static int HEIGHT = 500;
	public static int H = 20;
	public static int M = 20;
	public static int N = 10;
	public static int [][] map = new int[N][M];
	protected Figure figure;
	protected Timer timer;
	private long lastUpdateTime; 
    private long fallDelay = 500;
	protected HashMap<Integer, Boolean> pressedKeys;

	public GameMap() {
		System.out.println("GameMap instance created...");
		this.figure = new Figure(N/2, 1);

		this.pressedKeys = new HashMap<Integer, Boolean>();
		this.pressedKeys.put(KeyEvent.VK_W, false);
		this.pressedKeys.put(KeyEvent.VK_A, false);
		this.pressedKeys.put(KeyEvent.VK_S, false);
		this.pressedKeys.put(KeyEvent.VK_D, false);

		timer = new Timer(1000 / 60, e -> {
            update();
            repaint();
        });
        timer.start();
	}

	protected void update() {
		long currentTime = System.currentTimeMillis();

        // Если прошло достаточно времени для падения фигуры, двигаем ее вниз
        if (currentTime - lastUpdateTime >= fallDelay) {
        	if(this.checkDownCollision())
        		figure.moveDown();
        	else{
        		fixFigure();
        	}
            lastUpdateTime = currentTime;  // Обновляем время последнего падения
        }
	}
	
	protected void fixFigure() {
		for (int i = 0; i < this.figure.getWidth(); i++) {
	        for (int j = 0; j < this.figure.getHeight(); j++) {
	            if (this.figure.shape[j][i] == 1) {
	            	map[this.figure.x+i-1][this.figure.y+j-1] = 1;
	            }
	        }
		}
		this.figure = new Figure(N/2, 1);
		
		
		for(int i = 0; i < M; i++) {

			boolean isLineFull = true;
			for(int j = 0; j < N; j++) {
				if(map[j][i] != 1) {
					isLineFull = false;
					break;
				}
			}
			if(isLineFull) {
				for(int j = 0; j < N; j++)
					map[j][i] = 0;
				
				for(int j = i; j > 1; j--) {
					for(int k = 0; k < N; k++)
						map[k][j] = map[k][j-1];
				}
			}
		}
		
	}
	
	protected boolean checkDownCollision() {
		if(this.figure.y + this.figure.getHeight()-1 >= M)
			return false;
		
		for (int i = 0; i < this.figure.getWidth(); i++) {
	        for (int j = 0; j < this.figure.getHeight(); j++) {
	            if (this.figure.shape[j][i] == 1 && map[this.figure.x+i-1][this.figure.y+j] == 1) {
	            	return false;
	            }
	        }
		}
		
		return true;
	}
	protected boolean checkRightCollision() {
		for (int i = 0; i < this.figure.getWidth(); i++) {
	        for (int j = 0; j < this.figure.getHeight(); j++) {
	        	if(this.figure.x+i < N && this.figure.y+j < M)
		            if (this.figure.shape[j][i] == 1 && map[this.figure.x+i][this.figure.y+j-1] == 1) {
		            	return false;
		            }
	        }
		}
		
		return true;
	}
	protected boolean checkLeftCollision() {
		for (int i = 0; i < this.figure.getWidth(); i++) {
	        for (int j = 0; j < this.figure.getHeight(); j++) {
	        	if(this.figure.x+i-1 > 0 && this.figure.y+j < M)
		            if (this.figure.shape[j][i] == 1 && map[this.figure.x+i-2][this.figure.y+j-1] == 1) {
		            	return false;
		            }
	        }
		}
		
		return true;
	}
	
	// НЕ РАБОТАЕТ
	protected boolean checkRotateCollision() {
		for (int i = 0; i < this.figure.getWidth(); i++) {
	        for (int j = 0; j < this.figure.getHeight(); j++) {
	        	if(this.figure.x+j > N && this.figure.y+i < M)
		            if (this.figure.shape[j][i] == 1 && map[this.figure.x+i][this.figure.y+j-1] == 1) {
		            	return false;
		            }
	        }
		}
		
		return true;
	}
	
	
	protected void GameOver() {
		System.out.println("GameOver ... or not?");
		
	}
	
	protected void render(Graphics g) {
		for (int i = 0; i < map.length; i++) { 
			for (int j = 0; j < map[i].length; j++) {
				 if(map[i][j] == 1) 
					 drawSquare(g, i+1, j+1, Color.RED, Color.BLACK); 
				 else
					drawSquare(g, i+1, j+1, Color.GRAY, Color.BLACK);
			}
		}
		this.figure.render(g);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.render(g);
	}

	protected void drawSquare(Graphics g, int x, int y, Color lineColor, Color fillColor) {
		g.setColor(lineColor);
		g.fillRect(x * H, y * H, H, H);
		g.setColor(fillColor);
		g.drawRect(x * H, y * H, H, H);
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		System.out.println("keyCode: " + code);

		switch (code) {
			case KeyEvent.VK_W:
				this.pressedKeys.put(KeyEvent.VK_W, true);
				
				if(this.figure.x + this.figure.getHeight()-2 < N && this.figure.y + this.figure.getWidth()-2 < M)
					if(this.checkRotateCollision())
						this.figure.rotate();
				break;
				
			case KeyEvent.VK_A:
				this.pressedKeys.put(KeyEvent.VK_A, true);
				
				if(this.figure.x - 1 > 0)
					if(this.checkLeftCollision())
						this.figure.moveLeft();
				break;
				
			case KeyEvent.VK_S:
				this.pressedKeys.put(KeyEvent.VK_S, true);
				
				if(this.checkDownCollision())
	        		figure.moveDown();
	        	else{
	        		fixFigure();
	        	}
				break;
				
			case KeyEvent.VK_D:
				this.pressedKeys.put(KeyEvent.VK_D, true);
				
				if(this.figure.x + this.figure.getWidth() <= N)
					if(this.checkRightCollision())
						this.figure.moveRight();
				break;
				
			case KeyEvent.VK_BACK_SPACE:
				break;
		}
		
		System.out.println(pressedKeys);
	}
	
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		switch (code) {
			case KeyEvent.VK_W:
				this.pressedKeys.put(KeyEvent.VK_W, false);
				break;
				
			case KeyEvent.VK_A:
				this.pressedKeys.put(KeyEvent.VK_A, false);
				break;
				
			case KeyEvent.VK_S:
				this.pressedKeys.put(KeyEvent.VK_S, false);
				break;
				
			case KeyEvent.VK_D:
				this.pressedKeys.put(KeyEvent.VK_D, false);
				break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}
