package com.viverselftest;

import com.google.common.base.Splitter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ViverMainEnter {

    public static void main(String[] args) throws IOException,SOAPException {
        System.out.println("test123");
        SpringApplication.run(ViverMainEnter.class,args);
    }
}
