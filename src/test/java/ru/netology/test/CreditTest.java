package ru.netology.test;

/*import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.DebitPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.SQLHelper.getOrderCount;

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
    public void shouldSuccessfulDebitPurchase() {
        buy.fieldCardNumberInsert(DataHelper.cardNumberApproved);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals("APPROVED", SQLHelper.getDebitStatus());
    }

    @Test
    @DisplayName("ТС-2.2 Отказ в покупке при вводе невалидных данных карты для покупки в кредит")
    public void shouldDeclinedDebitPurchase() {
        buy.fieldCardNumberInsert(DataHelper.cardNumberDeclined);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgCardInvalid();
        assertEquals("DECLINED", SQLHelper.getDebitStatus());
    }

    @Test
    @DisplayName("ТС-2.3 Неверно заполнено поле Номер карты, пустое значение")
    public void shouldIncorrectCardNumberEmpty() {
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, getOrderCount());
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
        assertEquals(0, getOrderCount());
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
        assertEquals(0, getOrderCount());
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
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("ТС-2.3.4 Неверно заполнено поле Номер карты, слишком длинное значение, 17 цифр")
    public void shouldIncorrectCardNumberLong() {
        buy.fieldCardNumberInsert(DataHelper.getCardNumberLong());
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgSuccess();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("ТС-2.3.5 Неверно заполнено поле Номер карты, слишком длинное значение,  16 нулей")
    public void shouldIncorrectCardNumberZeros() {
        buy.fieldCardNumberInsert(DataHelper.cardNumberZeros);
        buy.fieldCardMonthInsert(DataHelper.getMonthValid());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("ТС-2.4 Неверно заполнено поле Месяц, пустое значение")
    public void shouldIncorrectMonthEmpty() {
        buy.fieldCardNumberInsert(DataHelper.cardNumberApproved);
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgIncorrectFormat();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("ТС-2.4.1 Неверно заполнено поле Месяц, ввод букв")
    public void shouldIncorrectMonthLetters() {
        buy.fieldCardNumberInsert(DataHelper.cardNumberApproved);
        buy.fieldCardMonthInsert(DataHelper.getMonthLetters());
        buy.fieldCardYearInsert(DataHelper.getYearValid());
        buy.fieldCardholderInsert(DataHelper.getCardholderFullNameEn());
        buy.fieldCardCvcInsert(DataHelper.getCvc());
        buy.buttonContinueClick();
        buy.shouldHaveMsgRequiredField();
        assertEquals(0, getOrderCount());
    }

    @Test
    @DisplayName("ТС-2.4.2 Неверно заполнено поле Месяц, ввод спецсимволов")

    @Test
    @DisplayName("ТС-2.4.3 Неверно заполнено поле Месяц,слишком длинное значение")

    @Test
    @DisplayName("ТС-2.4.4 Неверно заполнено поле Месяц, введены нули")

    @Test
    @DisplayName("ТС-2.4.5 Неверно заполнено поле Месяц, значения больше 12")

    @Test
    @DisplayName("ТС-2.5 Неверно заполнено поле Год, пустое значение")

    @Test
    @DisplayName("ТС-2.5.1 Неверно заполнено поле Год, ввод букв")

    @Test
    @DisplayName("ТС-2.5.2 Неверно заполнено поле Год, ввод спецсимволов")

    @Test
    @DisplayName("ТС-2.5.3 Неверно заполнено поле Год, слишком короткое значение")

    @Test
    @DisplayName("ТС-2.5.4 Неверно заполнено поле Год, слишком длинное значение")

    @Test
    @DisplayName("ТС-2.6 Неверно заполнено поле Владелец, пустое значение")

    @Test
    @DisplayName("ТС-2.6.1 Неверно заполнено поле Владелец, ввод спецсимволов")

    @Test
    @DisplayName("ТС-2.6.3 Неверно заполнено поле Владелец, ввод кириллицы")

    @Test
    @DisplayName("ТС-2.7 Неверно заполнено поле CVC/CVV, пустое значение")

    @Test
    @DisplayName("ТС-2.7.1 Неверно заполнено поле CVC/CVV, ввод букв")

    @Test
    @DisplayName("ТС-2.7.2 Неверно заполнено поле CVC/CVV, ввод спецсимволов")

    @Test
    @DisplayName("ТС-2.7.3 Неверно заполнено поле CVC/CVV, слишком короткое значение")

    @Test
    @DisplayName("ТС-2.7.4 Неверно заполнено поле CVC/CVV, нули")


*/