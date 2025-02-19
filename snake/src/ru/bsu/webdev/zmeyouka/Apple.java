package ru.bsu.webdev.zmeyouka;

import java.awt.Color;
import java.awt.Graphics;

public class Apple {
	public int x;
	public int y;
	protected Color lineColor = Color.BLACK;
	protected Color fillColor = Color.RED;
	
	public Apple(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(Graphics g) {
		g.setColor(fillColor);
		g.fillRect(x * GameMap.H, y * GameMap.H, GameMap.H, GameMap.H);
		g.setColor(lineColor);
		g.drawRect(x * GameMap.H, y * GameMap.H, GameMap.H, GameMap.H);
	}
}
