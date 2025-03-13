package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Objects;
import java.util.Random;


public class Player implements Serializable, Cloneable{
	private static final long serialVersionUID = -4244531400163884594L;

	private Color fillColor = Color.blue;
			// ColorEnum.getRandomColor();
	
	public int ID;
	public Vector2 position;
	private Vector2 direction;
	
	private double speed = 5;
	public int size = 20;
	public boolean isCurrentPlayer = false;
	public boolean isDestroyed = false;
	
	
	public Player() {
	}
	
	public void sendPlayerData() {
		try {
			Player sendPlayer = (Player) this.clone();
			sendPlayer.isCurrentPlayer = false; // Костыль мб както пофиксится потом
			// P.S. не пофиксилось
			
            Client.out.writeObject(sendPlayer);
            Client.out.reset(); // Очищаем кеш
            Client.out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void update(double deltaTime) {
		if(isCurrentPlayer) {
			move(deltaTime);
			sendPlayerData();
		}
		
		// Так как список игроков перебирается добавляем играка в очередь на удаление чтобы небыло java.util.ConcurrentModificationException
		if(isDestroyed)
			GameMap.instance.playersToRemove.add(this);
	}
	
	public void render(Graphics g) {
		g.setColor(fillColor);
        g.fillOval((int)position.x, (int)position.y, size, size);
        //g.drawOval(position.x, size, size, size);
	      
	}
	
	private void move(double deltaTime) {
		if(isCurrentPlayer)
			if(GameMap.instance.getMousePosition() != null) {
				try {
					Vector2 mousePosition = new Vector2(GameMap.instance.getMousePosition());
					
					Vector2 targetDirection = mousePosition.subtract(position).clampMagnitude(1f);
					System.out.println(targetDirection.magnitude());
					
					if(targetDirection.magnitude() < 0.99)
						targetDirection = targetDirection.mul(targetDirection.abs());
					
					direction = targetDirection;
				}
				catch (Exception e) {
				}
			}
		
		if(position.x + size/2 > GameMap.WIDTH-290 && direction.x > 0)
			direction.x = 0;
		if(position.y + size/2 > GameMap.HEIGHT-70 && direction.y > 0)
			direction.y = 0;
		if(position.x - size/2 < 10 && direction.x < 0)
			direction.x = 0;
		if(position.y - size/2 < 10 && direction.y < 0)
			direction.y = 0;
		
		double correctedSpeed = speed * deltaTime * 10;
		Vector2 targetPosition = position.add(direction.mul(correctedSpeed));
		position = targetPosition;
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
		return "Player{ID ='" + ID + "', position='" + position + "', direction=" + direction + ", speed=" + speed + ", size=" + size + "}";
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) return false;
		return this.ID == ((Player) other).ID;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(ID);
	}
	
}
