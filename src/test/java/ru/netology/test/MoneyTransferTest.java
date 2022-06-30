package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromSecondToFirstCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo(); // vasya / qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // 12345
        verificationPage.validVerify(verificationCode);

        var amount = 500;//amount for transfer
        var cardInfoTo = DataHelper.getCardInfoToFirst(); // transfer to first from second card
        var startCardBalance = DashboardPage.getStartBalance(cardInfoTo);
        var transferPage = DashboardPage.choiceTransferTo(cardInfoTo, amount);
        transferPage.validation(cardInfoTo, startCardBalance, amount);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo(); // vasya / qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // 12345
        verificationPage.validVerify(verificationCode);

        var amount = 500;//amount for transfer
        var cardInfoTo = DataHelper.getCardInfoToSecond(); // transfer to second from first card
        var startCardBalance = DashboardPage.getStartBalance(cardInfoTo);
        var transferPage = DashboardPage.choiceTransferTo(cardInfoTo, amount);
        transferPage.validation(cardInfoTo, startCardBalance, amount);
    }


    @Test
    void shouldTransferMoneyFromSecondToFirstCardUnderZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo(); // vasya / qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // 12345
        verificationPage.validVerify(verificationCode);

        var amount = -500;//amount for transfer
        var cardInfoTo = DataHelper.getCardInfoToFirst(); // transfer to first from second card
        var startCardBalance = DashboardPage.getStartBalance(cardInfoTo);
        var transferPage = DashboardPage.choiceTransferTo(cardInfoTo, amount);
        transferPage.validation(cardInfoTo, startCardBalance, Math.abs(amount));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo(); // vasya / qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // 12345
        verificationPage.validVerify(verificationCode);

        var amount = 0;//amount for transfer
        var cardInfoTo = DataHelper.getCardInfoToSecond(); // transfer to second from first card
        var startCardBalance = DashboardPage.getStartBalance(cardInfoTo);
        var transferPage = DashboardPage.choiceTransferTo(cardInfoTo, amount);
        transferPage.validation(cardInfoTo, startCardBalance, amount);
    }

    @Test
    void shouldTransferMoneyFromWrongSecondToFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo(); // vasya / qwerty123
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo); // 12345
        verificationPage.validVerify(verificationCode);

        var amount = 500;//amount for transfer
        var cardInfoTo = DataHelper.getCardInfoWrongToFirst(); // transfer to wrong first from second card
        var startCardBalance = DashboardPage.getStartBalance(cardInfoTo);
        var transferPage = DashboardPage.choiceTransferWrongTo(cardInfoTo, amount);
    }


}
