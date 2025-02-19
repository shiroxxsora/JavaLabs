package ru.bsu.webdev.agario.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.bsu.webdev.agario.Client.Player;
import ru.bsu.webdev.agario.Server.Commands.CMD;
import ru.bsu.webdev.agario.Server.Commands.InitializePlayer;

public class ClientHandler extends Thread{
	private Socket socket;
	private Player player;
	
	private static int freePlayerID = 0;
	
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
	public ClientHandler(Socket clientSocket) {
		this.socket = clientSocket;
		System.out.println("Создан новый ClientHandler" + socket.getInetAddress());
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush(); // Обязательно или клиент или сервер должны отправить заголовок
			in = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		System.out.println("Запущен поток ClientHandler" + socket.getInetAddress());
		try {
            Object message;
            while ((message = in.readObject()) != null) {
                if(message instanceof CMD) {
	                if(message instanceof InitializePlayer) {
	                	addNewPlayerOnServer();
	                }
                }
                else if(message instanceof Player) {
                	broadcast((Player) message);
                }
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
		try {
	//		if(ServerCore.clients.size() == 1) 
			//Пока рандомно надо теперь проверять....
		
			player = new Player();
			player.ID = freePlayerID++;
			player.randomPosition();
			
			System.out.println(player);
			try {
				out.writeObject(player);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			synchronized (ServerCore.clients) {
	            for (ClientHandler client : ServerCore.clients) {
	                if (client != this) {
							out.writeObject(client.player);
							client.out.writeObject(player);
							client.out.flush();
	                }
	            }
	        }
			out.flush();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void broadcast(Player player) { 
		//System.out.println(player);
		try {
			synchronized (ServerCore.clients) {
				for (ClientHandler client : ServerCore.clients) { 
					if (client != this) {
							client.out.writeObject(player);
							client.out.flush();
					} 
				} 
			} 
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
