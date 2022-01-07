package com.example.linearequations.methods_linear;

import com.example.linearequations.Solve;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussJordan implements Solve {

    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final int precision;
    private final long time;

    public GaussJordan(double[][] co_eff, double[] b, int precision) {
        this.stringBuilder = new StringBuilder("Gauss Jordan Method\n\n");
        this.finalAnswer = new StringBuilder("After Gauss Jordan Method:\n");
        this.precision = precision;
        long start = System.nanoTime();
        Object[] forward = forwardElimination(co_eff, b);
        backwardElimination((double[][]) forward[0], (double[]) forward[1]);
        time = System.nanoTime() - start;
    }

    private Object[] forwardElimination(double[][] co_eff, double[] b) {

        int len = b.length;
        stringBuilder.append("Steps To reach Forward Elimination:");
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
            this.stringBuilder.append("\nStep: ").append(k+1).append("\n").append(s).append("\n");
            if (k != len - 2) {
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < len; j++) {
                        // System.out.println(String.format("%s%c", co_eff[i][j], ' '));
                        this.stringBuilder.append(co_eff[i][j]).append(" ");
                    }
                    this.stringBuilder.append(" | ").append(b[i]).append("\n");
                }
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

        return new Object[]{co_eff, b};
    }

    private void backwardElimination(double[][] co_eff, double[] b) {
        int len = b.length;
        stringBuilder.append("\n  -----------------------------------  \n\nSteps To reach Back Elimination:");
        StringBuilder s;
        for (int k = len - 2; k >= 0; k--) {
            s = new StringBuilder();
            for (int i = k; i >= 0; i--) {
                double factor = co_eff[i][k + 1] / co_eff[k + 1][k + 1];  // m
                co_eff[i][k + 1] = BigDecimal.valueOf(co_eff[i][k + 1] - factor * co_eff[k + 1][k + 1])
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                b[i] = BigDecimal.valueOf(b[i] - factor * b[k + 1])
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                s.append("R").append(i + 1).append(" -> ").append("R").append(i + 1).append(" - a").append(i + 1).append(k + 2)
                        .append(" / a").append(k + 2).append(k + 2).append(" x R").append(k + 2).append("\n");
            }
            this.stringBuilder.append("\nStep: ").append(len - k - 1).append("\n").append(s).append("\n");
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    // System.out.println(String.format("%s%c", co_eff[i][j], ' '));
                    this.stringBuilder.append(co_eff[i][j]).append(" ");
                }
                this.stringBuilder.append(" | ").append(b[i]).append("\n");
            }
        }

        s = new StringBuilder();
        for (int k = 0; k < len; k++) {
            b[k] = BigDecimal.valueOf(b[k] / co_eff[k][k])
                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            co_eff[k][k] = 1;
            s.append("R").append(k+1).append(" -> ").append("1 / ")
                    .append("b").append(k+1).append(k+1).append(" x R").append(k+1).append("\n");
        }
        stringBuilder.append("\nLast Step: \n").append(s).append("\n");
        this.stringBuilder.append("Backward Elimination:\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                this.stringBuilder.append(co_eff[i][j]).append(" ");
            }
            this.stringBuilder.append(" | ").append(b[i]).append("\n");
        }

        this.stringBuilder.append("\nResult:\n");
        for (int i = 0; i < len; i++) {
            this.stringBuilder.append("x").append(i + 1).append(" = ").append(b[i]).append("\n");
            this.finalAnswer.append("x").append(i + 1).append(" = ").append(b[i]).append("\n");
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
    public String toString() {
        // Steps For the method
        return stringBuilder.toString();
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String finalRoot() {
        // Final Answer
        return finalAnswer.toString();
    }
}

