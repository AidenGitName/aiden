package com.wonders.hms.shcedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SayHello {

//    @Scheduled(fixedRate=5000)
    public void sayHello() {
        System.out.println("Hello!");
    }
}
