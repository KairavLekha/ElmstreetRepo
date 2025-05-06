/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.working;

import Backend.dbManager;
import java.sql.SQLException;

/**
 *
 * @author Kairav
 */
public class Item {

    private int itemID;
    private String name;
    private String imagePath;
    private int quantity;
    private double productionCost;
    private double sellingPrice;
    private int discount;
    private int category;
    private static dbManager manager;

    public static final int AUTOMOVTIVE = 0;
    public static final int ARTIST = 1;
    public static final int HUMOUR = 2;

    public Item(int inID, String inN, String inPath, int inQ, double inSP, double inPC, int inD, int inC,dbManager inManager) {
        this.itemID = inID;
        this.name = inN;
        this.imagePath = inPath;
        this.quantity = inQ;
        this.sellingPrice = inSP;
        this.productionCost = inPC;
        this.discount = inD;
        this.category = inC;
        this.manager=inManager;
    }

    public int getItemID() {
        return itemID;
    }

    public static Item getFeaturedItem() throws SQLException {
       return manager.getFeatItem();
    }

    public String toString() {
        return this.name + " R" + getCurrentPrice();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStock(dbManager inManager) throws SQLException {
        return inManager.getStock(itemID);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getName() {
        if (this.quantity < 1) {
            return name + " Out of Stock";
        }
        return name;
    }

    public double getCurrentPrice() {
        return sellingPrice - (sellingPrice * (discount / 100.0));
    }

}
