package com.example.linearequations.methods_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussElimination implements Solve {

    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final int precision;
    private final long time;

    public GaussElimination(double[][] co_eff, double[] b, int precision) {
        System.out.println(HelloController.getExpression());
        this.stringBuilder = new StringBuilder("Gauss Elimination Method\n\n");
        this.finalAnswer = new StringBuilder("After Gauss Elimination Method:\n");
        this.precision = precision;
        long start = System.nanoTime();
        Object[] forward = forwardElimination(co_eff, b);
        backSubstitution((double[][]) forward[0], (double[]) forward[1]);
        time = System.nanoTime() - start;
    }

    private Object[] forwardElimination(double[][] co_eff, double[] b) {

        int len = b.length;
        for (int k = 0; k < len - 1; k++) {
            StringBuilder s = new StringBuilder();
            cal(co_eff, b);
            for (int i = k + 1; i < len; i++) {

                double factor = co_eff[i][k] / co_eff[k][k];  // m
                for (int j = 0; j < len; j++) {
                    co_eff[i][j] = BigDecimal.valueOf(co_eff[i][j] - factor * co_eff[k][j])
                            .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                    // co_eff[i][j] = co_eff[i][j] - factor * co_eff[k][j];
                }
                b[i] = BigDecimal.valueOf(b[i] - factor * b[k])
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                s.append("R").append(i + 1).append(" -> ").append("R").append(i + 1).append(" - a").append(i + 1).append(k + 1)
                        .append(" / a").append(k + 1).append(k + 1).append(" x R").append(k + 1).append("\n");
            }

            this.stringBuilder.append("Forward Elimination: \nStep: ").append(k+1).append("\n").append(s).append("\n");
            if (k != len - 2) {
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < len; j++) {
                        // System.out.println(String.format("%s%c", co_eff[i][j], ' '));
                        this.stringBuilder.append(co_eff[i][j]).append(" ");
                    }
                    this.stringBuilder.append(" | ").append(b[i]).append("\n");
                }
                this.stringBuilder.append("\n");
            }
        }

        this.stringBuilder.append("Forward Elimination:\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                // System.out.println(String.format("%s%c", co_eff[i][j], ' '));
                this.stringBuilder.append(co_eff[i][j]).append(" ");
            }
            this.stringBuilder.append(" | ").append(b[i]).append("\n");
        }
        this.stringBuilder.append("\n");

        return new Object[]{co_eff, b};
    }

    private void backSubstitution(double[][] co_eff, double[] b) {
        int len = b.length;
        double[] res = new double[len];

        stringBuilder.append("\nSteps For Backward Substitution: \n");
        stringBuilder.append("Step: 1\n")
                .append("x").append(len).append(" = ").append("b").append(len)
                .append(" / ").append("A[").append(len).append("][")
                .append(len).append("]").append("\n\n");
        res[len - 1] = BigDecimal.valueOf(b[len - 1] / co_eff[len - 1][len - 1])
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
        for (int i = len - 2; i >= 0; i--) {
            double sum = 0;
            StringBuilder s = new StringBuilder();
            for (int j = i + 1; j < len; j++) {
                sum = BigDecimal.valueOf(sum + (co_eff[i][j] * res[j]))
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            res[i] = BigDecimal.valueOf((b[i] - sum) / co_eff[i][i])
                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();

            s.append("x").append(i + 1).append(" = ")
                    .append("b").append(i + 1).append(" - ").append("\u03A3").append(" A[i][j]")
                    .append(" * xi) / A[").append(i + 1).append("][").append(i + 1).append("]")
                    .append("  (From j = ").append(i + 1).append(" To ").append(len)
                    .append(" & i = ").append(i + 1).append(")").append("\n");
            stringBuilder.append("Step: ").append(len - i).append("\n").append(s).append("\n");
        }

        this.stringBuilder.append("After Back Substitution:\n");
        for (int z = 0; z < len; z++) {
            this.stringBuilder.append("x").append(z + 1).append(" = ").append(res[z]).append("\n");
            this.finalAnswer.append("x").append(z + 1).append(" = ").append(res[z]).append("\n");
        }
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

    @Override
    public long getTime() {
        return this.time;
    }

    @Override
    public String finalRoot() {
        // Final Answer
        return this.finalAnswer.toString();
    }

    @Override
    public String toString() {
        // The Steps
        return this.stringBuilder.toString();
    }

}
  
  

