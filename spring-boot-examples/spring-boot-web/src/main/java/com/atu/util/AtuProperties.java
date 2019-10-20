package com.atu.util;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Data
@Component
public class AtuProperties {
    @Value("${com.atu.title}")
    private String title;
    @Value("${com.atu.description}")
    private String description;
}
