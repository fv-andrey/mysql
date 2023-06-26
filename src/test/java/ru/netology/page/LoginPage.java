package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.Data;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    private SelenideElement blockedError = $("[data-test-id=error-notification]");
    private SelenideElement withoutLogin = $("[data-test-id=login].input_invalid .input__sub");
    private SelenideElement withoutPassword = $("[data-test-id=password].input_invalid .input__sub");

    public void inputLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }

    public void clearFields() {
        loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
    }

    public VerificationPage validLogin(String login, String password) {
        inputLogin(login, password);
        return new VerificationPage();
    }

    public void invalidLogin(String login, String password, String msg) {
        inputLogin(login, password);
        errorNotification.shouldBe(text(msg), visible);
    }

    public void withoutLogin(String login, String password, String msg) {
        inputLogin(login, password);
        withoutLogin.shouldBe(text(msg), visible);
    }

    public void withoutPassword(String login, String password, String msg) {
        inputLogin(login, password);
        withoutPassword.shouldBe(text(msg), visible);
    }

    public void blockedSystem() {
        inputLogin(Data.getAuthInfo().getLogin(), Data.getRandomPassword());
        clearFields();
        inputLogin(Data.getAuthInfo().getLogin(), Data.getRandomPassword());
        clearFields();
        inputLogin(Data.getAuthInfo().getLogin(), Data.getRandomPassword());
        clearFields();
        inputLogin(Data.getAuthInfo().getLogin(), Data.getAuthInfo().getPassword());
        blockedError.shouldBe(visible);
    }
}
