package com.chenminhua.hbaseexp;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired private Table table;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args)  {
        System.out.println("wfee");
        Put p = new Put(Bytes.toBytes("myLittleRow"));
        p.add(Bytes.toBytes("myLittleFamily"), Bytes.toBytes("someQualifier"),
                Bytes.toBytes("Some Value"));
        try {
            table.put(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("wee");
    }
}
