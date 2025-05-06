/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.working;

import Backend.working.Item;
import java.util.ArrayList;

/**
 *
 * @author Kairav
 */
public class Storefront {

    private ArrayList<Item> items;

    public ArrayList<ArrayList<Item>> getScreens() {
        ArrayList<ArrayList<Item>> screenArray = new ArrayList<>();
        int screenNum = 1;
        while (screenNum <= getNumScreens()) {

            ArrayList<Item> screen = new ArrayList<>();
            int startIndex = (screenNum - 1) * 6;
            int lastIndex = Math.min(startIndex + 6, items.size()); // Prevent out-of-bounds errors

            for (int index = startIndex; index < lastIndex; index++) {
                screen.add(items.get(index));
            }
            screenArray.add(screen);
            screenNum++;
        }

        return screenArray;
    }

    public int getNumScreens() {
        return (int) Math.ceil(items.size() / 6.0);
    }

    public Storefront(ArrayList<Item> items) {
        this.items = items;
    }

}
