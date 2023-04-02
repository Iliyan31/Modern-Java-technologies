package bg.sofia.uni.fmi.mjt.smartfridge;

import bg.sofia.uni.fmi.mjt.smartfridge.exception.FridgeCapacityExceededException;
import bg.sofia.uni.fmi.mjt.smartfridge.exception.InsufficientQuantityException;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.DefaultIngredient;
import bg.sofia.uni.fmi.mjt.smartfridge.ingredient.Ingredient;
import bg.sofia.uni.fmi.mjt.smartfridge.recipe.Recipe;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.Storable;
import bg.sofia.uni.fmi.mjt.smartfridge.storable.StorableComparator;

import java.util.*;

public class SmartFridge implements SmartFridgeAPI {
    private final int totalFridgeCapacity;
    private int currentFridgeCapacity;
    private final Set<String> fridgeItems;
    private final Map<String, List<Storable>> fridge;

    public SmartFridge(int totalCapacity) {
        this.totalFridgeCapacity = totalCapacity;
        this.fridgeItems = new HashSet<>();
        this.fridge = new HashMap<>();
    }


    @Override
    public <E extends Storable> void store(E item, int quantity) throws FridgeCapacityExceededException {
        validateItemForNull(item);
        validateQuantityOfItems(quantity);
        validateCapacityOfFridge(quantity);

        fridgeItems.add(item.getName());
        addItemToFridge(item, quantity);
        currentFridgeCapacity += quantity;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName) {
        validateItemName(itemName);

        if (!fridgeItems.contains(itemName)) {
            return new LinkedList<>();
        }

        fridge.get(itemName).sort(new StorableComparator());
        int quantity = fridge.get(itemName).size();

        List<Storable> orderedByExpirationDateItems;
        orderedByExpirationDateItems = fridge.get(itemName);

        fridge.remove(itemName);
        fridgeItems.remove(itemName);
        currentFridgeCapacity -= quantity;

        return orderedByExpirationDateItems;
    }

    @Override
    public List<? extends Storable> retrieve(String itemName, int quantity) throws InsufficientQuantityException {
        validateItemName(itemName);
        validateQuantityOfItems(quantity);
        containsItemInFridge(itemName);
        containsNeededQuantity(itemName, quantity);

        fridge.get(itemName).sort(new StorableComparator());

        List<Storable> orderedByExpirationDateItems = new LinkedList<>();

        for (int i = 0; i < quantity; i++) {
            Storable storable = fridge.get(itemName).get(0);
            orderedByExpirationDateItems.add(storable);
            fridge.get(itemName).remove(0);
            --currentFridgeCapacity;
        }

        if (fridge.get(itemName).isEmpty()) {
            fridgeItems.remove(itemName);
        }

        return orderedByExpirationDateItems;
    }

    @Override
    public int getQuantityOfItem(String itemName) {
        validateItemName(itemName);

        if (fridgeItems.contains(itemName)) {
            return fridge.get(itemName).size();
        }

        return 0;
    }

    @Override
    public Iterator<Ingredient<? extends Storable>> getMissingIngredientsFromRecipe(Recipe recipe) {
        validateRecipeForNull(recipe);

        List<Ingredient<? extends Storable>> missingIngredientsList = new LinkedList<>();

        for (Ingredient<? extends Storable> recipeIngredient : recipe.getIngredients()) {
            Storable recipeIngredientItem = recipeIngredient.item();
            String recipeIngredientItemName = recipeIngredientItem.getName();
            int recipeIngredientItemQuantity = recipeIngredient.quantity();

            if (!fridgeItems.contains(recipeIngredientItemName)) {
                missingIngredientsList.add(recipeIngredient);
                continue;
            }

            int fridgeIngredientItemQuantity = fridge.get(recipeIngredientItemName).size();

            if (fridgeIngredientItemQuantity <= recipeIngredientItemQuantity) {
                int neededQuantity = recipeIngredientItemQuantity - fridgeIngredientItemQuantity;

                for (Storable item : fridge.get(recipeIngredientItemName)) {
                    if (item.isExpired()) {
                        neededQuantity++;
                    }
                }

                Ingredient<? extends Storable> missingIngredient =
                    new DefaultIngredient<>(recipeIngredientItem, neededQuantity);
                missingIngredientsList.add(missingIngredient);

            } else {
                int neededQuantity;

                int expired = 0;
                for (Storable item : fridge.get(recipeIngredientItemName)) {
                    if (item.isExpired()) {
                        expired++;
                    }
                }

                if (fridgeIngredientItemQuantity - expired < recipeIngredientItemQuantity) {
                    neededQuantity = recipeIngredientItemQuantity - (fridgeIngredientItemQuantity - expired);

                    Ingredient<? extends Storable> missingIngredient =
                        new DefaultIngredient<>(recipeIngredientItem, neededQuantity);
                    missingIngredientsList.add(missingIngredient);
                }
            }
        }

        return missingIngredientsList.iterator();
    }

    @Override
    public List<? extends Storable> removeExpired() {
        List<Storable> expiredItems = new LinkedList<>();

        for (List<Storable> list : fridge.values()) {
            List<Storable> itemsToRemove = new ArrayList<>();
            String itemName = null;

            for (Storable product : list) {
                if (product.isExpired()) {
                    itemName = product.getName();
                    expiredItems.add(product);
                    itemsToRemove.add(product);

                    --currentFridgeCapacity;
                }
            }

            clearFridgeFromExpired(itemName, itemsToRemove);
        }

        return expiredItems;
    }

    private <E extends Storable> void validateItemForNull(E item) {
        if (item == null) {
            throw new IllegalArgumentException("The item cannot be null!");
        }
    }

    private void validateQuantityOfItems(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("The quantity of the Item cannot be below or equal to zero!");
        }
    }

    private void validateCapacityOfFridge(int quantity) throws FridgeCapacityExceededException {
        if (currentFridgeCapacity + quantity > totalFridgeCapacity) {
            throw new FridgeCapacityExceededException(
                "there is no free space in the fridge to accommodate the item(s)");
        }
    }

    private void validateItemName(String itemName) {
        if (itemName == null || itemName.isEmpty() || itemName.isBlank()) {
            throw new IllegalArgumentException("The item name cannot be null, empty or blank!");
        }
    }

    private void containsItemInFridge(String itemName) throws InsufficientQuantityException {
        if (!fridgeItems.contains(itemName)) {
            throw new InsufficientQuantityException("There is no such item in the fridge!");
        }
    }

    private void containsNeededQuantity(String itemName, int quantity) throws InsufficientQuantityException {
        if (fridge.get(itemName).size() < quantity) {
            throw new InsufficientQuantityException("There aren't enough items needed!");
        }
    }

    private void validateRecipeForNull(Recipe recipe) {
        if (recipe == null) {
            throw new IllegalArgumentException("The recipe cannot be null!");
        }
    }

    private <E extends Storable> void addItemToFridge(E item, int quantity) {
        if (!fridge.containsKey(item.getName())) {
            fridge.put(item.getName(), new LinkedList<>());
        }

        for (int i = 0; i < quantity; i++) {
            fridge.get(item.getName()).add(item);
        }
    }

    private void clearFridgeFromExpired(String itemName, Collection<Storable> itemsToRemove) {
        if (itemName != null) {
            fridge.get(itemName).removeAll(itemsToRemove);
        }

        if (fridge.get(itemName).isEmpty()) {
            fridge.remove(itemName);
        }
    }

}
