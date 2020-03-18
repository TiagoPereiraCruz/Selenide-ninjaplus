package tests;

import common.BaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;

public class LoginTests extends BaseTest {

    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"tiago@ninjaplus.com", "abc123", "Usuário e/ou senha inválidos"},
                {"404@gmail.com", "pwd123", "Usuário e/ou senha inválidos"},
                {"", "pwd123", "Opps. Cadê o email?"},
                {"tiago@ninjaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @Test
    public void shouldSeeLoggedUser() {
        Login
                .open()
                .with("tiago@ninjaplus.com", "pwd123");

        Side.loggedUser().shouldHave(text("Tiago"));
    }

    @Test(dataProvider = "login-alerts")
    public void ShouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        Login
                .open()
                .with(email, pass)
                .alert().shouldHave(text(expectAlert));
    }

    @AfterMethod
    public void tearDown() {
        Login.clearSession();
    }
}
