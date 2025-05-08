package matrixmultiplication;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMatrixMultiply {

    public static double[][] randomMatrix(int n){
        double[][] mat = new double[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                mat[i][j] = Math.random();
            }
        }
        return mat;
    }

    static void printOut(double[][] C){
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.printf("%10.2f", C[i][j]); // 10-character wide, 2 decimal places
            }
            System.out.println(); // Move to next line after each row
        }
    }



    public static void main(String[] args) {
        System.out.println("Starting....");


        int size = 512; // power of 2 - refer to my readme for why
        double[][] A = randomMatrix(size);
        double[][] B = randomMatrix(size);
        double[][] C = new double[size][size];

        SubMatrix smA = new SubMatrix(A, 0, 0, size);
        SubMatrix smB = new SubMatrix(B, 0, 0, size);
        SubMatrix smC = new SubMatrix(C, 0, 0, size);

        ForkJoinPool pool = new ForkJoinPool();
        long start = System.currentTimeMillis();
        pool.invoke(new MultiplyMatrixTask(smA, smB, smC));
        long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start) + "ms");

        // print out the C
        // printOut(C);

    }


}



/*


        int size = 4;
        double[][] A = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        double[][] B = {
                {16, 15, 14, 13},
                {12, 11, 10, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };

        double[][] C = new double[size][size];


Starting....
Time taken: 1ms
     80.00     70.00     60.00     50.00
    240.00    214.00    188.00    162.00
    400.00    358.00    316.00    274.00
    560.00    502.00    444.00    386.00


==========================


        double[][] A = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
        };

        double[][] B = {
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };

        double[][] C = new double[size][size]; // the result in matrix C should be exactly A.


*/
