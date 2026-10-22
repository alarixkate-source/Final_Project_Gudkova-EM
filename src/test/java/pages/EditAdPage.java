package pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;

public class EditAdPage extends BasePage {

    public SelenideElement titleInput = $x("//input[@name='name']");
    public SelenideElement descriptionTextarea = $x("//textarea[@name='description']");
    public SelenideElement saveButton = $x("//button[contains(text(),'Сохранить изменения')]");

    public void editTitle(String newTitle) {
        titleInput.clear();
        titleInput.setValue(newTitle);
    }

    public void editDescription(String newDescription) {
        descriptionTextarea.clear();
        descriptionTextarea.setValue(newDescription);
    }

    public void saveChanges() {
        saveButton.click();
    }
}