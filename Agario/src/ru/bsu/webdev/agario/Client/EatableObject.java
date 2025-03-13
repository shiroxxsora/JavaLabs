package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;

public class EatableObject implements Serializable{
	private static final long serialVersionUID = 2250095057886616992L;

	private Vector2 position;
	
	private static Random rand = new Random();
	
	public EatableObject() {
		position = new Vector2(rand.nextInt(20, GameMap.WIDTH-300), rand.nextInt(20, GameMap.HEIGHT-80));
	}
	
	public String toString() {
		return "EatableObject{position =" + position + "}";
	}

	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.drawOval((int)position.x, (int)position.y, 2, 2);
	}
}
