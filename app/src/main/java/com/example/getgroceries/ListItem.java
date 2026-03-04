package com.example.getgroceries;
// This class hold the product id, name, price, image that will be stored in a List on the List page
import java.util.UUID;

public class ListItem
{
    private String id;
    private String productName;
    private Integer productImage;
    private  Double productPrice;

    public ListItem(String productName, Integer productImage, Double productPrice)
    {
        this.id = UUID.randomUUID().toString();
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public String getId()
    {
      return id;
    }

    public String getProductName()
    {
        return productName;
    }

    public Integer getProductImage()
    {
        return productImage;
    }

    public  Double getProductPrice()
    {
        return productPrice;
    }

}
