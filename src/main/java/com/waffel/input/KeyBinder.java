package com.waffel.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Created by yosephsa on 8/2/16.
 */
public class KeyBinder {

    public static final String MV_FORWARD = "MV_FORWARD";
    public static final String MV_BACKWARD = "MV_BACKWARD";
    public static final String MV_RIGHT = "MV_RIGHT";
    public static final String MV_LEFT = "MV_LEFT";
    public static final String MV_BOOST = "MV_BOOST";


    // The instance of this class.
    private static final KeyBinder instance = new KeyBinder();

    //The hashmap to store all the key bindings with their corresponding keys.
    public HashMap<String, int[]> bindings = new HashMap<String, int[]>();

    // Initializes the key bindings to the default bindings.
    private KeyBinder() {
        addBinding(MV_FORWARD, KeyEvent.VK_W);
        addBinding(MV_BACKWARD, KeyEvent.VK_S);
        addBinding(MV_RIGHT, KeyEvent.VK_D);
        addBinding(MV_LEFT, KeyEvent.VK_A);
        addBinding(MV_BOOST, KeyEvent.VK_SHIFT);

    }

    /**
     * Returns weather a certain binding is active. If none is available false is returned.
     * @param name Binding Name/Identifier
     * @return If that binding is pressed.
     */
    public boolean isBindingActive(String name) {
        if(!bindings.containsKey(name))
            return false;
        int[] keys = bindings.get(name);
        for(int i = 0; i < keys.length; i++)
            if(!Keyboard.getInstence().isPressed(keys[i]))
                return false;
        return true;
    }

    /**
     * Will set the given binding name with the given keys. If the binding name is already exists it will be overrided.
     * Will return false if another binding contains the same keys.
     * @param name The binding Name/Identifier.
     * @param keys The binding keys.
     * @return True if no bindings with the same keys exists. False if so, or if keys is empty.
     */
    public boolean addBinding(String name , int ... keys) {
        if(bindings.containsValue(keys))
            return false;
        return addBindingOverride(name, keys);
    }

    /**
     * Will set the given command to the given keys regardless if there already is another command with the same key
     * bindings. If the binding name already exists it will be overrided.
     * @param name The binding Name/Identifier.
     * @param keys The binding keys.
     * @return False if keys is empty.
     */
    public boolean addBindingOverride(String name , int ... keys) {
        if(keys.length <= 0)
            return false;
        bindings.put(name, keys);
        return true;
    }

    /**
     * Retrieves the keys for a given binding name. If binding name is not registered with any keys it will return null.
     * @param name The binding name.
     * @return An array of the ascii values of the keys that belong to this binding.
     */
    public int[] getBinding(String name) {
        if(!bindings.containsKey(name))
            return null;
        return bindings.get(name);
    }

    /**
     * A accessor method for the singelton object of this class.
     * @return The object of this class.
     */
    public static KeyBinder getInstance() {
        return instance;
    }

}
