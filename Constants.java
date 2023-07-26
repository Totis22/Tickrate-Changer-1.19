package com.totis.infinityg;

@SuppressWarnings("all")
public class Constants {

    public static final String MOD_ID = "infinityg";

    public static final float PORTAL_SPAWN_DISTANCE = 3f;

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


    // Usefull Nbt
    public static final String BOUND_KNIFE = "bound_knife";
    public static final String BOUND_ENTITY = "bound_entity";
    public static final String BOUND_LOCATION = "bound_location";
    public static final String ENTITY_NAME = "entity_name";
    public static final String DIMENSION = "dim";
    public static final String DIMENSION_NAME = "dimName";
    public static final String PITCH = "pitch";
    public static final String YAW = "yaw";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";
    public static final String POSITION = "pos";
    public static final String DIRECTION = "dir";
    public static final String KNIFE = "knife";
    public static final String GLOWING = "glowing";
    public static final String ID = "uuid";
    public static final String STACK_ID = "stack_id";


    // Translation
    public static final String KEY_CATEGORY = "key.category."+MOD_ID+".powerx";
    public static final String RIGHTMOUSE = "key."+MOD_ID+".rightmouse";

    public static final String CHANGE_MODE = "key."+MOD_ID+".changemode";
    public static final String ABILITY1 = "key."+MOD_ID+".ability1";
    public static final String ABILITY2 = "key."+MOD_ID+".ability2";
    public static final String ABILITY3 = "key."+MOD_ID+".ability3";
    public static final String ABILITY4 = "key."+MOD_ID+".ability4";
    public static final String ABILITY5 = "key."+MOD_ID+".ability5";
}
