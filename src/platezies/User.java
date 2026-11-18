/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platezies;

public class User {
    protected String name;
    protected String username;
    protected String userEmail;
    protected String phone;
    protected String password;

    public User(String name, String username, String userEmail, String phone, String password) {
        this.name = name;
        this.username = username;
        this.userEmail = userEmail;
        this.phone = phone;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}