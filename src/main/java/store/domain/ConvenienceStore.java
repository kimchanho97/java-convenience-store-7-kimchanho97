package store.domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConvenienceStore {

    private final Map<String, List<Product>> inventory;

    public ConvenienceStore() {
        this.inventory = new LinkedHashMap<>();
        loadProductsFromFile();
        addDefaultProducts();
    }

    public boolean hasProduct(String name) {
        return inventory.containsKey(name);
    }

    public boolean hasSufficientQuantity(String name, Integer quantityToPurchase) {
        int sum = inventory.get(name).stream()
                .mapToInt(Product::getQuantity)
                .sum();
        return sum >= quantityToPurchase;
    }

    public List<Product> getProducts(String name) {
        return inventory.get(name).stream().sorted().toList();
    }

    public Map<String, List<Product>> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }

    private void loadProductsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            br.lines().skip(1).forEach(this::parseAndAddProduct);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseAndAddProduct(String line) {
        String[] fields = line.split(",");

        String name = fields[0];
        int price = Integer.parseInt(fields[1]);
        int quantity = Integer.parseInt(fields[2]);
        String promotionName = fields[3];

        Promotion promotion = findPromotionByName(promotionName);
        addProductToInventory(name, price, quantity, promotion);
    }

    private Promotion findPromotionByName(String name) {
        return PromotionManager.getInstance().findPromotionByName(name);
    }

    private void addProductToInventory(String name, int price, int quantity, Promotion promotion) {
        inventory.computeIfAbsent(name, key -> new ArrayList<>())
                .add(new Product(name, price, quantity, promotion));
    }

    private void addDefaultProducts() {
        inventory.forEach((name, products) -> {
            if (hasOnlyPromotionProducts(products)) {
                addDefaultProduct(products);
            }
        });
    }

    private boolean hasOnlyPromotionProducts(List<Product> products) {
        return products.stream().noneMatch(product -> product.getPromotion() == null);
    }

    private void addDefaultProduct(List<Product> products) {
        Product promoProduct = products.getFirst();
        products.add(new Product(promoProduct.getName(), promoProduct.getPrice(), 0, null));
    }

}
