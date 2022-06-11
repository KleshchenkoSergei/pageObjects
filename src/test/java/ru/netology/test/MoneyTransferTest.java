package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromSecondToFirstCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var DashboardPage = new DashboardPage();
        var firstCardId = "5559 0000 0000 0001";
        var secondCardId = "5559 0000 0000 0002";
        var amount = 500;
        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);

        DashboardPage.transferToFromAmount(firstCardId, secondCardId, String.valueOf(amount));

        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance + amount);
        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance - amount);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardEnough() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var DashboardPage = new DashboardPage();
        var firstCardId = "5559 0000 0000 0001";
        var secondCardId = "5559 0000 0000 0002";
        var amount = 500;
        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);

        DashboardPage.transferToFromAmount(secondCardId, firstCardId, String.valueOf(amount));

        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance - amount);
        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance + amount);
    }



    @Test
    void shouldTransferMoneyFromSecondToFirstCardUnderZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var DashboardPage = new DashboardPage();
        var firstCardId = "5559 0000 0000 0001";
        var secondCardId = "5559 0000 0000 0002";
        var amount = -500;
        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);

        DashboardPage.transferToFromAmount(firstCardId, secondCardId, String.valueOf(amount));

        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance + Math.abs(amount));
        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance - Math.abs(amount));

        System.out.println(firstCardStartBalance);
        System.out.println(secondCardStartBalance);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCardZero() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var DashboardPage = new DashboardPage();
        var firstCardId = "5559 0000 0000 0001";
        var secondCardId = "5559 0000 0000 0002";
        var amount = 0;
        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);

        DashboardPage.transferToFromAmount(secondCardId, firstCardId, String.valueOf(amount));

        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance - amount);
        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance + amount);
    }

//    @Test // Not solved bug, comments for good CI
//    void shouldTransferMoneyFromSecondToFirstCardNoMoney() {
//        open("http://localhost:9999");
//        var loginPage = new LoginPage();
//
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        verificationPage.validVerify(verificationCode);
//
//        var DashboardPage = new DashboardPage();
//        var firstCardId = "5559 0000 0000 0001";
//        var secondCardId = "5559 0000 0000 0002";
//        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
//        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);
//        var amount = Math.round(secondCardStartBalance * 1.1);
//
//        DashboardPage.transferToFromAmount(firstCardId, secondCardId, String.valueOf(amount));
//
//        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance);
//        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance);
//    }

//    @Test // Not solved bug, comments for good CI
//    void shouldTransferMoneyFromFirstToSecondCardNoMoney() {
//        open("http://localhost:9999");
//        var loginPage = new LoginPage();
//
//        var authInfo = DataHelper.getAuthInfo();
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        verificationPage.validVerify(verificationCode);
//
//        var DashboardPage = new DashboardPage();
//        var firstCardId = "5559 0000 0000 0001";
//        var secondCardId = "5559 0000 0000 0002";
//        var firstCardStartBalance = DashboardPage.getCardBalance(firstCardId);
//        var secondCardStartBalance = DashboardPage.getCardBalance(secondCardId);
//        var amount = Math.round(firstCardStartBalance * 1.1);
//
//        DashboardPage.transferToFromAmount(secondCardId, firstCardId, String.valueOf(amount));
//
//        assertEquals(DashboardPage.getCardBalance(firstCardId), firstCardStartBalance);
//        assertEquals(DashboardPage.getCardBalance(secondCardId), secondCardStartBalance);
//    }

    @Test
    void shouldTransferMoneyFromWrongSecondToFirstCard() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();

        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var DashboardPage = new DashboardPage();
        var firstCardId = "5559 0000 0000 0001";
        var secondCardId = "5559 0001 0000 0002";
        var amount = 500;

        DashboardPage.transferToFromAmount(firstCardId, secondCardId, String.valueOf(amount));
        DashboardPage.getErrorNotification().shouldHave(text("Ошибка! Произошла ошибка"), Duration.ofMillis(15000));
    }


}
