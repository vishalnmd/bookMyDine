package com.restaurant.enums;

public enum Slot {
    Zero (0,12,"AM"),
    One(1, 1, "AM"),
    Two(2, 2, "AM"),
    Three(3, 3, "AM"),
    Four(4, 4, "AM"),
    Five(5, 5, "AM"),
    Six(6, 6, "AM"),
    Seven(7, 7, "AM"),
    Eight(8, 8, "AM"),
    Nine(9, 9, "AM"),
    Ten(10, 10, "AM"),
    Eleven(11, 11, "AM"),
    Twelve (12, 12, "PM"),
    Thirteen (13, 1, "PM"),
    Fourteen (14, 2, "PM"),
    Fifteen (15, 3, "PM"),
    Sixteen (16, 4, "PM"),
    Seventeen (17, 5, "PM"),
    Eighteen (18, 6, "PM"),
    Nineteen (19, 7, "PM"),
    Twenty (20, 8, "PM"),
    TwentyOne (21, 9, "PM"),
    TwentyTwo (22, 10, "PM"),
    TwentyThree (23, 11, "PM"),
    ;

    Slot(int twentyFourHr, int hh, String meridian) {
    }

}
