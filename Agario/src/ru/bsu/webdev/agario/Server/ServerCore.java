package ru.bsu.webdev.agario.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ru.bsu.webdev.agario.Client.EatableObject;
import ru.bsu.webdev.agario.Client.Player;

public class ServerCore {
	// Храним подключенных клиентов
	public static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
	
	public void run(int port) {
		System.out.println("Server starting...");
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
//			FoodSpawner spawner = new FoodSpawner();
//			new Thread(spawner).run();
			
			System.out.println("Waiting for clients");
			while(true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Connection with " + clientSocket.getInetAddress());
				
				ClientHandler client = new ClientHandler(clientSocket);
				clients.add(client);
				client.start();
			}
		}
		catch(Exception e) {
			System.out.println("Some troubles shit shit shit...");
			e.printStackTrace();
		}
	}
	
	public static void broadcastEatableObject(EatableObject obj) {
		try {
			synchronized (clients) {
				for (ClientHandler client : ServerCore.clients) { 
					client.sendEatable(obj);
				} 
			} 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
