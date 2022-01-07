package com.example.linearequations.methods_non_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;
import com.example.linearequations.checks.GetFunctionValue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Bisection implements Solve {

    private final StringBuilder stringBuilder;
    private StringBuilder finalAnswer;
    private final long time;
    private final List<Double> xL;
    private final List<Double> xU;
    private double xR;

    public Bisection(double xl, double xu, double e, int itr) {
        this.xL = new ArrayList<>();
        this.xU = new ArrayList<>();
        this.stringBuilder = new StringBuilder("Bisection Method:\n\n");
        this.finalAnswer = new StringBuilder();
        long start = System.nanoTime();
        calculate(xl, xu, e, itr);
        this.time = System.nanoTime() - start;
    }

    private void calculate(double xl, double xu, double e, int itr) {
        GetFunctionValue func = new GetFunctionValue();
        int iteration = 1;
        int precision = HelloController.getPre();
        if (func.parseFun(xl) * func.parseFun(xu) >= 0) {
            this.stringBuilder.append("You have not assumed the range of a and b right :)");
            return;
        }

        this.xL.add(xl);
        this.xU.add(xu);
        double xr = 0, xrOld;
        boolean flag = true;
        while (flag && iteration < itr + 1) {
            xrOld = xr;

            //find root
            xr = BigDecimal.valueOf((xu + xl) / 2).setScale(precision, RoundingMode.HALF_UP).doubleValue();

            //for printing the steps
            this.stringBuilder.append("Iteration #").append(iteration).append(": ").append("xr=")
                    .append(xr).append(", func(xr)= ").append(func.parseFun(xr)).append("\n");
            final BigDecimal bigDecimal = BigDecimal.valueOf(Math.abs(((xr - xrOld) / xr) * 100));
            this.stringBuilder.append("The epsilon of iteration #").append(iteration).append(" = ")
                    .append(bigDecimal.setScale(precision, RoundingMode.UP).doubleValue());
            this.stringBuilder.append("\n\n------------------------------------------\n\n");

            iteration++;
            if (func.parseFun(xr) == 0) {
                break;
            } else if (func.parseFun(xr) * func.parseFun(xl) < 0) {
                xu = xr;
            } else {
                xl = xr;
            }
            this.xL.add(xl);
            this.xU.add(xu);
            double eps = bigDecimal.setScale(precision, RoundingMode.HALF_UP).doubleValue();
            flag = eps > e;

        }
        // Final Answer -- xr
        this.xR = xr;
        this.stringBuilder.append("After Bisection Method The final root = ").append(xr);
        this.finalAnswer = new StringBuilder("The Value of the final root = ").append(xr);

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
    public String toString() {
        // The Steps
        return stringBuilder.toString();
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
}