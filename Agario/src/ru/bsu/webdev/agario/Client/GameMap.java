package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GameMap extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1184690316216124564L;
	
	public static int WIDTH = 960;
	public static int HEIGHT = 640;
	
	private ArrayList<Player> players;
	private Player player;
	
	private long lastTime;
	private double deltaTime;
	private Timer timer;
	
	
	public GameMap() {
		System.out.println("GameMap instance created...");
		
		players = new ArrayList<>();
		
		getNewPlayerFromServer();
		lastTime = System.nanoTime();

		timer = new Timer(1000 / 60, e -> {
			long currentTime = System.nanoTime();
			deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
			lastTime = currentTime;
			
            update(deltaTime);
            repaint();
        });
        timer.start();
	}
	
	private void getNewPlayerFromServer() {
		Client.out.println("initialize_player");
		try {
			player = (Player) Client.in.readObject();
			players.add(player);
			System.out.println(player);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void update(double deltaTime) {
		System.out.println(deltaTime);
		
		if(players.isEmpty()) return;
		
		for(Player player:players){
			player.update(deltaTime);
		}
	}
	
	protected void render(Graphics g) {
		if(players.isEmpty()) return;
		
		for(Player player:players){
			player.render(g);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.render(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
