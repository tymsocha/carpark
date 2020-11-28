package com.myprojects.carpark.utils;

import java.util.Arrays;
import java.util.List;

//Klasa ze zmiennymi, które zawsze mają tą samą wartość przez cały okres działania aplikacji
public class GeneralConstants {
    public static final List<Integer> monthsWith31Days = Arrays.asList(1, 3, 5, 7, 8, 10, 12);
    public static final List<Integer> monthsWith30Days = Arrays.asList(4, 6, 9, 11);
    public static final Integer february = 2;

    //Headers
    public static final String slotEntityName = "SLOT";
    public static final String timeUnitEntityName = "TIME_UNIT";
    public static final String occupationEntityName = "OCCUPATION";

    public static final String slotIdFieldName = "SLOT_ID";
    public static final String slotNameFieldName = "SLOT_NAME";
    public static final String slotFloorNumberFieldName = "SLOT_FLOOR_NUMBER";
    public static final String timeUnitIdFieldName = "TIME_UNIT_ID";
    public static final String timeUnitDateTimeFieldName = "DATE_TIME";
    public static final String occupationIdFieldName = "OCCUPATION_ID";
    public static final String occupiedFieldName = "OCCUPIED";
    public static final String isClosedFieldName = "IS_CLOSED";
}
