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
        addMissingDefaultProducts();
    }

    private void addMissingDefaultProducts() {
        inventory.forEach((name, products) -> {
            if (needsDefaultProduct(products)) {
                addDefaultProduct(products);
            }
        });
    }

    private boolean needsDefaultProduct(List<Product> products) {
        return products.stream().noneMatch(product -> product.getPromotion() == null);
    }

    private void addDefaultProduct(List<Product> products) {
        Product promoProduct = products.getFirst();
        products.add(new Product(promoProduct.getName(), promoProduct.getPrice(), 0, null));
    }

    public boolean hasProductByName(String name) {
        return inventory.containsKey(name);
    }

    public Map<String, List<Product>> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }

    private void loadProductsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                String name = fields[0];
                int price = Integer.parseInt(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                String promotionName = fields[3];

                Promotion promotion = findPromotionByName(promotionName);

                inventory.computeIfAbsent(name, key -> new ArrayList<>())
                        .add(new Product(name, price, quantity, promotion));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Promotion findPromotionByName(String name) {
        PromotionManager promotions = PromotionManager.getInstance();
        if (name.equals("null")) {
            return null;
        }
        return promotions.getPromotion(name);
    }

    public boolean hasAvailableQuantity(String name, Integer quantityToBoy) {
        int sum = inventory.get(name).stream()
                .mapToInt(Product::getQuantity)
                .sum();
        return sum >= quantityToBoy;
    }

    public List<Product> getProductsByName(String name) {
        return inventory.get(name).stream().sorted().toList();
    }

}
