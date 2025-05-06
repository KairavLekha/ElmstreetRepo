/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import Backend.working.DB;
import Backend.working.Item;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Kairav
 */
public class dbManager {

    public static int PENDING_OR_NOT_ORDERED = 0;
    public static int CANCEl = 1;
    public static int DELIVERED = 2;

    public void addToCart(int inUserID, int inItemID, int inQuantity) throws SQLException {
        int cartID = getUserCartID(inUserID);
        ResultSet rs = DB.read("SELECT count(*) FROM elmstreet.cart_item where cart_id=" + cartID + " and item_id=" + inItemID + ";");
        int numSameEntries = rs.getInt("count(*)");
        if (numSameEntries == 0) {
            DB.update("INSERT INTO `elmstreet`.`cart_item` (`cart_id`, `item_id`, `quantity`) VALUES ('" + cartID + "', '" + inItemID + "', '" + Math.abs(inQuantity) + "');");
        } else {
            DB.update("UPDATE `elmstreet`.`cart_item` SET `quantity` = `quantity`+'" + Math.abs(inQuantity) + "' WHERE (`cart_id` = '" + cartID + "') and (`item_id` = '" + inItemID + "');");
        }
    }

    public int getStock(int itemID) throws SQLException {
        ResultSet rs = DB.read("SELECT sum(quantity) as \"Stock\" FROM elmstreet.stock_entries where item_id like "+itemID+";");
        if (rs.next()) {
            return rs.getInt("Stock");
        }
        return 0;
    }

    public int getUserCartID(int inUserID) throws SQLException {
        ResultSet rs = DB.read("SELECT cart_id FROM elmstreet.cart where cart_owner = " + inUserID + " and ordered=" + PENDING_OR_NOT_ORDERED + ";");
        int cartID = -1; // or whatever "not found" value you want
        if (rs.next()) {
            cartID = rs.getInt("cart_id");
        }
        return cartID;
    }

    //working
    public void UpdateItem(int itemID, int changeQuantity, String itemName, int discount, int category, double price) throws SQLException {
        DB.update("INSERT INTO `elmstreet`.`stock_entries` (`item_id`, `quantity`) VALUES ('" + itemID + "', '" + changeQuantity + "');");
        DB.update("UPDATE `elmstreet`.`items` SET `itemName` = '" + itemName + "', `salesPrice` = '" + price + "', `discount` = '" + discount + "', `category_id` = '" + category + "' WHERE (`item_id` = '" + itemID + "');");
    }

    public void newCart(int userId) throws SQLException {
        DB.update("INSERT INTO `elmstreet`.`cart` (`cart_owner`, `ordered`, `numItems`) VALUES ('" + userId + "', '" + PENDING_OR_NOT_ORDERED + "', '0');");
    }

    public ArrayList<Item> getItems() throws ClassNotFoundException, SQLException {
        ResultSet rs = DB.read("SELECT items.item_id, items.itemName, items.imageName, items.productionCost, items.salesPrice, items.discount, items.category_id,sum(stock_entries.quantity) AS \"stockRemaining\" FROM elmstreet.items inner join elmstreet.stock_entries on stock_entries.item_id= items.item_id group by items.item_id;");
        ArrayList<Item> items = new ArrayList<>();

        while (rs.next()) {
            int itemID = (rs.getInt("item_id"));
            String name = (rs.getString("itemName"));
            String imagePath = (rs.getString("imageName"));
            int stock = (rs.getInt("stockRemaining"));
            double productionCost = (rs.getDouble("productionCost"));
            double sellingPrice = (rs.getDouble("salesPrice"));
            int discount = (rs.getInt("discount"));
            int category = (rs.getInt("category_id"));
            Item i = new Item(itemID, name, imagePath, stock, sellingPrice, productionCost, discount, category,this);
            items.add(i);
        }
        return items;
    }

    public ArrayList<Item> getItemsInCart(int inCartID) throws ClassNotFoundException, SQLException {
        ResultSet rs = DB.read("SELECT \n"
                + "    i.item_id,\n"
                + "    i.itemName,\n"
                + "    i.imageName,\n"
                + "    i.productionCost,\n"
                + "    i.salesPrice,\n"
                + "    i.discount,\n"
                + "    i.category_id,\n"
                + "    ci.quantity\n"
                + "FROM \n"
                + "    elmstreet.cart_item ci\n"
                + "INNER JOIN \n"
                + "    elmstreet.items i ON ci.item_id = i.item_id\n"
                + "WHERE \n"
                + "    ci.cart_id = " + inCartID + ";");
        ArrayList<Item> items = new ArrayList<>();

        while (rs.next()) {
            int itemID = (rs.getInt("item_id"));
            String name = (rs.getString("itemName"));
            String imagePath = (rs.getString("imageName"));
            int stock = rs.getInt("quantity"); // ✅ safe after moving to the first row
            double productionCost = (rs.getDouble("productionCost"));
            double sellingPrice = (rs.getDouble("salesPrice"));
            int discount = (rs.getInt("discount"));
            int category = (rs.getInt("category_id"));

            Item i = new Item(itemID, name, imagePath, stock, sellingPrice, productionCost, discount, category,this);
            items.add(i);
        }
        return items;

    }

    public Item getFeatItem() throws SQLException {
        Item i = null;
        ResultSet rs = DB.read("SELECT items.item_id, items.itemName, items.imageName, items.productionCost, \n"
                + "items.salesPrice, items.discount, items.category_id, \n"
                + "SUM(stock_entries.quantity) AS \"stockRemaining\" \n"
                + "FROM elmstreet.items \n"
                + "INNER JOIN elmstreet.stock_entries ON stock_entries.item_id = items.item_id \n"
                + "GROUP BY items.item_id\n"
                + "having (stockRemaining+3<(SELECT AVG(SUM(stock_entries.quantity)) FROM elmstreet.items) and  discount !=0)\n"
                + "or discount !=0 order by (stockRemaining+discount) limit 1;");
        while (rs.next()) {
            int itemID = (rs.getInt("item_id"));
            String name = (rs.getString("itemName"));
            String imagePath = (rs.getString("imageName"));
            int stock = (rs.getInt("stockRemaining"));
            double productionCost = (rs.getDouble("productionCost"));
            double sellingPrice = (rs.getDouble("salesPrice"));
            int discount = (rs.getInt("discount"));
            int category = (rs.getInt("category_id"));
            i = new Item(itemID, name, imagePath, stock, sellingPrice, productionCost, discount, category,this);
        }

        return i;
    }

    public boolean addNewUser(String inFullname, String inAdd, String inPhone, String inEmail, String inPass, boolean isOwner) throws SQLException {
        int ownerInt = 0;
        if (isOwner) {
            ownerInt = 1;
        }
        ResultSet rs = DB.read("SELECT count(*) FROM elmstreet.user_tbl where email = '" + inEmail + "';");
        if (rs.equals(null)) {
            return false;
        } else {
            DB.update("INSERT INTO user_tbl (fullname, address, phone, email, password, isOwner) "
                    + "VALUES ('" + inFullname + "', '" + inAdd + "', '" + inPhone + "', '" + inEmail + "', '" + inPass + "', '" + ownerInt + "');");
            if (!isOwner) {
                newCart(getIDofUser(inEmail));
            }
            return true;
        }

    }

    public ResultSet login(String inEmail, String inPass) throws SQLException {
        ResultSet rs = DB.read("Select * FROM elmstreet.user_tbl where email like '" + inEmail + "' AND password like '" + inPass + "';");
        return rs;
    }

    public int getIDofUser(String email) throws SQLException {
        ResultSet rs = DB.read("SELECT userID FROM elmstreet.user_tbl where email like '" + email + "';");
        return rs.getInt(1);
    }

    public ResultSet getUserInfo(int inUserID) throws SQLException {
        return DB.read("SELECT fullname, address, phone, email, password, isOwner FROM elmstreet.user_tbl WHERE userID = '" + inUserID + "';");
    }

    public void updateUser(String inFullname, String inAdd, String inPhone, String inEmail, String inPass, boolean isOwner, int userID) throws SQLException {
        int ownerInt = 0;
        if (isOwner) {
            ownerInt = 1;
        }
        String updateQuery = "UPDATE user_tbl SET "
                + "fullname = '" + inFullname + "', "
                + "address = '" + inAdd + "', "
                + "phone = '" + inPhone + "', "
                + "gmail = '" + inEmail + "', "
                + "password = '" + inPass + "', "
                + "isOwner = '" + ownerInt + "' "
                + "WHERE userID = '" + userID + "';";
        DB.update(updateQuery);

    }

}
