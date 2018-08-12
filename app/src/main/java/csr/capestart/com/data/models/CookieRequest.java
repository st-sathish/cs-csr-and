package csr.capestart.com.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import csr.capestart.com.extras.SessionStore;

public class CookieRequest {

    @Expose
    @SerializedName("item_name")
    private String itemName;

    @Expose
    @SerializedName("barcode")
    private String barcode;

    @Expose
    @SerializedName("selling_price")
    private String sellingPrice;

    @Expose
    @SerializedName("purchase_price")
    private String purchasePrice;

    @Expose
    @SerializedName("expiry_date")
    private String expiryDate;

    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("category")
    private String category;

    public CookieRequest(String itemName, String barcode, String purchasePrice, String sellingPrice, String expiryDate, String category) {
        this.itemName = itemName;
        this.barcode = barcode;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.expiryDate = expiryDate;
        this.category = category;
        this.username = SessionStore.user.getEmail();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
