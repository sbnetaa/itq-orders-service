package ru.terentyev.itq_orders_service.utils;

public class SwaggerExamples {


    public static final String POST_REQUEST_CREATE = "{\n" +
            "    \"article\" : 43,\n" +
            "    \"amount\" : 2,\n" +
            "    \"receiver\" : \"Дмитрий Т.\",\n" +
            "    \"paymentType\" : \"Наличкой\",\n" +
            "    \"deliveryType\" : \"Курьером\",\n" +
            "    \"address\" : \"Развилка\"\n" +
            "}";

    public static final String POST_200_RESPONSE_CREATE = "{\n" +
            "    \"id\": 20,\n" +
            "    \"number\": \"9194720250105\",\n" +
            "    \"title\": \"Гаджет\",\n" +
            "    \"cost\": 20000,\n" +
            "    \"date\": \"2025-01-05\",\n" +
            "    \"receiver\": \"Дмитрий Т.\",\n" +
            "    \"address\": \"Развилка\",\n" +
            "    \"paymentType\": \"Наличкой\",\n" +
            "    \"deliveryType\": \"Курьером\",\n" +
            "    \"article\": 28371,\n" +
            "    \"amount\": 2,\n" +
            "    \"price\": 10000\n" +
            "}";

    public static final String POST_400_RESPONSE_CREATE = "{\n" +
            "    \"message\": \"Товар с артикулом null не найден.\",\n" +
            "    \"timestamp\": \"2025-01-05T18:30:22.2870986\",\n" +
            "    \"statusCode\": 404\n" +
            "}";

    public static final String GET_200_RESPONSE_FIND_BY_ID = "{\n" +
            "    \"id\": 20,\n" +
            "    \"number\": \"9194720250105\",\n" +
            "    \"title\": \"Гаджет\",\n" +
            "    \"cost\": 20000,\n" +
            "    \"date\": \"2025-01-05\",\n" +
            "    \"receiver\": \"Дмитрий Т.\",\n" +
            "    \"address\": \"Развилка\",\n" +
            "    \"paymentType\": \"Наличкой\",\n" +
            "    \"deliveryType\": \"Курьером\",\n" +
            "    \"article\": 28371,\n" +
            "    \"amount\": 2,\n" +
            "    \"price\": 10000\n" +
            "}";

    public static final String GET_REQUEST_FIND_BY_ID = "{}";

    public static final String GET_400_RESPONSE_FIND_BY_ID = "{\n" +
            "    \"message\": \"Заказ с ID 22 не найден.\",\n" +
            "    \"timestamp\": \"2025-01-05T18:49:48.7048915\",\n" +
            "    \"statusCode\": 400\n" +
            "}";

    public static final String POST_REQUEST_SEARCH_WITH_SUM = "{\n" +
            "    \"sum\" : 50,\n" +
            "    \"date\" : \"2024-12-14\"\n" +
            "}";

    public static final String POST_REQUEST_SEARCH_WITH_ARTICLE = "{\n" +
            "    \"article\" : 43, \n" +
            "    \"dateFrom\" : \"2024-12-14\",\n" +
            "    \"dateTo\" : \"2024-12-15\"\n" +
            "}";

    public static final String POST_200_RESPONSE_SEARCH = "[" +
            "    {\n" +
            "        \"id\": 8,\n" +
            "        \"number\": \"46377\",\n" +
            "        \"title\": \"Книга\",\n" +
            "        \"cost\": 600,\n" +
            "        \"date\": \"2024-12-14\",\n" +
            "        \"receiver\": \"Дмитрий Т.\",\n" +
            "        \"address\": \"Развилка\",\n" +
            "        \"paymentType\": \"Наличкой\",\n" +
            "        \"deliveryType\": \"Курьером\",\n" +
            "        \"article\": 131,\n" +
            "        \"amount\": 2,\n" +
            "        \"price\": 300\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 9,\n" +
            "        \"number\": \"30112\",\n" +
            "        \"title\": \"Обувь\",\n" +
            "        \"cost\": 2000,\n" +
            "        \"date\": \"2024-12-14\",\n" +
            "        \"receiver\": \"Дмитрий Т.\",\n" +
            "        \"address\": \"Развилка\",\n" +
            "        \"paymentType\": \"Наличкой\",\n" +
            "        \"deliveryType\": \"Курьером\",\n" +
            "        \"article\": 43,\n" +
            "        \"amount\": 2,\n" +
            "        \"price\": 1000\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 10,\n" +
            "        \"number\": \"81641\",\n" +
            "        \"title\": \"Гаджет\",\n" +
            "        \"cost\": 20000,\n" +
            "        \"date\": \"2024-12-14\",\n" +
            "        \"receiver\": \"Дмитрий Т.\",\n" +
            "        \"address\": \"Развилка\",\n" +
            "        \"paymentType\": \"Наличкой\",\n" +
            "        \"deliveryType\": \"Курьером\",\n" +
            "        \"article\": 28371,\n" +
            "        \"amount\": 2,\n" +
            "        \"price\": 10000\n" +
            "    }\n" +
            "]";

    public static final String POST_400_RESPONSE_SEARCH = "{\n" +
            "    \"message\": \"Некорректный запрос на поиск\",\n" +
            "    \"timestamp\": \"2025-01-05T18:58:49.7382487\",\n" +
            "    \"statusCode\": 400\n" +
            "}";
}
