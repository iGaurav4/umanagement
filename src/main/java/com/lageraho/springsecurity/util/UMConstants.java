package com.lageraho.springsecurity.util;

import org.springframework.stereotype.Component;

@Component
public class UMConstants {

    public final static String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]*$";
    public final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

}
