package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    @SneakyThrows
    public static void cleanDatabase() {
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        String deleteOrderEntity = "DELETE FROM order_entity";
        String deletePaymentEntity = "DELETE FROM payment_entity";
        QueryRunner runner = new QueryRunner();
        Connection conn = DriverManager.getConnection(url, user, password);
        runner.update(conn, deleteCreditRequestEntity);
        runner.update(conn, deleteOrderEntity);
        runner.update(conn, deletePaymentEntity);
        conn.close();
    }

    public static String getDebitStatusFromDatabase() {
        String codesSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getDataFromDatabase(codesSQL);
    }

    public static String getCreditStatusFromDatabase() {
        String codesSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getDataFromDatabase(codesSQL);
    }

    @SneakyThrows
    public static long getOrdersFromDatabase() {
        String codesSQL = "SELECT COUNT(*) FROM order_entity";
        Connection conn = DriverManager.getConnection(url, user, password);
        QueryRunner runner = new QueryRunner();
        return runner.query(conn, codesSQL, new ScalarHandler<>());
    }

    @SneakyThrows
    private static String getDataFromDatabase(String query) {
        QueryRunner runner = new QueryRunner();
        String data = "";
        Connection conn = DriverManager.getConnection(url, user, password);
        data = runner.query(conn, query, new ScalarHandler<>());
        return data;
    }
}
