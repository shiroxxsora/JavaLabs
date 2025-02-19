package ru.bsu.webdev.agario.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServerCore {
	private static final int PORT = 10003;
	public static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
	
	public void run() {
		System.out.println("Server starting...");
		
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
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
	
	public ServerCore() {
		System.out.println("Server initialize...");
	}
}
