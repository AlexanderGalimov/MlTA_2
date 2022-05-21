package ru.vsu.cs.galimov.tasks;

import java.io.*;
import java.util.*;

public class TaskManager{

    private TaskManager(){}

    public static double[][] parseInputFile(String inputFilePath) {
        try (Scanner sc = new Scanner(new File(inputFilePath))){
            List<String> lines = new ArrayList<>();
            while (sc.hasNext()) lines.add(sc.nextLine());
            int n = lines.size();
            double[][] result = new double[n][];
            for (int j = 0; j < lines.size(); j++) { // превращаем все строки в массивы
                String x = lines.get(j);
                String[] line = x.split("\\s+");
                int m = line.length;
                double[] row = new double[m];
                for (int i = 0; i < m; i++) {
                    int number = Integer.parseInt(line[i]);
                    row[i] = number;
                }
                result[j] = row; // добавляем собранный массив в результирующий набор
            }
            return result;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // для первой таски
    /*public static void writeToOutputFileDet(String outputFilePath, int result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(result + " ");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }*/

    //для второй таски
    public static void writeToOutputFileInverseMatrix(String outputFilePath, double[][] result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (double[] ints : result) {
                for (double x : ints) {
                    writer.write(Math.round(x * 1000) / 1000.0 + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void writeToConsole(double[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(Math.round(arr[i][j] * 1000) / 1000.0 + " ");
            }
            System.out.println();
        }
    }

}
