package com.totis.mymodid;

@SuppressWarnings("all")
public class Constants {

    public static final String MOD_ID = "mymodid";

    // Tickrate changer
    public static final String GAME_RULE = "tickrate";
    public static float DEFAULT_TICKRATE = 20; // Default tickrate - can be changed in the config file
    public static float TICKS_PER_SECOND = 20; // Stored client-side tickrate
    public static long MILISECONDS_PER_TICK = 50L; // Server-side tickrate in miliseconds
    public static float GAME_SPEED = 1; // Sound speed
    public static float MIN_TICKRATE = 0.1F; // Min Tickrate
    public static float MAX_TICKRATE = 50F; // Max Tickrate
    public static boolean SHOW_MESSAGES = true; // Show Messages
    public static boolean CHANGE_SOUND = true; // Change sound speed
}
