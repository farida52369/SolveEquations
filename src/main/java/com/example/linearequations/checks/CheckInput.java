package com.example.linearequations.checks;

public class CheckInput {

    private final int num;
    private final double[] b;
    private final double[][] co_eff;
    private final String[] equations;

    public CheckInput(String equations) {
        this.equations = equations.split(" ");
        this.num = this.equations.length;
        this.b = new double[this.num];
        this.co_eff = new double[this.num][this.num];
    }

    public boolean is_input_valid() {
        for (int i = 0; i < num; i++) {
            equations[i] = equations[i].replaceAll("\\+--", "+");
            equations[i] = equations[i].replaceAll("---", "-");
            equations[i] = equations[i].replaceAll("--", "+");
            equations[i] = equations[i].replaceAll("\\+-", "-");
            equations[i] = equations[i].replaceAll("-", "+-");

            if (equations[i].charAt(0) == '+') {
                equations[i] = equations[i].replaceFirst("\\+", "");
            }

            for (int j = 0; j < equations[i].length(); j++) {
                if (equations[i].charAt(j) != 'x' && equations[i].charAt(j) != '-'
                        && equations[i].charAt(j) != '+' && equations[i].charAt(j) != '='
                        && !Character.isDigit(equations[i].charAt(j))
                        && equations[i].charAt(j) != '.') {
                    return false;
                }
            }

            // [0-9]{1,13}(\.[0-9]*)?
            String[] split = equations[i].split("=");
            if (!split[1].equals("") && split[1].charAt(0) == '+') split[1] = split[1].substring(1);
            if (split.length != 2 || split[0].equals("") || split[1].equals("")
                    || !split[1].matches("-?\\d+(\\.\\d+)?")) {
                // System.out.println("HELLO");
                return false;
            } else {
                b[i] = Double.parseDouble(split[1]);
            }

            String[] temp = split[0].split("\\+");
            for (String y : temp) {
                if (y.equals("")) {
                    // System.out.println("HELLO 4");
                    return false;
                }

                String[] temp2 = y.split("x");

                try {
                    if (temp2[0].equals("")) temp2[0] = "1";
                    else if (temp2[0].equals("-")) temp2[0] = "-1";
                } catch (ArrayIndexOutOfBoundsException e) {
                    return false;
                }

                if (temp2.length != 2 || temp2[1].equals("")) {
                    // System.out.println("HELLO 5 when i: " + i);
                    return false;
                }

                // -?\d+ -> Check integer number
                if (!temp2[0].matches("-?\\d+(\\.\\d+)?")
                        || !temp2[1].matches("-?\\d+")) {
                    // System.out.println("HELLO 6 when i: " + i);
                    return false;
                } else {
                    try {
                        co_eff[i][Integer.parseInt(temp2[1]) - 1] = Double.parseDouble(temp2[0]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public double[][] getCo_eff() {
        return this.co_eff;
    }

    public double[] getB() {
        return this.b;
    }
}
