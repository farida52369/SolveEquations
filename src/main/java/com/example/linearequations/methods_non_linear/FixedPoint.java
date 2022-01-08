package com.example.linearequations.methods_non_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;
import com.example.linearequations.checks.GetFunctionValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FixedPoint implements Solve {

    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final long time;
    private double xR;
    private double start;

    public FixedPoint(double x, int itr, double epsilon) {
        this.stringBuilder = new StringBuilder("Fixed Point Method\n\n");
        this.finalAnswer = new StringBuilder();

        long start = System.nanoTime();
        calculate(x, itr, epsilon);
        time = System.nanoTime() - start;
    }

    private double g(double x) {
        GetFunctionValue obj = new GetFunctionValue();
        return obj.parseFun(x);
    }

    private void calculate(double x, int itr, double epsilon) {
        // For the plot
        this.start = x;

        int i = 0;
        double p, xOld = 0;

        //New
        this.stringBuilder.append("The Rule:\n").append("Iteration #i: xi + 1= g(xi) = f(xi) + xi")
                .append("\nRelative Approximate Error= |((xi+1) - (xi)) / (xi+1)| * 100").append("\n-------------------------------------\n\n");
        //p = g(fun,x);
        while (i <= itr) {
            // System.out.println("HELLO: " + HelloController.getExpression());
            p = g(x);
            if (i == 0) {
                this.stringBuilder.append("Iteration #").append(i).append(": x").append(i).append("= ").append(x)
                        .append("\n-------------------------------------\n\n");
            } else if (i >= 1) {
                this.stringBuilder.append("Iteration #").append(i).append(": x").append(i).append("= ").append(x)
                        .append("\nRelative Approximate Error= ").append(
                                BigDecimal.valueOf(Math.abs((xOld - x) / x) * 100)
                                        .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue())
                        .append("\n-------------------------------------\n\n");
            }
            i++;
            if (i <= itr && (Math.abs((p - x) / p) * 100) < epsilon) {
                x = p;
                this.stringBuilder.append("Iteration #").append(i).append(": x").append(i).append("= ")
                        .append(x).append(", Relative Approximate Error= ").append(
                                BigDecimal.valueOf(Math.abs((xOld - x) / xOld) * 100)
                                        .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue())
                        .append("\n-------------------------------------\n\n");
                break;
            }
            xOld = x;
            x = p;
        }

        // Final Answer
        xR = x;  // For the plot
        this.stringBuilder.append("After Fixed Point Method The final root = ").append(x);
        this.finalAnswer.append("The value of the final root = ").append(x);
    }

    public double getStart() {
        return start;
    }

    public double get_xR() {
        return xR;
    }

    @Override
    public long getTime() {
        // Time
        return time;
    }

    @Override
    public String finalRoot() {
        // Final Answer
        return finalAnswer.toString();
    }

    @Override
    public String toString() {
        // The Steps
        return stringBuilder.toString();
    }
}
