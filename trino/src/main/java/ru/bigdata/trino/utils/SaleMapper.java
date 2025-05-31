package ru.bigdata.trino.utils;

import jakarta.annotation.Nonnull;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.bigdata.trino.model.SaleDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SaleMapper implements RowMapper<SaleDto> {

    @Override
    public SaleDto mapRow(@Nonnull ResultSet rs, int rowNum) throws SQLException {
        return new SaleDto(rs.getLong("id"),
                rs.getString("customer_first_name"),
                rs.getString("customer_last_name"),
                rs.getInt("customer_age"),
                rs.getString("customer_email"),
                rs.getString("customer_country"),
                rs.getString("customer_postal_code"),
                rs.getString("customer_pet_type"),
                rs.getString("customer_pet_name"),
                rs.getString("customer_pet_breed"),
                rs.getString("seller_first_name"),
                rs.getString("seller_last_name"),
                rs.getString("seller_email"),
                rs.getString("seller_country"),
                rs.getString("seller_postal_code"),
                rs.getString("product_name"),
                rs.getString("product_category"),
                rs.getDouble("product_price"),
                rs.getInt("product_quantity"),
                rs.getString("sale_date"),
                rs.getLong("sale_customer_id"),
                rs.getLong("sale_seller_id"),
                rs.getLong("sale_product_id"),
                rs.getInt("sale_quantity"),
                rs.getDouble("sale_total_price"),
                rs.getString("store_name"),
                rs.getString("store_location"),
                rs.getString("store_city"),
                rs.getString("store_state"),
                rs.getString("store_country"),
                rs.getString("store_phone"),
                rs.getString("store_email"),
                rs.getString("pet_category"),
                rs.getDouble("product_weight"),
                rs.getString("product_color"),
                rs.getString("product_size"),
                rs.getString("product_brand"),
                rs.getString("product_material"),
                rs.getString("product_description"),
                rs.getDouble("product_rating"),
                rs.getInt("product_reviews"),
                rs.getString("product_release_date"),
                rs.getString("product_expiry_date"),
                rs.getString("supplier_name"),
                rs.getString("supplier_contact"),
                rs.getString("supplier_email"),
                rs.getString("supplier_phone"),
                rs.getString("supplier_address"),
                rs.getString("supplier_city"),
                rs.getString("supplier_country"));
    }
}
