/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.working;

import Backend.dbManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Kairav
 */
public class User {

    public void setUserID(int userID) {
        this.userID = userID;
    }

    private boolean signedin;

    private boolean owner;
    private int userID;
    private String fullname;
    private String phoneNumber;
    private String address;
    private String email;
    private String password;


    public boolean createUser(boolean isOwner, String fullname, String phoneNumber, String address, String email, String password, dbManager inManager) throws SQLException {
        this.owner = isOwner;
        this.fullname = fullname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        return inManager.addNewUser(fullname, address, phoneNumber, email, password, isOwner);

    }

    public int getCartID(dbManager inManager) throws SQLException{
       return inManager.getUserCartID(userID);
    }
    public void updateUser(boolean inIsOwner, String inFullname, String inPhoneNumber, String inAddress, String inEmail, String inPassword, dbManager inManager) throws SQLException {
        this.owner = inIsOwner;
        this.fullname = inFullname;
        this.phoneNumber = inPhoneNumber;
        this.address = inAddress;
        this.email = inEmail;
        this.password = inPassword;
        inManager.updateUser(inFullname, inEmail, inPhoneNumber, inEmail, inPassword, inIsOwner, findUserID(inManager, inEmail));
    }

public Boolean login(String inEmail, String inPassword, dbManager inManager) throws SQLException {
    // Call the login method from dbManager to execute the query
    ResultSet rs = inManager.login(inEmail, inPassword);
    // Check if the result set has data
    if (rs.next()) {
        // Retrieve data from ResultSet
        this.userID = rs.getInt("userID"); // Assuming userID is an int
        this.fullname = rs.getString("fullname");
        this.address = rs.getString("address");
        this.phoneNumber = rs.getString("phone");
        this.email = rs.getString("email");
        this.password = rs.getString("password");
        this.owner = rs.getBoolean("isOwner"); // Assuming isOwner is a boolean
        return true;
    } else {
        // Handle case where no user data is found (e.g., invalid credentials)
        return false;
        // You can throw an exception or handle the login failure
    }
}


    public boolean isOwner() {
        return owner;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return this.userID;
    }
    public String getEmail() {
        return email;
    }

    public void setOwner(boolean isOwner, dbManager inManager) {
        this.owner = isOwner;
    }

    public void setSignedin(boolean signedin) {
        this.signedin = signedin;
    }

    public User(boolean signedin) {
        this.signedin = signedin;
    }

    public int findUserID(dbManager inManager, String inEmail) throws SQLException {
        this.userID = inManager.getIDofUser(inEmail);
        return this.userID;
    }


    public boolean isSignedin() {
        return signedin;
    }

}
