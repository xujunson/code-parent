package com.atu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Tom
 * @date: 2020-04-12 10:15
 * @description: 使用jdbc实现简单的事务管理
 */
public class LocalTranJdbcApplication {
    private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication.class);

    public static void main(String[] args) throws SQLException {
        String plusAmountSQL = "UPDATE T_USER SET amount = amount + 100 WHERE username = ?";
        String minusAmountSQL = "UPDATE T_USER SET amount = amount - 100 WHERE username = ?";
        Connection connection = getConnection();
        LOG.debug("Begin");
        connection.setAutoCommit(false); //关闭自动提交——相当于开启事务
        PreparedStatement plusAmountPS = connection.prepareStatement(plusAmountSQL);
        plusAmountPS.setString(1, "SuperMan");
        plusAmountPS.executeUpdate();

        //simulateError();

        PreparedStatement minusAmountPS = connection.prepareStatement(minusAmountSQL);
        minusAmountPS.setString(1, "BatMan");
        minusAmountPS.executeUpdate();

        connection.commit(); //提交
        plusAmountPS.close();
        minusAmountPS.close();
        connection.close();
    }

    private static void simulateError() throws SQLException {
        throw new SQLException("Simulate some error!");
    }

    /**
     * 数据库连接
     *
     * @return
     */
    private static Connection getConnection() throws SQLException {
        String DB_DRIVER = "com.mysql.jdbc.Driver";
        String DB_CONNECTION = "jdbc:mysql://localhost:3306/test";
        String DB_USER = "root";
        String DB_PASSWORD = "111111";

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
        return DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
    }
}
