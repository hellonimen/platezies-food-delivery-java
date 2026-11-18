/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platezies;



/**
 *
 */

class Order {
    String orderItemId;
    String orderId;
    String customerName;
    String orderItem;
    String cuisine;
    String orderType;
    double cost;
    double price;
    String status;
    String orderDate;

    public Order(String orderItemId, String orderId, String customerName, String cuisine, String orderItem, String orderType, double cost, double price, String status, String orderDate) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderItem = orderItem;
        this.cuisine = cuisine;
        this.orderType = orderType;
        this.cost = cost;
        this.price = price;
        this.status = status;
        this.orderDate = orderDate;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }
}

