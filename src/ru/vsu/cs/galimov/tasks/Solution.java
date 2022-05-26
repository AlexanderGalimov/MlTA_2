package ru.vsu.cs.galimov.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    // метод уменьшения матрицы для подсчета определителя
    private static double[][] cutMatrix(double[][] arr, int i){
        double[][] currMatrix = new double[arr.length - 1][arr[0].length - 1];

        int a = 0;
        for (int j = 0; j < arr.length; j++) {
            if(a == arr.length - 1){
                break;
            }
            for (int k = 1; k < arr[0].length; k++) {
                if(j != i){
                    currMatrix[a][k-1] = arr[j][k];
                }
            }
            if(j != i){
                a++;
            }
        }
        return currMatrix;
    }

    // подсчет попределителя
    public static double determinantСount(double[][] matrix){
        double sum = 0;
        if(matrix.length == 1){
            return matrix[0][0];
        }
        for (int i = 0; i < matrix.length; i++) {
            if(matrix.length > 2){
                sum = sum + matrix[i][0] * (int) (Math.pow(-1,i+2)) * determinantСount(cutMatrix(matrix,i));
            }
            else{
                sum = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
            }
        }
        return sum;
    }

    // основной метод вызова функции
    public static double[][] inverseMatrixCount(double[][] matrix){
        double detMatrix = determinantСount(matrix);

        if(detMatrix == 0){
            System.out.println("inverse matrix doesnt exist");
            throw new IllegalArgumentException();
        }
        double[][] complementMatrix = makeComplementMatrix(matrix);
        double[][] associatedMatrix = findAssociatedMatrix(complementMatrix);
        return divideMatrixByDet(associatedMatrix,detMatrix);
    }

    // для построения матрицы из алгебраичеких дополнений
    private static double[][] makeComplementMatrix(double[][] matrix){
        double[][] resultComplMatrix = new double[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                resultComplMatrix[i][j] = (int) Math.pow(-1,i+1+j+1) * determinantСount(cutMatrixForInvertMatrix(matrix,i,j));
            }
        }
        return resultComplMatrix;
    }

    // обрезка матрицы по столбцу и строке
    private static double[][] cutMatrixForInvertMatrix(double[][] matrix,int row,int col){
        double[][] currMatrix = new double[matrix.length - 1][matrix[0].length - 1];
        int a = 0;
        int b = 0;
        for (int i = 0; i < matrix.length; i++) {
            if(a == matrix.length - 1){
                break;
            }
            for (int j = 0; j < matrix[0].length; j++) {
                if(j != col){
                    currMatrix[a][b] = matrix[i][j];
                    b++;
                }
            }
            b = 0;
            if(i != row){
                a++;
            }
        }
        return currMatrix;
    }

    // нахождение транспонированной матрицы
    private static double[][] findAssociatedMatrix(double[][] matrix){
        double[][] resultAssociatedMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                resultAssociatedMatrix[i][j] = matrix[j][i];
            }
        }
        return resultAssociatedMatrix;
    }

    // деление матрицы на детерминант
    private static double[][] divideMatrixByDet(double[][] matrix,double det){
        double[][] resultDivideMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                resultDivideMatrix[i][j] = matrix[i][j] / det;
            }
        }
        return resultDivideMatrix;
    }

    // для подсчета методом крамера
    public static double[][] solveKramer(double[][] matrix,double[][] freeMembersMatrix){
        double[][] resultCramer = new double[matrix.length][1];
        double currDelta;

        double mainDelta = determinantСount(matrix);
        double[][] replacedMatrix;

        for (int i = 0; i < matrix.length; i++) {
            replacedMatrix = replaceMatrix(matrix,freeMembersMatrix,i);

            currDelta = determinantСount(replacedMatrix);

            resultCramer[i][0] = currDelta / mainDelta;
        }
        return resultCramer;
    }

    // нахождение матрицы для подсчета текущей дельты
    private static double[][] replaceMatrix(double[][] m,double[][] fmm,int ind){

        double[][] resultReplacedMatrix = new double[m.length][m.length];
        for (int i = 0; i < m.length; i++) {
            System.arraycopy(m[i], 0, resultReplacedMatrix[i], 0, m.length);
        }

        for (int i = 0; i < m.length; i++) {
            resultReplacedMatrix[i][ind] = fmm[i][0];
        }

        return resultReplacedMatrix;
    }

    // решить систему уравнений методом гауса
    public static double[][] solveGauss(double[][] sourceMatrix,double[][] fMemberMatrix){
        double[][] extendedMatrix = makeExtendedMatrix(sourceMatrix,fMemberMatrix);

        double[][] steppedMatrix = makeSteppedMatrix(extendedMatrix);

        TaskManager.writeToConsole(steppedMatrix);

        double[][] result = new double[1][sourceMatrix[0].length];

        System.out.println(Arrays.toString(findRang(sourceMatrix)));

        if(checkSolutions(steppedMatrix) == -1){
            System.out.println("нет решений");
            throw new IllegalArgumentException();
        }
        else if(checkSolutions(steppedMatrix) == 1){
            result = solveKramer(sourceMatrix,fMemberMatrix);
        }
        else if(checkSolutions(steppedMatrix) == 0){
            return gaussWithBasicVariables(steppedMatrix);
        }
        return result;
    }

   // составить расширенную матрицу
    private static double[][] makeExtendedMatrix(double[][] src,double[][] fmm){
        double[][] resultExtMatr = new double[src.length][src.length + 1];

        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, resultExtMatr[i], 0, src[0].length);
        }

        for (int i = 0; i < src.length; i++) {
            resultExtMatr[i][src.length] = fmm[i][0];
        }
        return resultExtMatr;
    }

    // понять имеет ли система решения , если имеет то сколько
    public static int checkSolutions(double[][] steppedMatrix){
        // -1 - нет решений
        // 1 - единственное решение
        // 0 - решение со свободными переменными
        int flag = 0;
        if(!isJoint(steppedMatrix)){
            flag = -1;
        }
        int[] rang = findRang(steppedMatrix);
        if(steppedMatrix[0].length - 1 == rang[0]){
            flag = 1;
        }
        if(steppedMatrix[0].length - 1 < rang[0]){
            flag = 0;
        }
        return flag;
    }

    private static double[][] gaussWithBasicVariables(double[][] resultMatrixGauss){
        int from = findBasicMinor(resultMatrixGauss);
        int to = from + resultMatrixGauss.length - 1;
        int pos = 0;
        double[][] resultOfBasic = new double[resultMatrixGauss[0].length - 1][1];

        double[][] resultOfMin = solveKramer(removeFmm(makeMatrixForBasicVariables(resultMatrixGauss)),getFmm(makeMatrixForBasicVariables(resultMatrixGauss)));

        for (int i = 0; i < resultOfBasic.length; i++) {
            if(i >= from && i <= to){
                resultOfBasic[i][0] = resultOfMin[pos][0];
                pos++;
            }
            else{
                resultOfBasic[i][0] = 1;
            }
        }
        return resultOfBasic;
    }

    // составить матрицу для решения со своюодными переменными. с вычитанием единиц
    private static double[][] makeMatrixForBasicVariables(double[][] matrix){
        double[][] transferredCuttedMatrix = new double[matrix.length][matrix.length + 1];
        int from = findBasicMinor(matrix);
        int to = from + matrix.length - 1;

        int row = 0;
        int col = 0;
        double sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            col = 0;
            sum = 0;
            for (int j = 0; j < matrix[0].length - 1; j++) {
                if(j >= from && j <= to){
                    transferredCuttedMatrix[row][col] = matrix[i][j];
                    col++;
                }
                if(!(j >= from && j <= to)){
                    sum = sum + matrix[i][j];
                }
            }
            transferredCuttedMatrix[row][col] = matrix[i][matrix[0].length - 1] - sum;
            row++;
        }
        return transferredCuttedMatrix;
    }

    //убрать матрицу свободных членов
    private static double[][] removeFmm(double[][] matrixToR){
        double[][] removed = new double[matrixToR.length][matrixToR[0].length - 1];

        for (int i = 0; i < matrixToR.length; i++) {
            for (int j = 0; j < matrixToR[0].length - 1; j++) {
                removed[i][j] = matrixToR[i][j];
            }
        }
        return removed;
    }

    //получить матрицу свободных членов
    private static double[][] getFmm(double[][] m){
        double[][] fmmatrix = new double[m.length][1];

        for (int i = 0; i < m.length; i++) {
            fmmatrix[i][0] = m[i][m[0].length - 1];
        }
        return fmmatrix;
    }

    // сделать ступенчатую матрицу
    private static double[][] makeSteppedMatrix(double[][] extMatrix){

        for (int i = 0; i < Math.min(extMatrix.length,extMatrix[0].length); i++) {
            for (int j = i + 1; j < extMatrix.length; j++) {
                if(extMatrix[j][i] != 0 && !isZeroLine(extMatrix,i)){
                    subtractTwoLines(extMatrix,j,i,i);
                }
            }
            extMatrix = deleteZeroLines(extMatrix);
        }
        return deleteZeroLines(extMatrix);
    }

    // метод который находит нулевые строки
    private static boolean isZeroLine(double[][] matrix,int row){
        int count = 1;
        boolean flag = false;
        for (int i = 0; i < matrix[0].length; i++) {
            if(matrix[row][i] == 0){
                count++;
            }
        }
        if(count == matrix.length){
            flag = true;
        }
        return flag;
    }

    // вычесть две строки
    private static void subtractTwoLines(double[][] extMatrix,int currRow,int firstRow,int col){
        double k = extMatrix[currRow][col] * 1 / extMatrix[firstRow][col];
        for (int i = 0; i < extMatrix[0].length; i++) {
            extMatrix[currRow][i] = extMatrix[currRow][i] - k * extMatrix[firstRow][i];
        }
    }

    // удалить ненужные нулевые строки
    private static double[][] deleteZeroLines(double[][] extMatrix){
        int count;
        int index = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < extMatrix.length; i++) {
            count = 0;
            for (int j = 0; j < extMatrix[0].length; j++) {
                if(extMatrix[i][j] != 0){
                    count++;
                }
            }
            if(count == 0){
                list.add(index,i);
                index++;
            }
        }

        int row = 0;
        index = 0;

        double[][] rm = new double[extMatrix.length - list.size()][extMatrix[0].length];
        for (int i = 0; i < extMatrix.length; i++) {
            if( index <= list.size() - 1 && i == list.get(index)){
                index++;
                continue;
            }
            for (int j = 0; j < extMatrix[0].length; j++) {
                    rm[row][j] = extMatrix[i][j];
            }
            row++;
        }
        return rm;
    }

    // ищем базовый минор
    private static int findBasicMinor(double[][] steppedMatrix){
        int colIndex = 0;
        int row = 0;
        int col = 0;
        boolean flag = true;
        while (flag && colIndex != steppedMatrix[0].length - steppedMatrix.length - 1){
            row = 0;
            col = 0;
            double[][] currBasicMinor = new double[steppedMatrix.length][steppedMatrix.length];
            for (int i = 0; i < steppedMatrix.length; i++) {
                for (int j = colIndex; j < colIndex + steppedMatrix.length; j++) {
                    currBasicMinor[row][col] = steppedMatrix[i][j];
                    col++;
                }
                col = 0;
                row++;
            }
            if(determinantСount(currBasicMinor) == 0){
                colIndex++;
            }
            else if(determinantСount(currBasicMinor) != 0){
                flag = false;
            }
        }
        return colIndex;
    }

    // смотрим имеет ли система решения
    private static boolean isJoint(double[][] source){
        int[] arr = findRang(source);
        int rgA = arr[0];
        int rg_A = arr[1];
        return rg_A == rgA;
    }

    // находим ранги обычной и расширенной матрицы
    public static int[] findRang(double[][] matrix){
        int[] ranges = new int[2];
        int count;
        double last;
        int countMatrix = 0;
        int countExtendedMatrix = 0;

        for (int i = 0; i < matrix.length; i++) {
            count = 0;
            last = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] != 0 && j < matrix[0].length - 1){
                    count++;
                }
                if(j == matrix[0].length - 1){
                    last = matrix[i][j];
                }
            }
            if(count != 0){
                countMatrix++;
            }
            if(last != 0 || count != 0){
                countExtendedMatrix++;
            }
        }
        ranges[0] = countMatrix;
        ranges[1] = countExtendedMatrix;

        return ranges;
    }

    // метод для подсчета собственных векторов для матрицы 2 на 2 и 3 на 3
    public static double[][] findMatrixEigenvectors(double[][] matrixToFindEigenvectors){
        if(matrixToFindEigenvectors.length == 2){
            double[] odds = Solution.findOddsForTwo(matrixToFindEigenvectors);

            double[] lambdas = Solution.findRootsOfAQuadraticEquation(odds);
            System.out.println("корни квадратного уравнения");
            System.out.println(Arrays.toString(lambdas));

            double[][] substractedMatrix1 = Solution.subtractRootFromMatrix(matrixToFindEigenvectors,lambdas[0]);
            double[][] vector1 = Solution.findVectorForTwo(substractedMatrix1);

            double[][] substractedMatrix2 = Solution.subtractRootFromMatrix(matrixToFindEigenvectors,lambdas[1]);
            double[][] vector2 = Solution.findVectorForTwo(substractedMatrix2);

            return Solution.makeAnswerVectorForTwo(vector1,vector2);
        }
        if(matrixToFindEigenvectors.length == 3){

            double[] odds = Solution.findOddsForThree(matrixToFindEigenvectors);

            double[] lambdas = Solution.SolveCubicEquation(-odds[1],-odds[2],-odds[3]);
            System.out.println("корни кубического уравнения");
            System.out.println(Arrays.toString(lambdas));

            return findVectorForThree(matrixToFindEigenvectors,lambdas);
        }
        return null;
    }

    // служебный метод для формирования удобного ответа
    private static double[][] makeAnswerVectorForTwo(double[][] v1,double[][] v2){
        double[][] resultVectors = new double[2][2];
        for (int i = 0; i < resultVectors.length; i++) {
            for (int j = 0; j < resultVectors.length; j++) {
                if(i == 0){
                    resultVectors[j][i] = v1[j][0];
                }
                if(i == 1){
                    resultVectors[j][i] = v2[j][0];
                }
            }
        }
        return resultVectors;
    }

    // метод для поиска собственного вектора матрицы 2 на 2
    private static double[][] findVectorForTwo(double[][] substractedMatr){
        double[][] taskAnswer = new double[2][1];
        double x = -(substractedMatr[0][1]) / substractedMatr[0][0];
        taskAnswer[0][0] = x;
        taskAnswer[1][0] = 1;

        return taskAnswer;
    }

    // метод для вычитания корня из матрицы
    private static double[][] subtractRootFromMatrix(double[][] entry,double root){
        double[][] res = new double[entry.length][entry.length];

        for (int i = 0; i < entry.length; i++) {
            System.arraycopy(entry[i], 0, res[i], 0, entry[0].length);
        }

        for (int i = 0; i < res.length; i++) {
            res[i][i] = res[i][i] - root;
        }
        return res;
    }

    // метод для нахождения коэффициентов в квадратном уравнении для матрицы 2 на 2
    private static double[] findOddsForTwo(double[][] srcMatrix){
        double[] odds = new double[3];
        double x2 = 1;
        double x = -1 * (srcMatrix[1][1] + srcMatrix[0][0]);
        double one = (srcMatrix[0][0] * srcMatrix[1][1]) - (srcMatrix[0][1] * srcMatrix[1][0]);

        odds[0] = x2;
        odds[1] = x;
        odds[2] = one;

        return odds;
    }

    // метод для поиска корней квадратного уравнения
    private static double[] findRootsOfAQuadraticEquation(double[] odds){// на вход коэффициенты квадратного уравниения
        double[] roots = new double[2];
        double D = odds[1] * odds[1] - 4 * odds[0] * odds[2];
        double x1,x2;
        if(D >= 0){
            x1 = (-odds[1] - Math.sqrt(D)) / (2 * odds[0]);
            x2 = (-odds[1] + Math.sqrt(D)) / (2 * odds[0]);
        }
        else{
            throw new IllegalArgumentException("D < 0");
        }
        roots[0] = x1;
        roots[1] = x2;
        return roots;
    }

    // метод для поиска коэффициентов кубического уравнения
    public static double[] findOddsForThree(double[][] arr){
    double[] odds = new double[4];
    double x3 = -1;

    double x2 = arr[0][0] + arr[1][1] + arr[2][2];

    double x = (arr[0][0] * arr[1][1] * -1)
                + (arr[0][0] * arr[2][2] * -1)
                + (arr[1][1] * arr[2][2] * -1)
                + (arr[2][0] * arr[0][2])
                + (arr[2][1] * arr[1][2])
                + (arr[1][0] * arr[0][1]);

    double one = (arr[1][0] * arr[2][1] * arr[0][2])
                + (arr[0][1] * arr[1][2] * arr[2][0])
                + (arr[0][0] * arr[1][1] * arr[2][2])
                - (arr[2][0] * arr[1][1] * arr[0][2])
                - (arr[2][1] * arr[1][2] * arr[0][0])
                - (arr[1][0] * arr[0][1] * arr[2][2]);

    odds[0] = x3;
    odds[1] = x2;
    odds[2] = x;
    odds[3] = one;

    return odds;
    }

    // метод для нахождения корней кубического уравнения с помощью способа Кардано
    private static double[] SolveCubicEquation(double a, double b, double c) {
        double[] answer = new double[3];
        double Q = (Math.pow(a, 2) - 3 * b) / 9;
        double R = (2 * Math.pow(a, 3) - 9 * a * b + 27 * c) / 54;

        if (Math.pow(R, 2) < Math.pow(Q, 3)) {
            double t = Math.acos(R / Math.sqrt(Math.pow(Q, 3))) / 3;
            double x1 = -2 * Math.sqrt(Q) * Math.cos(t) - a / 3;
            double x2 = -2 * Math.sqrt(Q) * Math.cos(t + (2 * Math.PI / 3)) - a / 3;
            double x3 = -2 * Math.sqrt(Q) * Math.cos(t - (2 * Math.PI / 3)) - a / 3;
            answer[0] = x3;
            answer[1] = x2;
            answer[2] = x1;
            return answer;
        }
        else {
            double A;
            double a1 = Math.floor(R) + Math.sqrt(Math.pow(R, 2) - Math.pow(Q, 3));
            if(a1 > 0){
                A = -Math.signum(R) * Math.pow(a1, (1.0 / 3.0));
            }
            else{
                A = Math.signum(R) * -Math.pow(Math.abs(a1),(1.0 / 3.0));
            }
            double B = (A == 0) ? 0.0 : Q / A;

            double x1 = (A + B) - a / 3;

            if (A == B) {
                double x2 = -A - a / 3;
                answer[0] = x1;
                answer[1] = x2;
                answer[2] = x2;
                return answer;
            }
            else {
                double x2 = -(A + B) / 2 - a / 3 + -Math.sqrt(3) * (A - B) / 2;
                double x3 = -(A + B) / 2 - a / 3 + Math.sqrt(3) * (A - B) / 2;
                answer[0] = x1;
                answer[1] = x2;
                answer[2] = x3;
                return answer;
            }
        }
    }

    // служебный метод для объединения векторов в ответ при нахождении собственных векторов 3 на 3
    private static double[][] makeAnswerVectorForTwoVectors(double[][] v1,double[][] v2){
        double[][] resultVectors = new double[3][v1[0].length+1];

        for (int i = 0; i < resultVectors.length; i++) {
            for (int j = 0; j < resultVectors[0].length; j++) {
                if(j < v1[0].length){
                    resultVectors[i][j] = v1[i][j];
                }
                if(j == resultVectors[0].length - 1){
                    resultVectors[i][j] = v2[i][0];
                }
            }
        }
        return resultVectors;
    }

    // служебный метод для объединения векторов в ответ при нахождении собственных векторов 3 на 3
    private static double[][] makeAnswerVectorForThreeVectors(double[][] v1,double[][] v2,double[][] v3){
        double[][] resultVectors = new double[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(j == 0){
                    resultVectors[j][i] = v1[i][0];
                }
                if(i == 1){
                    resultVectors[j][i] = v2[j][0];
                }
                if(i == 2){
                    resultVectors[j][i] = v3[j][0];
                }
            }
        }
        return resultVectors;
    }

    // метод для нахождения собственного вектора при случае, когда лямбды не равны
    private static double[][] findVectorWithNotSameLambdas(double[][] srcMatr,double lambda){
        double[][] taskAnswer = new double[3][1];
        double[][] matrix = new double[2][2];
        double[][] substractedMatrix = Solution.subtractRootFromMatrix(srcMatr,lambda);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matrix[i][j] = substractedMatrix[i][j];
            }
        }

        double[][] freeMembersMatrix = new double[2][1];
        for (int i = 0; i < 2; i++) {
            freeMembersMatrix[i][0] = -(substractedMatrix[i][2] * 1);
        }

        double[][] answers = Solution.solveKramer(matrix,freeMembersMatrix);

        double x1 = answers[0][0];
        double x2 = answers[1][0];

        taskAnswer[0][0] = x1;
        taskAnswer[1][0] = x2;
        taskAnswer[2][0] = 1;

        return taskAnswer;
    }

    // служебный метод для обраюотки событий, чтобы понять, равны ли лямбды друг другу и чтобы вызвать соответсвующее решение для них
    private static double[][] findVectorForThree(double[][] srcMatrix,double[] lambdas){
        double[][] taskAnswer;

        boolean v12 = lambdas[0] == lambdas[1];
        boolean v13 = lambdas[0] == lambdas[2];
        boolean v23 = lambdas[1] == lambdas[2];

        if(v12 && v13 && v23){
            taskAnswer = findVectorWithSameLambdas(srcMatrix,lambdas[0]);
            return taskAnswer;
        }
        if(v12){
            taskAnswer = makeAnswerVectorForTwoVectors(findVectorWithSameLambdas(srcMatrix,lambdas[0]),findVectorWithNotSameLambdas(srcMatrix,lambdas[2]));
            return taskAnswer;
        }
        if(v13){
            taskAnswer = makeAnswerVectorForTwoVectors(findVectorWithSameLambdas(srcMatrix,lambdas[0]),findVectorWithNotSameLambdas(srcMatrix,lambdas[1]));
            return taskAnswer;
        }
        if(v23){
            taskAnswer = makeAnswerVectorForTwoVectors(findVectorWithSameLambdas(srcMatrix,lambdas[1]),findVectorWithNotSameLambdas(srcMatrix,lambdas[0]));
            return taskAnswer;
        }
        if(!v12 && !v13 && !v23){
            taskAnswer = makeAnswerVectorForThreeVectors(findVectorWithNotSameLambdas(srcMatrix,lambdas[0]),findVectorWithNotSameLambdas(srcMatrix,lambdas[1]),findVectorWithNotSameLambdas(srcMatrix,lambdas[2]));
            return taskAnswer;
        }
        return null;
    }

    // метод для нахождения собственного вектора при случае, когда лямбды равны
    private static double[][] findVectorWithSameLambdas(double[][] matrix,double l){
        double[][] steppedMatr = makeSteppedMatrix(subtractRootFromMatrix(matrix,l));
        int s = 3 - steppedMatr.length;
        double x1;
        double x2;
        double x3;
        if(s == 2){
            double[][] answers = new double[3][2];
            x2 = 1;
            x3 = 0;
            answers[1][0] = x2;
            answers[2][0] = x3;
            answers[0][0] = -steppedMatr[0][1] / steppedMatr[0][0];

            x2 = 0;
            x3 = 1;
            answers[1][1] = x2;
            answers[2][1] = x3;
            answers[0][1] = -steppedMatr[0][2] / steppedMatr[0][0];
            return answers;
        }

        if(s == 1){
            double[][] answers = new double[3][1];
            double[][] m = new double[2][2];

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    m[i][j] = steppedMatr[i][j];
                }
            }

            double[][] freeMembersMatrix = new double[2][1];
            for (int i = 0; i < 2; i++) {
                freeMembersMatrix[i][0] = -(steppedMatr[i][2] * 1);
            }

            double[][] answer = Solution.solveKramer(m,freeMembersMatrix);

            x1 = answer[0][0];
            x2 = answer[1][0];

            answers[0][0] = x1;
            answers[1][0] = x2;
            answers[2][0] = 1;

            return answers;
        }
        return null;
    }


    private static int[] findBasicMinorForVectorsThree(double[][] matrixToFind){
        int colIndex = 0;
        int row = 0;
        int col = 0;
        int rowIndex = 0;
        boolean flag = true;
        double[][] currBasicMinor = new double[2][2];

        while(flag && rowIndex != 2){
            for (int i = rowIndex; i < currBasicMinor.length; i++) {
                for (int j = colIndex; j < colIndex + currBasicMinor.length && j < matrixToFind.length - 1; j++) {
                    currBasicMinor[row][col] = matrixToFind[i][j];
                    col++;
                }
                col = 0;
                row++;
            }
            if(determinantСount(currBasicMinor) == 0){
                colIndex++;
            }
            if(determinantСount(currBasicMinor) != 0){
                flag = false;
            }
            if(colIndex == 2){
                rowIndex++;
            }
        }
        int[] answer = new int[2];
        answer[0] = rowIndex;
        answer[1] = colIndex;
        return answer;
    }
}


