package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Data;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    private SelenideElement withoutCode = $("[data-test-id=code].input_invalid .input__sub");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public void inputCode(String code) {
        codeField.setValue(code);
        verifyButton.click();
    }

    public DashboardPage validVerify(String code) {
        inputCode(code);
        return new DashboardPage();
    }

    public VerificationPage invalidVerify(String code, String msg) {
        inputCode(code);
        errorNotification.shouldBe(text(msg), visible);
        return this;
    }

    public VerificationPage withoutCode(String code, String msg) {
        inputCode(code);
        withoutCode.shouldBe(text(msg), visible);
        return this;
    }
}
