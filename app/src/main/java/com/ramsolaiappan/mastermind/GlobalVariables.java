package com.ramsolaiappan.mastermind;


import android.app.Application;

public class GlobalVariables extends Application {
    public static boolean allowDuplicatePegs = false;
    public static boolean allowEmptyPegs = false;
    public static final int[] colorCodes = {R.color.noPeg,R.color.redPeg,R.color.greenPeg,R.color.bluePeg,R.color.yellowPeg,R.color.whitePeg,R.color.violetPeg};
    public static final int[] hintCodes = {R.color.noPeg, R.color.redPeg,R.color.white};
}
