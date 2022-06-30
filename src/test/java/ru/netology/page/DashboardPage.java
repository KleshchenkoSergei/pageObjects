package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private static SelenideElement amount = $("[data-test-id=amount] [class=input__control]");
    private static SelenideElement from = $("[data-test-id=from] [class=input__control]");
    private static SelenideElement actionTransferButton = $("[data-test-id=\"action-transfer\"]");
    private static ElementsCollection cards = $$(".list__item div");
    private static SelenideElement errorNotification = $("[data-test-id=\"error-notification\"] [class=\"notification__content\"]");

    private static final String balanceStart = "баланс: ";
    private static final String balanceFinish = " р.";


    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static void verification(String error) {
        errorNotification.shouldHave(text(error), Duration.ofMillis(15000));
    }

    public static int[] getStartBalance(DataHelper.CardInfoTo cardInfo) {
        int[] startBalance = new int[2];
        startBalance[0] = getCardBalance(cardInfo.getCardIdTo());
        startBalance[1] = getCardBalance(cardInfo.getCardIdFrom());
        return startBalance;
    }

    public static TransferPage choiceTransferTo(DataHelper.CardInfoTo cardInfo, int amountValue) {

        $(byXpath(".//*[contains(text(), '**** **** **** " + cardInfo.getCardIdTo().substring(15) + "\')]/button")).click();

        amount.setValue(String.valueOf(amountValue));
        from.setValue(cardInfo.getCardIdFrom());
        actionTransferButton.click();

        return new TransferPage();
    }

    public static TransferPage choiceTransferWrongTo(DataHelper.CardInfoTo cardInfo, int amountValue) {

        $(byXpath(".//*[contains(text(), '**** **** **** " + cardInfo.getCardIdTo().substring(15) + "\')]/button")).click();

        amount.setValue(String.valueOf(amountValue));
        from.setValue(cardInfo.getCardIdFrom());
        actionTransferButton.click();
        DashboardPage.verification("Ошибка! Произошла ошибка");

        return new TransferPage();
    }


    public static int getCardBalance(String cardBalance) {
        String cardText = "null";
        for (SelenideElement card : cards) {
            if (card.getText().contains(cardBalance.substring(15))) { // 4 last chars of the card
                cardText = card.getText();
            }
        }
        int balance = extractBalance(cardText);
        return balance;
    }

    private static int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

}
