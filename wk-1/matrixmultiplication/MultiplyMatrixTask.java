package matrixmultiplication;

import java.util.concurrent.RecursiveAction;

public class MultiplyMatrixTask extends RecursiveAction {

    private final SubMatrix A;
    private final SubMatrix B;
    private final SubMatrix C;

    private final static int THRESHOLD = 64;

    public MultiplyMatrixTask(SubMatrix A, SubMatrix B, SubMatrix C) {
        this.A = A;
        this.B = B;
        this.C = C;
    }


    @Override
    protected void compute() {
        if(A.size < THRESHOLD){
            new MultiplyMatrixBase(A, B, C).compute();
            return;
        }

        int half = A.size / 2;

        // Sub matrix blocks
        SubMatrix A11 = A.subBlock(0, 0),  A12 = A.subBlock(0, 1);
        SubMatrix A21 = A.subBlock(1, 0), A22 = A.subBlock(1, 1);
        SubMatrix B11 = B.subBlock(0, 0), B12 = B.subBlock(0, 1);
        SubMatrix B21 = B.subBlock(1, 0), B22 = B.subBlock(1, 1);
        SubMatrix C11 = C.subBlock(0, 0), C12 = C.subBlock(0, 1);
        SubMatrix C21 = C.subBlock(1, 0), C22 = C.subBlock(1, 1);

        // Temp blocks for second half
        double[][] temp = new double[C.size][C.size];
        SubMatrix T = new SubMatrix(temp,  0, 0, C.size);
        SubMatrix T11 = T.subBlock(0, 0);
        SubMatrix T12 = T.subBlock(0, 1);
        SubMatrix T21 = T.subBlock(1, 0);
        SubMatrix T22 = T.subBlock(1, 1);

        // multiply in parallel
        invokeAll(
                // First set of forks: compute the first half of each Cij directly into Cij
                new MultiplyMatrixTask(A11, B11, C11), // C11 += A11*B11
                new MultiplyMatrixTask(A11, B12, C12), // C12 += A11*B12
                new MultiplyMatrixTask(A21, B11, C21), // C21 += A21*B11
                new MultiplyMatrixTask(A21, B12, C22), // C22 += A21*B12

                // Second set: compute the second half of each Cij into temporary T blocks
                new MultiplyMatrixTask(A12, B21, T11), // T11 = A12*B21
                new MultiplyMatrixTask(A12, B22, T12), // T12 = A12*B22
                new MultiplyMatrixTask(A22, B21, T21), // T21 = A22*B21
                new MultiplyMatrixTask(A22, B22, T22)  // T22 = A22*B22
        );

        // Join phase complete → now add Tij into corresponding Cij
        // Fork/Join principle: breaking down combining phase into parallel add subtasks
        invokeAll(
                new MatrixAddTask(C11, T11), // C11 += T11 → total C11 = A11*B11 + A12*B21
                new MatrixAddTask(C12, T12), // C12 += T12 → total C12 = A11*B12 + A12*B22
                new MatrixAddTask(C21, T21), // C21 += T21 → total C21 = A21*B11 + A22*B21
                new MatrixAddTask(C22, T22)  // C22 += T22 → total C22 = A21*B12 + A22*B22
        );
    }
}
