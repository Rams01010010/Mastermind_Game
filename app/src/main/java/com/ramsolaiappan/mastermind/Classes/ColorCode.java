package com.ramsolaiappan.mastermind.Classes;

import java.util.ArrayList;
import java.util.Arrays;

public class ColorCode {

    public static int idCount = 0;
    protected int id = 0;
    protected int[] colorCode = new int[4];
    protected int[] hintCode = new int[4];

    public ColorCode() {
        idCount++;
        this.id = idCount;
        for(int i = 0; i < 4; i++)
        {
            this.colorCode[i] = 0;
            this.hintCode[i] = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getColorCode() {
        return colorCode;
    }

    public void setColorCode(int index, int color) {
        this.colorCode[index] = color;
    }

    public int[] getHintCode() {
        return hintCode;
    }

    public void setHintCode(int index, int color) {
        this.hintCode[index] = color;
    }

    public ArrayList<Integer> getColorArrayList()
    {
        ArrayList<Integer> colorArrayList = new ArrayList<>();
        for(int i = 0; i < colorCode.length; i++)
        {
            colorArrayList.add(colorCode[i]);
        }
        return colorArrayList;
    }
}
