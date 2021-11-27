package com.forward.colorit.coloring;

/**
 * Данные о месте заливки в изображении.
 */
public class ColoringMap {
    private int x;
    private int y;

    /**
     * Цвет активации заливки.
     */
    private String color;

    /**
     * Цвет которым будет проводиться заливка.
     */
    private String targetColor;

    public ColoringMap() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(String targetColor) {
        this.targetColor = targetColor;
    }
}
