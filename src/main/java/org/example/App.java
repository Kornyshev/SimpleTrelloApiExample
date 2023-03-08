package org.example;

import io.restassured.RestAssured;

public class App {

    private static final String BASE_URL = "https://api.trello.com";
    private static final String CREATE_BOARD_ENDPOINT = "/1/boards/";
    private static final String GET_OR_DELETE_BOARD_ENDPOINT = "/1/boards/{id}";
    private static final String API_KEY = "23e04a2ee69d35a6887a4c4c72dd6f19";
    private static final String TOKEN = "ATTA048c40080b1f181b93861b07a1bd86ff417a4442feed1ef97b333875e4c989f3A8244524";

    /**
     * Temporary credentials:
     * E-Mail - hipid39184@fenwazi.com
     * Display name - Automation
     * Password - C#!tr=TqH,9;6BF
     * <p>
     * Trello Key: 23e04a2ee69d35a6887a4c4c72dd6f19
     * Trello Token: ATTA048c40080b1f181b93861b07a1bd86ff417a4442feed1ef97b333875e4c989f3A8244524
     *
     * @param args
     */
    public static void main(String[] args) {
        String createdBoardId = createBoardAndReturnCreatedBoardId(generateBoardName());
        deleteBoardById(createdBoardId);
        System.out.println("Check that board was deleted. Status code: " + getBoardById(createdBoardId));
    }

    public static String createBoardAndReturnCreatedBoardId(String boardName) {
        return RestAssured.given().baseUri(BASE_URL)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .queryParam("name", boardName)
                .header("Content-Type", "application/json; charset=utf-8")
                .log().uri().when()
                .post(CREATE_BOARD_ENDPOINT)
                .then().statusCode(200).extract().response().getBody().jsonPath().get("id");
    }

    public static void deleteBoardById(String boardId) {
        RestAssured.given().baseUri(BASE_URL)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .header("Content-Type", "application/json; charset=utf-8")
                .log().uri().when()
                .pathParam("id", boardId).delete(GET_OR_DELETE_BOARD_ENDPOINT)
                .then().statusCode(200).extract().response();
    }

    public static int getBoardById(String boardId) {
        return RestAssured.given().baseUri(BASE_URL)
                .queryParam("key", API_KEY)
                .queryParam("token", TOKEN)
                .header("Content-Type", "application/json; charset=utf-8")
                .log().uri().when()
                .pathParam("id", boardId).get(GET_OR_DELETE_BOARD_ENDPOINT)
                .then().extract().response().statusCode();
    }

    public static String generateBoardName() {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return "NewBoard_" + timestamp.substring(timestamp.length() - 5);
    }

}
