package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.Timer;

import ru.bsu.webdev.agario.Server.Commands.InitializePlayer;


public class GameMap extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1184690316216124564L;
	
	public static GameMap instance;
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
	
	private ArrayList<Player> players;
	private Player player;
	private FPSCounter counter;
	
	private long lastTime;
	private double deltaTime;
	
	private Timer timer;
	
	public GameMap() {
		instance = this;
		System.out.println("GameMap instance created...");
		
		players = new ArrayList<>();
		createCurrentPlayerFromServer();
		new Thread(this::listenToServerPlayerMessage).start();
		counter = new FPSCounter(10, 20);
		mainTimer();
	}
	
	private void listenToServerPlayerMessage() {
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
	
	private void createCurrentPlayerFromServer() {
		try { 
			Client.out.writeObject(new InitializePlayer());  // Запрос серверу
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
		System.out.println(deltaTime);
		
		if(players.isEmpty()) return;
		
		for(Player player:players){
			player.update(deltaTime);
		}

		
		counter.update(deltaTime);
	}
	
	protected void render(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(20, 20, WIDTH-300, HEIGHT-80);
		
		if(players.isEmpty()) return;
		
		for(Player player:players){
			player.render(g);
		}
		
		counter.render(g);
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
