package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage {

    public SelenideElement emailInput = $x("//input[@name='email']");
    public SelenideElement passwordInput = $x("//input[@name='password']");
    public SelenideElement loginButton = $x("//button[contains(text(),'Войти')]");
    public SelenideElement noAccountButton = $x("//button[contains(text(),'Нет аккаунта')]");

    public void fillLoginForm(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
    }

    public void submitLogin() {
        loginButton.click();
    }

    public void clickNoAccount() {
        noAccountButton.click();
    }
}