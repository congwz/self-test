package com.viverselftest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.soap.SOAPException;
import java.io.IOException;

@SpringBootApplication
public class ViverMainEnter {

    public static void main(String[] args) throws IOException,SOAPException {
        SpringApplication.run(ViverMainEnter.class,args);
    }
}
