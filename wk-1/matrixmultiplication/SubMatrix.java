package matrixmultiplication;

public class SubMatrix {

    double[][] matrix;
    int rowOffset;
    int colOffset;
    int size;

    public SubMatrix(double[][] matrix, int rowOffset, int colOffset, int size){
        this.matrix = matrix;
        this.rowOffset = rowOffset;
        this.colOffset = colOffset;
        this.size = size;
    }

    public double get(int i, int j){
        return matrix[rowOffset + i][colOffset +j];
    }

    public void set(int i, int j, double value){
        matrix[rowOffset + i][colOffset + j] = value;
    }

    public void add(int i, int j, double value){
        matrix[rowOffset + i][colOffset + j] += value;
    }

    public SubMatrix subBlock(int rOffset, int cOffset){
        int half = size / 2;
        return new SubMatrix(
                matrix,
                rowOffset + rOffset * half,
                colOffset + cOffset * half,
                half
        );
    }

}
