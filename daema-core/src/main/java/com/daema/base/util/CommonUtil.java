package com.daema.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CommonUtil {

    public static long diffDaysLocalDateTime(LocalDateTime targetDateTime) {
        return targetDateTime != null ? ChronoUnit.DAYS.between(targetDateTime.toLocalDate(), LocalDate.now()) : 0;
    }
}
