package ru.bsu.webdev.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class Figure {
	public int x;
	public int y;
	protected Color lineColor = Color.BLACK;
	protected Color fillColor = Color.YELLOW;
	protected int [][] shape;

	public Figure(int x, int y) {
		this.x = x;
		this.y = y;
		
		FigureType type = FigureType.getRandomType();
		
		switch(type) {
			case I:
			{
				shape = new int [][] { {1,1,1,1} };
				break;
			}
			case O:
			{
				shape = new int [][] { {1,1}, {1,1} };
				break;
			}
			case T:
			{
				shape = new int [][] { {1,1,1}, {0,1,0} };
				break;
			}
			case S:
			{
				shape = new int [][] { {0,1,1}, {1,1,0}};
				break;
			}
			case Z:
			{
				shape = new int [][] { {1,1,0}, {0,1,1}};
				break;
			}
			case J:
			{
				shape = new int [][] { {0,1}, {0,1}, {1,1}};
				break;
			}
			case L:
			{
				shape = new int [][] { {1,0}, {1,0}, {1,1}};
				break;
			}
		}
		
	}
	
	public int getHeight() {
		return shape.length;
	}
	public int getWidth() {
		return shape[0].length;
	}

	public void setColor(Color c) {
		this.fillColor = c;
	}
	
	public void render(Graphics g) {
	    for (int i = 0; i < shape.length; i++) {
	        for (int j = 0; j < shape[i].length; j++) {
	            if (shape[i][j] == 1) {
	                int blockX = (x + j) * GameMap.H;
	                int blockY = (y + i) * GameMap.H;

	        	    g.setColor(fillColor);
	                g.fillRect(blockX, blockY, GameMap.H, GameMap.H);
	                g.setColor(lineColor);
	                g.drawRect(blockX, blockY, GameMap.H, GameMap.H);
	            }
	        }
	    }
	}

	public void moveLeft() {
		this.x--;
	}

	public void moveRight() {
		this.x++;
	}

	public void moveDown() {
		this.y++;
	}
	
	public void rotate() {
		int[][] rotatedShape = new int[shape[0].length][shape.length];

        // Поворот фигуры (по часовой стрелке)
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[j][shape.length - 1 - i] = shape[i][j];
            }
        }

        shape = rotatedShape;
    }
}
