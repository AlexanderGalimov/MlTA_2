package ru.vsu.cs.galimov.tasks;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

import static ru.vsu.cs.galimov.tasks.TaskManager.*;

public class Main {

    // todo задания---------------------
    // для первой таски
    /*public static void runInCommandLineModeDet(String[] args) {// для детерминанта
        InputArgs inputArgs = ArgsParser.parseCmdArgs(args);
        int[][] arr = parseInputFile(inputArgs.getInput());
        int result = Solution.determinantСount(arr);
        writeToOutputFileDet(inputArgs.getOutput(), result);
    }*/

    // для второй таски
    /*public static void runInCommandLineModeInverseMatrix(String[] args) { //для подсчета обратной матрицы
        InputArgs inputArgs = ArgsParser.parseCmdArgs(args);
        double[][] arr = parseInputFile(inputArgs.getInput());
        double[][] result = Solution.inverseMatrixCount(arr);
        writeToOutputFileInverseMatrix(inputArgs.getOutput(), result);
    }*/

    // для третьей таски
    // решить систему методом обратной матрицы квадратную, любого размера,
    // + надо еще интерфейс ,кол-во переменных,

    public static void main(String[] args) {

        // todo-----------------------------------------------------
        // 2 таск
        // для записи в файл второй таск

        /*runInCommandLineModeInverseMatrix(args);*/

        // тест на единичную матрицу после умножения
        /*int[][] matrix = {{6,0,0},
                        {0,1,2},
                        {0,3,5}};
        Solution.writeInt(matrix);
        System.out.println();
        double[][] result = Solution.inverseMatrixCount(matrix);
        Solution.writeDouble(result);
        System.out.println();

        MultiplicationMatrix.multiplicationMatrix(matrix,result);*/

        //todo-------------------------------------------------
        // 3 таск
            /*new MainForm();*/

        //todo-------------------------------------------------
        // 4 таск

        /*new MainForm();
        double[][] src = {{1,-1},{2,1}};
        double[][] fmm = {{-5},{-7}};
*/
        //todo-----------------------------------------------
        // todo 5 таск собственные вектора

        /*new MainForm();*/

        /*double[] testOdds = Solution.findOddsForTwo(test);*/
        /*double[] testOdds = {1,4,4};
        System.out.println(Arrays.toString(testOdds));
        System.out.println("=----------------------");

        double[] roots = Solution.findRootsOfAQuadraticEquation(testOdds);
        System.out.println(Arrays.toString(roots));
        System.out.println("=----------------------");

        Solution.subtractRootFromMatrix(test,roots[0]);*/

        /*-1 -6
          2   6*/

        /*double[][] test = {{-9,6,-2},{-9,7,0},{5,-3,2}};
        double[][] test1 = {{6,5,-3},{-8,-7,3},{-1,-1,-2}};
        double[] answ = Solution.findOddsForThree(test);
        double[] answ1 = Solution.findOddsForThree(test1);*/

        /*double a = -3;
        double b = 3;
        double c = -1;
        double[] aava = Solution.SolveCubicEquation(a,b,c);

        System.out.println(Arrays.toString(aava));*/

        /*double[][] test1 = {{-9,6,-2},{-9,7,0},{5,-3,2}};// тест
        double[][] test2 = {{6,5,-3},{-8,-7,3},{-1,-1,-2}};// ?
        double[][] test3 = {{-3,-12,18},{6,15,-18},{3,6,-6}};// g
        double[][] test4 = {{0,-3,4},{-1,-2,4},{-1,-3,5}};// g
        TaskManager.writeToConsole(Objects.requireNonNull(Solution.findMatrixEigenvectors(test4)));*/

        //todo-----------------------------------------------
        // todo 6 таск кривые третьего порядка
        GraphicApp app = new GraphicApp();
        app.show();


    }
}
