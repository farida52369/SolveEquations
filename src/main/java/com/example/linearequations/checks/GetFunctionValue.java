package com.example.linearequations.checks;

import com.example.linearequations.HelloController;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class GetFunctionValue {

    /*
     *
    public double func(String fun, double val) {
        String co_effStr = "";
        double v, d;
        int i;

        if (fun.contains("sin")) {
            co_effStr = fun.substring(0, fun.length() - 6);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.sin(val);
            return v * d;
        } else if (fun.contains("cos")) {
            co_effStr = fun.substring(0, fun.length() - 6);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.cos(val);
            return v * d;
        } else if (fun.contains("e^(x)")) {
            if (fun.contains("e^(x)")) {
                fun = "1e^(x)";
            } else if (fun.contains("-e^(x)")) {
                fun = "-1e^(x)";
            }
            co_effStr = fun.substring(0, fun.length() - 5);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.exp(val);
            return v * d;
        } else if (fun.contains("e^(-x)")) {
            if (fun.contains("e^(-x)")) {
                fun = "1e^(-x)";
            } else if (fun.contains("-e^(-x)")) {
                fun = "-1e^(-x)";
            }
            co_effStr = fun.substring(0, fun.length() - 6);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.exp(-1 * val);
            return v * d;
        } else if (!fun.contains("x")) {
            return Double.parseDouble(fun);
        } else if (fun.contains("^x")) {
            v = Double.parseDouble(fun.substring(0, fun.length() - 2));
            return Math.pow(v, val);
        } else if (fun.contains("^-x")) {
            v = Double.parseDouble(fun.substring(0, fun.length() - 3));
            return Math.pow(v, -1 * val);
        }

        for (i = 0; fun.charAt(i) != 'x'; i++) {
            if (fun.charAt(i) == ' ')
                continue;
            //System.out.println("mk");
            co_effStr += (fun.charAt(i));
        }
        if (co_effStr.equals("")) {
            co_effStr = "1";
        }
        if (co_effStr.equals("-")) {
            co_effStr = "-1";
        }
        double co_eff = Double.parseDouble(co_effStr);
        String powStr = "";
        for (i = i + 2; fun.contains("^") && i != fun.length(); i++) {
            powStr += fun.charAt(i);
        }
        if (powStr.equals("")) {
            powStr = "1";
        }
        double power = Double.parseDouble(powStr);
        return co_eff * Math.pow(val, power);
    }*/

    public double parseFun(double val) {
        Expression e = new ExpressionBuilder(HelloController.getExpression())
                .variables("x")
                .build()
                .setVariable("x", val);
        double ans = e.evaluate();

        return BigDecimal.valueOf(ans).round(new MathContext(HelloController.getPre(),
                RoundingMode.HALF_UP)).doubleValue();

        /*
         *
        int i = 0;
        String express = HelloController.getExpression();
        // String express = "x^3-0.165x^2+0.0003993";
        express = express.replaceAll("\\+", ",").replaceAll("-", ",-");
        express = express.replaceAll("\\(+,-", "(-").replaceAll("\\^+,", "^");
        String[] stSplit = express.split(",");

        while (i < stSplit.length) {
            // System.out.println(stSplit[i]);
            ans = (ans + func(stSplit[i], val));
            i++;
        }
        */
    }
}