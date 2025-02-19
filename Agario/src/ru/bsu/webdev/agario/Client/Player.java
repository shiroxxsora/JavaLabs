package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Random;


public class Player implements Serializable{
	private static final long serialVersionUID = -4244531400163884594L;

	private Color fillColor = Color.blue;
			// ColorEnum.getRandomColor();
	
	public Vector2 position;
	private Vector2 direction;
	
	private double speed = 5;
	public int size = 20;
	public boolean isCurrentPlayer = false;
	
	
	public Player() {
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
		if(isCurrentPlayer)
			if(GameMap.instance.getMousePosition() != null) {
				Vector2 mousePosition = new Vector2(GameMap.instance.getMousePosition());
				
				
				Vector2 targetDirection = mousePosition.subtract(position).clampMagnitude(1f);
				System.out.println(targetDirection.magnitude());
				
				if(targetDirection.magnitude() < 0.99)
					targetDirection = targetDirection.mul(targetDirection.abs());
				
				direction = targetDirection;
			}
		
		if(position.x + size/2 + 290 > GameMap.WIDTH && direction.x > 0)
			direction.x = 0;
		if(position.y + size/2 + 70 > GameMap.HEIGHT && direction.y > 0)
			direction.y = 0;
		if(position.x - size/2 - 10 < 0 && direction.x < 0)
			direction.x = 0;
		if(position.y - size/2 - 10 < 0 && direction.y < 0)
			direction.y = 0;
		
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
		position = new Vector2(rand.nextInt(20, GameMap.WIDTH-300), rand.nextInt(20, GameMap.HEIGHT-80));
		
		do
			direction = new Vector2(rand.nextInt(-1, 2), rand.nextInt(-1, 2)).clampMagnitude(1f);
		while(direction.equals(Vector2.zero));
	}
	
	@Override
	public String toString() {
		return "Player{position='" + position + "', direction=" + direction + ", speed=" + speed + ", size=" + size + "}";
	}
	
}
