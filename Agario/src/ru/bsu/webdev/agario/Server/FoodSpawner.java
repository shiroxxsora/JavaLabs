package ru.bsu.webdev.agario.Server;

import ru.bsu.webdev.agario.Client.EatableObject;

public class FoodSpawner implements Runnable{
	private int spawnInterval = 5;
	
	private int eatableObjectsCount = 0;
	private int maxEatableObjectsCount = 1000;

	public void run() {
		while(true) {
			if(eatableObjectsCount < maxEatableObjectsCount)
				ServerCore.broadcastEatableObject(new EatableObject());
			
			try {
				Thread.sleep(spawnInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
