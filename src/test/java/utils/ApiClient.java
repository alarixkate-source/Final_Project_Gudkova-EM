package utils;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;

import java.util.Map;

public class ApiClient {

    private static final String BASE_URL = "https://qa-desk.education-services.ru";

    public static Response registerUser(String email, String password) {
        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"submitPassword\":\"%s\"}",
                email, password, password);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE_URL + "/api/signup");
    }

    public static Response loginUser(String email, String password) {
        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                email, password);
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post(BASE_URL + "/api/signin");
    }

    /**
     * Создание объявления через multipart/form-data.
     *
     * Для каждой части используем MultiPartSpecBuilder с явным charset="UTF-8" иначе фигня сохраняется на сервере.
     */
    public static Response createAd(String token, Map<String, String> formParams) {
        var request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("multipart/form-data");

        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            MultiPartSpecification spec = new MultiPartSpecBuilder(entry.getValue())
                    .controlName(entry.getKey())
                    .mimeType("text/plain")
                    .charset("UTF-8")
                    .build();
            request = request.multiPart(spec);
        }

        return request.post(BASE_URL + "/api/create-listing");
    }
//не потребовалось, изменение через апи
    public static Response updateAd(String token, int adId, Map<String, String> formParams) {
        var request = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .contentType("multipart/form-data");

        for (Map.Entry<String, String> entry : formParams.entrySet()) {
            MultiPartSpecification spec = new MultiPartSpecBuilder(entry.getValue())
                    .controlName(entry.getKey())
                    .mimeType("text/plain")
                    .charset("UTF-8")
                    .build();
            request = request.multiPart(spec);
        }

        return request.patch(BASE_URL + "/api/update-offer/" + adId);
    }

    public static Response deleteAd(String token, int adId) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .delete(BASE_URL + "/api/listings/" + adId);
    }
// тоже использовала сначала, а потом решила что не надо, нет смысла гет запрос делать
    public static Response getListingById(int id) {
        return RestAssured.given()
                .get(BASE_URL + "/api/listings/" + id);
    }
}