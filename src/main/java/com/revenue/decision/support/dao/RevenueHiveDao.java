package com.revenue.decision.support.dao;

import com.revenue.decision.support.model.Revenue;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Benedict Jin on 2016/4/4.
 */
@Component
public class RevenueHiveDao {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static Connection con;
    private static String tableName = "revenue";

    static {
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection("jdbc:hive2://192.168.1.111:10000/default", "", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            Statement stmt = con.createStatement();
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query() {

        try {
            Statement stmt = con.createStatement();
            String sql = "select * from " + tableName;
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(String.valueOf(res.getInt(1)) + "\t"
                        + res.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void count() {

        try {
            Statement stmt = con.createStatement();
            String sql = "select count(1) from " + tableName;
            System.out.println("Running: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(res.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Revenue> queryTop10() {

        List<Revenue> revenues = null;
        try {
            Statement stmt = con.createStatement();
            //select name, category, sum(size) as sum_size from revenue group by name, category order by sum_size desc limit 10;
            String sql = "select name, category, sum(size) as sum_size from " + tableName + " group by name, category order by sum_size desc limit 10";
            System.out.println("Running: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            revenues = new LinkedList<>();
            Revenue revenue;
            while (res.next()) {
                String name = res.getString(1);
                String category = res.getString(2);
                int sum = res.getInt(3);
                revenue = new Revenue();
                revenue.setName(name);
                revenue.setCategory(category);
                revenue.setSize(sum);
                revenues.add(revenue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenues;
    }

}
