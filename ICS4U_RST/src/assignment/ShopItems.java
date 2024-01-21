package assignment; 

public class ShopItems {

    private String itemName;
    private int itemPoints;

    public ShopItems(String itemName, int itemPoints) {
        this.itemName = itemName;
        this.itemPoints = itemPoints;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPoints() {
        return itemPoints;
    }

    public void setItemPoints(int itemPoints) {
        this.itemPoints = itemPoints;
    }
}