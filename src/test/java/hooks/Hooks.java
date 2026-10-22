package hooks;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ApiClient;
import utils.TestContext;

import java.io.ByteArrayInputStream;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    /**
     * Перед каждым сценарием берём путь к ChromeDriver из системной переменной.
     * Так проект не привязан к конкретной машине или папке.
     */
    @Before(order = 0)
    public void setupDriverPath() {
        String driverPath = System.getenv("CHROMEDRIVER_PATH");
        if (driverPath != null && !driverPath.isEmpty()) {
            System.setProperty("webdriver.chrome.driver", driverPath);
            System.out.println("ChromeDriver path = " + driverPath);
        } else {
            System.out.println("Переменная CHROMEDRIVER_PATH не задана, "
                    + "Selenium Manager попробует скачать драйвер сам");
        }
    }

    @After
    public void cleanUp(Scenario scenario) {
         // 1. Скриншот при падении
        if (scenario.isFailed() && WebDriverRunner.hasWebDriverStarted()) {
            try {
                byte[] screenshot = ((TakesScreenshot) WebDriverRunner.getWebDriver())
                        .getScreenshotAs(OutputType.BYTES);

                if (screenshot != null && screenshot.length > 0) {
                    scenario.attach(screenshot, "image/png", "Скриншот при падении");
                    Allure.addAttachment("Скриншот при падении",
                            new ByteArrayInputStream(screenshot));
                }
            } catch (Exception e) {
                System.err.println("Не удалось сделать скриншот: " + e.getMessage());
            }
        }

        // 2. Удаление созданного объявления через API
        if (context.getAdId() != null && context.getToken() != null) {
            try {
                var response = ApiClient.deleteAd(context.getToken(), context.getAdId());
                if (response.statusCode() == 200) {
                    System.out.println("Объявление " + context.getAdId() + " удалено через API");
                } else {
                    System.err.println("Не удалось удалить объявление " + context.getAdId()
                            + ", код: " + response.statusCode());
                }
            } catch (Exception e) {
                System.err.println("Ошибка при удалении объявления: " + e.getMessage());
            }
        }

        // 3. Закрываем браузер между сценариями
        Selenide.closeWebDriver();
    }
}