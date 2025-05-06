/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.working.Item;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Kairav
 */
public class Cart {

    private int cartID;
    private int cartStatus;
    private ArrayList<Item> itemsInCart;
    public static final int NOT_ORDERED = 0;
    public static final int ORDERED = 1;
    private dbManager manager;

    public Cart(int cartID, int cartStatus, dbManager inManager) throws ClassNotFoundException, SQLException {
        this.cartID = cartID;
        this.cartStatus = cartStatus;
        this.manager=inManager;
        this.itemsInCart = manager.getItemsInCart(cartID);
    }

    public void setCartStatus(int cartStatus) {
        this.cartStatus = cartStatus;
    }

    public int getCartID() {
        return cartID;
    }

    public int getCartStatus() {
        return cartStatus;
    }

    public ArrayList<Item> getItemsInCart() {
        return itemsInCart;
    }

    public double getCartValue() {
        double cartValue = 0.0;
        for (Item item : itemsInCart) {
            cartValue += item.getCurrentPrice() * item.getQuantity();
        }
        return cartValue;
    }

    public boolean orderCanBeExecuted() throws SQLException {
        for (Item item : itemsInCart) {
            if (item.getQuantity()>manager.getStock(item.getItemID())) {
                return false;
            }
        }
        return true;
    }

}
