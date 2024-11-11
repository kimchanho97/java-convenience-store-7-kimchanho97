package store.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {

    private final Map<Product, Integer> cart;

    public Cart() {
        cart = new LinkedHashMap<>();
    }

    public void addCart(Product product, int quantity) {
        cart.put(product, quantity);
    }

    public Map<Product, Integer> getCart() {
        return cart;
    }
}
