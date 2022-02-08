package com.tsystems.javaschool.tasks.calculator;

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
        if (statement == null || statement.equals("") || statement.contains(",")) {
            return null;
        }

        statement = statement.replaceAll("\\s+", "");
        if (checkIfStatementCorrect(statement) && checkIfAllOpenParenthesesHasPair(statement)) {
            statement = calculateInParentheses(statement);
            statement = calculateUntilSignExists(statement);
            if (statement == null) {
                return null;
            }
            statement = cleanDecimal(statement, 4);
        } else {
            return null;
        }

        return statement;
    }

    private boolean checkIfStatementCorrect(String statement) {
        String comparableChar = String.valueOf(statement.charAt(0));
        for (int i = 1; i < statement.length(); i++) {
            String currentChar = String.valueOf(statement.charAt(i));
            String currentSequence = comparableChar + currentChar;
            if (currentSequence.matches("[^0-9)(]{2,}")) {
                return false;
            }
            comparableChar = currentChar;
        }
        return true;
    }

    private boolean checkIfAllOpenParenthesesHasPair(String statement) {
        boolean result = true;
        while (statement.contains(")") || statement.contains("(")) {
            result = checkIfNoOpenParentheses(statement);
            statement = removeParentheses(statement);
        }
        return result;
    }


    private boolean checkIfNoOpenParentheses(String statement) {
        int index = statement.indexOf('(');
        int index2 = statement.indexOf(')');
        if (index != -1 && index2 != -1) {
            return index < index2;
        }
        return false;
    }

    private String removeParentheses(String statement) {
        statement = statement.replaceFirst("\\)", "@");
        return statement.replaceFirst("\\(", "@");
    }

    private String calculateInParentheses(String statement) {
        int index = statement.indexOf('(');
        int index2 = statement.indexOf(')');
        if (index != -1) {
            String sub = statement.substring(index + 1, index2);
            String resultString = calculateUntilSignExists(sub);
            if (resultString != null) {
                return statement.replace('(' + sub + ')', resultString);
            } else {
                return null;
            }
        }
        return statement;
    }

    private String calculateUntilSignExists(String statement) {
        char[] signsChars = new char[]{'/', '*', '-', '+'};
        for (char calculatingChar : signsChars) {
            while (statement.indexOf(calculatingChar, 1) != -1) {
                statement = findCalculatingSign(statement, calculatingChar);
                if (statement == null) {
                    return null;
                }
            }
        }
        return statement;
    }

    private String findCalculatingSign(String statement, char calculatingChar) {
        String[] parts;
        String format = "[^0-9.]";
        String leftPart = "";
        if (statement.indexOf('-') == 0) {
            parts = statement.substring(1).split(String.valueOf("\\" + calculatingChar), 2);
            leftPart = "-" + parts[0];
        } else {
            parts = statement.split(String.valueOf("\\" + calculatingChar), 2);
            leftPart = parts[0];
            String[] leftParts = leftPart.split(format);
            leftPart = leftParts[leftParts.length - 1];
        }

        String rightPart = parts[1];
        String[] rightParts = rightPart.split(format);
        String rightSubPart = "";
        if (rightPart.indexOf('-') == 0) {
            rightSubPart = "-" + rightParts[1];
        } else {
            rightSubPart = rightParts[0];
        }

        String resultString = calculateStrings(leftPart, rightSubPart, calculatingChar);
        if (resultString != null) {
            return statement.replace(leftPart + calculatingChar + rightSubPart, resultString);
        }
        return null;
    }


    private String calculateStrings(String str1, String str2, char calculatingChar) {
        double value1 = Double.parseDouble(str1);
        double value2 = Double.parseDouble(str2);
        double result = 0;
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
                if (value2 != 0) {
                    result = value1 / (double) value2;
                } else {
                    return null;
                }
                break;
        }
        return String.valueOf(result);
    }

    private String cleanDecimal(String statement, int roundNumberAfterDot) {

        String[] parts = statement.split("[.]");
        String leftPart = parts[0];
        String rightPart = parts[1];
        if (rightPart.equals("0")) {
            return leftPart;
        }
        if (rightPart.length() > roundNumberAfterDot) {
            rightPart = rightPart.substring(0, 3);
        }

        return leftPart + "." + rightPart;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        System.out.println(calculator.evaluate("-10-5-3"));

    }
}
