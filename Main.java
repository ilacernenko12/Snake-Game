import javax.swing.*;

public class Main extends JFrame {
    public Main() {
        setTitle("Snake"); // имя программы
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //чтобы прога завершилась когда мы нажмем на крестик
        setSize(320, 345); // +25 - для title
        setLocation(400, 400); // где будет появляться окно
        add(new GameField());
        setVisible(true); // делает наш экран видимым
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}
