package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory for parts and products
 */
public class Inventory {

    private static int partID = 0;
    private static int productID = 0;

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the list of parts
     * @param newPart part to be added
     */
    public static void AddPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the list of parts
     * @param newProduct part to be added
     */
    public static void AddProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup Part based on partID
     *
     * @param partID
     * @return
     */
    public static Part lookupPart(int partID) {
        Part foundPart = null;

        for (Part part : allParts) {
            if (part.getId() == partID) {
                foundPart = part;
            }
        }
        return foundPart;
    }

    /**
     * Lookup product based on productID
     *
     * @param productID
     * @return Product
     */
    public static Product lookupProduct(int productID) {
        Product foundProduct = null;

        for (Product product : allProducts) {
            if (product.getId() == productID) {
                foundProduct = product;
            }
        }
        return foundProduct;
    }

    /**
     * lookup part using string
     *
     * @param partName
     * @return A Part list
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().equals(partName)) {
                foundParts.add(part);
            }
        }
        return foundParts;
    }

    /**
     * lookup products based on string
     *
     * @param productName
     * @return list of products
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    /**
     * Replaces part at an index
     *
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Replaces product at an index
     *
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Delete a part
     *
     * @param selectedPart
     * @return bool
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else
            return false;
    }

    /**
     * Delete a Product
     *
     * @param selectedProduct
     * @return bool
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else
            return false;
    }


    /**
     * @return list of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return list of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * counter for partID
     * @return unique partID
     */
    public static int getNewPartID() { return ++partID; }

    /**
     * counter for productID
     * @return unique productID
     */
    public static int getNewProductID() {return ++productID;}
}
