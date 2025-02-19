package ru.bsu.webdev.agario.Client;

import java.awt.Color;
import java.util.Random;

public enum ColorEnum {
    WHITE(Color.white),
    CYAN(Color.cyan),
    ORANGE(Color.orange),
    MAGENTO(Color.magenta),
    BLUE(Color.blue),
    GREEN(Color.green),
    ;

	private Color color;
	
    ColorEnum(Color color) {
		this.color = color;
	}
    

	public static Color getRandomColor() {
		ColorEnum[] values = values();  // Получаем все значения enum
        int index = new Random().nextInt(values.length);  
        return values[index].color;  // Возвращаем случайный цвет
    }
}