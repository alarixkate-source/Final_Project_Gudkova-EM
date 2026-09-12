package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class ProfilePage extends BasePage {

    public SelenideElement profileTitle =
            $x("//h1[normalize-space(.)='Мой профиль']");

    /** Заголовок карточки объявления по названию. */
    public SelenideElement adTitleElement(String title) {
        return $x("//h2[normalize-space(.)='" + title + "']");
    }

    /**
     * Кнопка редактирования объявления по названию.
     */
    public SelenideElement editButtonForAd(String title) {
        return $x("//div[contains(@class,'card')]"
                + "[.//h2[normalize-space(.)='" + title + "']]"
                + "//button[contains(@class,'editButton')]");
    }

    /** Ждём, что карточка появится (до 10 секунд). */
    public void verifyAdIsDisplayedInProfile(String title) {
        scrollToBottom();
        adTitleElement(title).shouldBe(Condition.visible, Duration.ofSeconds(10));
    }

    /**
     * Клик по кнопке редактирования объявления.
     * Перед кликом прокручиваем страницу, чтобы подгрузились lazy-карточки.
     */
    public void clickEditForAd(String title) {
        // 1. Прокручиваем вниз — карточки объявлений подгружаются в lazy-режиме
        scrollToBottom();
        // 2. Убеждаемся, что карточка появилась
        adTitleElement(title).shouldBe(Condition.visible, Duration.ofSeconds(10));
        // 3. Прокручиваем к самой карточке
        adTitleElement(title).scrollTo();
        // 4. Ждём, что кнопка активна, и кликаем
        editButtonForAd(title)
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .shouldBe(Condition.enabled, Duration.ofSeconds(10))
                .click();
    }
}