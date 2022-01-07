package com.example.linearequations.methods_non_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;
import com.example.linearequations.checks.GetFunctionValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class FalsePosition implements Solve {

    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final long time;
    private final List<Double> xL;
    private final List<Double> xU;
    private double xR;

    public FalsePosition(double xl, double xu, double e, int itr) {
        this.xL = new ArrayList<>();
        this.xU = new ArrayList<>();
        this.stringBuilder = new StringBuilder("False Position Method\n\n");
        this.finalAnswer = new StringBuilder();
        long start = System.nanoTime();
        calculate(xl, xu, e, itr);
        time = System.nanoTime() - start;
    }

    private void calculate(double xl, double xu, double e, int itr) {

        GetFunctionValue f = new GetFunctionValue();
        int iteration = 1;
        int precision = HelloController.getPre();

        if (f.parseFun(xl) * f.parseFun(xu) >= 0) {
            this.stringBuilder.append("You have not assumed the range of a and b right :))");
            return;
        }

        // For the plot
        this.xL.add(xl);
        this.xU.add(xu);

        double xr = 0;
        double xrOld;
        boolean flag = true;
        while (flag && iteration < itr + 1) {
            xrOld = xr;
            //find root
            xr = BigDecimal.valueOf((xl - ((xu - xl) * f.parseFun(xl)) / (f.parseFun(xu) - f.parseFun(xl))))
                    .setScale(precision, RoundingMode.HALF_UP).doubleValue();

            //for printing the steps
            this.stringBuilder.append("Iteration #").append(iteration).append(" : ")
                    .append("Xr=").append(xr).append(", and f(Xr)= ").append(f.parseFun(xr)).append("\n");
            BigDecimal bigDecimal = BigDecimal.valueOf(Math.abs(((xr - xrOld) / xr) * 100));
            this.stringBuilder.append("The epsilon of iteration #").append(iteration).append(" = ")
                    .append(bigDecimal.setScale(precision, RoundingMode.UP).doubleValue()).append("\n");
            this.stringBuilder.append("\n----------------------------------------------------------------\n\n");
            iteration++;
            if (f.parseFun(xr) == 0) {
                break;
            } else if (f.parseFun(xr) * f.parseFun(xl) < 0) {
                xu = xr;
            } else {
                xl = xr;
            }

            // For the plot
            this.xL.add(xl);
            this.xU.add(xu);

            double eps = bigDecimal.setScale(precision, RoundingMode.HALF_UP).doubleValue();
            flag = eps > e;
        }
        // Final Answer -- xr
        this.xR = xr;  // For the plot
        this.stringBuilder.append("The value of final root is: ").append(xr);
        this.finalAnswer.append("The value of final root = ").append(xr);
    }

    // For the Plot
    public List<Double> getXL() {
        return this.xL;
    }

    public List<Double> getXU() {
        return this.xU;
    }

    public double getXR() {
        return this.xR;
    }

    @Override
    public long getTime() {
        // Time Taken in nano Seconds
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
