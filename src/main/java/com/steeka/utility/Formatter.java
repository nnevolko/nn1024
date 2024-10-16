package com.steeka.utility;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatter {

    public final static NumberFormat CURRENCY_CONVERTER = NumberFormat.getCurrencyInstance(Locale.US);
    public final static DateTimeFormatter DATE_CONVERTER = DateTimeFormatter.ofPattern("MM/dd/yy");

}
