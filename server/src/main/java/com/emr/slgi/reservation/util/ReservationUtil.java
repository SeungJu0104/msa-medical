package com.emr.slgi.reservation.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class ReservationUtil {

    public static LocalDate addBusinessDays(LocalDate startDate, int days) {
        LocalDate result = startDate;
        int addedDays = 0;

        while (addedDays < days) {
            result = result.plusDays(1);

            if (!(result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                addedDays++;
            }
        }

        return result;

    }

}
