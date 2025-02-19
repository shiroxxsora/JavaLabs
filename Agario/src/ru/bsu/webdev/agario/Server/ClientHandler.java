package ru.bsu.webdev.agario.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import ru.bsu.webdev.agario.Client.Player;

public class ClientHandler extends Thread{
	private Socket socket;
	private Player player;
    private BufferedReader in;
    private ObjectOutputStream out;
    
	public ClientHandler(Socket clientSocket) {
		this.socket = clientSocket;
		System.out.println("Создан новый ClientHandler" + socket.getInetAddress());
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("Запущен поток ClientHandler" + socket.getInetAddress());
		try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received: " + message);
                if("initialize_player".equals(message)) {
                	addNewPlayerOnServer();
                }
                //broadcast(message);
            }
        } 
		catch (Exception e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        } 
		finally {
            try {
                socket.close();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
            ServerCore.clients.remove(this);
        }
	}
	
	
	private void addNewPlayerOnServer() {
//		if(ServerCore.clients.size() == 1) 
		//Пока рандомно надо теперь проверять....
		{
			player = new Player();
			player.randomPosition();
			
			System.out.println(player);
			try {
				out.writeObject(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		synchronized (ServerCore.clients) {
            for (ClientHandler client : ServerCore.clients) {
                if (client != this) {
                    
                }
            }
        }
	}
	
	/*
	 * private void broadcast(String message) { synchronized (ServerCore.clients) {
	 * for (ClientHandler client : ServerCore.clients) { if (client != this) {
	 * client.out.println(message); } } } }
	 */
	
}
