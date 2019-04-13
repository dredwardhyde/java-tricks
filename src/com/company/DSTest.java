package com.company;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.UUID;

public class DSTest {
    public static void main(String[] args) {

        try {
            ComboPooledDataSource ods = new ComboPooledDataSource();
            ods.setDriverClass("oracle.jdbc.pool.OracleDataSource");
            ods.setJdbcUrl("jdbc:oracle:thin:@192.168.1.45:1521:orcl");
            ods.setUser("kronos");
            ods.setPassword("KRONOS");
            ods.setMinPoolSize(5);
            ods.setMaxPoolSize(100);

            long start = System.currentTimeMillis();

            for (int i = 0; i < 600_000; i++) {
                Connection conn = ods.getConnection();
                conn.setAutoCommit(false);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO ERRORINFO (id, firstname, lastname, servicetypecode, servicecode, updatetime) VALUES (?,?,?,?,?,?)");
                String key = UUID.randomUUID().toString();
                ps.setString(1, key);
                ps.setString(2, "TEST " + key);
                ps.setString(3, "TESTOV " + key);
                ps.setString(4, "servicetypecode " + key);
                ps.setString(5, "servicecode " + key);
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                ps.executeUpdate();
                conn.commit();
                ps.close();
                conn.close();
                if (i % 1_000 == 0) System.out.println(i + " " + (System.currentTimeMillis() - start));
            }
            // Finished in 504936 ms
            System.out.println("Finished in " + (System.currentTimeMillis() - start) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
