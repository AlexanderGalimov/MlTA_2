package ru.vsu.cs.galimov.tasks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

class TableForm extends JFrame implements ActionListener {
    private JButton solveButton1;
    private JButton solveButton2;
    private JButton solveButton3;
    private JButton solveButton4;
    private JTextField size;
    private JTextField sizeOfRows;
    private JTextField sizeOfCols;
    private JButton changeSize12;
    private JButton changeSize3;
    private JButton changeSize4;
    private JLabel sizeLabel;
    private JLabel MatrixLabel;
    private JLabel FreeMembersLabel;
    private JLabel xLabel;
    private JTable tableMatrix;
    private JTable tableFreeMembers;
    private JTable tableX;
    private JScrollPane scrollPaneMatrix;
    private JScrollPane scrollPaneFreeMembers;
    private JScrollPane scrollPaneX;

    public TableForm(int index) {
        this.setTitle("Таблица");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container formElementsContainer = getContentPane();
        formElementsContainer.setLayout(null);

        tableMatrix = new JTable(3,3);
        scrollPaneMatrix = new JScrollPane(tableMatrix);
        scrollPaneMatrix.setSize(500, 300);
        scrollPaneMatrix.setLocation(0, 60);
        tableMatrix.getTableHeader().setVisible(false);
        formElementsContainer.add(scrollPaneMatrix);

        if(index != 4){
            tableFreeMembers = new JTable(3, 1);
            scrollPaneFreeMembers = new JScrollPane(tableFreeMembers);
            scrollPaneFreeMembers.setSize(200, 300);
            scrollPaneFreeMembers.setLocation(520, 60);
            tableFreeMembers.getTableHeader().setVisible(false);
            formElementsContainer.add(scrollPaneFreeMembers);
        }

        if(index == 0 || index == 2){
            size = new JTextField(5);
            size.setSize(50, 50);
            size.setLocation(70, 0);
            formElementsContainer.add(size);

            sizeLabel = new JLabel("Enter size: ");
            sizeLabel.setLabelFor(size);
            sizeLabel.setSize(80, 10);
            sizeLabel.setLocation(0, 25);
            add(sizeLabel);

            changeSize12 = new JButton("Change size");
            changeSize12.setSize(150, 50);
            changeSize12.setLocation(240, 350);
            changeSize12.addActionListener(this);
            formElementsContainer.add(changeSize12);
        }

        if(index == 4){
            size = new JTextField(5);
            size.setSize(50, 50);
            size.setLocation(70, 0);
            formElementsContainer.add(size);

            sizeLabel = new JLabel("Enter size: ");
            sizeLabel.setLabelFor(size);
            sizeLabel.setSize(80, 10);
            sizeLabel.setLocation(0, 25);
            add(sizeLabel);

            changeSize4 = new JButton("Change size");
            changeSize4.setSize(150, 50);
            changeSize4.setLocation(240, 350);
            changeSize4.addActionListener(this);
            formElementsContainer.add(changeSize4);
        }

        if(index == 3){
            sizeOfCols = new JTextField(5);
            sizeOfCols.setSize(50, 50);
            sizeOfCols.setLocation(45, 0);
            formElementsContainer.add(sizeOfCols);

            sizeLabel = new JLabel("cols: ");
            sizeLabel.setLabelFor(sizeOfCols);
            sizeLabel.setSize(70, 10);
            sizeLabel.setLocation(0, 25);
            add(sizeLabel);

            sizeOfRows = new JTextField(5);
            sizeOfRows.setSize(50, 50);
            sizeOfRows.setLocation(150, 0);
            formElementsContainer.add(sizeOfRows);

            sizeLabel = new JLabel("rows: ");
            sizeLabel.setLabelFor(sizeOfRows);
            sizeLabel.setSize(80, 10);
            sizeLabel.setLocation(100, 25);
            add(sizeLabel);

            changeSize3 = new JButton("Change size");
            changeSize3.setSize(150, 50);
            changeSize3.setLocation(240, 350);
            changeSize3.addActionListener(this);
            formElementsContainer.add(changeSize3);
        }

        MatrixLabel = new JLabel("Matrix");
        MatrixLabel.setSize(80, 10);
        MatrixLabel.setLocation(250, 25);
        add(MatrixLabel);

        if(index != 4){
            FreeMembersLabel = new JLabel("Free members");
            FreeMembersLabel.setSize(100, 10);
            FreeMembersLabel.setLocation(570, 25);
            add(FreeMembersLabel);
        }

        if(index == 1){
            solveButton1 = new JButton("Solve");
            solveButton1.setSize(100, 50);
            solveButton1.setLocation(0, 350);
            solveButton1.addActionListener(this);
            formElementsContainer.add(solveButton1);
        }

        if(index == 2){
            solveButton2 = new JButton("Solve");
            solveButton2.setSize(100, 50);
            solveButton2.setLocation(0, 350);
            solveButton2.addActionListener(this);
            formElementsContainer.add(solveButton2);
        }

        if(index == 3){
            solveButton3 = new JButton("Solve");
            solveButton3.setSize(100, 50);
            solveButton3.setLocation(0, 350);
            solveButton3.addActionListener(this);
            formElementsContainer.add(solveButton3);
        }

        if(index == 4){
            solveButton4 = new JButton("Solve");
            solveButton4.setSize(100, 50);
            solveButton4.setLocation(0, 350);
            solveButton4.addActionListener(this);
            formElementsContainer.add(solveButton4);
        }

        this.repaint();
        this.setSize(900, 500);
        this.setVisible(true);
    }

    // обработка событий
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == solveButton1) { // при нажатии на кнопку посчитать запускается функция handleSolveButtonClick()
            handleSolveButtonClick(1);
        }
        else if (e.getSource() == solveButton2) {
            handleSolveButtonClick(2);
        }
        else if (e.getSource() == solveButton3) {
            handleSolveButtonClick(3);
        }
        else if(e.getSource() == solveButton4){
            handleSolveButtonClick(4);
        }
        else if (e.getSource() == changeSize12) {
            setTableSize(0);
        }
        else if (e.getSource() == changeSize3) {
            setTableSize(1);
        }
        else if (e.getSource() == changeSize4) {
            setTableSize(2);
        }
    }

    private void setTableSize(int index) {
            if(tableX != null){
                remove(tableX);
                remove(scrollPaneX);
                remove(xLabel);
                this.repaint();
            }
        try {
            int height = 0;
            int length = 0;

            if(index == 0){
                length = Integer.parseInt(size.getText());
                height = Integer.parseInt(size.getText());
                if (length < 1) throw new NumberFormatException(); // если данные некорректные кидаем исключение
            }

            if(index == 1){
                height = Integer.parseInt(sizeOfRows.getText());
                length = Integer.parseInt(sizeOfCols.getText());
            }

            if(index == 2){
                length = Integer.parseInt(size.getText());
                height = Integer.parseInt(size.getText());
                if (length > 3) throw new NumberFormatException("не больше 3х :)"); // если данные некорректные кидаем исключение
            }

            remove(scrollPaneMatrix); // удаляем старую таблицу
            tableMatrix = new JTable(height, length); // создаем новую
            scrollPaneMatrix = new JScrollPane(tableMatrix);
            scrollPaneMatrix.setSize(500, 300);
            scrollPaneMatrix.setLocation(0, 60);
            tableMatrix.getTableHeader().setVisible(false);
            add(scrollPaneMatrix);// добавляем новую таблицу в окошко нашего приложения
            if(index != 2){
                remove(scrollPaneFreeMembers); // удаляем старую таблицу
                tableFreeMembers = new JTable(height, 1); // создаем новую
                scrollPaneFreeMembers = new JScrollPane(tableFreeMembers);
                scrollPaneFreeMembers.setSize(200, 300);
                scrollPaneFreeMembers.setLocation(520, 60);
                tableFreeMembers.getTableHeader().setVisible(false);
                add(scrollPaneFreeMembers);// добавляем новую таблицу в окошко нашего приложения
            }

            this.repaint();

        } catch (NumberFormatException nfe) {
            displayError("enter data correctly");
        }
    }

    private void convertArrayToTable(double[][] result,int index) {
        if(index == 4){
            xLabel = new JLabel("V");
            xLabel.setSize(80, 10);
            xLabel.setLocation(840, 25);
            add(xLabel);
        }
        else{
            xLabel = new JLabel("X");
            xLabel.setSize(80, 10);
            xLabel.setLocation(840, 25);
            add(xLabel);
        }

        this.repaint();

        tableX = new JTable(result.length, result[0].length);
        scrollPaneX = new JScrollPane(tableX);
        scrollPaneX.setSize(200, 300);
        scrollPaneX.setLocation(740, 60);
        tableX.getTableHeader().setVisible(false);
        add(scrollPaneX);

        this.repaint();
        DecimalFormat f = new DecimalFormat("#.##");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                tableX.getModel().setValueAt(f.format(result[i][j]), i, j); // заполняем таблицу данными
            }
        }
    }

    private void handleSolveButtonClick(int index) {
        try {
            double[][] matrix = convertTableToArray(tableMatrix);

            if(index == 1){
                double[][] matrixFreeMembers = convertTableToArray(tableFreeMembers);
                double[][] taskAnswer = MultiplicationMatrix.multiplicationMatrix(Solution.inverseMatrixCount(matrix),matrixFreeMembers); // выполняем решение задачи
                convertArrayToTable(taskAnswer,1); // полученный результат превращаем в таблицу
            }
            if(index == 2){
                double[][] matrixFreeMembers = convertTableToArray(tableFreeMembers);
                double[][] taskAnswer = Solution.solveKramer(matrix,matrixFreeMembers);
                convertArrayToTable(taskAnswer,2);
            }
            if(index == 3){
                double[][] matrixFreeMembers = convertTableToArray(tableFreeMembers);
                double[][] taskAnswer = Solution.solveGauss(matrix,matrixFreeMembers);
                convertArrayToTable(taskAnswer,3);
            }
            if(index == 4){
                double[][] taskAnswer = Solution.findMatrixEigenvectors(matrix);
                assert taskAnswer != null;
                convertArrayToTable(taskAnswer,4);
            }

            displayMessage("Выполнено успешно!");
        } catch (NumberFormatException exception) {
            displayError("Заполните таблицу корректно");
        }
    }

    private double[][] convertTableToArray(JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // берем модель таблицы

        double[][] array = new double[tableModel.getRowCount()][table.getColumnCount()]; // создаем массив

        for (int r = 0; r < tableModel.getRowCount(); r++) {
            for (int c = 0; c < tableModel.getColumnCount(); c++) {
                array[r][c] = Double.parseDouble(String.valueOf(tableModel.getValueAt(r, c))); // заполняем массив данными из модели таблицы
            }
        }

        return array;
    }

    private void displayMessage(String messageText) {
        JOptionPane.showMessageDialog(this, messageText,
                "Сообщение", JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayError(String errorText) {
        JOptionPane.showMessageDialog(this, errorText,
                "Ошибка", JOptionPane.ERROR_MESSAGE);
    }
}
