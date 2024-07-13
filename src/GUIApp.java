import GUI.MainWindow;
import core.ShoppingCart;

public class GUIApp {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        MainWindow window = new MainWindow(cart);
        window.setVisible(true);
    }
}
