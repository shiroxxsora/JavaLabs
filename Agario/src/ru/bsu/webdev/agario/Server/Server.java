package ru.bsu.webdev.agario.Server;

public class Server{
	public static void main(String[] args) {
		ServerCore server = new ServerCore();
		int port = 10003; // Задаем порт по умолчанию
		
		// Если прередан порт в параметре запуска, то пытаемся его конвертировать в инт
		if(args.length > 0)
			try {
				port = Integer.valueOf(args[0]);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		
		// Запуск сервера
		server.run(port);
	}
}
