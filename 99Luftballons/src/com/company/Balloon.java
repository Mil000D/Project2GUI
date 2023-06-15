package com.company;

class Balloon {
    private BalloonColor color;
    private static long nextNumber = 1;

    public Balloon(BalloonColor color) {
        nextNumber++;
        this.color = color;
    }

}
