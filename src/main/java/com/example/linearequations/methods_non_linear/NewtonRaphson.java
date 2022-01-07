package com.example.linearequations.methods_non_linear;

import com.example.linearequations.HelloController;
import com.example.linearequations.Solve;
import com.example.linearequations.checks.GetDerivativeValue;
import com.example.linearequations.checks.GetFunctionValue;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NewtonRaphson implements Solve {

    private final GetDerivativeValue der = new GetDerivativeValue();
    private final GetFunctionValue ob = new GetFunctionValue();
    private final StringBuilder stringBuilder;
    private final StringBuilder finalAnswer;
    private final long time;
    private double[] values;

    public NewtonRaphson(double x, int itr, double e) {
        this.stringBuilder = new StringBuilder("Newton Raphson Method:\n\n");
        this.finalAnswer = new StringBuilder();
        long start = System.nanoTime();
        newtonRaphson(x, itr, e);
        this.time = System.nanoTime() - start;
    }

    public double func(double x) {
        return ob.parseFun(x);
    }

    public double derivativeFunc(double x) {
        return der.derivativeVal(x);
    }

    // Function to find the root
    private void newtonRaphson(double x, int itr, double e) {
        ///new
        if( derivativeFunc(x)==0){
               this.stringBuilder.append("\nThe Function Diverge!\nTry better initial guess :))");
               return;
        }

        /////
        double h = BigDecimal.valueOf(func(x) / derivativeFunc(x))
                .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue();

        int i = 1;
        double derivative = 0, val = 0, startX = x;
        while ((Math.abs(h / x) * 100) >= e && i <= itr) {
            derivative = derivativeFunc(x);
            //new
            if (derivative == 0) {
                this.stringBuilder.append("\nThe Function Diverge!\nTry better initial guess :))");
                return;
            }
            //
            val = func(x);
            h = BigDecimal.valueOf(val / derivative)
                    .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue();
            x = BigDecimal.valueOf(x - h).setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue();
            this.stringBuilder.append("Iteration #").append(i).append(": X").append(i).append(" = ")
                    .append(x).append("\nAbsolute Error = ").append(BigDecimal.valueOf((Math.abs(h / x) * 100))
                            .setScale(HelloController.getPre(), RoundingMode.HALF_UP).doubleValue())
                    .append("\n\n-------------------------------\n\n");
            i++;
        }

        double c = val - derivative * x;  // C -- For the Line in tangent
        this.stringBuilder.append("The value of the final root is: ").append(x);
        this.finalAnswer.append("The value of the final root = ").append(x);

        // For the plot :)
        this.values = new double[4];
        this.values[0] = c;
        this.values[1] = derivative;
        this.values[2] = x;
        this.values[3] = startX;

    }

    public double[] getValues() {
        return values;
    }

    @Override
    public long getTime() {
        // Time
        return time;
    }

    @Override
    public String finalRoot() {
        // Final Root
        return this.finalAnswer.toString();
    }

    @Override
    public String toString() {
        // Steps
        return this.stringBuilder.toString();
    }

}
