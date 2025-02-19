package ru.bsu.webdev.agario.Server.Commands;

import java.io.Serializable;

public class CMD implements Serializable {
	private static final long serialVersionUID = -6550806947453245085L;
	
	public String command;
	
	public CMD(String cmd){
		command = cmd;
	}
	
	@Override
	public String toString() {
		return "CMD{command='" + command + "}";
	}
}
