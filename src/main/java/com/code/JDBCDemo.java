package com.code;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ElasticSearchDruidDataSourceFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("url", "jdbc:elasticsearch://one:9300/");
        DruidDataSource dds = (DruidDataSource) ElasticSearchDruidDataSourceFactory.createDataSource(properties);
        Connection connection = dds.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT * from test_index_one");
        System.out.println("OK");
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
//            System.out.println("name is :" + resultSet.getString("name")
//                    +",desc is: " + resultSet.getString("desc")
//                    +",price is: " + resultSet.getInt("price")
//                    +",producer is: " + resultSet.getString("producer")
//                    +",tags is: " + resultSet.getArray("tags"));
            System.out.println("id is: " + resultSet.getString("id")
                    +",name is: " + resultSet.getString("name")
                    +",age is: " + resultSet.getString("age"));
        }
        PreparedStatement ps1 = connection.prepareStatement("SELECT name,desc,price,producer,tags from ecommerce where name like '%jia%'");
        ResultSet resultSet1 = ps1.executeQuery();
        System.out.println("----------------------");
        while (resultSet1.next()) {
            System.out.println("name is :" + resultSet1.getString(1)
                    +",desc is: " + resultSet1.getString(2)
                    +",price is: " + resultSet1.getInt(3)
                    +",producer is: " + resultSet1.getString(4)
                    +",tags is: " + resultSet1.getArray(5));
        }
        System.out.println("----------------------");
        PreparedStatement ps2 = connection.prepareStatement("SELECT count(*) from ecommerce");
        ResultSet resultSet2 = ps2.executeQuery();
        if (resultSet2.next()){
            System.out.println(resultSet2.getDouble(1));
        }
        ps.close();
        ps1.close();
        ps2.close();
        connection.close();
        dds.close();
    }
}
