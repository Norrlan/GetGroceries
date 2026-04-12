package com.example.getgroceries;

public class GroceryProducts
// This class represents a grocery product stored in the Firestore database

{
    //initialize fields that match the Json models I need
    private String namefield;// name of the Product eg/: Pepperoni pizza
    private String productcategory; // e.g: Chilled food
    private String  pricefield;// price of product
    private String currencyfield;// in pounds
    private String imageUrl;// for the picture of product
    private String storeLogo;
    private String storeName;// Amamzon,Walmart, Asda
    private String storelink ; // direct link to the product on the website of the store



    public GroceryProducts()
    {

    }


    //getters
    public  String getNamefield()
    {
        return namefield;
    }

    public  String getPricefield()
    {
        return pricefield;
    }
    public String getCurrencyfield ()
    {
       return currencyfield;
    }
    public String getImageUrl ()
    {
        return imageUrl;
    }
    public String getStoreName ()
    {
        return storeName;
    }
    public String getStorelink ()
    {
        return storelink;
    }
    public String getProductcategory ()
    {
        return productcategory;
    }
    public int getStoreLogo () // /  Method to match the store name with the logog
    {

        if (storeName.equalsIgnoreCase("Amazon")) return R.drawable.amazon;
        if (storeName.equalsIgnoreCase("Walmart")) return R.drawable.walmart_logo;
        if (storeName.equalsIgnoreCase("Asda")) return R.drawable.asda_logo;
        if(storeName.equalsIgnoreCase("Ebay"))return R.drawable.ebay;
        if (storeName.equalsIgnoreCase("Tesco")) return  R.drawable.tesco;
        return R.drawable.default_store_logo;
    }

    //setters

    public void setNamefield(String setnamefield)
    {

        this.namefield = setnamefield;
    }
    public void setPricefield (String setpricefield)
    {

        this.pricefield = setpricefield;
    }
    public void setCurrencyfield(String setcurrencyfield)
    {
        this.currencyfield  = setcurrencyfield;
    }
    public void setImageUrl(String setimageUrl)
    {
        this.imageUrl = setimageUrl;
    }
    public void setStoreName(String setstoreName)
    {
        this.storeName  = setstoreName;
    }
    public void setStorelink(String setstorelink)
    {
        this.storelink  = setstorelink;
    }

    public void setProductcategory(String setproductcategory)
    {
        this.productcategory  = setproductcategory;
    }

    public void  setStoreLogo(String setstoreLogo)
    {
        this.storeLogo = setstoreLogo;
    }


}
