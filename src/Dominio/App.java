package Dominio;
import Vista.*;
import javax.swing.*;
public class App {
   public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        formularioiniciarsesion login = new formularioiniciarsesion();
        login.setVisible(true);
        
        
    });
}
}