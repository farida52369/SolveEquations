package com.example.linearequations.checks;

import com.example.linearequations.HelloController;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class GetDerivativeValue {
    // Java program to find value of derivative of
    // a polynomial
    private double derivativeTerm(String pTerm, double val) {

        // Get coefficient
        String co_effStr = "";
        int i;
        double v, d;

        if (pTerm.contains("sin")) {
            co_effStr = pTerm.substring(0, pTerm.length() - 6);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.cos(val);
            return v * d;
        } else if (pTerm.contains("cos")) {
            co_effStr = pTerm.substring(0, pTerm.length() - 6);
            if (co_effStr.equals("")) v = 1;
            else v = Double.parseDouble(co_effStr);
            d = Math.sin(val) * -1;
            return v * d;
        } else if (pTerm.contains("e^(x)")) {
            if (pTerm.contains("e^(x)")) {
                pTerm = "1e^(x)";
            } else if (pTerm.contains("-e^(x)")) {
                pTerm = "-1e^(x)";
            }
            v = Double.parseDouble(pTerm.substring(0, pTerm.length() - 5));
            d = Math.exp(val);
            return v * d;
        } else if (pTerm.contains("e^(-x)")) {
            if (pTerm.contains("e^(-x)")) {
                pTerm = "1e^(-x)";
            } else if (pTerm.contains("-e^(-x)")) {
                pTerm = "-1e^(-x)";
            }
            v = -1 * Double.parseDouble(pTerm.substring(0, pTerm.length() - 6));
            d = Math.exp(-1 * val);
            return v * d;
        } else if (!pTerm.contains("x")) {
            return 0.0;
        } else if (pTerm.contains("^x")) {
            v = Double.parseDouble(pTerm.substring(0, pTerm.length() - 2));
            return (Math.log(v) / Math.log(Math.exp(1))) * Math.pow(v, val);
        } else if (pTerm.contains("^-x")) {
            v = Double.parseDouble(pTerm.substring(0, pTerm.length() - 3));
            return -1 * (Math.log(v) / Math.log(Math.exp(1))) * Math.pow(v, val);
        }
        for (i = 0; pTerm.charAt(i) != 'x'; i++) {
            if (pTerm.charAt(i) == ' ')
                continue;
            co_effStr += (pTerm.charAt(i));
        }
        if (co_effStr.equals("")) {
            co_effStr = "1";
        }
        if (co_effStr.equals("-")) {
            co_effStr = "-1";
        }
        double co_eff = Double.parseDouble(co_effStr);
        String powStr = "";
        for (i = i + 2; pTerm.contains("^") && i != pTerm.length(); i++) {
            powStr += pTerm.charAt(i);
        }
        if (powStr.equals("")) {
            powStr = "1";
        }
        double power = Double.parseDouble(powStr);
        return co_eff * power * Math.pow(val, power - 1);
    }


    public double derivativeVal(double val) {
        double ans = 0;
        int i = 0;

        String express = HelloController.getExpression();
        express = express.replaceAll("\\+", ",").replaceAll("-", ",-");
        express = express.replaceAll("\\(+,-", "(-").replaceAll("^,", "^");
        String[] stSplit = express.split(",");

        while (i < stSplit.length) {
            ans = (ans + derivativeTerm(stSplit[i], val));
            i++;
        }
        return BigDecimal.valueOf(ans).round(new MathContext(HelloController.getPre(),
                RoundingMode.HALF_UP)).doubleValue();
    }
}
