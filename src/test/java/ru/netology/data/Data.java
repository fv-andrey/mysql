package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.sql.DriverManager;
import java.util.Locale;

public class Data {
    private static Faker faker = new Faker(new Locale("en"));

    private Data() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }


    public static String getRandomPassword() {
        return faker.internet().password();
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCode() {
        var code = "select code from auth_codes order by created desc limit 1";
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        var verCode = new QueryRunner().query(conn, code, new ScalarHandler<String>());
        return new VerificationCode(verCode);
    }

    public static VerificationCode getRandomCode() {
        return new VerificationCode(faker.numerify("######"));
    }
}

