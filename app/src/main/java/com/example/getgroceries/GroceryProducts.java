package com.example.getgroceries;

public class GroceryProducts

{
    //initialize fields that match the Json models I need
    String namefield;// c
    String productcategory; // e.g: Chilled food
    String  pricefield;//
    String currencyfield;// in pounds
    String imageUrl;// for the picture of product
    String storeName;// Amamzon or Walmart or Asda or
    String storelink ; // direct link to the product on the website of the store


   //  Empty Constructor for Firebase
    GroceryProducts()
    {

    }

    //getters
    public  String getNamefield() { return namefield; }

    public  String getPricefield() {return pricefield;}
    public String getCurrencyfield () {return currencyfield;}
    public String getImageUrl () {return imageUrl;}
    public String getStoreName () {return storeName;}
    public String getStorelink () {return storelink;}
    public String getProductcategory () {return productcategory;}

    //setters

    public void setNamefield(String setnamefield1) {
        this.namefield = setnamefield1;
    }
    public void setPricefield (String pricefield1)
    {
        this.pricefield = pricefield1;
    }
    public void setCurrencyfield(String currencyfield1)
    {
        this.currencyfield  = currencyfield1;
    }
    public void setImageUrl(String imageUrl1)
    {
        this.imageUrl = imageUrl1;
    }
    public void setStoreName(String storeName1)
    {
        this.storeName  = storeName1;
    }
    public void setStorelink(String storelink1)
    {
        this.storelink  = storelink1;
    }

    public void setProductcategory(String productcategory1)
    {
        this.productcategory  = productcategory1;
    }



}
