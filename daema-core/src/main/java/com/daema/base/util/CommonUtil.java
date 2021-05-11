package com.daema.base.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommonUtil {

    public static long diffLocalDateTimeToDays(LocalDateTime targetDateTime) {
        return ChronoUnit.DAYS.between(LocalDateTime.now(), targetDateTime);
    }
}
