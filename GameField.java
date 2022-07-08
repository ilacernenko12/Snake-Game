import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener { //то что в нем отображается
    private final int SIZE = 320; // размер поля
    private final int DOT_SIZE = 16; // размер клетки
    private final int ALL_DOTS = 400; // количество клеток

    // картинки
    private Image apple;
    private Image dot;

    // координаты
    private int [] appleX = new int[3];
    private int [] appleY = new int[3];

    private int [] x = new int[ALL_DOTS];
    private int [] y = new int[ALL_DOTS];

    private int dots;
    private Timer timer; // с какой частотой мы будем обновлять экран
    private boolean inGame = true;
    private boolean right = true;
    private boolean left= false;
    private boolean up = false;
    private boolean down = false;
    private int eatenAppleX = 0;
    private int eatenAppleY = 0;
    public void createApple() { // создаем координату яблока
        for (int i = 0; i < 3; i++) {
            if (eatenAppleX == appleX[i] && eatenAppleY == appleY[i]) {
                appleX[i] = new Random().nextInt(20) * DOT_SIZE;
                appleY[i] = new Random().nextInt(20) * DOT_SIZE;
            }
        }
    }

    public void loadImage(){ // сеттер для наших картинок
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    public void checkApple(){
        for (int i = 0; i < 3; i++) {
            if (appleX[i]==x[0] && appleY[i] == y[0]) {
                dots++;
                eatenAppleX = appleX[i];
                eatenAppleY = appleY[i];
                createApple();
            }
        }
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 3 * DOT_SIZE;
            x[i] = DOT_SIZE*4 - i*DOT_SIZE;
        }
        timer = new Timer(100, this);
        timer.start();
        createApple();
    }

    public void checkCollision(){
        for (int i = dots; i >3 ; i--) {
            if (x[0]==x[i] && y[0] == y[i])
                inGame = false;
        }
        if (x[0]>SIZE)
            x[0] = 0;
        if (x[0]<0)
            x[0] = SIZE;
        if (y[0]>SIZE)
            inGame = false;
        if (y[0]<0)
            inGame = false;
    }

    public void move () {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }
    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            for (int i = 0; i < 3; i++) {
                g.drawImage(apple, appleX[i], appleY[i], this);
            }
            g.setColor(Color.magenta);
            g.drawString(String.valueOf(dots), 250, 30);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i], y[i], this);
            }
        } else {
            String str = "GG WP";
            g.setColor(Color.PINK);
            g.drawString(str, SIZE/6,SIZE/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }

    public GameField() {
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e); // кладет в объект "е" ту кнопку которую мы нажали
            int key = e.getKeyCode();
            if (key==KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key==KeyEvent.VK_UP && !down) {
                up = true;
                left = false;
                right = false;
            }
            if (key==KeyEvent.VK_DOWN && !up) {
                down = true;
                left = false;
                right = false;
            }
            if (key==KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
        }
    }
}
