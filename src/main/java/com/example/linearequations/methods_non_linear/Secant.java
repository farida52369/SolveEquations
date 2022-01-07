package com.example.linearequations.methods_non_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;
import com.example.linearequations.checks.GetFunctionValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Secant implements Solve {

    GetFunctionValue obj = new GetFunctionValue();
    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final long time;
    private final List<Double> values;

    public Secant(double x0, double x1, double conv_level, int itr) {
        this.stringBuilder = new StringBuilder("Secant Method\n\n");
        this.finalAnswer = new StringBuilder();
        values = new ArrayList<>(3);
        long start = System.nanoTime();
        secantMethod(x0, x1, conv_level, itr);
        this.time = System.nanoTime() - start;
    }

    private double expression(double x) {
        return obj.parseFun(x);
    }

    private void secantMethod(double x0, double x1, double conv_level, int itr) {
        //new
        if((expression(f,x0) - expression(f,x1))==0){
                    System.out.println("\nThe Function Diverge!\nTry better initial guess :))");
                    return;
                }
        ////
        // For the plot
        this.values.add(x0);
        this.values.add(x1);

        int iterations = 0;
        double x2;
        double check = expression(x0) * expression(x1);

        if (check < 0) {
            do {
                x2 = BigDecimal.valueOf(x1 - (expression(x1) * (x0 - x1)) / (expression(x0) - expression(x1)))
                        .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue();

                // The Steps
                this.stringBuilder.append("Iteration #").append(iterations + 1).append(": Xn-1= ")
                        .append(x0).append(", xn= ").append(x1).append(", xn+1= ").append(x2)
                        .append("\nApproximate Error= ").append(BigDecimal.valueOf(Math.abs(x2 - x1))
                                .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue())
                        .append("\n");
                this.stringBuilder.append("\n------------------------------------------------\n\n");

                // updating the values of x0 and x1
                x0 = x1;
                x1 = x2;
                iterations++;
            } while (Math.abs((x1 - x0) / x1) * 100 >= conv_level && iterations <= itr - 1);

            double xr = BigDecimal.valueOf(x1)
                    .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue();
            this.stringBuilder.append("The Final root of the equation =").append(xr).append("\n")
                    .append("The number of iterations: ").append(iterations);

            this.finalAnswer.append("The value of the final root = ").append(xr).append("\n")
                    .append("The number of iterations: ").append(iterations);

            // For the plot
            this.values.add(xr);

        } else {
            this.stringBuilder.append("In this interval there is no root :))");
        }
    }

    public List<Double> getValues() {
        return this.values;
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
        // Steps
        return stringBuilder.toString();
    }
}
