package ru.bsu.webdev.tetris;

public enum FigureType {
    I, O, T, S, Z, J, L;

    public static FigureType getRandomType() {
        FigureType[] values = values();  // Получаем все значения enum
        int index = (int) (Math.random() * values.length);  // Генерация случайного индекса
        return values[index];  // Возвращаем случайный элемент
    }
}
