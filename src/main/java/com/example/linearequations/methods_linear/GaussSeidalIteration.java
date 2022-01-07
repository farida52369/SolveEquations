package com.example.linearequations.methods_linear;

import com.example.linearequations.Solve;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GaussSeidalIteration implements Solve {

    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final long time;

    public GaussSeidalIteration(double[][] co_eff, double[] b,
                                double[] initialGuess, int noIteration, double e, int precision) {
        this.stringBuilder = new StringBuilder("Gauss Seidal Iteration\n\n");
        this.finalAnswer = new StringBuilder("After Gauss Seidal Iteration:\n");
        long start = System.nanoTime();
        calculate(co_eff, b, initialGuess, noIteration, e, precision);
        time = System.nanoTime() - start;
    }

    private void calculate(double[][] co_eff, double[] b,
                          double[] initialGuess, int noIteration, double e, int precision) {
        int n = initialGuess.length;
        double[] xNew = new double[n];
        double[] xOld = new double[n];

        //
        System.arraycopy(initialGuess, 0, xNew, 0, n);
        System.arraycopy(initialGuess, 0, xOld, 0, n);

        stringBuilder.append("Steps For Gauss Seidal Iteration\n\n");
        for (int i = 0; i < noIteration; i++) {
            StringBuilder s = new StringBuilder();

            s.append("The Equations  of seidal for iteration: ").append(i + 1).append("\n");
            for (int j = 0; j < n; j++) {
                double value = b[j];
                s.append("x").append(j + 1).append(" = (").append(value);
                for (int k = 0; k < n; k++) {
                    if (j != k) {
                        value = BigDecimal.valueOf(value - BigDecimal.valueOf((co_eff[j][k] * xNew[k]))
                                        .setScale(precision, RoundingMode.HALF_UP).doubleValue())
                                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                        s.append(" - ").append(co_eff[j][k]).append(" * ").append(xNew[k]);
                    }
                }
                //for no. of significant we use big decimal to round
                xNew[j] = BigDecimal.valueOf(value / co_eff[j][j])
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                s.append(") / ").append(co_eff[j][j]).append("\n");
            }
            s.append("relative error of iteration ").append(i + 1).append(" = ").append(BigDecimal.valueOf(check(xOld, xNew)).
                    setScale(precision, RoundingMode.HALF_UP).doubleValue()).append("\n");
            if (check(xOld, xNew) < e) {
                this.stringBuilder.append("the epsilon reached: ").append(BigDecimal.valueOf(check(xOld, xNew)).
                        setScale(precision, RoundingMode.HALF_UP).doubleValue()).append("\n");

                this.finalAnswer.append("the epsilon reached: ").append(BigDecimal.valueOf(check(xOld, xNew)).
                        setScale(precision, RoundingMode.HALF_UP).doubleValue()).append("\n");
                for (int z = 0; z < n; z++) {
                    this.finalAnswer.append("x").append(z + 1).append(" = ").append(xNew[z]).append("\n");
                }
                return;
            }
            stringBuilder.append(s);

            this.stringBuilder.append("\nFinal Values For the Variables in Iteration: ").append(i + 1).append("\n");
            if (i == noIteration -1) {
                this.finalAnswer.append("After Exhausting the iterations:\n");
                for (int z = 0; z < n; z++) {
                    this.stringBuilder.append("x").append(z + 1).append(" = ").append(xNew[z]).append("\n");
                    this.finalAnswer.append("x").append(z + 1).append(" = ").append(xNew[z]).append("\n");
                }
                return;
            } else {
                for (int z = 0; z < n; z++) {
                    this.stringBuilder.append("x").append(z + 1).append(" = ").append(xNew[z]).append("\n");
                    xOld[z] = xNew[z];
                }
            }
            this.stringBuilder.append("\n   ---------------------------------------------------  \n\n");
        }

    }

    public static double check(double[] xOld, double[] xNew) {
        double max = Double.MAX_VALUE * -1;
        double check;
        for (int i = 0; i < xNew.length; i++) {
            check = (Math.abs((xNew[i] - xOld[i])) / xNew[i]) * 100;
            if (check > max)
                max = check;
        }
        return max;
    }

    @Override
    public String toString() {
        // The Steps
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
