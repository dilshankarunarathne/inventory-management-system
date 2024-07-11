package core;

import java.util.Scanner;

public interface ShoppingManager {
    void displayMenu();
    void deleteProduct(Scanner scanner);
    void printProducts();
    void saveProducts();
    void loadProducts();
}
