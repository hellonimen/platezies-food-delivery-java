/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platezies;



/**
 *
 */
public class Notification {
   
    private String recipient; // The "To" field
    private String title;
    private String time;
    private String date;

    // Constructor
    public Notification(String recipient, String title, String date, String time) {
        this.recipient = recipient;
        this.title = title;
        this.date = date;
        this.time = time;
   
    }

    // Getters
    public String getRecipient() {
        return recipient;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }
    
    public String getDate(){
        return date;
    }

    // Setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    // Parse a notification from a CSV string
    public static Notification fromCsv(String csv) {
        String[] parts = csv.split(",");
        if (parts.length == 4) {
            return new Notification(parts[0], parts[1], parts[2],parts[3]);
        }
        throw new IllegalArgumentException("Invalid CSV format for Notification");
    }

    // Convert the notification to a CSV string
    @Override
    public String toString() {
        return recipient + "," + title + "," + date + ","+ time;
    }

    public String toCsv() {
        return recipient + "," + title + "," + date + ","+ time;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Notification other = (Notification) obj;
        return recipient.equals(other.recipient) &&
               title.equals(other.title) &&
               date.equals(other.date) &&
               time.equals(other.time);
    }

    @Override
    public int hashCode() {
        return recipient.hashCode() + title.hashCode() + date.hashCode() + time.hashCode();
    }

}

    

