package ru.vsu.cs.galimov.tasks;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainForm extends JFrame  implements ActionListener {
    private JPanel panelMain;
    private JButton buttonInverceMatrix;
    private JButton buttonKramer;
    private JButton buttonGauss;
    private JButton buttonMatrixEigenvectors;

    public MainForm() {

        panelMain = new JPanel();

        this.setTitle("MainDemo");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();

        buttonInverceMatrix = new JButton();
        buttonInverceMatrix.setText("Решение системы с помощью обратной матрицы");
        buttonInverceMatrix.setSize(100, 50);
        buttonInverceMatrix.setLocation(0, 100);
        buttonInverceMatrix.addActionListener(this);
        add(buttonInverceMatrix);

        buttonKramer = new JButton();
        buttonKramer.setText("Решение системы с помощью метода Крамера");
        buttonKramer.setSize(100, 50);
        buttonKramer.setLocation(50, 100);
        buttonKramer.addActionListener(this);
        add(buttonKramer);

        buttonGauss = new JButton();
        buttonGauss.setText("Решение системы с помощью метода Гаусса");
        buttonGauss.setSize(100, 50);
        buttonGauss.setLocation(100, 100);
        buttonGauss.addActionListener(this);
        add(buttonGauss);

        buttonMatrixEigenvectors = new JButton();
        buttonMatrixEigenvectors.setText("Нахождение собственных векторов матрицы");
        buttonMatrixEigenvectors.setSize(100, 50);
        buttonMatrixEigenvectors.setLocation(150, 100);
        buttonMatrixEigenvectors.addActionListener(this);
        add(buttonMatrixEigenvectors);

        this.setSize(600, 600);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonInverceMatrix) {
            new TableForm(1).setVisible(true);
        }
        else if (e.getSource() == buttonKramer) {
            new TableForm(2);
        }
        else if (e.getSource() == buttonGauss) {
            new TableForm(3);
        }
        else if(e.getSource() == buttonMatrixEigenvectors){
            new TableForm(4);
        }
    }
}
