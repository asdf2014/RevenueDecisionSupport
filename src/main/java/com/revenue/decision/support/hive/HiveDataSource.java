package com.revenue.decision.support.hive;

import java.sql.*;

public class HiveDataSource {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    // [doc]:https://cwiki.apache.org/confluence/display/Hive/HiveServer2+Clients#HiveServer2Clients-UsingJDBC
    public static void main(String[] args)
            throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Connection con = DriverManager.getConnection("jdbc:hive2://192.168.1.111:10000/default", "", "");
        Statement stmt = con.createStatement();
        String tableName = "revenue";
        stmt.execute("drop table if exists " + tableName);
        stmt.execute("create table " + tableName + " (id bigint, name string, category string, size bigint) partitioned by (pubdate string) row format delimited fields terminated by '\\t'");
        System.out.println("Create table success!");
        // show tables
        String sql = "show tables '" + tableName + "'";
        System.out.println("Running: " + sql);
        ResultSet res = stmt.executeQuery(sql);
        if (res.next()) {
            System.out.println(res.getString(1));
        }

        // load data
        sql = "load data inpath '/opt/data/revenue.txt' overwrite into table revenue partition (pubdate='2016-4-4')";
        System.out.println("Running: " + sql);
        int lineCount = stmt.executeUpdate(sql);
        System.out.println("Lines: " + lineCount);

        // describe table
        sql = "describe " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "\t" + res.getString(2));
        }

        sql = "select * from " + tableName;
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t"
                    + res.getString(2));
        }

        sql = "select count(1) from " + tableName;
        System.out.println("Running: " + sql);
        res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }
}