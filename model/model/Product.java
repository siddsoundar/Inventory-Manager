package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class for products. A product can have multiple parts
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id, stock, min, max;
    private String name;
    private double price;


    /**
     * constructor for Product class
     *
     * @param id
     * @param stock
     * @param min
     * @param max
     * @param name
     * @param price
     */
    public Product(int id, int stock, int min, int max, String name, double price) {
        this.id = id;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.name = name;
        this.price = price;
    }

    /**
     * ID getter
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * ID setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * stock getter
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * stock setter
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * min getter
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * min setter
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * max getter
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Max setter
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * name getter
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * price getter
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * price setter
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Adds a part to the associated parts list
     * @param part part to be added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes part from associated parts
     * @param selectAssociatedPart part to be removed
     * @return bool
     */
    public boolean deleteAssociatedPart(Part selectAssociatedPart) {
        if (associatedParts.contains(selectAssociatedPart)) {
            associatedParts.remove(selectAssociatedPart);
            return true;
        } else
            return false;
    }

    /**
     * used to get a list of associated parts for a product
     * @return list of associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}

