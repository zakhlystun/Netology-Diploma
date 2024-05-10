package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private final SelenideElement buttonCreditBuy = $$("button").find(exactText("Купить в кредит"));
    private final SelenideElement fieldCardNumber = $(byText("Номер карты")).parent().$("[class='input__control']");
    private final SelenideElement fieldCardMonth = $(byText("Месяц")).parent().$("[class='input__control']");
    private final SelenideElement fieldCardYear = $(byText("Год")).parent().$("[class='input__control']");
    private final SelenideElement fieldCardholder = $(byText("Владелец")).parent().$("[class='input__control']");
    private final SelenideElement fieldCardCvc = $(byText("CVC/CVV")).parent().$("[class='input__control']");
    private final SelenideElement msgSuccess = $(byText("Операция одобрена Банком.")).parent().$("[class='notification__content']");
    private final SelenideElement msgDecline = $(byText("Ошибка! Банк отказал в проведении операции.")).parent().$("[class='notification__content']");
    private final SelenideElement msgIncorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement msgIncorrectDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement msgExpiredDate = $(byText("Истёк срок действия карты"));
    private final SelenideElement msgRequiredField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement buttonContinue = $$("button").find(exactText("Продолжить"));

    public void buyCredit() {
        buttonCreditBuy.click();
    }

    public void buttonContinueClick() {
        buttonContinue.click();
    }

    public void fieldCardNumberInsert(String cardNumber) {
        fieldCardNumber.setValue(cardNumber);
    }

    public void fieldCardMonthInsert(String cardMonth) {
        fieldCardMonth.setValue(cardMonth);
    }

    public void fieldCardYearInsert(String cardYear) {
        fieldCardYear.setValue(cardYear);
    }

    public void fieldCardholderInsert(String cardHolder) {
        fieldCardholder.setValue(cardHolder);
    }

    public void fieldCardCvcInsert(String cvc) {
        fieldCardCvc.setValue(cvc);
    }

    public void shouldHaveMsgSuccess() {
        msgSuccess.shouldHave(Condition.visible, Duration.ofSeconds(10));
    }

    public void shouldHaveMsgCardInvalid() {
        msgDecline.shouldHave(Condition.visible, Duration.ofSeconds(10));
    }

    public void shouldHaveMsgIncorrectFormat() {
        msgIncorrectFormat.shouldHave(Condition.visible);
    }

    public void shouldHaveMsgExpiredDate() {
        msgExpiredDate.shouldHave(Condition.visible);
    }

    public void shouldHaveMsgIncorrectDate() {
        msgIncorrectDate.shouldHave(Condition.visible);
    }

    public void shouldHaveMsgRequiredField() {
        msgRequiredField.shouldHave(Condition.visible);
    }

}
