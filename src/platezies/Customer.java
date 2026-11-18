package platezies;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/**
 *
 */
public class Customer extends User {
    
    private double eWalletBal;

    public Customer(String name, String username, String userEmail, String phone, String password, double eWalletBal) {
        super(name, username, userEmail, phone, password);
        this.eWalletBal = eWalletBal;
    }

    // Getter for wallet balance
    public double getWalletBal() {
        return eWalletBal;
    }

    // Setter to update wallet balance
    public void setWalletBal(double eWalletBal) {
        this.eWalletBal = eWalletBal;
    }
    
    // Reads customer details from file and returns a Customer object if found
    public static Customer getCustomerFromFile(String relativePath, String email, String password) {
        String filePath = Paths.get(relativePath).toAbsolutePath().toString(); // Convert relative path to absolute
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails.length >= 6) {
                    String fileEmail = userDetails[2].trim();
                    String filePassword = userDetails[4].trim();

                    if (fileEmail.equals(email) && filePassword.equals(password)) {
                        return new Customer(
                            userDetails[0].trim(),  // Name
                            userDetails[1].trim(),  // Username
                            fileEmail,              // Email
                            userDetails[3].trim(),  // Phone
                            filePassword,           // Password
                            Double.parseDouble(userDetails[5].trim())  // Fix: Convert eWalletBal to double
                        );
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading customer file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid eWallet balance format in file.");
        }
        return null;
    }
}
