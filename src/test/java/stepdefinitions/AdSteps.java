package stepdefinitions;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import pages.MainPage;
import pages.CreateAdPage;
import utils.TestContext;
import utils.ApiClient;
import io.restassured.response.Response;
import utils.TestDataGenerator;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


public class AdSteps {
    private final TestContext context;
    private final MainPage mainPage = new MainPage();
    private final CreateAdPage createAdPage = new CreateAdPage();

    public AdSteps(TestContext context) {
        this.context = context;
    }
    // Создавала через апи, потом стала делать через ui. Ну пусть повисит, есть не просит
    @Дано("пользователь создает объявление через API")
    public void createAdViaApi() {
        String title = TestDataGenerator.generateAdTitle();
        String category = TestDataGenerator.generateCategory();
        String description = TestDataGenerator.generateAdDescription();
        String price = TestDataGenerator.generatePrice();

        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", title);
        formParams.put("category", category);
        formParams.put("condition", TestDataGenerator.generateCondition());
        formParams.put("city", TestDataGenerator.generateCityFromList());
        formParams.put("description", description);
        formParams.put("price", price);

        Response response = ApiClient.createAd(context.getToken(), formParams);
        assertEquals(201, response.statusCode());
        context.setAdId(response.jsonPath().getInt("id"));
        context.setAdTitle(title);
        context.setAdCategory(category);
        context.setAdDescription(description);
        context.setAdPrice(price);
    }

    @Когда("пользователь переходит на страницу создания объявления")
    public void goToCreateAdPage() {
        mainPage.openMainPage();
        mainPage.clickCreateAd();
    }

    @Когда("заполняет форму объявления валидными данными и публикует")
    public void fillAndPublishAd() {
        String title = TestDataGenerator.generateAdTitle();
        String category = TestDataGenerator.generateCategory();
        String description = TestDataGenerator.generateAdDescription();
        String price = TestDataGenerator.generatePrice();

        context.setAdTitle(title);
        context.setAdCategory(category);
        context.setAdDescription(description);
        context.setAdPrice(price);

        createAdPage.fillAdForm(title, category, description, price);
        createAdPage.uploadPhoto("images/sample.jpg");   // ← вот здесь
        createAdPage.submitAd();
    }

    @Тогда("объявление успешно создано и отображается на главной странице")
    public void verifyAdCreated() {
        mainPage.scrollToTop();
        mainPage.searchByTitleAndCategoryAndCity(
                context.getAdTitle(),
                context.getAdCategory(),
                context.getAdCity() != null ? context.getAdCity() : "Москва"
        );
        mainPage.verifyAdCardIsDisplayed(context.getAdTitle());
    }
}