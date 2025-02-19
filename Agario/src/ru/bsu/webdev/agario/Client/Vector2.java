package ru.bsu.webdev.agario.Client;

import java.io.Serializable;

public class Vector2 implements Serializable{
	private static final long serialVersionUID = 559688245149447959L;
	
	public double x;
	public double y;
	
	public static Vector2 zero = new Vector2(0.0, 0.0);
	
	public Vector2 (double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2 add(Vector2 other){
		return new Vector2(this.x + other.x, this.y + other.y);
	}

	public Vector2 mul(Vector2 other) {
		return new Vector2(this.x * other.x, this.y * other.y);
	}
	
	public Vector2 mul(double number) {
		return new Vector2(this.x * number, this.y * number);
	}
	
	public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }
	 
	public Vector2 clampMagnitude(double maxLength) {
        double length = magnitude(); 

        if (length > maxLength) {
            double scale = maxLength / length; // коэффициент уменьшения
            return new Vector2(x * scale, y * scale); // Масштабируем вектор
        }
        
        return this;
    }
	
	public boolean equals(Vector2 other) {
		return this.x == other.x && this.y == other.y;
	}
	
	@Override
	public String toString() {
		return "Vector2{x=" + x + ", y=" + y + "}";
	}
}
