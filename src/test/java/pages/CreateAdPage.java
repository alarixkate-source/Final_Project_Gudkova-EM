package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CreateAdPage extends BasePage {

    public SelenideElement titleInput =
            $x("//input[@name='name']");
    public SelenideElement categoryDropdown =
            $x("//button[contains(@class,'dropDownMenu_arrow')]");
    public SelenideElement descriptionTextarea =
            $x("//textarea[@name='description']");
    public SelenideElement priceInput =
            $x("//input[@name='price']");
    public SelenideElement publishButton =
            $x("//button[contains(normalize-space(.),'Опубликовать')]");


    /** input для первой картинки. */
    public SelenideElement photoInput1 =
            $x("//input[@type='file' and @name='img1']");

    public void fillAdForm(String title, String category, String description, String price) {
        titleInput.setValue(title);

        categoryDropdown.click();
        $x("//button[contains(@class,'dropDownMenu_btn')]//span[normalize-space(.)='"
                + category + "']").click();

        descriptionTextarea.setValue(description);
        priceInput.setValue(price);
    }

    /**
     * Загружаем картинку из ресурсов проекта.
     * @param resourcePath путь src/test/resources"
     */
    public void uploadPhoto(String resourcePath) {
        photoInput1.uploadFromClasspath(resourcePath);
    }

    public void submitAd() {
        publishButton.click();
    }
}