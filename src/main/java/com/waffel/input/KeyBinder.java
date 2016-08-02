package com.waffel.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import static com.waffel.input.KeyBinder.Binding.*;

/**
 * Created by yosephsa on 8/2/16.
 */
public class KeyBinder {

    public enum Binding {
        MV_FORWARD, MV_BACKWARD, MV_RIGHT, MV_LEFT, MV_BOOST;
    }

    private static final KeyBinder instance = new KeyBinder();

    public HashMap<Binding, int[]> bindings = new HashMap<Binding, int[]>();

    private KeyBinder() {
        addBinding(MV_FORWARD, KeyEvent.VK_W);
        addBinding(MV_BACKWARD, KeyEvent.VK_S);
        addBinding(MV_RIGHT, KeyEvent.VK_D);
        addBinding(MV_LEFT, KeyEvent.VK_A);
        addBinding(MV_BOOST, KeyEvent.VK_SHIFT);

    }

    public boolean isBindingActive(Binding a) {
        if(!bindings.containsKey(a))
            return false;
        int[] keys = bindings.get(a);
        for(int i = 0; i < keys.length; i++)
            if(!Keyboard.getInstence().isPressed(keys[i]))
                return false;
        return true;
    }

    public boolean addBinding(Binding a , int ... keys) {
        if(bindings.containsValue(keys))
            return false;
        return addBindingOverride(a, keys);
    }

    public boolean addBindingOverride(Binding a , int ... keys) {
        if(keys.length <= 0)
            return false;
        bindings.put(a, keys);
        return true;
    }

    public int[] getBinding(Binding a) {
        if(!bindings.containsKey(a))
            return new int[0];
        return bindings.get(a);
    }

    public static KeyBinder getInstance() {
        return instance;
    }

}
