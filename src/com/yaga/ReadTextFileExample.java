package com.yaga;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadTextFileExample {
    private static final Logger LOGGER = Logger.getLogger(ReadTextFileExample.class.getName());

    public static void main(String[] args) {
        String fileName = "example.txt"; // Имя файла для чтения

        try {
            processFile(fileName);
        } catch (IOException e) {
            LOGGER.severe("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static void processFile(String fileName) throws IOException {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;

            // Читаем файл построчно
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    processLine(line);
                } catch (ArithmeticException e) {
                    LOGGER.warning("Ошибка в строке: " + line + ". " + e.getMessage());
                }
            }
        }
    }

    private static void processLine(String line) {
        Pattern pattern = Pattern.compile("(-?\\d+)\\s*([-+*/])\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(line);

        // Проверяем, есть ли совпадение с регулярным выражением
        if (matcher.find()) {
            int operand1 = Integer.parseInt(matcher.group(1));
            int operand2 = Integer.parseInt(matcher.group(3));
            String operator = matcher.group(2);

            // Выполняем арифметическую операцию
            int result = 0;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        LOGGER.warning("Деление на ноль в строке: " + line);
                        return;
                    }
                    break;
                default:
                    LOGGER.warning("Неизвестный оператор в строке: " + line);
                    return;
            }

            // Выводим операцию и результат
            System.out.println("Операция: " + operand1 + " " + operator + " " + operand2);
            System.out.println("Результат операции: " + result);
        } else {
            // Если строка не является арифметической операцией, просто выводим ее
            System.out.println(line);
        }
    }
}
