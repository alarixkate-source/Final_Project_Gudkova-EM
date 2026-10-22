package stepdefinitions;

import com.codeborne.selenide.Condition;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import pages.AdDetailsPage;
import pages.EditAdPage;
import pages.MainPage;
import pages.ProfilePage;
import utils.ApiClient;
import utils.TestContext;
import utils.TestDataGenerator;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EditDeleteAdSteps {

    private final TestContext context;
    private final MainPage mainPage = new MainPage();
    private final ProfilePage profilePage = new ProfilePage();
    private final EditAdPage editAdPage = new EditAdPage();
    private final AdDetailsPage adDetailsPage = new AdDetailsPage();

    public EditDeleteAdSteps(TestContext context) {
        this.context = context;
    }

    // ============================================================
    //             ПРЕДУСЛОВИЕ — создать объявление через API
    // ============================================================

    @Дано("у пользователя есть объявление в профиле")
    public void userHasAdInProfile() {
        if (context.getAdId() != null) return;

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

        System.out.println("=== СОЗДАЁМ ОБЪЯВЛЕНИЕ ===");
        System.out.println("  name = " + title);
        System.out.println("  category = " + category);
        System.out.println("  condition = " + formParams.get("condition"));
        System.out.println("  city = " + formParams.get("city"));

        Response response = ApiClient.createAd(context.getToken(), formParams);
        assertEquals(201, response.statusCode(),
                "Не удалось создать объявление через API: " + response.asString());

        int adId = response.jsonPath().getInt("id");
        String savedName = response.jsonPath().getString("name");

        System.out.println("=== СОЗДАНО, id = " + adId + " ===");
        System.out.println("  name из ответа = " + savedName);

        context.setAdId(adId);
        context.setAdTitle(title);
        context.setAdCategory(category);
        context.setAdDescription(description);
        context.setAdPrice(price);
        context.setAdCity(formParams.get("city"));


    }

    // ============================================================
    //               РЕДАКТИРОВАНИЕ (через профиль)
    // ============================================================

    @Когда("пользователь переходит в профиль и выбирает свое объявление для редактирования")
    public void goToEditAd() {
        mainPage.openMainPage();
        mainPage.goToProfile();
        profilePage.profileTitle.shouldBe(Condition.visible, Duration.ofSeconds(10));
        profilePage.clickEditForAd(context.getAdTitle());
    }

    @Когда("вносит изменения в название и описание и сохраняет")
    public void editAdDetails() {
        String newTitle = "Изменённый " + TestDataGenerator.generateAdTitle();
        String newDescription = "Изменено: " + TestDataGenerator.generateAdDescription();
        editAdPage.editTitle(newTitle);
        editAdPage.editDescription(newDescription);
        editAdPage.saveChanges();
        context.setAdTitle(newTitle);
        context.setAdDescription(newDescription);
    }

    @Тогда("изменения сохраняются и объявление отображается обновленным")
    public void verifyAdUpdated() {
        // После сохранения нас редиректит на главную — проверим, что мы там
        mainPage.verifyOnMainPage();

        // Переходим в профиль
        mainPage.goToProfile();
        profilePage.profileTitle.shouldBe(Condition.visible, Duration.ofSeconds(10));

        // Проверяем, что карточка с новым названием видна (с ожиданием!)
        profilePage.verifyAdIsDisplayedInProfile(context.getAdTitle());
    }

    // ============================================================
    //               УДАЛЕНИЕ (через поиск на главной)
    // ============================================================

    @Когда("пользователь находит свое объявление через поиск")
    public void findAdViaSearch() {
        mainPage.searchByTitleAndCategoryAndCity(
                context.getAdTitle(),
                context.getAdCategory(),
                context.getAdCity()
        );
        mainPage.verifyAdCardIsDisplayed(context.getAdTitle());
    }

    @Когда("открывает карточку объявления")
    public void openAdCard() {
        mainPage.openAdCard(context.getAdTitle());
    }

    @Когда("нажимает кнопку Удалить")
    public void clickDelete() {
        adDetailsPage.clickDelete();
    }

    @Тогда("объявление удалено и не отображается в поиске")
    public void verifyAdDeleted() {
        mainPage.openMainPage();
        mainPage.searchByTitleAndCategoryAndCity(
                context.getAdTitle(),
                context.getAdCategory(),
                context.getAdCity()
        );
        mainPage.verifyAdNotPresent(context.getAdTitle());
    }
}