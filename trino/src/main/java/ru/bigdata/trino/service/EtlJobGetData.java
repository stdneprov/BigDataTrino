package ru.bigdata.trino.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.bigdata.trino.model.SaleDto;
import ru.bigdata.trino.utils.SaleMapper;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class EtlJobGetData {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ClickHouseTableInitializer tableInitializer;

    @Autowired
    ClickHouseDataTransferService dataTransferService;

    // Явный конструктор

    public List<SaleDto> readFromPostgres() {
        String sql = "SELECT * FROM postgres.public.sales";
        return jdbcTemplate.query(sql, new SaleMapper());
    }

    public List<SaleDto> readFromClickhouse() {
        String sql = "SELECT * FROM clickhouse.default.sales";
        return jdbcTemplate.query(sql, new SaleMapper());
    }

    public void getAllData() {
        tableInitializer.createSchema();
        tableInitializer.createTables();
        System.out.println("создали");
        List<SaleDto> postgresData = readFromPostgres();
        for (var sale : postgresData) {
            dataTransferService.transferData(sale);
        }


//        var clickHouseData = readFromClickhouse();
//        System.out.println("Получаем данные из постгреса");
//        postgresData.forEach(System.out::println);
//        System.out.println("Получаем данные из кликхауса");
//        clickHouseData.forEach(System.out::println);
//        System.out.println("получение данных завершено");
    }
}

