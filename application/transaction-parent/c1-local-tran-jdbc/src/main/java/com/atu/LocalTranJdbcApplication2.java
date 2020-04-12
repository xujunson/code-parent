package com.atu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author: Tom
 * @date: 2020-04-12 10:15
 * @description: 事务的隔离机制如何起作用
 */
public class LocalTranJdbcApplication2 {
    private static final Logger LOG = LoggerFactory.getLogger(LocalTranJdbcApplication.class);

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        LOG.debug("Begin");
        connection.setAutoCommit(false); //关闭自动提交——相当于开启事务

        //FOR UPDATE会把 SELECT 语句的结果锁住不加条件的话会锁住全表
        String query = "SELECT * FROM t_user where id = 1 FOR UPDATE";
        PreparedStatement queryPS = connection.prepareStatement(query);
        ResultSet rs = queryPS.executeQuery();
        Long superManAmount = 0L;
        while (rs.next()) {
            String name = rs.getString(2);
            Long amount = rs.getLong(3);
            LOG.info("{} has amount:{}", name, amount);
            if (name.equals("SuperMan")) {
                superManAmount = amount;
            }
        }
        String plusAmountSQL = "UPDATE T_USER SET amount = ? WHERE username = ?";
        String minusAmountSQL = "UPDATE T_USER SET amount = amount - 100 WHERE username = ?";

        PreparedStatement plusAmountPS = connection.prepareStatement(plusAmountSQL);
        plusAmountPS.setLong(1, superManAmount + 100L);
        plusAmountPS.setString(2, "SuperMan");
        plusAmountPS.executeUpdate();

        //simulateError();

        /*PreparedStatement minusAmountPS = connection.prepareStatement(minusAmountSQL);
        minusAmountPS.setString(1, "BatMan");
        minusAmountPS.executeUpdate();*/

        connection.commit(); //提交
        plusAmountPS.close();
        //minusAmountPS.close();
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

