package org.example;

import org.example.classes.Father;
import org.example.classes.First;
import org.example.classes.Ss;
import org.example.util.ApplicationContext;
import org.example.util.ApplicationContextImpl;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContextImpl();

        First first = applicationContext.getBean(First.class);
        System.out.println(first.print());

        Ss second = applicationContext.getBean("second", Ss.class);
        System.out.println(second.print());

        Map<String, ? extends Father> allBeans = applicationContext.getAllBeans(Father.class);
        System.out.println(allBeans);
    }
}