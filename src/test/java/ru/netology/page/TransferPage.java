package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");

    public TransferPage() {
        heading.shouldBe(visible);
    }


    public static TransferPage validation(DataHelper.CardInfoTo cardInfoToFirst, int[] startCardBalance, int amount) {
        assertEquals(DashboardPage.getCardBalance(cardInfoToFirst.getCardIdTo()), startCardBalance[0] + amount);
        assertEquals(DashboardPage.getCardBalance(cardInfoToFirst.getCardIdFrom()), startCardBalance[1] - amount);
        return new TransferPage();
    }
}
