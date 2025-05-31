package ru.bigdata.trino.model;

import lombok.*;


@Data
@RequiredArgsConstructor
public class SaleDto {
    private Long id;
    private String customerFirstName;
    private String customerLastName;
    private Integer customerAge;
    private String customerEmail;
    private String customerCountry;
    private String customerPostalCode;
    private String customerPetType;
    private String customerPetName;
    private String customerPetBreed;
    private String sellerFirstName;
    private String sellerLastName;
    private String sellerEmail;
    private String sellerCountry;
    private String sellerPostalCode;
    private String productName;
    private String productCategory;
    private Double productPrice;
    private Integer productQuantity;
    private String saleDate;
    private Long saleCustomerId;
    private Long saleSellerId;
    private Long saleProductId;
    private Integer saleQuantity;
    private Double saleTotalPrice;
    private String storeName;
    private String storeLocation;
    private String storeCity;
    private String storeState;
    private String storeCountry;
    private String storePhone;
    private String storeEmail;
    private String petCategory;
    private Double productWeight;
    private String productColor;
    private String productSize;
    private String productBrand;
    private String productMaterial;
    private String productDescription;
    private Double productRating;
    private Integer productReviews;
    private String productReleaseDate;
    private String productExpiryDate;
    private String supplierName;
    private String supplierContact;
    private String supplierEmail;
    private String supplierPhone;
    private String supplierAddress;
    private String supplierCity;
    private String supplierCountry;


    public SaleDto(Long id, String customerFirstName, String customerLastName, Integer customerAge,
                   String customerEmail, String customerCountry, String customerPostalCode,
                   String customerPetType, String customerPetName, String customerPetBreed,
                   String sellerFirstName, String sellerLastName, String sellerEmail,
                   String sellerCountry, String sellerPostalCode, String productName,
                   String productCategory, Double productPrice, Integer productQuantity,
                   String saleDate, Long saleCustomerId, Long saleSellerId, Long saleProductId,
                   Integer saleQuantity, Double saleTotalPrice, String storeName,
                   String storeLocation, String storeCity, String storeState,
                   String storeCountry, String storePhone, String storeEmail,
                   String petCategory, Double productWeight, String productColor,
                   String productSize, String productBrand, String productMaterial,
                   String productDescription, Double productRating, Integer productReviews,
                   String productReleaseDate, String productExpiryDate, String supplierName,
                   String supplierContact, String supplierEmail, String supplierPhone,
                   String supplierAddress, String supplierCity, String supplierCountry) {
        this.id = id;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerAge = customerAge;
        this.customerEmail = customerEmail;
        this.customerCountry = customerCountry;
        this.customerPostalCode = customerPostalCode;
        this.customerPetType = customerPetType;
        this.customerPetName = customerPetName;
        this.customerPetBreed = customerPetBreed;
        this.sellerFirstName = sellerFirstName;
        this.sellerLastName = sellerLastName;
        this.sellerEmail = sellerEmail;
        this.sellerCountry = sellerCountry;
        this.sellerPostalCode = sellerPostalCode;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.saleDate = saleDate;
        this.saleCustomerId = saleCustomerId;
        this.saleSellerId = saleSellerId;
        this.saleProductId = saleProductId;
        this.saleQuantity = saleQuantity;
        this.saleTotalPrice = saleTotalPrice;
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storeCity = storeCity;
        this.storeState = storeState;
        this.storeCountry = storeCountry;
        this.storePhone = storePhone;
        this.storeEmail = storeEmail;
        this.petCategory = petCategory;
        this.productWeight = productWeight;
        this.productColor = productColor;
        this.productSize = productSize;
        this.productBrand = productBrand;
        this.productMaterial = productMaterial;
        this.productDescription = productDescription;
        this.productRating = productRating;
        this.productReviews = productReviews;
        this.productReleaseDate = productReleaseDate;
        this.productExpiryDate = productExpiryDate;
        this.supplierName = supplierName;
        this.supplierContact = supplierContact;
        this.supplierEmail = supplierEmail;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
        this.supplierCity = supplierCity;
        this.supplierCountry = supplierCountry;
    }
}
