package com.tsystems.javaschool.tasks.calculator;

import org.w3c.dom.ls.LSOutput;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    public String evaluate(String statement) {
        //parseString(statement);
        String sub = findParentheses(statement);
        System.out.println(sub);
//        String[] parts = statement.split("\\s");
//        for (int i = 0; i < parts.length; i++) {
//            System.out.println(parts[i]);
//        }
        return "";
    }

    private String findParentheses(String statement) {
        int index = statement.indexOf('(');
        int index2 = statement.indexOf(')');
        String sub = "";
        if (index != -1) {
            sub = statement.substring(index + 1, index2);
        }
        return sub;
    }

    private String findSign(String statement, char calculatingChar) {
        int indexMinus = statement.indexOf(calculatingChar);
        if (indexMinus != -1 && indexMinus != 0) {
            String[] parts = statement.split(String.valueOf("\\" + calculatingChar));
            String resultValue = parts[0];
            if (parts.length > 1) {
                for (int i = 1; i < parts.length; i++) {
                    resultValue = calculateStrings(resultValue, parts[i], calculatingChar);
                }
            }
            return String.valueOf(resultValue);
        }
        return statement;
    }

    private String calculateStrings(String str1, String str2, char calculatingChar) {
        int value1 = Integer.parseInt(str1);
        int value2 = Integer.parseInt(str2);
        int result = 0;
        switch (calculatingChar) {
            case ('+'):
                result = value1 + value2;
                break;
            case ('-'):
                result = value1 - value2;
                break;
            case ('*'):
                result = value1 * value2;
                break;
            case ('/'):
                if (value2 >= 0) {
                    result = value1 / value2;
                } else {
                    return null;
                }
                break;
        }
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String statement = "(1 + 38) * 4.5 - 1 / 2";
        statement = statement.replaceAll("\\s+","");

        String newString = calculator.findParentheses(statement);
        statement = statement.replace(newString, calculator.findSign(newString, '+'));
        System.out.println(statement);




        //calculator.evaluate("(1 + 38) * 4.5 - 1 / 2");
    }
}
