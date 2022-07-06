package org.example.classes;

import org.example.util.Bean;

@Bean(name = "second")
public class Ss extends Father {
    @Override
   public String print() {
        return "second";
    }
}
