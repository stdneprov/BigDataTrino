package ru.bigdata.trino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.bigdata.trino.model.SaleDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClickHouseDataTransferService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClickHouseDataTransferService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void transferData(SaleDto t) {
        int petTypeId = getOrCreatePetTypeId(t.getCustomerPetType());
        int petBreedId = getOrCreatePetBreedId(t.getCustomerPetBreed(), petTypeId);
        int petId = getOrCreatePetId(t.getCustomerPetName(), petBreedId);

        int customerCountryId = getOrCreateCountryId(t.getCustomerCountry());
        int sellerCountryId = getOrCreateCountryId(t.getSellerCountry());
        int storeCountryId = getOrCreateCountryId(t.getStoreCountry());
        int supplierCountryId = getOrCreateCountryId(t.getSupplierCountry());

        int storeCityId = getOrCreateCityId(t.getStoreCity());
        int storeStateId = getOrCreateStateId(t.getStoreState());
        int supplierCityId = getOrCreateCityId(t.getSupplierCity());

        int customerId = getOrCreateCustomerId(t.getCustomerFirstName(), t.getCustomerLastName(), t.getCustomerEmail(), t.getCustomerAge(), customerCountryId, t.getCustomerPostalCode(), petId);
        int sellerId = getOrCreateSellerId(t.getSellerFirstName(), t.getSellerLastName(), t.getSellerEmail(), sellerCountryId, t.getSellerPostalCode());
        int storeId = getOrCreateStoreId(t.getStoreName(), t.getStoreLocation(), storeCountryId, storeStateId, storeCityId, t.getStorePhone(), t.getStoreEmail());

        int productCategoryId = getOrCreateProductCategoryId(t.getProductCategory());
        int petCategoryId = getOrCreatePetCategoryId(t.getPetCategory());
        int colorId = getOrCreateColorId(t.getProductColor());
        int brandId = getOrCreateBrandId(t.getProductBrand());
        int materialId = getOrCreateMaterialId(t.getProductMaterial());
        int supplierId = getOrCreateSupplierId(t.getSupplierName(), t.getSupplierContact(), t.getSupplierEmail(), t.getSupplierPhone(), t.getSupplierAddress(), supplierCityId, supplierCountryId);

        int productId = getOrCreateProductId(t, petCategoryId, productCategoryId, colorId, brandId, materialId, supplierId);

        List<Object[]> batchArgs = new ArrayList<>();
        batchArgs.add(new Object[] {
                customerId, sellerId, productId, storeId,
                t.getSaleQuantity(), t.getSaleTotalPrice(), convertToSqlDate(t.getSaleDate())
        });

        jdbcTemplate.batchUpdate(
                "INSERT INTO clickhouse.snowflake.fact_sales (customer_id, seller_id, product_id, store_id, quantity, total_price, date) VALUES (?, ?, ?, ?, ?, ?, ?)",
                batchArgs
        );
    }

    // ======================= ID Methods =======================

    private int getOrCreatePetTypeId(String name) {
        return getOrInsert("dim_pet_types", "pet_type_id", "name", name);
    }

    private int getOrCreatePetBreedId(String name, int petTypeId) {
        return getOrInsert("dim_pet_breeds", "pet_breed_id", new String[]{"name", "pet_type_id"}, new Object[]{name, petTypeId});
    }

    private int getOrCreatePetId(String name, int breedId) {
        return getOrInsert("dim_pets", "pet_id", new String[]{"name", "breed_id"}, new Object[]{name, breedId});
    }

    private int getOrCreateCountryId(String name) {
        return getOrInsert("dim_countries", "country_id", "name", name);
    }

    private int getOrCreateStateId(String name) {
        return getOrInsert("dim_states", "state_id", "name", name);
    }

    private int getOrCreateCityId(String name) {
        return getOrInsert("dim_cities", "city_id", "name", name);
    }

    private int getOrCreateCustomerId(String firstName, String lastName, String email, int age, int countryId, String postalCode, int petId) {
        Integer id = getId("dim_customers", "customer_id", "email", email);
        if (id != null) return id;

        jdbcTemplate.update(
                "INSERT INTO clickhouse.snowflake.dim_customers (first_name, last_name, email, age, country_id, postal_code, pet_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                firstName, lastName, email, age, countryId, postalCode, petId
        );
        return getId("dim_customers", "customer_id", "email", email);
    }

    private int getOrCreateSellerId(String firstName, String lastName, String email, int countryId, String postalCode) {
        Integer id = getId("dim_sellers", "seller_id", "email", email);
        if (id != null) return id;

        jdbcTemplate.update(
                "INSERT INTO clickhouse.snowflake.dim_sellers (first_name, last_name, email, country_id, postal_code) VALUES (?, ?, ?, ?, ?)",
                firstName, lastName, email, countryId, postalCode
        );
        return getId("dim_sellers", "seller_id", "email", email);
    }

    private int getOrCreateStoreId(String name, String location, int countryId, int stateId, int cityId, String phone, String email) {
        Integer id = getId("dim_stores", "store_id", "name", name);
        if (id != null) return id;

        jdbcTemplate.update(
                "INSERT INTO clickhouse.snowflake.dim_stores (name, location, country_id, state_id, city_id, phone, email) VALUES (?, ?, ?, ?, ?, ?, ?)",
                name, location, countryId, stateId, cityId, phone, email
        );
        return getId("dim_stores", "store_id", "name", name);
    }

    private int getOrCreateProductCategoryId(String name) {
        return getOrInsert("dim_product_categories", "category_id", "name", name);
    }

    private int getOrCreatePetCategoryId(String name) {
        return getOrInsert("dim_pet_categories", "pet_category_id", "name", name);
    }

    private int getOrCreateColorId(String name) {
        return getOrInsert("dim_colors", "color_id", "name", name);
    }

    private int getOrCreateBrandId(String name) {
        return getOrInsert("dim_brands", "brand_id", "name", name);
    }

    private int getOrCreateMaterialId(String name) {
        return getOrInsert("dim_materials", "material_id", "name", name);
    }

    private int getOrCreateSupplierId(String name, String contact, String email, String phone, String address, int cityId, int countryId) {
        Integer id = getId("dim_suppliers", "supplier_id", "email", email);
        if (id != null) return id;

        jdbcTemplate.update(
                "INSERT INTO clickhouse.snowflake.dim_suppliers (name, contact, email, phone, address, city_id, country_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
                name, contact, email, phone, address, cityId, countryId
        );
        return getId("dim_suppliers", "supplier_id", "email", email);
    }

    private int getOrCreateProductId(SaleDto t, int petCategoryId, int categoryId, int colorId, int brandId, int materialId, int supplierId) {
        Integer id = getId("dim_products", "product_id", "name", t.getProductName());
        if (id != null) return id;

        jdbcTemplate.update("""
            INSERT INTO clickhouse.snowflake.dim_products 
            (name, pet_category_id, category_id, price, weight, color_id, size, brand_id, material_id, description, rating, reviews, release_date, expiry_date, supplier_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """,
                t.getProductName(), petCategoryId, categoryId, t.getProductPrice(), t.getProductWeight(),
                colorId, t.getProductSize(), brandId, materialId, t.getProductDescription(),
                t.getProductRating(), t.getProductReviews(),
                convertToSqlDate(t.getProductReleaseDate()), convertToSqlDate(t.getProductExpiryDate()), supplierId
        );
        return getId("dim_products", "product_id", "name", t.getProductName());
    }

    // ======================= Utility =======================

    private int getOrInsert(String table, String idCol, String nameCol, String value) {
        Integer id = getId(table, idCol, nameCol, value);
        if (id != null) return id;

        jdbcTemplate.update("INSERT INTO clickhouse.snowflake." + table + " (" + nameCol + ") VALUES (?)", value);
        return getId(table, idCol, nameCol, value);
    }

    private int getOrInsert(String table, String idCol, String[] cols, Object[] vals) {
        String where = cols[0] + " = ?";
        for (int i = 1; i < cols.length; i++) where += " AND " + cols[i] + " = ?";
        String colList = String.join(", ", cols);
        String placeholders = "?".repeat(cols.length).replaceAll("(.)", "$1,").replaceAll(",$", "");

        Integer id = queryForId("SELECT " + idCol + " FROM clickhouse.snowflake." + table + " WHERE " + where, vals);
        if (id != null) return id;

        jdbcTemplate.update("INSERT INTO clickhouse.snowflake." + table + " (" + colList + ") VALUES (" + placeholders + ")", vals);
        return queryForId("SELECT " + idCol + " FROM clickhouse.snowflake." + table + " WHERE " + where, vals);
    }

    private Integer getId(String table, String idCol, String keyCol, String value) {
        return queryForId("SELECT " + idCol + " FROM clickhouse.snowflake." + table + " WHERE " + keyCol + " = ?", new Object[]{value});
    }

    private Integer queryForId(String sql, Object[] args) {
        try {
            return jdbcTemplate.queryForObject(sql, args, Integer.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Date convertToSqlDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        return Date.valueOf(dateStr);
    }
}
