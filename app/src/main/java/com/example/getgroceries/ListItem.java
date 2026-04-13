package com.example.getgroceries;
// This class hold the product id, name, price, image that will be stored in a List on the List page
//
import java.util.UUID;

public class ListItem
{
    private String id;
    private String productName;
    private String productImageUrl;
    private String productPrice;
    private String storeLink;
    private String productCategory;
    private String productCurrency;

    public ListItem() {}

    public ListItem(GroceryProducts p) // ListItem constructor
    {
        this.id = UUID.randomUUID().toString();
        this.productName = p.getNamefield();
        this.productImageUrl = p.getImageUrl();
        this.productPrice = p.getPricefield();
        this.storeLink = p.getStorelink();
        this.productCategory = p.getProductcategory();
        this.productCurrency = p.getCurrencyfield();
    }


    //Getters

    public String getId()
    {
      return id;
    }

    public String getProductName()
    {
        return productName;
    }

    public String getProductImageUrl()
    {
        return productImageUrl;
    }

    public String getProductPrice()
    {
        return productPrice;
    }
    public  String getProductCurrency()
    {
        return productCurrency;
    }
    public  String getGetStoreLink()
    {
        return storeLink;
    }

    public String getProductCategory()
    {
        return productCategory;
    }

    //setters
    public void setId(String id)
    {
        this.id = id;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public void setProductImageUrl(String productImageUrl)
    {
        this.productImageUrl = productImageUrl;
    }
    public void setProductPrice(String productPrice)
    {
        this.productPrice = productPrice;
    }
    public void setProductCurrency(String productCurrency)
    {
        this.productCurrency = productCurrency;
    }
    public void setStoreLink(String storeLink)
    {
        this.storeLink = storeLink;
    }
    public void setProductCategory(String productCategory)
    {
        this.productCategory = productCategory;
    }
}
