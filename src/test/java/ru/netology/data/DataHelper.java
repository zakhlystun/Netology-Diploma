package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class DataHelper {
    public static final String cardNumberApproved = "4444 4444 4444 4441";
    public static final String cardNumberDeclined = "4444 4444 4444 4442";
    public static final String cardNumberZeros = "0000 0000 0000 0000";
    public static final String cardNumberInvalid = "";
    public static final String cardNumberEmpty = " ";
    public static final String dateZeros = "00";
    public static final String cvcZeros = "000";
    public static final String extraMonth = "13";
    public static final String monthInvalid = "!q";
    public static final String longMonth = "321";
    public static final String yearInvalid = "!q";
    public static final String cardholderInvalid = "T@12n3M";

    public static final String cvcInvalid = "!Qq";
    public static final String longCvc = "1234";

    public static String getSymbol() {
        String[] Symbols = {
                "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "+", "=", "{", "}", "[", "]", "<", ">", "/", "?"
        };
        return Symbols[new Random().nextInt(Symbols.length)];
    }

    public static String getLetters() {
        Faker faker = new Faker();
        return faker.name().username();
    }

    public static String getOneDigit() {
        Faker faker = new Faker();
        return faker.number().digits(1);
    }

    public static String getCardNumberLetters() {
        Faker faker = new Faker();
        return faker.name().username();
    }

    public static String getCardNumberShort() {
        Faker faker = new Faker();
        return faker.number().digits(15);
    }

    public static String getCardNumberLong() {
        Faker faker = new Faker();
        return faker.number().digits(17);
    }

    public static String getMonthValid() {
        String month = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public static String getMonthInvalid13() {
        int randomNumber = ThreadLocalRandom.current().nextInt(13, 99);
        return String.valueOf(randomNumber);
    }

    public static String getYearValid() {
        String year = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public static String getExpiredYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getLongYear() {
        String fullYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        return fullYear;
    }

    public static String getCardholderFullNameRu() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getCardholderFullNameEn() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().fullName();
    }

    public static String getCvc() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }
}
