package org.example.classes;

import org.example.util.Bean;

@Bean
public class First extends Father {
    @Override
    public String print() {
        return "first";
    }
}
