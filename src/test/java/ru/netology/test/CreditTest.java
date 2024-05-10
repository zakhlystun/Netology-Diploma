package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getOneDigit;
import static ru.netology.data.SQLHelper.*;

public class CreditTest {
    String url = System.getProperty("sut.url");
    private CreditPage buy;

    @BeforeAll
    static void setAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDown() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void openPage() {
        open(url);
        buy = new CreditPage();
        buy.buyCredit();
    }

    @AfterEach
    public void cleanDataBase() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("ТС-2.1 Успешная покупка в кредит при вводе валидных данных")
    public void shouldSuccessfulCreditPurchase() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals("APPROVED", SQLHelper.getCreditStatusFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.2 Отказ в покупке при вводе невалидных данных карты для покупки в кредит")
    public void shouldDeclinedCreditPurchase() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_DECLINED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgCardInvalid();
        assertEquals("DECLINED", SQLHelper.getCreditStatusFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3 Неверно заполнено поле Номер карты, пустое значение")
    public void shouldIncorrectCardNumberEmpty() {
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3.1 Неверно заполнено поле Номер карты, ввод букв")
    public void shouldIncorrectCardNumberLetter() {
        buy.fieldCardNumberInsert(DataHelper.getCardNumberLetters());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3.2 Неверно заполнено поле Номер карты, ввод спецсимволов")
    public void shouldIncorrectCardNumberSymbols() {
        buy.fieldCardNumberInsert(DataHelper.getSymbol());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3.3 Неверно заполнено поле Номер карты, слишком короткое значение, 15 цифр")
    public void shouldIncorrectCardNumberShort() {
        buy.fieldCardNumberInsert(DataHelper.getCardNumberShort());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3.4 Неверно заполнено поле Номер карты, слишком длинное значение, 17 цифр")
    public void shouldIncorrectCardNumberLong() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED + getOneDigit());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals("APPROVED", getCreditStatusFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.3.5 Неверно заполнено поле Номер карты,  16 нулей")
    public void shouldIncorrectCardNumberZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_ZEROS);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4 Неверно заполнено поле Месяц, пустое значение")
    public void shouldIncorrectMonthEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.1 Неверно заполнено поле Месяц, ввод букв")
    public void shouldIncorrectMonthLetters() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getLetters());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.2 Неверно заполнено поле Месяц, ввод спецсимволов")
    public void shouldIncorrectMonthSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getSymbol());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.3 Неверно заполнено поле Месяц,слишком длинное значение")
    public void shouldIncorrectMonthLong() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid() + getOneDigit());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals("APPROVED", getCreditStatusFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.4 Неверно заполнено поле Месяц, введены нули")
    public void shouldIncorrectMonthZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.DATE_ZEROS);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.5 Неверно заполнено поле Месяц, значения больше 12")
    public void shouldIncorrectMonth13() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthInvalid13());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.4.6 Неверно заполнено поле Месяц, срок действия карты истек")
    public void shouldIncorrectMonthExpired() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthExpired());
        buy.fieldCardYearInsert(DataHelper.getYearExpired());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgExpiredDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5 Неверно заполнено поле Год, пустое значение")
    public void shouldIncorrectYearEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5.1 Неверно заполнено поле Год, ввод букв")
    public void shouldIncorrectYearLetters() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getLetters());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5.2 Неверно заполнено поле Год, ввод спецсимволов")
    public void shouldIncorrectYearSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getSymbol());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5.3 Неверно заполнено поле Год, слишком короткое значение")
    public void shouldIncorrectYearShort() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getOneDigit());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5.4 Неверно заполнено поле Год, слишком длинное значение")
    public void shouldIncorrectYearLong() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid() + getOneDigit());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals(1, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.5.5 Неверно заполнено поле Год, нули")
    public void shouldIncorrectYearZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.DATE_ZEROS);
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgExpiredDate();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.6 Неверно заполнено поле Владелец, пустое значение")
    public void shouldIncorrectCardholderEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.6.1 Неверно заполнено поле Владелец, ввод спецсимволов")
    public void shouldIncorrectCardholderSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getSymbol());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.6.2 Неверно заполнено поле Владелец, ввод цифр")
    public void shouldIncorrectCardholderDigits() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getOneDigit());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.6.3 Неверно заполнено поле Владелец, ввод кириллицы")
    public void shouldIncorrectCardholderCyrillic() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameRu());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.7 Неверно заполнено поле CVC/CVV, пустое значение")
    public void shouldIncorrectCvcEmpty() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.7.1 Неверно заполнено поле CVC/CVV, ввод букв")
    public void shouldIncorrectCvcLetters() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getLetters());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.7.2 Неверно заполнено поле CVC/CVV, ввод спецсимволов")
    public void shouldIncorrectCvcSymbols() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getSymbol());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.7.3 Неверно заполнено поле CVC/CVV, слишком короткое значение")
    public void shouldIncorrectCvcShort() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getOneDigit());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }

    @Test
    @DisplayName("ТС-2.7.4 Неверно заполнено поле CVC/CVV, нули")
    public void shouldIncorrectCvcZeros() {
        buy.fieldCardNumberInsert(DataHelper.CARD_NUMBER_APPROVED);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.CVC_ZEROS);
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, SQLHelper.getOrdersFromDatabase());
    }
}