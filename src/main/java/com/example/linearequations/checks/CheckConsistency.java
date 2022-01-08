package com.example.linearequations.checks;

public class CheckConsistency {

    public int check(double[][] tempMatrix, double[] tempB) {
        int len = tempB.length;
        double[][] co_eff = new double[len][len];
        double[] b = new double[len];
        for (int i = 0; i < len; i++) {
            System.arraycopy(tempMatrix[i], 0, co_eff[i], 0, len);
        }
        System.arraycopy(tempB, 0, b, 0, len);
        int i, j, k;
        for (k = 0; k < len - 1; k++) {
            cal(co_eff, b);
            for (i = k + 1; i < len; i++) {
                double factor = co_eff[i][k] / co_eff[k][k];  // m
                for (j = 0; j < len; j++) {
                    co_eff[i][j] = co_eff[i][j] - factor * co_eff[k][j];
                    // co_eff[i][j] = co_eff[i][j] - factor * co_eff[k][j];
                }
                b[i] = b[i] - factor * b[k];
            }
        }

        int rank1 = len, rank2 = len;
        for (i = 0; i < len; i++) {
            boolean flag = true;
            for (j = 0; j < len; j++) {
                if (co_eff[i][j] != 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) rank1 -= 1;
            if (flag && b[i] == 0) rank2 -= 1;
        }

        if (rank1 == rank2 && len == rank1) return 0;
        else if (rank1 == rank2 && len != rank1) return 1;
        else return 2;
    }

    private void cal(double[][] co_eff, double[] b) {
        for (int p = 0; p < b.length; p++) {
            int max = p;
            for (int i = p + 1; i < b.length; i++) {
                if (Math.abs(co_eff[i][p]) > Math.abs(co_eff[max][p])) {
                    max = i;
                }
            }
            // exchange row p with row max
            swap(p, max, co_eff);
            double temp = b[p];
            b[p] = b[max];
            b[max] = temp;
        }
    }

    private void swap(int row1, int row2, double[][] a) {
        double[] temp = a[row1];
        a[row1] = a[row2];
        a[row2] = temp;
    }
}
