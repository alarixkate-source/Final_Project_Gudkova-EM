package stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import pages.LoginPage;
import pages.MainPage;
import utils.ApiClient;
import utils.TestContext;
import utils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginSteps {

    private final TestContext context;
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Дано("пользователь зарегистрирован через API")
    public void registerUserViaApi() {
        String email = TestDataGenerator.generateEmail();
        String password = TestDataGenerator.generatePassword();
        Response response = ApiClient.registerUser(email, password);
        assertEquals(201, response.statusCode(),
                "Регистрация через API не удалась: " + response.asString());

        context.setEmail(email);
        context.setPassword(password);
        context.setToken(response.jsonPath().getString("access_token.access_token"));
        context.setUserId(response.jsonPath().getInt("user.id"));
    }

    @Когда("пользователь вводит свои учетные данные и нажимает Войти")
    public void loginWithCredentials() {
        mainPage.openMainPage();
        mainPage.clickLoginRegister();
        loginPage.fillLoginForm(context.getEmail(), context.getPassword());
        loginPage.submitLogin();
    }

    @Тогда("пользователь успешно авторизован")
    public void verifyLoginSuccess() {
        mainPage.verifyUserIsLoggedIn();
    }
}