package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    static final int FOUR = 4;
    static final int EIGHT = 8;

    /**
     * Prints the polynomial based on given cofficients
     * @param coeffs
     */
    void printPoly(Integer[] coeffs) {
        int n = coeffs.length;
        StringBuilder poly = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int coeff = coeffs[i];

            if (coeff != 0) {
                poly.append(coeff > 0 ? i > 0 ? "+" : "" : "")
                        .append(Math.abs(coeff) != 1 ? coeff + "" : "")
                        .append(coeff == -1 ? "-" : "")
                        .append("x^")
                        .append(n - i - 1);
            }
        }

        for (int i = 0, sz = poly.length(); i < sz; i++) {
            char ch = poly.charAt(i);
            if (ch == '+' || ch == '-') {
                poly.replace(i, i + 1, " " + ch + " ");
                i++;
            }
        }

        System.out.println(poly.charAt(0) != '+' ? poly : poly.substring(1));
    }

    /**
     * Parse the cofficients from command line arguments.
     * If everything is OK, pass the polynomial to checking functions.
     */
    void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {

            while (true) {
                System.out.println("Введите коэффициенты полинома через пробел: ");

                try {
                    String[] input = in.readLine().split(" ");
                    Integer[] intInput = new Integer[input.length];
                    for (int i = 0; i < input.length; i++) {
                        intInput[i] = Integer.parseInt(input[i]);
                    }
                    printPoly(intInput);
                    solveAndPrintRes(intInput);
                }

                catch (NumberFormatException e) {
                    System.out.println("Один или несколько аргументов не являются целочисленными");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check polynom for bijectivity and transitivity and print the results
     */
    void solveAndPrintRes(Integer[] coeffs) {
        System.out.println(isBijective(coeffs) ? "Полином биективен" : "Полином не биективен");
        System.out.println(isTransitive(coeffs) ? "Полином транзитивен\n" : "Полином не транзитивен\n");
    }

    /**
     * Calculate the polynomial value in given point
     * @param coeffs - polynomial coefficients
     * @param x - given point
     * @return polynomial value
     */
    long calc(Integer[] coeffs, long x) {
        long res = 0;
        int n = coeffs.length;
        for (int i = 0; i < n; i++) {
            res += coeffs[i] * Math.pow(x, n - i - 1);
        }
        return res;
    }

    /**
     * Check the polynomial for bijectivity.
     *
     * If every polynomial value in points 0..3 by modulus 4 is different,
     * then it is bijective.
     *
     * @param coeffs
     * @return is polynomial bijective or not
     */
    boolean isBijective(Integer[] coeffs) {
        Set<Long> computations = new HashSet<>();
        for (int i = 0; i < FOUR; i++) {
            computations.add(calc(coeffs, i) % FOUR);
        }
        return computations.size() == FOUR;
    }

    /**
     * Check the polynomial for transitivity.
     *
     * If all 8 polynomial values in point that equals to polynomial value from last step
     * (starting from point 0) by modulus 8 is different, then it is transitive.
     *
     * @param coeffs
     * @return is polynomial transitive or not
     */
    boolean isTransitive(Integer[] coeffs) {
        Set<Long> computations = new HashSet<>();
        long x1 , x2 = 0;
        for (int i = 0; i < EIGHT; i++) {
            x1 = calc(coeffs, x2);
            computations.add(x1);
            x2 = x1;
        }
        return computations.size() == EIGHT;
    }

    /**
     * Entry point of program
     * @param args - polynomial coefficients from command line
     */
    public static void main(String[] args) {
        new Solution().run();
    }
}
