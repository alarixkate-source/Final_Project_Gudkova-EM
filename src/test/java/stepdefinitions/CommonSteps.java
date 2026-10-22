package stepdefinitions;

import io.cucumber.java.ru.Дано;
import pages.LoginPage;
import pages.MainPage;
import utils.TestContext;

public class CommonSteps {

    private final TestContext context;
    private final MainPage mainPage = new MainPage();
    private final LoginPage loginPage = new LoginPage();

    public CommonSteps(TestContext context) {
        this.context = context;
    }
// Тоже использовала сначала, потом отказалась. Шаг пусть останется.
    @Дано("пользователь открыл главную страницу")
    public void openMainPage() {
        mainPage.openMainPage();
    }

    /**
     * Универсальный шаг авторизации через UI.
     * Использует email/пароль, сохранённые в TestContext (обычно после регистрации через API).
     * После выполнения — пользователь залогинен в браузере, кнопка "Выйти" видна.
     */
    @Дано("пользователь авторизуется в UI")
    public void loginViaUi() {
        mainPage.openMainPage();
        mainPage.clickLoginRegister();
        loginPage.fillLoginForm(context.getEmail(), context.getPassword());
        loginPage.submitLogin();
        mainPage.verifyUserIsLoggedIn();
    }
}