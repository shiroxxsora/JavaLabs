package ru.bsu.webdev.agario.Client;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class Client extends JFrame{
	private static final long serialVersionUID = 4320750064986123215L;
	
	private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 10003;
    
    public static Socket socket;
    public static ObjectInputStream in;
    public static PrintWriter out;
	
	public void run() {
		try{
			socket = new Socket(SERVER_IP, PORT);
			try {
				in = new ObjectInputStream(Client.socket.getInputStream());
	            out = new PrintWriter(Client.socket.getOutputStream(), true);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			GameMap gmap = new GameMap();
//			gmap.addKeyListener(gmap);
//			gmap.setFocusable(true); // don't forget
				
			add(gmap);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(GameMap.WIDTH, GameMap.HEIGHT);
			setVisible(true);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Client() {
		System.out.println("Создан Клиент");
	}
}
