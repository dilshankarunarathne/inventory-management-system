import GUI.LoginWindow;
import GUI.MainWindow;
import core.ShoppingCart;
import core.WestminsterShoppingManager;

public class GUIApp {
    public static void main(String[] args) {
//        ShoppingCart cart = new ShoppingCart();
//        WestminsterShoppingManager manager = new WestminsterShoppingManager();
//        MainWindow window = new MainWindow(cart, manager);
//        window.setVisible(true);

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
    }
}
