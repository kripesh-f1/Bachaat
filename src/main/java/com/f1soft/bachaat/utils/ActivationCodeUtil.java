package com.f1soft.bachaat.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ActivationCodeUtil {

    @Bean
    public int getActivationCode() {
        Random rand = new Random();
        int num = rand.nextInt(900000) + 100000;
        return num;
    }
}