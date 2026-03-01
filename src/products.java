import java.io.File;
import java.util.Scanner;

public class products {
    String name;
    String imagePath;
    String details;
    double price;

    products(String name, String imagePath, String details, double price) {
        this.name = name;
        this.imagePath = imagePath;
        this.details = details;
        this.price = price;
    }
    products(){}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDetails() {
        return details;
    }
    public void setDetails(String details) {
        this.details = details;
    }

    public void readProductCSV(File file) {
        
    }


}
