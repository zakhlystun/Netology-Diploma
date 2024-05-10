package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class DataHelper {
    public static final String CARD_NUMBER_APPROVED = "4444 4444 4444 4441";
    public static final String CARD_NUMBER_DECLINED = "4444 4444 4444 4442";
    public static final String CARD_NUMBER_ZEROS = "0000 0000 0000 0000";
    public static final String DATE_ZEROS = "00";
    public static final String CVC_ZEROS = "000";

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
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getMonthInvalid13() {
        int randomNumber = ThreadLocalRandom.current().nextInt(13, 99);
        return String.valueOf(randomNumber);
    }

    public static String getMonthExpired() {
        LocalDate currentData = LocalDate.now();
        LocalDate currentMonth = currentData.minusMonths(1);
        return currentMonth.format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getYearValid() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getYearExpired() {
        LocalDate currentData = LocalDate.now();
        LocalDate currentYear = currentData.minusMonths(1);
        return currentYear.format(DateTimeFormatter.ofPattern("YY"));
    }

    public static String getCardholderFullNameRu() {
        Faker faker = new Faker(new Locale("RU"));
        return faker.name().fullName();
    }

    public static String getCardholderFullNameEn() {
        Faker faker = new Faker(new Locale("EN"));
        return faker.name().fullName();
    }

    public static String getCvc() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }
}
