package com.hardwarestore.orderservice.config;

import com.hardwarestore.orderservice.external.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    ErrorDecoder feignErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
