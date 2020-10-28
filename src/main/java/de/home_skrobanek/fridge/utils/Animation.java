package de.home_skrobanek.fridge.utils;


public class Animation {

    //starting a new timed animation which is fully customize able
    public static void startAnimation(Runnable runnable){
        Thread t = new Thread(runnable);
        t.start();
    }

}
