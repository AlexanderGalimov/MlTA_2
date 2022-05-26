package ru.vsu.cs.galimov.tasks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GraphicApp {
    private JFrame frame;
    private JLabel statusLabel;
    private JTextField nameTextField;
    private GraphicPanel graphicPanel;

    public GraphicApp(){
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Отрисовка графика");
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void show(){
        frame.setVisible(true);
    }

    private void initElements() {
        Container mainContainer = frame.getContentPane();
        mainContainer.setLayout(new BorderLayout());

        JPanel bottomPanel = new JPanel(); // нижняя панель состояния
        bottomPanel.setBackground(Color.black); // фон светло-серый
        mainContainer.add(bottomPanel, BorderLayout.SOUTH); // распологается внизу

        statusLabel = new JLabel("приложение"); // Элемент, который будет показывать текст состояния программы
        bottomPanel.add(statusLabel);    // добавляем его в нижнюю панель

        Box leftPanel = createLeftPanel(); // создаем левую панель в другом методе
        mainContainer.add(leftPanel, BorderLayout.WEST); // эта панель будет слева

        graphicPanel = new GraphicPanel();
        graphicPanel.setBackground(Color.WHITE);
        mainContainer.add(graphicPanel);
    }

    private Box createLeftPanel() {
        Box panel = Box.createVerticalBox();  // вертикальный Box
        // Box это контейнер, в котором элементы выстраиваются в одном порядке

        JLabel title = new JLabel("<html>Построение графика функции</html>");
        // чтобы добавить перевод строки в тексте, нужно писать в тегах <html>
        title.setFont(new Font(null, Font.BOLD, 12)); // изменяем шрифт
        panel.add(title);

        panel.add(Box.createVerticalStrut(20)); // отступы

        panel.add(new JLabel("Введите а:"));

        nameTextField = new JTextField();  // поле ввода названия
        nameTextField.setMaximumSize(new Dimension(300, 30)); // чтобы не был слишком большим
        panel.add(nameTextField);

        JButton button1 = new JButton("Нарисовать 1 кривую"); // Кнопка 1
        panel.add(button1);
        panel.add(new JLabel("y = sqrt(x^3 / 3 * a - x)"));
        JButton button2 = new JButton("Нарисовать 2 кривую"); // Кнопка 2
        panel.add(button2);
        panel.add(new JLabel("y^2 = x^2 * a - x / a + x"));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeGraphicColor(1);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeGraphicColor(2);
            }
        });

        return panel;
    }

    private void changeGraphicColor(int index){
        try{
            int a = Integer.parseInt(nameTextField.getText());
            Random rand = new Random();

            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();

            Color randomColor = new Color(r, g, b);

            graphicPanel.setAAndColor(a, randomColor,index);
        }catch(NumberFormatException e){
            System.out.print("ошибка");
        }
    }
}
