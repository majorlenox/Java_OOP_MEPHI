package com.company;

import java.util.ArrayList;

public class StrAddUtils {

    public static double stringToDouble(StringBuilder str) {    // Use only with correct double

        int t = 0;  // power of ten
        double num = 0;

        for (int j = str.length() - 1; j >= 1; j--) {
            if (str.charAt(j) == '.') {
                num /= Math.pow(10, t);
                t = 0;
                continue;
            }
            num += Math.pow(10, t) * ((double) str.charAt(j) - '0');
            t++;
        }
        if (str.charAt(0) == '-') {
            num = -num;
        } else {
            num += Math.pow(10, t) * ((double) str.charAt(0) - '0');
        }

        return num;
    }

    public static ArrayList<Double> first2NumbersInStr(String line) {
        ArrayList<Double> numbers = new ArrayList<Double>();
        boolean isNum = false;
        boolean isPoint = false;
        StringBuilder number = new StringBuilder();

        line += "ee";  // End of the Line

        for (int i = 0; i < line.length() - 1; i++) {

            // Start of Positive Number
            if ((!isNum) && ('0' <= line.charAt(i)) && ('9' >= line.charAt(i))) {
                isNum = true;
                isPoint = false;
                number.append(line.charAt(i));
                continue;
            }
            //

            // Start of Negative Number
            if ((!isNum) && (line.charAt(i) == '-') && ('0' <= line.charAt(i + 1)) && ('9' >= line.charAt(i + 1))) {
                isNum = true;
                isPoint = false;
                number.setLength(0);
                number.append('-');
                continue;
            }
            //

            // Continuation of the number
            if ((isNum) && ('0' <= line.charAt(i)) && ('9' >= line.charAt(i))) {
                number.append(line.charAt(i));
                continue;
            }
            //

            // Point was detected
            if ((isNum) && ('.' == line.charAt(i)) && (!isPoint)) {
                isPoint = true;
                number.append('.');
                continue;
            }
            //

            // End of the Number, add to other numbers

            if ((numbers.size() == 0) && ((line.charAt(i) != ' ')||(line.charAt(i+1) == ' ')) && (line.length() - 2 != i)) {    // after first number needs to be ' ' (or end of line)
                return numbers;
            }

            if (isNum) {

                double d_num = stringToDouble(number);

                numbers.add(d_num);

                if (numbers.size() == 2) {
                    return numbers;
                }

                isNum = false;
                isPoint = false;
                number.setLength(0);

                //

                //Checking for the beginning of a negative number

                if ((line.charAt(i) == '-') && ('0' <= line.charAt(i + 1)) && ('9' >= line.charAt(i + 1))) {
                    isNum = true;
                    number.setLength(0);
                    number.append('-');
                }

                //

            } else {

                if ((line.charAt(i) != ' ')) {
                    numbers.clear();
                    return numbers;
                }

            } // only ' ' after numbers

        }

        return numbers;
    }

}
