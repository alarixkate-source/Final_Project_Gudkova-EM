package stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import utils.ApiClient;
import utils.TestContext;
import utils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationSteps {

    private final TestContext context;
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();
    private final RegistrationPage registrationPage = new RegistrationPage();

    public RegistrationSteps(TestContext context) {
        this.context = context;
    }

    // -------- Сценарий 1: успешная регистрация --------

    @Дано("пользователь генерирует уникальные email и пароль")
    public void generateUniqueCredentials() {
        context.setEmail(TestDataGenerator.generateEmail());
        context.setPassword(TestDataGenerator.generatePassword());
    }

    @Когда("пользователь открывает страницу регистрации")
    public void openRegistrationPage() {
        mainPage.openMainPage();
        mainPage.clickLoginRegister();
        loginPage.clickNoAccount();
    }

    @Когда("пользователь заполняет форму регистрации и отправляет")
    public void fillAndSubmitRegistration() {
        registrationPage.fillRegistrationForm(context.getEmail(), context.getPassword());
        registrationPage.submitRegistration();
    }

    @Тогда("пользователь видит сообщение об успешной регистрации")
    public void verifySuccessfulRegistration() {
        mainPage.verifyUserIsLoggedIn();
    }

    // -------- Сценарий 2: повторная регистрация --------

    /**
     * Предусловие: создаём свежего пользователя через API с уникальным email.
     * Регистрация должна вернуть 200, иначе ошибка на стороне API.
     */
    @Дано("пользователь с уникальным email зарегистрирован через API")
    public void registerUniqueUserViaApi() {
        String email = TestDataGenerator.generateEmail();
        String password = TestDataGenerator.generatePassword();

        Response response = ApiClient.registerUser(email, password);
        assertEquals(201, response.statusCode(),
                "Не удалось зарегистрировать пользователя через API. Ответ: " + response.asString());

        context.setEmail(email);
        context.setPassword(password);
        context.setToken(response.jsonPath().getString("access_token.access_token"));
        context.setUserId(response.jsonPath().getInt("user.id"));
    }

    /**
     * Пытаемся зарегистрироваться повторно через UI с тем же email.
     * Перед этим проверяем, что мы разлогинены.
     */
    @Когда("пользователь пытается зарегистрироваться с тем же email")
    public void registerWithSameEmail() {
        mainPage.openMainPage();
        mainPage.clickLoginRegister();
        loginPage.clickNoAccount();
        registrationPage.fillRegistrationForm(context.getEmail(), context.getPassword());
        registrationPage.submitRegistration();
    }

    @Тогда("пользователь видит сообщение об ошибке")
    public void verifyError() {
        registrationPage.verifyErrorIsDisplayed();
    }
}