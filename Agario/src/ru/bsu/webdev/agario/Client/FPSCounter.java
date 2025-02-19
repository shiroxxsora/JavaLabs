package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;

public class FPSCounter {
	private Color fillColor = Color.black;
	private int x;
	private int y;
	private double fps;
	
	public FPSCounter(int x, int y) {
		this.x = x; 
		this.y = y;
	}
	
	public void update(double deltaTime) {
		fps = 1/deltaTime;
	}
	
	public void render(Graphics g) {
		char[] fpsChars = ("FPS " + String.format("%.2f", fps)).toCharArray();
		g.setColor(fillColor);
		g.drawChars(fpsChars, 0, fpsChars.length, x, y);
	}
}
