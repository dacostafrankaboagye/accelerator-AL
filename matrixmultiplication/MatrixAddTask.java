package matrixmultiplication;

import java.util.concurrent.RecursiveAction;

public class MatrixAddTask extends RecursiveAction {
    private final SubMatrix C, T;

    public MatrixAddTask(SubMatrix C, SubMatrix T) {
        this.C = C;
        this.T = T;
    }

    @Override
    protected void compute() {
        for(int i = 0; i < C.size; i++){
            for(int j = 0; j < C.size; j++){
                C.add(i, j, T.get(i, j)); // Cij += Tij
            }
        }

    }
}
