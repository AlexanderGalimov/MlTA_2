package ru.vsu.cs.galimov.tasks;

public class MultiplicationMatrix {

    //обычная версия
    /*public static void multiplicationMatrix(int[][] A, int[][] B) {
        if(A.length != B[0].length && B.length != A[0].length){
            System.out.println("количество столбцов одной матрицы должно соответствовать количеству строк другой!");
            return;
        }

        int height = Math.max(A.length, B.length);
        int length = Math.max(A[0].length, B[0].length);
        int min = Math.min(A.length, A[0].length);

        int[][] result = new int[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                int sum = 0;
                for (int k = 0; k < min; k++) {
                    sum = sum + A[i][k] * B[k][j];
                }
                result[i][j] = sum;
            }
        }
        writeMultiplicationAB(result);
    }*/

    // для второй таски
    public static double[][] multiplicationMatrix(double[][] A, double[][] B) {
        if(A.length != B[0].length && B.length != A[0].length){
            System.out.println("количество столбцов одной матрицы должно соответствовать количеству строк другой!");
            throw new IllegalArgumentException();
        }

        int height = Math.max(A.length, B.length);
        int length = Math.max(A[0].length, B[0].length);
        int min = Math.min(A.length, A[0].length);

        double[][] result = new double[height][1];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                double sum = 0;
                for (int k = 0; k < min; k++) {
                    sum = sum + A[i][k] * B[k][0];
                }
                result[i][0] = sum;
            }
        }
        return result;
    }
}

