package com.company;

import java.util.LinkedList;

public class StrAdd {

    /**
     * Regardless of which characters are between the digits, returns the array of numbers
     */
    public static LinkedList<Double> NumbersInStr(String line){
        LinkedList<Double> numbers = new LinkedList<Double>();
        boolean IsNum = false;
        boolean IsPoint = false;
        String number = "";

        line+="ee";  // End of the Line

        for (int i=0; i < line.length() - 1; i++){

            // Start of Positive Number
            if ((!IsNum)&&('0' <= line.charAt(i))&&('9' >= line.charAt(i))){
                IsNum = true;
                IsPoint = false;
                number = String.valueOf(line.charAt(i));
                continue;
            }
            //

            // Start of Negative Number
            if ((!IsNum)&&(line.charAt(i) == '-') && ('0' <= line.charAt(i+1)) && ('9' >= line.charAt(i+1))){
                IsNum = true;
                IsPoint = false;
                number = "-";
                continue;
            }
            //

            // Continuation of the number
            if ((IsNum)&&('0' <= line.charAt(i)) && ('9' >= line.charAt(i))){
                number += String.valueOf(line.charAt(i));
                continue;
            }
            //

            // Point was detected
            if ((IsNum)&&('.' == line.charAt(i))&&(!IsPoint)){
                IsPoint = true;
                number += ".";
                continue;
            }
            //

            // End of the Number, add to other numbers

            if (IsNum) {

                double d_num = StringToDouble(number);

                numbers.add(d_num);

                IsNum = false;
                IsPoint = false;
                number = "";

                //

                //Checking for the beginning of a negative number

                if ((line.charAt(i) == '-') && ('0' <= line.charAt(i + 1)) && ('9' >= line.charAt(i + 1))) {
                    IsNum = true;
                    number = "-";
                }

                //

            }
        }

        return numbers;
    }

    public static double StringToDouble(String str){    // Use only with correct double

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

    // Special version NumbersInStr for LAB1, too heavy for task
    public static LinkedList<Double> First2NumbersInStr(String line){
        LinkedList<Double> numbers = new LinkedList<Double>();
        boolean IsNum = false;
        boolean IsPoint = false;
        String number = "";

        line+="ee";  // End of the Line

        for (int i=0; i < line.length() - 1; i++){

            // Start of Positive Number
            if ((!IsNum)&&('0' <= line.charAt(i))&&('9' >= line.charAt(i))){
                IsNum = true;
                IsPoint = false;
                number = String.valueOf(line.charAt(i));
                continue;
            }
            //

            // Start of Negative Number
            if ((!IsNum)&&(line.charAt(i) == '-') && ('0' <= line.charAt(i+1)) && ('9' >= line.charAt(i+1))){
                IsNum = true;
                IsPoint = false;
                number = "-";
                continue;
            }
            //

            // Continuation of the number
            if ((IsNum)&&('0' <= line.charAt(i)) && ('9' >= line.charAt(i))){
                number += String.valueOf(line.charAt(i));
                continue;
            }
            //

            // Point was detected
            if ((IsNum)&&('.' == line.charAt(i))&&(!IsPoint)){
                IsPoint = true;
                number += ".";
                continue;
            }
            //

            // End of the Number, add to other numbers

            if ((numbers.size()==0)&&(line.charAt(i)!=' ')&&(line.length() - 2 != i)){    // after first number needs to be ' ' (or end of line)
                return numbers;
            }

            if (IsNum) {

                double d_num = StringToDouble(number);

                numbers.add(d_num);

                if (numbers.size() == 2){return numbers;}

                IsNum = false;
                IsPoint = false;
                number = "";

                //

                //Checking for the beginning of a negative number

                if ((line.charAt(i) == '-') && ('0' <= line.charAt(i + 1)) && ('9' >= line.charAt(i + 1))) {
                    IsNum = true;
                    number = "-";
                }

                //

            }else{ if (line.charAt(i) != ' '){numbers.clear(); return numbers;} } // only ' ' after numbers

        }

        return numbers;
    }

}
