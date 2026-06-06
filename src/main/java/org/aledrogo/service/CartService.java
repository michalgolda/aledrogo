package org.aledrogo.service;

import org.aledrogo.entity.Offer;

import java.util.ArrayList;

public class CartService {
    public ArrayList<Offer> items = new ArrayList<>();

    public void addItem(Offer item) {
        items.add(item);
    }

    public void removeItem(int itemIndex) {
        items.remove(itemIndex);
    }

    public ArrayList<Offer> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}
