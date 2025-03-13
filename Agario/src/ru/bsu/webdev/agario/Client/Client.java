package ru.bsu.webdev.agario.Client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client extends JFrame{
	private static final long serialVersionUID = 4320750064986123215L;
	
	public static Client instance;
    public static Socket socket;
    public static ObjectInputStream in;
    public static ObjectOutputStream out;
	
	public void run() {
		ConnectionScreen connectionScreen = new ConnectionScreen();
		connectionScreen.setVisible(true);
	}
	
	public void startGame(String address, String username) {
		
		// Создаем соединение с сервером
		try{
			String[] parts = address.split(":");
			String ip = parts[0];
            int port = Integer.parseInt(parts[1]);
            
			socket = new Socket(ip, port);
			out = new ObjectOutputStream(Client.socket.getOutputStream());
			out.flush(); // Обязательно или клиент или сервер должны отправить заголовок
			in = new ObjectInputStream(Client.socket.getInputStream());
		}
		catch(Exception e) {
			e.printStackTrace();
			
			// Завершаем прогу если не смогли подключится
			JOptionPane.showMessageDialog(null, "Проверьте введённые данные!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
			System.exit(1);
		}
		
		// Закрываем окно подключения
        getContentPane().removeAll();
        repaint();

        // Создаем и показываем игровую карту
        GameMap gmap = new GameMap();
        gmap.setFocusable(true);
        add(gmap);

        setSize(GameMap.WIDTH, GameMap.HEIGHT);
        setLocationRelativeTo(null);
        
        // Действия при закрытии окна
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                closeConnection();
            }
        });
        
        setVisible(true);
    }
	
	public Client() {
		instance = this;
		System.out.println("Создан Клиент");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Метод для корректного закрытия соединения
    public void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
            	Optional<Player> result = GameMap.instance.players.stream()
            							.filter(player -> player.isCurrentPlayer)
            							.findFirst();
            	
            	result.ifPresent(player -> {
					player.isDestroyed = true;
					player.sendPlayerData();
            	});
            	
                socket.close();
                System.out.println("Соединение с сервером закрыто.");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
