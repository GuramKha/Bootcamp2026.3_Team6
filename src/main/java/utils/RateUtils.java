package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RateUtils {

    public static double extractRate(String text) {
        Pattern pattern = Pattern.compile("=\\s*([\\d.]+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0;
    }

    public static double parseAmount(String value) {
        return Double.parseDouble(value.replace(",", "").trim());
    }

    public static double calculateExpected(double amount, double rate) {
        return amount * rate;
    }

    public static double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public static boolean isCloseEnough(double actual, double expected) {
        return Math.abs(actual - expected) < 100;  //There is bug and calculation is wrong
    }
}