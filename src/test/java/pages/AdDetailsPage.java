package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class AdDetailsPage extends BasePage {

    public SelenideElement deleteButton = $x("//button[contains(text(),'Удалить')]");

    public void clickDelete() {
        deleteButton.click();
    }
}