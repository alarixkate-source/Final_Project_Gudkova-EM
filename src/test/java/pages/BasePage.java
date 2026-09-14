package pages;

import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {
    protected static final String BASE_URL = "https://qa-desk.education-services.ru";


    public void openMainPage() {
        open(BASE_URL);
    }

    public void scrollToBottom() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0)");
    }
}