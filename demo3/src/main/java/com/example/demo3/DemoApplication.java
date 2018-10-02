package com.example.demo3;

import com.example.demo3.mapper.TestMapper;
import lombok.extern.java.Log;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@Log
@MapperScan("mapper")
public class DemoApplication  {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
