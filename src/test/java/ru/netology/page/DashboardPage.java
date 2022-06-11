package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amount = $("[data-test-id=amount] [class=input__control]");
    private SelenideElement from = $("[data-test-id=from] [class=input__control]");
    private SelenideElement actionTransferButton = $("[data-test-id=\"action-transfer\"]");
    private ElementsCollection cards = $$(".list__item div");
    private SelenideElement errorNotification = $("[data-test-id=\"error-notification\"] [class=\"notification__content\"]");

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String firstCardStartBalance) {
        String cardText = "null";
        for (SelenideElement card : cards) {
            if (card.getText().contains(firstCardStartBalance.substring(15))) { // 4 last chars of the card
                cardText = card.getText();
            }
        }
        int balance = extractBalance(cardText);
        return balance;
    }

    public SelenideElement getErrorNotification() {
        SelenideElement error = errorNotification;
        return error;
    }


    public DashboardPage transferToFromAmount(String toCardId, String fromCardId, String amountValue) {

        $(byXpath(".//*[contains(text(), '**** **** **** " + toCardId.substring(15) + "\')]/button")).click();
        amount.setValue(String.valueOf(amountValue));
        from.setValue(fromCardId);
        actionTransferButton.click();

        return new DashboardPage();
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
