package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        // write your code here
        String statement = "2+3";
        //System.out.println(splitToElements(statement));
        System.out.println(splitToElements(statement));
        List<String> list = splitToElements(statement);
        list = convertToReversePolishNotation(list);
        System.out.println(list);
        String answer = count(list);
        System.out.println(answer);

    }
    private static List<String> splitToElements(String input){
        char[] elements = input.toCharArray();
        List<String> output = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            Character c = elements[i];
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else if (c != ' ') {

                if (number.length() != 0) {
                    output.add(number.toString());
                }
                output.add(c.toString());
                number.setLength(0);
            } else {
                if (number.length() != 0) {
                    output.add(number.toString());
                }
                number.setLength(0);
            }
        }
        output.add(number.toString());
        return output;
    }


    //Проверяет содержит ли строка число
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    //Переводит лист элементов в Обратную Польскую Нотацию
    private static List<String> convertToReversePolishNotation(List<String> elements) {

        List<String> operations = Arrays.asList("+","-","*","/", "um");
        List<String> output = new ArrayList<>();
        Stack<String> stack = new Stack<>();

        for (int i = 0; i < elements.size(); i++) {
            String element = elements.get(i);
            if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                stack.pop();
            } else if (operations.contains(element)) {
                while (!stack.empty() && operations.contains(stack.peek())) {
                    if (isPriorityEqualOrLess(element, stack.peek())) {
                        output.add(stack.pop());
                        continue;
                    }
                    break;
                }
                stack.push(element);
            } else {
                output.add(element);
            }
        }
        while (!stack.empty()){
            output.add(stack.pop());
        }
        return output;

    }

    //Сравнивает приоритетность операторов
    private static boolean isPriorityEqualOrLess(String b, String a) {
        int priorityA = 0;
        int priorityB = 0;
        if (b.equals("+") || b.equals("-")){
            priorityB = 1;
        }else {priorityB = 2;}
        if (a.equals("+") || a.equals("-")){
            priorityA = 1;
        } else {priorityA = 2;}
        int result = priorityA - priorityB;
        return result >= 0;
    }

    //Высчитывает результат выражения, записанного в Обратной Польской Нотации
    private static String count(List<String> statement) {
        List<String> operations = Arrays.asList("+","-","*","/");
        Stack<String> stack = new Stack<>();
        for (String element:
                statement) {
            if(!operations.contains(element)){
                stack.push(element);
            }else {
                Double d2 = Double.valueOf(stack.pop());
                Double d1 = Double.valueOf(stack.pop());
                Double result = null;
                switch (element) {
                    case "+":
                        result = d1 + d2;
                        break;
                    case "-":
                        result = d1 - d2;
                        break;
                    case "*":
                        result = d1 * d2;
                        break;
                    case "/":
                        result = d1 / d2;
                        break;
                }
                stack.push(String.valueOf(result));

            }
        }
        return stack.pop();
    }
}
