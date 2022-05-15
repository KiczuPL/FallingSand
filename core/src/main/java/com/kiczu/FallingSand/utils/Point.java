package com.kiczu.FallingSand.utils;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getTranslatedCopy(Point transition) {
        return new Point(x + transition.getX(), y + transition.getY());
    }

    public Point getTranslatedCopy(int transitionX, int transitionY) {
        return new Point(x + transitionX, y + transitionY);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode(){
        return x*13 + y*29;
    }

}
