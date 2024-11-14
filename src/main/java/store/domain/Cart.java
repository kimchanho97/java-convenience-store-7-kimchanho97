package store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Product, Integer> items;

    public Cart() {
        items = new LinkedHashMap<>();
    }

    public void addItem(Product product, int quantity) {
        items.put(product, quantity);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }
}
