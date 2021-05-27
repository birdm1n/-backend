package com.daema.rest.common.consts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValue {

    public static String profilesActive;

    @Value("${spring.profiles.active}")
    public void setProfilesActive(String value) {
        profilesActive = value;
    }
}