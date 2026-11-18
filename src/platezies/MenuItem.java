package platezies;


public class MenuItem {
    private String cuisine;
    private String name;
    private double cost;
    private double price;
    private String imagePath;

    public MenuItem(String cuisine, String name, double cost, double price, String imagePath) {
        this.cuisine = cuisine;
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.imagePath = imagePath;
    }

    

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getCost() {
        return cost;
    }
    public double getPrice() {
        return price;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return cuisine + "," + name + "," + cost + "," + price + "," + imagePath;
    }

    public static MenuItem fromString(String line) {
        String[] parts = line.split(",", 5);
        if (parts.length == 5) {
            try {
                double cost = Double.parseDouble(parts[2]);
                double price = Double.parseDouble(parts[3]);
                return new MenuItem(parts[0], parts[1], cost, price, parts[4]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    
}

