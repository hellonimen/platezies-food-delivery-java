package platezies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;


public class Vendor extends User {
    private String cuisine;

    public Vendor(String name, String username, String userEmail, String phone, String password, String cuisine) {
        super(name, username, userEmail, phone, password);
        this.cuisine = cuisine;
    }

    public String getCuisine() {
        return cuisine;
    }
    
    public static Vendor getVendorFromFile(String relativePath, String email, String password) {
        String filePath = Paths.get(relativePath).toAbsolutePath().toString(); // Convert relative path to absolute
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length >= 6) {
                    String fileEmail = userDetails[2].trim();
                    String filePassword = userDetails[4].trim();

                    if (fileEmail.equals(email) && filePassword.equals(password)) {
                        return new Vendor(
                            userDetails[0].trim(),  
                            userDetails[1].trim(),  
                            fileEmail,              
                            userDetails[3].trim(),  
                            filePassword,           
                            userDetails[5].trim()   
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading vendor file: " + e.getMessage());
        }
        return null;
    
    }
}
