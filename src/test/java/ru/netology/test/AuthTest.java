package ru.netology.test;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Data;
import ru.netology.page.LoginPage;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @SneakyThrows
    @AfterAll
    public static void clearDB() {
        QueryRunner runner = new QueryRunner();
        var con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        runner.update(con, "delete from auth_codes");
        runner.update(con, "delete from card_transactions");
        runner.update(con, "delete from cards");
        runner.update(con, "delete from users");
    }

    @Test
    public void happyPathTest() {
        new LoginPage()
                .validLogin(Data.getAuthInfo().getLogin(), Data.getAuthInfo().getPassword())
                .validVerify(Data.getVerificationCode().getCode());
    }

    @Test
    public void inputInvalidLoginTest() {
        String msg = "Ошибка! Неверно указан логин или пароль";
        new LoginPage()
                .invalidLogin(Data.getRandomLogin(), Data.getAuthInfo().getPassword(), msg);
    }

    @Test
    public void withoutLoginTest() {
        String msg = "Поле обязательно для заполнения";
        new LoginPage()
                .withoutLogin("", Data.getAuthInfo().getPassword(), msg);
    }

    @Test
    public void inputInvalidPasswordTest() {
        String msg = "Ошибка! Неверно указан логин или пароль";
        new LoginPage()
                .invalidLogin(Data.getAuthInfo().getLogin(), Data.getRandomPassword(), msg);
    }

    @Test
    public void withoutPasswordTest() {
        String msg = "Поле обязательно для заполнения";
        new LoginPage()
                .withoutPassword(Data.getAuthInfo().getLogin(), "", msg);
    }

    @Test
    public void inputInvalidPasswordThreeTimesTest() {
        new LoginPage()
                .blockedSystem(Data.getAuthInfo().getLogin(), Data.getRandomPassword());
    }

    @Test
    public void inputInvalidVerificationCode() {
        String msg = "Ошибка! Неверно указан код! Попробуйте ещё раз.";
        new LoginPage()
                .validLogin(Data.getAuthInfo().getLogin(), Data.getAuthInfo().getPassword())
                .invalidVerify(Data.getRandomCode().getCode(), msg);
    }

    @Test
    public void withoutCodeTest() {
        String msg = "Поле обязательно для заполнения";
        new LoginPage()
                .validLogin(Data.getAuthInfo().getLogin(), Data.getAuthInfo().getPassword())
                .withoutCode("", msg);
    }
}
