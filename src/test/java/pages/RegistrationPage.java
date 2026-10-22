package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage extends BasePage {

    public SelenideElement emailInput =
            $x("//input[@name='email']");
    public SelenideElement passwordInput =
            $x("//input[@name='password']");
    public SelenideElement submitPasswordInput =
            $x("//input[@name='submitPassword']");
    public SelenideElement createAccountButton =
            $x("//button[contains(normalize-space(.),'Создать аккаунт')]");

    /**
     * Ошибка валидации поля — span с классом input_span (с динамическим суффиксом)
     * и текстом "Ошибка". Используем normalize-space, чтобы не зависеть от пробелов.
     */
    public SelenideElement errorSpan =
            $x("//span[contains(@class,'input_span') and normalize-space(.)='Ошибка']");

    public void fillRegistrationForm(String email, String password) {
        emailInput.setValue(email);
        passwordInput.setValue(password);
        submitPasswordInput.setValue(password);
    }

    public void submitRegistration() {
        createAccountButton.click();
    }

    /**
     * Ждём появления ошибки под полем email (до 10 секунд).
     * Используется в шаге "пользователь видит сообщение об ошибке".
     */
    public void verifyErrorIsDisplayed() {
        errorSpan.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }


}