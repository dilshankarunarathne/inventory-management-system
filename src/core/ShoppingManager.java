package core;

import java.util.Scanner;

public interface ShoppingManager {
    void displayMenu();
    void addProduct(Scanner scanner);
    void deleteProduct(Scanner scanner);
    void printProducts();
    void saveProducts();
}
