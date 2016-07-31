package com.waffel.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Jonny on 7/31/16.
 */
public class ItemList {

    private String[] items;
    private int current;

    private ItemList(Builder builder) {
        items = builder.items.toArray(new String[builder.items.size()]);
        current = 0;
    }

    public void moveUp() {
        current--;
        if (current < 0) {
            current = items.length;
        }
    }

    public void moveDown() {
        current++;
        if (current >= items.length) {
            current = 0;
        }
    }

    public String[] getItems() {
        return items;
    }

    public int getCurrent() {
        return current;
    }

    public String getCurrentItem() {
        return items[current];
    }

    public static class Builder {
        private List<String> items;

        public Builder() {
            items = Lists.newArrayList();
        }

        public void setItem(String item) {
            items.add(item);
        }

        public ItemList build() {
            return new ItemList(this);
        }
    }

}
