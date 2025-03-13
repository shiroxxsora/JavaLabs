package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.Timer;

import ru.bsu.webdev.agario.Server.Commands.CreatePlayerRequest;


public class GameMap extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1184690316216124564L;
	
	public static GameMap instance;
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	public ArrayList<Player> players;
	public ArrayList<Player> playersToRemove; 
	private ArrayList<EatableObject> eatables;
	public Player player;
	private FPSCounter fpsCounter;
	
	private long lastTime;
	private double deltaTime;
	
	private Timer timer;
	
	public GameMap() {
		instance = this;
		System.out.println("GameMap instance created...");
		
		players = new ArrayList<>();
		playersToRemove = new ArrayList<>();
		eatables = new ArrayList<>();
		
		sendCreatePlayerRequest(); //  Просим сервер создать игрока
		new Thread(this::serverListener).start(); //  Запускаем поток который будет слушать сообщения сервера
		
		fpsCounter = new FPSCounter(10, 20);
		
		// Запускаем цикл update и render
		mainTimer();
	}
	
	private void serverListener() {
		try {
            while (true) {
                // Ожидаем получения объекта Player от сервера
                Object received = Client.in.readObject();
                if (received instanceof Player) {
                	Player receivedPlayer = (Player) received;
                	if(!players.contains((Player) received)) {
	                    players.add(receivedPlayer);
	                    System.out.println("Подключился новый игрок: " + receivedPlayer);
                	}
                	else {
                		System.out.println("МЕНЯЕМ ПЛЕЕРА " + receivedPlayer);
                		System.out.println(players.indexOf(receivedPlayer));
                		players.set(players.indexOf(receivedPlayer), receivedPlayer);
                	}
                }
                if(received instanceof EatableObject) {
                	eatables.add((EatableObject) received);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void mainTimer() {
		lastTime = System.nanoTime();
		timer = new Timer(1000 / 120, e -> {
			long currentTime = System.nanoTime();
			deltaTime = (currentTime - lastTime) / 1_000_000_000.0;
			lastTime = currentTime;
			
            update(deltaTime);
            repaint();
        });
        timer.start();
	}
	
	private void sendCreatePlayerRequest() {
		try { 
			Client.out.writeObject(new CreatePlayerRequest());  // Запрос серверу
			Client.out.flush();
			
			player = (Player) Client.in.readObject();
			player.isCurrentPlayer = true;
			players.add(player);
			System.out.println("Мы игрок" + player);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected void update(double deltaTime) {
		if(players.isEmpty()) return;
		
		for(Player player : players){
			player.update(deltaTime);
		}
		
		for (Player player : playersToRemove) {
            players.remove(player);
        }
		
        playersToRemove.clear();

		fpsCounter.update(deltaTime);
	}
	
	protected void render(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(20, 20, WIDTH-300, HEIGHT-80);
		
		if(players.isEmpty()) return;
		
		for(EatableObject eatable : eatables) {
			eatable.render(g);
		}
		
		for(Player player : players){
			player.render(g);
		}
		
		fpsCounter.render(g);
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
