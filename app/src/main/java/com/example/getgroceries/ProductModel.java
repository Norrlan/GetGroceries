// This porivdes the structure that holds data fetched from the API for the products under each subcategory
/*
The list of products for each subcategory should contain: Name of the product,
*  button called save to list,image of the product, maybe price(if the API offers it
) */
package com.example.getgroceries;

public class ProductModel
{
 String name;
 String detail;
 int imageId;
 //String price; incase we find API containing the prices for Grocery items
 //String store; for store comparison. the APIs i have do not provide the store name.
 public ProductModel (String name, String price, int imageId)
 {
     this.name = name;
     this.imageId = imageId;
     this.detail = detail;
     //this.price = price;
 }
}
