package utils;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class TestDataGenerator {

    // Русская локаль для города, имени и т.п.
    private static final Faker faker = new Faker(new Locale("ru"));
    private static final Random random = new Random();

    // ---------- Email и пароль ----------
    // Email всегда латиницей — это требование стандарта, иначе сервер может отклонить.
    public static String generateEmail() {
        // Латиница: буквы + цифры для уникальности
        String prefix = "user" + System.currentTimeMillis()
                + random.nextInt(10000);
        String[] domains = {"ya.ru", "mail.ru", "test.ru", "example.com"};
        String domain = domains[random.nextInt(domains.length)];
        return prefix + "@" + domain;
    }

    public static String generatePassword() {
        // Только ASCII, без спецсимволов — чтобы точно прошло валидацию
        return "Pass" + (100000 + random.nextInt(900000));
    }

    // ---------- Русские тексты ----------

    private static final String[] ADJECTIVES = {
            "Красивый", "Новый", "Старинный", "Редкий", "Уникальный",
            "Компактный", "Большой", "Удобный", "Качественный", "Оригинальный"
    };

    private static final String[] NOUNS = {
            "стол", "стул", "шкаф", "диван", "компьютер", "телефон",
            "велосипед", "самокат", "чемодан", "рюкзак", "фотоаппарат",
            "гитара", "пианино", "картина", "книга", "лампа",
            "ковёр", "подушка", "часы", "портфель"
    };

    /**
     * Генерирует русское название объявления — например «Новый компьютер».
     */
    public static String generateAdTitle() {
        String adjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[random.nextInt(NOUNS.length)];
        String result = adjective + " " + noun;
        // Добавляем короткий числовой суффикс для уникальности — иначе поиск может дать ложное совпадение
        return result + " " + (100 + random.nextInt(900));
    }

    private static final String[] DESCRIPTION_PHRASES = {
            "Продаю в хорошем состоянии, использовалось аккуратно.",
            "Торг возможен, но небольшой.",
            "Возможен самовывоз или доставка по договорённости.",
            "Всё работает, никаких нареканий нет.",
            "Отличный вариант за свои деньги.",
            "Причина продажи — переезд, больше не нужно.",
            "Готов ответить на любые вопросы.",
            "Есть небольшие следы использования, цена снижена.",
            "Покупал сам, доволен, но решил продать.",
            "Не требует ремонта, можно пользоваться сразу."
    };

    /**
     * Генерирует русское описание из 1-3 фраз.
     */
    public static String generateAdDescription() {
        int count = 1 + random.nextInt(3);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (i > 0) sb.append(" ");
            sb.append(DESCRIPTION_PHRASES[random.nextInt(DESCRIPTION_PHRASES.length)]);
        }
        return sb.toString();
    }

    // ---------- Категории, цена, город ----------

    /**
     * Одна из реальных категорий сайта.
     */
    public static String generateCategory() {
        String[] categories = {"Авто", "Книги", "Садоводство", "Хобби", "Технологии"};
        return categories[random.nextInt(categories.length)];
    }

    public static String generatePrice() {
        return String.valueOf(100 + random.nextInt(9900));
    }

    public static String generateCityFromList() {
        String[] cities = {"Москва", "Санкт-Петербург", "Новосибирск",
                "Екатеринбург", "Нижний Новгород", "Казань"};
        return cities[random.nextInt(cities.length)];
    }

    public static String generateCondition() {
        // Только два допустимых значения на сайте
        return random.nextBoolean() ? "Новый" : "Б/У";
    }
}