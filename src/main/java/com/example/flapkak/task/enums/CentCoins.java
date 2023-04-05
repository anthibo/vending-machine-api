package com.example.flapkak.task.enums;

public enum CentCoins {
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    ONE_HUNDRED(100);

    private int numVal;


    CentCoins(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
