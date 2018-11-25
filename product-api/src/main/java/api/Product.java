package api;

public class Product {

    private final String ownerName;

    private final String productName;

    public Product(String ownerName, String productName) {
        this.ownerName = ownerName;
        this.productName = productName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getProductName() {
        return productName;
    }
}
