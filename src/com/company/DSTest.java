package com.company;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;

/**
 * -- Create table
     create table PERSONS
     (
     id        VARCHAR2(100) not null,
     firstname VARCHAR2(300),
     lastname  VARCHAR2(300)
     )
     tablespace USERS
     pctfree 10
     initrans 1
     maxtrans 255
     storage
     (
     initial 64K
     next 1M
     minextents 1
     maxextents unlimited
     );
     -- Create/Recreate primary, unique and foreign key constraints
     alter table PERSONS
     add constraint PK primary key (ID)
     using index
     tablespace USERS
     pctfree 10
     initrans 2
     maxtrans 255
     storage
     (
     initial 64K
     next 1M
     minextents 1
     maxextents unlimited
     );
 */
public class DSTest {
    public static void main(String[] args){

        try {
            ComboPooledDataSource ods = new ComboPooledDataSource();
            ods.setDriverClass("oracle.jdbc.pool.OracleDataSource");
            ods.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:orcl");
            ods.setUser("kronos");
            ods.setPassword("KRONOS");
            ods.setMinPoolSize(5);
            ods.setMaxPoolSize(100);

            long start = System.currentTimeMillis();

            for(int i = 0; i < 10_000; i++){
                Connection conn = ods.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO PERSONS (id, firstname, lastname) VALUES (?,?,?)");
                String key = UUID.randomUUID().toString();
                ps.setString(1, key);
                ps.setString(2, "TEST " + key);
                ps.setString(3, "TESTOV " + key);
                ps.executeUpdate();
                ps.close();
                conn.close();
            }
            System.out.println("Finished in " + (System.currentTimeMillis()  - start) + " ms");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
