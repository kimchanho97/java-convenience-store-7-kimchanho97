package store.util;

import static store.exception.message.InputExceptionMessage.INVALID_INPUT_FORMAT;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import store.exception.CustomException.InputException;

public class ProductInputParser {

    private ProductInputParser() {
    }

    public static Map<String, Integer> parse(String input) {
        InputValidator.validateInput(input);

        return Arrays.stream(input.split(","))
                .map(String::strip)
                .collect(Collectors.toMap(
                        ProductInputParser::extractProductName,
                        ProductInputParser::extractQuantity,
                        Integer::sum,
                        LinkedHashMap::new));
    }

    private static String extractProductName(String item) {
        validateItemFormat(item);
        return item.substring(1, item.length() - 1).split("-")[0];
    }

    private static int extractQuantity(String item) {
        validateItemFormat(item);
        String quantity = item.substring(1, item.length() - 1).split("-")[1];
        return NumberParser.parsePositiveInteger(quantity);
    }

    private static void validateItemFormat(String item) {
        if (!item.matches("\\[([a-zA-Z가-힣\\s]+)-(\\d+)]")) {
            throw new InputException(INVALID_INPUT_FORMAT);
        }
    }

}
