package com.example.linearequations.methods_linear;

import com.example.linearequations.Solve;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LU implements Solve {
    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final int num_of_variables;
    private final double[][] lower_matrix;
    private double[][] upper_matrix;
    double[] b;
    private final int precision;
    private final double[][] co_eff;
    private final long time;
    private final String form;


    public LU(double[][] co_eff, double[] b, int precision, String form) {
        this.co_eff = co_eff;
        this.precision = precision;
        this.b = b;
        this.form = form;

        this.num_of_variables = co_eff[0].length;
        this.lower_matrix = new double[num_of_variables][num_of_variables];
        this.upper_matrix = new double[num_of_variables][num_of_variables];
        for (int i = 0; i < num_of_variables; i++) {
            System.arraycopy(co_eff[i], 0, this.upper_matrix[i], 0, num_of_variables);
        }

        stringBuilder = new StringBuilder("LU Decomposition\n\n");
        finalAnswer = new StringBuilder("After LU Decomposition:\n");
        long start = System.nanoTime();
        switch (form) {
            case "Crout Form":
                if (this.is_crout_valid())
                    backSubstitution();
                break;
            case "Cholesky Form":
                this.cholesky();
                break;
            default:
                this.doolittle();
                backSubstitution();
                break;
        }
        time = System.nanoTime() - start;
    }

    private double[][] transpose(double[][] x) {
        double[][] temp = new double[num_of_variables][num_of_variables];
        for (int i = 0; i < num_of_variables; i++) {
            for (int j = 0; j < num_of_variables; j++) {
                temp[i][j] = x[j][i];
            }
        }
        return temp;
    }

    private boolean is_symmetric() {
        for (int i = 0; i < num_of_variables; i++)
            for (int j = 0; j < num_of_variables; j++)
                if (co_eff[i][j] != co_eff[j][i]) return false;
        return true;
    }

    public void doolittle() {
        int i, j, k;
        for (i = 0; i < num_of_variables; i++) {
            lower_matrix[i][i] = 1;
        }

        stringBuilder.append("Lower and Upper Matrices Steps");
        for (i = 0; i < num_of_variables - 1; i++) {
            StringBuilder s = new StringBuilder();
            for (j = i + 1; j < num_of_variables; j++) {
                double factor = upper_matrix[j][i] / upper_matrix[i][i];
                lower_matrix[j][i] = BigDecimal.valueOf(factor)
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                for (k = 0; k < num_of_variables; k++) {
                    upper_matrix[j][k] = BigDecimal.valueOf(upper_matrix[j][k] - factor * upper_matrix[i][k])
                            .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                }
                s.append("R").append(j + 1).append(" -> ").append("R").append(j + 1).append(" - a")
                        .append(j + 1).append(i + 1).append(" / a").append(i + 1).append(i + 1).append(" x R")
                        .append(i + 1).append("   For Lower Matrix: L[").append(j + 1).append("][").append(i + 1)
                        .append("] = ").append("a").append(j + 1).append(i + 1).append(" / a").append(i + 1)
                        .append(i + 1).append("\n");
            }
            stringBuilder.append("\nStep: ").append(i + 1).append("\n").append(s).append("\n");
            if (i != num_of_variables - 2) {
                for (k = 0; k < num_of_variables; k++) {
                    for (j = 0; j < num_of_variables; j++) {
                        stringBuilder.append(lower_matrix[k][j]).append(" ");
                    }
                    stringBuilder.append(" | ");
                    for (j = 0; j < num_of_variables; j++) {
                        stringBuilder.append(upper_matrix[k][j]).append(" ");
                    }
                    stringBuilder.append("\n");
                }
            }
        }

        if (this.form.equals("Doolittle Form")) {
            stringBuilder.append("Doolittle Form:\n");
        } else {
            stringBuilder.append("LU To help reaching the Solution:\n");
        }

        for (i = 0; i < num_of_variables; i++) {
            for (j = 0; j < num_of_variables; j++) {
                stringBuilder.append(lower_matrix[i][j]).append(" ");
            }
            stringBuilder.append(" | ");
            for (j = 0; j < num_of_variables; j++) {
                stringBuilder.append(upper_matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n\n  --------------------------------------------------------- \n\n");
    }

    // Get the lower and the upper from doolittle
    public boolean is_crout_valid() {
        this.doolittle();
        double sum;
        int i, j, k;

        for (i = 0; i < num_of_variables; i++) {
            upper_matrix[i][i] = 1;
        }

        stringBuilder.append("\nSteps After getting Lower and Upper Matrices \nTo get Crout Lower and Upper Matrices");
        for (j = 0; j < num_of_variables; j++) {
            StringBuilder s = new StringBuilder();
            for (i = j; i < num_of_variables; i++) {
                sum = 0;
                for (k = 0; k < j; k++) {
                    sum = BigDecimal.valueOf(sum + BigDecimal.valueOf(lower_matrix[i][k] * upper_matrix[k][j])
                                    .setScale(precision, RoundingMode.HALF_UP).doubleValue())
                            .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                }
                s.append("L[").append(i + 1).append("][").append(j + 1).append("] = ").append("A[").append(i + 1)
                        .append("][").append(j + 1).append("] - ").append("\u03A3")
                        .append(" L[i][k] x U[k][j]   (k=1 : j-1, where 1\u2264k\u2264j-1 & i=")
                        .append(i + 1).append(" & j=").append(j + 1).append(")").append("\n");
                lower_matrix[i][j] = BigDecimal.valueOf(co_eff[i][j] - sum)
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }

            for (i = j; i < num_of_variables; i++) {
                sum = 0;
                for (k = 0; k < j; k++) {
                    sum = BigDecimal.valueOf(sum + BigDecimal.valueOf(lower_matrix[j][k] * upper_matrix[k][i])
                                    .setScale(precision, RoundingMode.HALF_UP).doubleValue())
                            .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                }
                if (lower_matrix[j][j] == 0) {
                    return false;
                }
                s.append("U[").append(j + 1).append("][").append(i + 1).append("] = ").append("A[").append(j + 1)
                        .append("][").append(i + 1).append("] - ").append("\u03A3")
                        .append(" L[j][k] x U[k][i]   (k=1 : i-1, where 1\u2264k\u2264j-1 & i=")
                        .append(i + 1).append(" & j=").append(j + 1).append(")").append("\n");
                upper_matrix[j][i] = BigDecimal.valueOf((co_eff[j][i] - sum) / lower_matrix[j][j])
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            stringBuilder.append("\nStep: ").append(j + 1).append("\n").append(s).append("\n");
        }

        if (this.form.equals("Crout Form")) {
            stringBuilder.append("\nCrout Form:\n");
        } else {
            stringBuilder.append("\nLU To help reaching the Solution:\n");
        }
        for (i = 0; i < num_of_variables; i++) {
            for (j = 0; j < num_of_variables; j++) {
                stringBuilder.append(lower_matrix[i][j]).append(" ");
            }
            stringBuilder.append(" | ");
            for (j = 0; j < num_of_variables; j++) {
                stringBuilder.append(upper_matrix[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n\n  --------------------------------------------------------- \n\n");
        return true;
    }

    public void cholesky() {
        if (is_symmetric()) {
            if (is_crout_valid()) {
                int i, j, k;
                stringBuilder.append("\nSteps After getting Lower and Upper Matrices from Crout Form" +
                        "\nTo get Cholesky Lower and Upper Matrices");
                for (j = 0; j < num_of_variables; j++) {
                    StringBuilder s = new StringBuilder();
                    double sum = 0;
                    for (k = 0; k < j; k++) {
                        sum = BigDecimal.valueOf(sum + BigDecimal.valueOf(lower_matrix[j][k] * lower_matrix[j][k])
                                        .setScale(precision, RoundingMode.HALF_UP).doubleValue())
                                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                    }
                    // System.out.println("VALUE: " + (co_eff[j][j] - sum));
                    s.append("L[").append(j + 1).append("][").append(j + 1).append("] = \u221A").append("A[")
                            .append(j + 1).append("][").append(j + 1).append("] - ").append("\u03A3")
                            .append(" L[j][k] x L[j][k]   (k=1 : j-1, where 1\u2264k\u2264j-1 & j=")
                            .append(j + 1).append("\n");
                    lower_matrix[j][j] = BigDecimal.valueOf(Math.sqrt(co_eff[j][j] - sum))
                            .setScale(precision, RoundingMode.HALF_UP).doubleValue();

                    for (i = j + 1; i < num_of_variables; i++) {
                        sum = 0;
                        for (k = 0; k < j; k++) {
                            sum = BigDecimal.valueOf(sum + BigDecimal.valueOf(lower_matrix[i][k] * lower_matrix[j][k])
                                            .setScale(precision, RoundingMode.HALF_UP).doubleValue())
                                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                        }
                        s.append("L[").append(i + 1).append("][").append(j + 1).append("] = 1/").append("L[")
                                .append(j + 1).append("][").append(j + 1).append("] x (").append("A[")
                                .append(i + 1).append("][").append(j + 1).append("] - ").append("\u03A3")
                                .append(" L[i][k] x L[j][k])   (k=1 : j-1, where 1\u2264k\u2264j-1 & j=")
                                .append(j + 1).append(" & i = ").append(i + 1).append(")").append("\n");
                        lower_matrix[i][j] = BigDecimal.valueOf(1.0 / lower_matrix[j][j] * (co_eff[i][j] - sum))
                                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
                    }
                    stringBuilder.append("\nStep: ").append(j + 1).append("\n").append(s).append("\n");
                }
                upper_matrix = transpose(lower_matrix);

                stringBuilder.append("\nCholesky Form:\n");
                for (i = 0; i < num_of_variables; i++) {
                    for (j = 0; j < num_of_variables; j++) {
                        stringBuilder.append(lower_matrix[i][j]).append(" ");
                    }
                    stringBuilder.append(" | ");
                    for (j = 0; j < num_of_variables; j++) {
                        stringBuilder.append(upper_matrix[i][j]).append(" ");
                    }
                    stringBuilder.append("\n");
                }
                stringBuilder.append("\n\n  --------------------------------------------------------- \n\n");
                backSubstitution();
            }

        } else {
            stringBuilder.append("Cholesky Form is invalid!\nExpected Check if the matrix is symmetric!");
        }
    }

    private void backSubstitution() {
        double[] res = new double[num_of_variables];
        b = forwardSubstitution();

        stringBuilder.append("\nSteps For Backward Substitution: \n");
        stringBuilder.append("Step: 1\n")
                .append("x").append(num_of_variables).append(" = ").append("b").append(num_of_variables)
                .append(" / ").append("A[").append(num_of_variables).append("][")
                .append(num_of_variables).append("]").append("\n\n");

        res[num_of_variables - 1] = BigDecimal.valueOf(b[num_of_variables - 1] /
                        upper_matrix[num_of_variables - 1][num_of_variables - 1])
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
        for (int i = num_of_variables - 2; i >= 0; i--) {
            double sum = 0;
            StringBuilder s = new StringBuilder();
            for (int j = i + 1; j < num_of_variables; j++) {
                sum = BigDecimal.valueOf(sum + (upper_matrix[i][j] * res[j]))
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            s.append("x").append(i + 1).append(" = ")
                    .append("b").append(i + 1).append(" - ").append("\u03A3").append(" A[i][j]")
                    .append(" * xi) / A[").append(i + 1).append("][").append(i + 1).append("]")
                    .append("  (From j = ").append(i + 1).append(" To ").append(num_of_variables)
                    .append(" & i = ").append(i + 1).append(")").append("\n");
            res[i] = BigDecimal.valueOf((b[i] - sum) / upper_matrix[i][i])
                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            stringBuilder.append("Step: ").append(num_of_variables - i).append("\n").append(s).append("\n");
        }

        this.stringBuilder.append("\nAfter Back Substitution:\n");
        for (int z = 0; z < num_of_variables; z++) {
            this.stringBuilder.append("x").append(z + 1).append(" = ").append(res[z]).append("\n");
            this.finalAnswer.append("x").append(z + 1).append(" = ").append(res[z]).append("\n");
        }
        stringBuilder.append("\n\n  --------------------------------------------------------- \n\n");
    }

    private double[] forwardSubstitution() {
        double[] res = new double[num_of_variables];
        res[0] = BigDecimal.valueOf(b[0] / lower_matrix[0][0])
                .setScale(precision, RoundingMode.HALF_UP).doubleValue();
        stringBuilder.append("Steps For Forward Substitution: \nStep: 1\n").append("z1").append(" = ")
                .append("b1 / A[1][1]\n\n");
        for (int i = 1; i < num_of_variables; i++) {
            double sum = 0;
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sum = BigDecimal.valueOf(sum + (lower_matrix[i][j] * res[j]))
                        .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            }
            res[i] = BigDecimal.valueOf((b[i] - sum) / lower_matrix[i][i])
                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();
            s.append("z").append(i + 1).append(" = ").append("(b").append(i + 1).append(" - ").append("\u03A3")
                    .append(" A[i][j]").append(" * xi) / A[").append(i + 1).append("][").append(i + 1).append("]")
                    .append("  (From j=1 To ").append(i + 1).append(" & i=").append(i + 1).append("\n");
            stringBuilder.append("Step: ").append(i + 1).append("\n").append(s).append("\n");
        }

        this.stringBuilder.append("\nAfter Forward Substitution:\n");
        for (int z = 0; z < num_of_variables; z++) {
            this.stringBuilder.append("z").append(z + 1).append(" = ").append(res[z]).append("\n");
        }
        stringBuilder.append("\n\n  --------------------------------------------------------- \n\n");
        return res;
    }

    public double[][] getLower_matrix() {
        return lower_matrix;
    }

    public double[][] getUpper_matrix() {
        return upper_matrix;
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String finalRoot() {
        return finalAnswer.toString();
    }
}
