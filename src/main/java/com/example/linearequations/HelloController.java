package com.example.linearequations;

import com.example.linearequations.GUI.DrawUI;
import com.example.linearequations.GUI.Plot;
import com.example.linearequations.checks.CheckConsistency;
import com.example.linearequations.checks.CheckInput;
import com.example.linearequations.methods_linear.*;
import com.example.linearequations.methods_non_linear.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private AnchorPane myPane;

    @FXML
    private Button calculateButton;

    @FXML
    private TextField precision;

    @FXML
    private TextArea myInput;

    @FXML
    private TextField time;

    @FXML
    private Label finalOutput;

    // ChoiceBox For The Methods
    String[] methods = {"Gauss Elimination", "Gauss Jordan", "LU Decomposition",
            "Gauss Seidil", "Jacobi Iteration", "Bisection", "False-Position", "Fixed point",
            "Newton-Raphson", "Secant Method"};

    // For LU Decomposition
    String[] formsOfLU = {"Doolittle Form", "Crout Form", "Cholesky Form"};
    Label labelLU = DrawUI.addLabel("Choose form of LU: ", 20, 50, 200);
    ChoiceBox<String> choiceBoxLU = DrawUI.addChoiceBox(formsOfLU, 300, 200, 136, 24);

    // For Gauss Seidil and Jacobi Iteration
    Label labelGuess = DrawUI.addLabel("Initial guess: ", 20, 20, 180);
    Label labelWarn1 = DrawUI.addLabel("* values separated by comma\ndefault:[0] \"0,0,0,0\"", 11, 40, 215);
    TextField initialGuess = DrawUI.addTextField(155, 178);

    Label numOfIterationLabel = DrawUI.addLabel("# of Iteration:", 20, 300, 180);
    Label labelWarn2 = DrawUI.addLabel("* value of # Iteration\ndefault:[# iteration=10]",
            11, 320, 215);
    TextField numOfIteration = DrawUI.addTextField(450, 175);

    Label epsilonLabel = DrawUI.addLabel("\u03B5:", 20, 580, 170);
    Label labelWarn3 = DrawUI.addLabel("* value of Error\ndefault:[eps=0.00001]",
            11, 600, 215);
    TextField epsilon = DrawUI.addTextField(620, 175);

    // input valid
    Label inputWarn4 = DrawUI.addLabel("* put valid input :)", 16, 330, 535);

    // method validation
    Label inputWarn5 = DrawUI.addLabel("* Choose Method :)", 18, 330, 535);
    private LineChart<Number, Number> LineChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().addAll(methods);
        // :: Method reference Operator
        myChoiceBox.setOnAction(this::getMethod);

        time.setEditable(false);
        precision.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    precision.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        epsilon.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*(\\.\\d*)?")) {
                    epsilon.setText(oldValue);
                }
            }
        });

        numOfIteration.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    numOfIteration.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    public void getMethod(ActionEvent event) {
        switch (myChoiceBox.getValue()) {
            case "LU Decomposition":
                myPane.getChildren().removeAll(labelLU, choiceBoxLU, labelGuess,
                        initialGuess, labelWarn1, numOfIteration, labelWarn2, numOfIterationLabel,
                        epsilonLabel, epsilon, labelWarn3);
                myPane.getChildren().addAll(labelLU, choiceBoxLU);
                break;
            case "Gauss Seidil":
            case "Jacobi Iteration":
                initialGuess.setText("0,0,0,0");
                decoration();
                break;

                /*
                 * Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");
                UnaryOperator<TextFormatter.Change> filter = (c) ->{
                    if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                        return c;
                    } else {
                        return null;
                    }
                };
                TextFormatter<Double> formatter = new TextFormatter<Double>(filter);
                epsilon.setTextFormatter(formatter);
                */
            case "Bisection":
            case "False-Position":
            case "Secant Method":
                initialGuess.setText("0,0");
                decoration();
                break;
            case "Fixed point":
            case "Newton-Raphson":
                initialGuess.setText("0");
                decoration();
                break;
            default:
                myPane.getChildren().removeAll(labelLU, choiceBoxLU, labelGuess,
                        initialGuess, labelWarn1, numOfIteration, labelWarn2, numOfIterationLabel,
                        epsilonLabel, epsilon, labelWarn3);
                break;
        }

        System.out.println(myChoiceBox.getValue());
    }

    private void decoration() {
        labelWarn1.setTextFill(Color.RED);
        labelWarn2.setTextFill(Color.RED);
        labelWarn3.setTextFill(Color.RED);

        initialGuess.setPrefWidth(120);
        numOfIteration.setPrefWidth(90);
        numOfIteration.setText("10");
        epsilon.setPrefWidth(150);
        epsilon.setText("0.00001");
        epsilonLabel.setFont(new Font("System", 35));
        myPane.getChildren().removeAll(labelLU, choiceBoxLU, labelGuess,
                initialGuess, labelWarn1, numOfIteration, labelWarn2, numOfIterationLabel,
                epsilonLabel, epsilon, labelWarn3);
        myPane.getChildren().addAll(labelGuess, initialGuess, labelWarn1,
                numOfIteration, labelWarn2, numOfIterationLabel,
                epsilonLabel, epsilon, labelWarn3);
    }

    private static String expression;
    private static int pre;

    public static String getExpression() {
        // The expression for non-linear equations
        return expression;
    }

    public void setExpression(String expression) {
        // The expression for non-linear equations
        HelloController.expression = expression;
    }

    public static int getPre() {
        // Precision --
        return pre;
    }

    public void setPre(int pre) {
        // Precision --
        HelloController.pre = pre;
    }

    public void calculate(ActionEvent event) throws IOException {
        // Set Text Final Output on new Calculation
        finalOutput.setText("");
        // Remove Warning
        myPane.getChildren().removeAll(inputWarn5, inputWarn4);

        String methodUsed = myChoiceBox.getValue();
        System.out.println("My Choice: " + methodUsed);
        if (methodUsed == null) {
            inputWarn5.setTextFill(Color.RED);
            myPane.getChildren().add(inputWarn5);
            return;
        }
        Solve solution = null;
        if (methodUsed.equals("Gauss Elimination") || methodUsed.equals("Gauss Jordan")
                || methodUsed.equals("LU Decomposition") || methodUsed.equals("Gauss Seidil")
                || methodUsed.equals("Jacobi Iteration")) {
            CheckInput checkInput = new CheckInput(myInput.getText().replaceAll("\n", " "));
            CheckConsistency checkConsistency = new CheckConsistency();
            if (checkInput.is_input_valid()) {

                myPane.getChildren().remove(inputWarn4);
                double[][] co_eff = checkInput.getCo_eff();
                double[] b = checkInput.getB();
                int precision_val = Integer.parseInt(precision.getText());
                this.setPre(precision_val);

                // System.out.println("Precision: " + precision_val);
                if (checkConsistency.check(co_eff, b) == 0) {
                    switch (methodUsed) {
                        case "Gauss Jordan":
                            solution = new GaussJordan(co_eff, b, precision_val);
                            break;
                        case "Gauss Seidil":
                        case "Jacobi Iteration":
                            String[] val = initialGuess.getText().split(",");
                            double[] initial_guess = new double[b.length];
                            for (int i = 0; i < b.length; i++)
                                initial_guess[i] = Double.parseDouble(val[i]);
                            int num_of_iteration = Integer.parseInt(numOfIteration.getText());
                            double eps = Double.parseDouble(epsilon.getText());
                            if (myChoiceBox.getValue().equals("Jacobi Iteration")) {
                                solution = new JacobiIteration(co_eff, b, initial_guess,
                                        num_of_iteration, eps, precision_val);
                            } else if (myChoiceBox.getValue().equals("Gauss Seidil")) {
                                solution = new GaussSeidalIteration(co_eff, b, initial_guess,
                                        num_of_iteration, eps, precision_val);
                            }
                            break;
                        case "LU Decomposition":
                            String LUForm = (choiceBoxLU.getValue() == null) ? "Doolittle Form" : choiceBoxLU.getValue();
                            solution = new LU(co_eff, b, precision_val, LUForm);
                            break;
                        case "Gauss Elimination":
                            solution = new GaussElimination(co_eff, b, precision_val);
                            break;
                    }
                    assert solution != null;
                    displayOutputScene(solution.toString());
                    time.setText(String.valueOf(solution.getTime()));

                    // Final Answer Solution Window
                    displayFinalAnswer(solution.finalRoot());

                } else if (checkConsistency.check(co_eff, b) == 1) {
                    displayOutputScene("Infinite Number Of Solutions!");
                } else {
                    displayOutputScene("No Solution at all!");
                }
            } else {
                inputWarn4.setTextFill(Color.RED);
                myPane.getChildren().remove(inputWarn4);
                myPane.getChildren().add(inputWarn4);
            }
        } else if (methodUsed.equals("Bisection") || methodUsed.equals("False-Position")
                || methodUsed.equals("Fixed point") || methodUsed.equals("Newton-Raphson")
                || methodUsed.equals("Secant Method")) {

            String[] equation = myInput.getText().split("\n");

            String[] equ = equation[0].split("=");
            if (equ.length == 2 && (equ[1].equals("0") || equ[1].equals("0.0"))) {
                try {
                    this.setExpression(equ[0]);
                    Expression expression = new ExpressionBuilder(getExpression())
                            .variable("x")
                            .build();
                    int precision_val = Integer.parseInt(precision.getText());
                    this.setPre(precision_val);
                    String[] val = initialGuess.getText().split(",");

                    double[] initial_guess = new double[2];
                    for (int i = 0; i < 2; i++)
                        if (val.length > i)
                            initial_guess[i] = Double.parseDouble(val[i]);
                    int num_of_iteration = Integer.parseInt(numOfIteration.getText());
                    double eps = Double.parseDouble(epsilon.getText());

                    Plot plot = new Plot();
                    switch (methodUsed) {
                        case "Bisection":
                            Bisection bisection = new Bisection(initial_guess[0], initial_guess[1], eps, num_of_iteration);
                            solution = bisection;
                            LineChart = plot.draw(bisection.getXL(), bisection.getXU(), bisection.getXR());
                            break;
                        case "False-Position":
                            FalsePosition falsePosition = new FalsePosition(initial_guess[0],
                                    initial_guess[1], eps, num_of_iteration);
                            solution = falsePosition;
                            LineChart = plot.draw(falsePosition.getXL(), falsePosition.getXU(), falsePosition.getXR());
                            break;
                        case "Fixed point":
                            this.setExpression(myInput.getText() + "+x");
                            FixedPoint fixedPoint = new FixedPoint(initial_guess[0], num_of_iteration, eps);
                            solution = fixedPoint;
                            LineChart = plot.draw(fixedPoint.get_xR(), fixedPoint.getStart());
                            break;
                        case "Newton-Raphson":
                            NewtonRaphson newtonRaphson = new NewtonRaphson(initial_guess[0], num_of_iteration, eps);
                            solution = newtonRaphson;
                            LineChart = plot.draw(newtonRaphson.getValues());
                            break;
                        case "Secant Method":
                            Secant secant = new Secant(initial_guess[0], initial_guess[1], eps, num_of_iteration);
                            solution = secant;
                            LineChart = plot.draw(secant.getValues());
                            break;
                    }
                    displayThePlot();
                    displayOutputScene(solution.toString());
                    time.setText(String.valueOf(solution.getTime()));
                    finalOutput.setText(solution.finalRoot());
                } catch (IllegalArgumentException error) {
                    System.out.println("Invalid Input: " + error);
                    inputWarn4.setTextFill(Color.RED);
                    myPane.getChildren().remove(inputWarn4);
                    myPane.getChildren().add(inputWarn4);
                }
            } else {
                System.out.println("Invalid Input.");
                inputWarn4.setTextFill(Color.RED);
                myPane.getChildren().remove(inputWarn4);
                myPane.getChildren().add(inputWarn4);
            }
        }
    }

    private void displayThePlot() {
        // The Plot
        Stage stage = new Stage();
        stage.setTitle("Graph");
        Scene scene = new Scene(LineChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void displayFinalAnswer(String solution) {
        // The Final Answer
        Stage stage = new Stage();
        stage.setTitle("Final Answer");

        TextArea outputLabel = new TextArea();
        outputLabel.setMinWidth(100);
        outputLabel.setMinHeight(100);
        outputLabel.setLayoutX(10);
        outputLabel.setLayoutY(10);
        outputLabel.setFont(new Font("System", 17));
        outputLabel.setText(solution);
        outputLabel.setEditable(false);

        Scene scene = new Scene(outputLabel, 400, 350);
        stage.setScene(scene);
        stage.show();
    }

    private void displayOutputScene(String solution) throws IOException {
        // Output
        // For root
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("output.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = new Stage();
        stage.setTitle("Output of Linear Equations Solver!");

        // For Controller2
        OutputController outputController = loader.getController();
        outputController.display(solution);

        stage.setScene(scene);
        stage.show();
    }

}
