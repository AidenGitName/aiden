package com.example.demo3;

import com.example.demo3.controller.SampleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mock;

    @Test
    public void testHello() throws Exception{
    }

    @Test
    public void goldenBell(){
        List<String> buyer = new ArrayList<>();
        IntStream.range(1,10).forEach(i->{
            buyer.add(String.valueOf(i));
        });
        buyer.forEach(i->{
            System.out.println(i);
        });


    }
}
