package store.util;

import static store.exception.message.InputExceptionMessage.INVALID_INPUT_FORMAT;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import store.exception.CustomException.InputException;

public class ProductInputParser {

    private ProductInputParser() {
    }

    public static Map<String, Integer> parse(String input) {
        InputValidator.validateInput(input);

        Map<String, Integer> products = new LinkedHashMap<>();
        String[] items = input.split(",");

        Arrays.stream(items)
                .map(String::strip)
                .forEach(item -> parseAndAddItem(item, products));

        return products;
    }

    private static void parseAndAddItem(String item, Map<String, Integer> products) {
        validateItemFormat(item);

        String productName = extractProductName(item);
        int quantity = extractQuantity(item);

        products.put(productName, quantity);
    }

    private static void validateItemFormat(String item) {
        if (!item.matches("\\[(\\W+)-(\\d+)]")) {
            throw new InputException(INVALID_INPUT_FORMAT);
        }
    }

    private static String extractProductName(String item) {
        return item.substring(1, item.length() - 1).split("-")[0];
    }

    private static int extractQuantity(String item) {
        String quantity = item.substring(1, item.length() - 1).split("-")[1];
        return NumberParser.parsePositiveInteger(quantity);
    }

}
