package matrixmultiplication;

import java.util.concurrent.RecursiveAction;

public class MultiplyMatrixBase extends RecursiveAction {

    private final SubMatrix A;
    private final SubMatrix B;
    private final SubMatrix C;

    public MultiplyMatrixBase(SubMatrix A, SubMatrix  B, SubMatrix C){
        this.A = A;
        this.B = B;
        this.C = C;
    }

    @Override
    protected void compute() {

        for(int i = 0; i < A.size; i++){
            for(int j = 0; j < B.size; j++){
                double sum = 0;
                for(int k = 0 ; k <  A.size; k++){
                    sum +=  A.get(i, k) * B.get(k, j);
                }
                C.add(i, j, sum); // assuming  C starts at 0 // Accumulate result into C
            }
        }

    }
}
