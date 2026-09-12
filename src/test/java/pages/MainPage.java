package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage extends BasePage {

    // ============================================================
    //                    ЛОКАТОРЫ
    // ============================================================

    // --- Кнопки в шапке ---
    public SelenideElement loginRegisterButton =
            $x("//button[contains(normalize-space(.),'Вход и регистрация')]");
    public SelenideElement logoutButton =
            $x("//button[contains(normalize-space(.),'Выйти')]");
    public SelenideElement profileAvatarButton =
            $x("//button[contains(@class,'circleSmall')]");

    // --- Кнопка создания объявления ---
    public SelenideElement createAdButton =
            $x("//button[contains(normalize-space(.),'Разместить объявление')]");

    // --- Поиск ---
    public SelenideElement searchInput =
            $x("//input[@placeholder='Я хочу купить...']");
    public SelenideElement searchCategoryDropdown =
            $x("//input[@name='category']/following-sibling::button");
    public SelenideElement searchCityDropdown =
            $x("//input[@name='city']/following-sibling::button");
    public SelenideElement searchApplyButton =
            $x("//button[contains(normalize-space(.),'Применить')]");

    // --- Ссылки в футере (проверка, что мы на главной) ---
    public SelenideElement documentationLink =
            $x("//a[normalize-space(.)='Документация']");
    public SelenideElement aboutLink =
            $x("//a[normalize-space(.)='О нас']");
    public SelenideElement rulesLink =
            $x("//a[normalize-space(.)='Правила платформы']");

    // ============================================================
    //                    ДЕЙСТВИЯ
    // ============================================================

    public void clickLoginRegister() {
        loginRegisterButton.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public void clickProfileAvatar() {
        profileAvatarButton.click();
    }

    public void clickCreateAd() {
        createAdButton.click();
    }

    public void goToProfile() {
        clickProfileAvatar();
    }

    /**
     * Поиск объявления по названию, категории и городу.
     * Город обязателен, потому что форма поиска фильтрует и по нему
     * (по умолчанию в поле city стоит «Москва»).
     */
    public void searchByTitleAndCategoryAndCity(String title, String category, String city) {
        // 1. Название
        searchInput.setValue(title);

        // 2. Категория
        searchCategoryDropdown.click();
        $x("//button[contains(@class,'dropDownMenu_btn')]//span[normalize-space(.)='"
                + category + "']").click();

        // 3. Город
        searchCityDropdown.click();
        $x("//button[contains(@class,'dropDownMenu_btn')]//span[normalize-space(.)='"
                + city + "']").click();

        // 4. Применить
        searchApplyButton.click();
    }

    /**
     * Клик по карточке объявления с ожиданием.
     */
    public void openAdCard(String title) {
        SelenideElement card = $x(
                "//div[contains(@class,'card')][.//h2[normalize-space(.)='" + title + "']]"
        );
        card.shouldBe(Condition.visible, Duration.ofSeconds(15));
        card.scrollTo();
        card.shouldBe(Condition.enabled, Duration.ofSeconds(5));
        card.click();
    }

    // ============================================================
    //                    ПРОВЕРКИ
    // ============================================================

    /** Ждём появления кнопки «Выйти» — пользователь авторизован. */
    public void verifyUserIsLoggedIn() {
        logoutButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    /** Ждём появления карточки объявления с указанным названием (до 10 сек). */
    public void verifyAdCardIsDisplayed(String title) {
        $x("//h2[normalize-space(.)='" + title + "']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    /** Проверяем, что карточки с указанным названием нет на странице. */
    public void verifyAdNotPresent(String title) {
        $x("//h2[normalize-space(.)='" + title + "']")
                .shouldNotBe(Condition.visible, Duration.ofSeconds(5));
    }

    /** Проверить, что мы находимся на главной странице — по футеру. */
    public void verifyOnMainPage() {
        documentationLink.shouldBe(Condition.visible, Duration.ofSeconds(10));
        aboutLink.shouldBe(Condition.visible);
        rulesLink.shouldBe(Condition.visible);
    }
}