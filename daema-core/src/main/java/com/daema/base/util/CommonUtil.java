package com.daema.base.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CommonUtil {

    public static long diffDaysLocalDate(LocalDate targetDate) {
        return targetDate != null ? ChronoUnit.DAYS.between(targetDate, LocalDate.now()) : 0;
    }
}
