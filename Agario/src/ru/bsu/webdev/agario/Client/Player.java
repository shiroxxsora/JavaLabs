package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;


public class Player implements Serializable{
	private static final long serialVersionUID = -4244531400163884594L;

	private Color fillColor = ColorEnum.getRandomColor();
	
	public Vector2 position;
	private Vector2 direction;
	private double speed = 10;
	
	public int size = 20;
	
	
	public Player() {
	}
	public Player(String str) {
	}
	
	
	public void update(double deltaTime) {
		move(deltaTime);
		debug();
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
        g.fillOval((int)position.x, (int)position.y, size, size);
        //g.drawOval(position.x, size, size, size);
	      
	}
	
	private void move(double deltaTime) {
		double correctedSpeed = speed * deltaTime * 10;
		Vector2 targetPosition = position.add(direction.mul(correctedSpeed));
		position = targetPosition;
	}
	
	private void debug() {
		System.out.println("pos " + position.x + " " + position.y);
		System.out.println("dir " + direction.x + " " + direction.y);
	}
	
	public void randomPosition() {
		Random rand = new Random();
		position = new Vector2(rand.nextInt(GameMap.WIDTH), rand.nextInt(GameMap.HEIGHT));
		
		do
			direction = new Vector2(rand.nextInt(-1, 2), rand.nextInt(-1, 2)).clampMagnitude(1f);
		while(direction.equals(Vector2.zero));
	}
	
	@Override
	public String toString() {
		return "Player{position='" + position + "', direction=" + direction + ", speed=" + speed + ", size=" + size + "}";
	}
	
}
