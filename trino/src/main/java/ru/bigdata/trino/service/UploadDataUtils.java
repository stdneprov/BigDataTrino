package ru.bigdata.trino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UploadDataUtils {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UploadDataUtils(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static String convertToDateFormat(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("M/d/yyyy");
            Date date = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static java.sql.Date convertToSqlDate(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("M/d/yyyy");
            java.util.Date parsed = inputFormat.parse(dateString);
            return new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getOrCreatePetTypeId(String petType) {
        return getOrCreateId(
                "SELECT pet_type_id FROM clickhouse.snowflake.dim_pet_types WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_pet_types (name) VALUES (?)",
                "SELECT pet_type_id FROM clickhouse.snowflake.dim_pet_types WHERE name = ?",
                petType
        );
    }

    public int getOrCreatePetBreedId(String petBreed) {
        return getOrCreateId(
                "SELECT pet_breed_id FROM clickhouse.snowflake.dim_pet_breeds WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_pet_breeds (name) VALUES (?)",
                "SELECT pet_breed_id FROM clickhouse.snowflake.dim_pet_breeds WHERE name = ?",
                petBreed
        );
    }

    public int getOrCreatePetId(String petName) {
        return getOrCreateId(
                "SELECT pet_id FROM clickhouse.snowflake.dim_pets WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_pets (name) VALUES (?)",
                "SELECT pet_id FROM clickhouse.snowflake.dim_pets WHERE name = ?",
                petName
        );
    }

    public int getOrCreateCountryId(String country) {
        return getOrCreateId(
                "SELECT country_id FROM clickhouse.snowflake.dim_countries WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_countries (name) VALUES (?)",
                "SELECT country_id FROM clickhouse.snowflake.dim_countries WHERE name = ?",
                country
        );
    }

    public int getOrCreateCityId(String city) {
        return getOrCreateId(
                "SELECT city_id FROM clickhouse.snowflake.dim_cities WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_cities (name) VALUES (?)",
                "SELECT city_id FROM clickhouse.snowflake.dim_cities WHERE name = ?",
                city
        );
    }

    public int getOrCreateStateId(String state) {
        return getOrCreateId(
                "SELECT state_id FROM clickhouse.snowflake.dim_states WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_states (name) VALUES (?)",
                "SELECT state_id FROM clickhouse.snowflake.dim_states WHERE name = ?",
                state
        );
    }

    public int getOrCreateSellerId(String email) {
        return getOrCreateId(
                "SELECT seller_id FROM clickhouse.snowflake.dim_sellers WHERE email = ?",
                "INSERT INTO clickhouse.snowflake.dim_sellers (email) VALUES (?)",
                "SELECT seller_id FROM clickhouse.snowflake.dim_sellers WHERE email = ?",
                email
        );
    }

    public int getOrCreateStoreId(String name) {
        return getOrCreateId(
                "SELECT store_id FROM clickhouse.snowflake.dim_stores WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_stores (name) VALUES (?)",
                "SELECT store_id FROM clickhouse.snowflake.dim_stores WHERE name = ?",
                name
        );
    }

    public int getOrCreateProductCategoryId(String name) {
        return getOrCreateId(
                "SELECT category_id FROM clickhouse.snowflake.dim_product_categories WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_product_categories (name) VALUES (?)",
                "SELECT category_id FROM clickhouse.snowflake.dim_product_categories WHERE name = ?",
                name
        );
    }

    public int getOrCreatePetCategoryId(String name) {
        return getOrCreateId(
                "SELECT pet_category_id FROM clickhouse.snowflake.dim_pet_categories WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_pet_categories (name) VALUES (?)",
                "SELECT pet_category_id FROM clickhouse.snowflake.dim_pet_categories WHERE name = ?",
                name
        );
    }

    public int getOrCreateSupplierId(String email) {
        return getOrCreateId(
                "SELECT supplier_id FROM clickhouse.snowflake.dim_suppliers WHERE email = ?",
                "INSERT INTO clickhouse.snowflake.dim_suppliers (email) VALUES (?)",
                "SELECT supplier_id FROM clickhouse.snowflake.dim_suppliers WHERE email = ?",
                email
        );
    }

    public int getOrCreateColorId(String name) {
        return getOrCreateId(
                "SELECT color_id FROM clickhouse.snowflake.dim_colors WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_colors (name) VALUES (?)",
                "SELECT color_id FROM clickhouse.snowflake.dim_colors WHERE name = ?",
                name
        );
    }

    public int getOrCreateBrandId(String name) {
        return getOrCreateId(
                "SELECT brand_id FROM clickhouse.snowflake.dim_brands WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_brands (name) VALUES (?)",
                "SELECT brand_id FROM clickhouse.snowflake.dim_brands WHERE name = ?",
                name
        );
    }

    public int getOrCreateMaterialId(String name) {
        return getOrCreateId(
                "SELECT material_id FROM clickhouse.snowflake.dim_materials WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_materials (name) VALUES (?)",
                "SELECT material_id FROM clickhouse.snowflake.dim_materials WHERE name = ?",
                name
        );
    }

    public int getOrCreateProductId(String name) {
        return getOrCreateId(
                "SELECT product_id FROM clickhouse.snowflake.dim_products WHERE name = ?",
                "INSERT INTO clickhouse.snowflake.dim_products (name) VALUES (?)",
                "SELECT product_id FROM clickhouse.snowflake.dim_products WHERE name = ?",
                name
        );
    }

    public int getOrCreateCustomerId(String fullName) {
        String[] parts = fullName.split(" ");
        String firstName = parts[0];
        String lastName = parts.length > 1 ? parts[1] : "";

        return getOrCreateId(
                "SELECT customer_id FROM clickhouse.snowflake.dim_customers WHERE first_name = ? AND last_name = ?",
                "INSERT INTO clickhouse.snowflake.dim_customers (first_name, last_name) VALUES (?, ?)",
                "SELECT customer_id FROM clickhouse.snowflake.dim_customers WHERE first_name = ? AND last_name = ?",
                firstName, lastName
        );
    }

    public int getOrCreateId(String selectQuery, String insertQuery, String finalSelectQuery, Object... params) {
        try {
            List<Integer> ids = jdbcTemplate.query(selectQuery, params, (rs, rowNum) -> rs.getInt(1));
            if (!ids.isEmpty()) {
                return ids.get(0); // Берём первый ID, если их несколько
            }

            jdbcTemplate.update(insertQuery, params);

            List<Integer> newIds = jdbcTemplate.query(finalSelectQuery, params, (rs, rowNum) -> rs.getInt(1));
            return newIds.isEmpty() ? -1 : newIds.get(0);

        } catch (DataAccessException e) {
            System.err.println("Ошибка в getOrCreateId: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}
