package com.waffel.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import static com.waffel.input.KeyActions.Action.*;

/**
 * Created by yosephsa on 8/2/16.
 */
public class KeyActions {

    public enum Action {
        MV_FORWARD, MV_BACKWARD, MV_RIGHT, MV_LEFT, MV_BOOST;
    }

    private static final KeyActions instance = new KeyActions();

    public HashMap<Action, int[]> actions = new HashMap<Action, int[]>();

    private KeyActions() {
        addAction(MV_FORWARD, KeyEvent.VK_W);
        addAction(MV_BACKWARD, KeyEvent.VK_S);
        addAction(MV_RIGHT, KeyEvent.VK_D);
        addAction(MV_LEFT, KeyEvent.VK_A);
        addAction(MV_BOOST, KeyEvent.VK_SHIFT);

    }

    public boolean isActionActive(Action a) {
        if(!actions.containsKey(a))
            return false;
        int[] keys = actions.get(a);
        for(int i = 0; i < keys.length; i++)
            if(!Keyboard.getInstence().isPressed(keys[i]))
                return false;
        return true;
    }

    public boolean addAction(Action a , int ... keys) {
        if(actions.containsValue(keys))
            return false;
        return addActionOverride(a, keys);
    }

    public boolean addActionOverride(Action a , int ... keys) {
        if(keys.length <= 0)
            return false;
        actions.put(a, keys);
        return true;
    }

    public int[] getAction(Action a) {
        if(!actions.containsKey(a))
            return new int[0];
        return actions.get(a);
    }

    public static KeyActions getInstance() {
        return instance;
    }

}
