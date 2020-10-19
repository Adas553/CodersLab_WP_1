package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class TaskManager {
    static final String[] selectOptionTab = {"add", "remove", "list", "exit"};
    static String pathToTask = "./tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        String pathToTasks = "./tasks.csv";
        selectOption(selectOptionTab);
        readFile(pathToTasks);

        Scanner scan = new Scanner(System.in);


        while (scan.hasNextLine()) {
            String input = scan.nextLine();

            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks);
                    break;
                case "list":
                    listTask();
                    break;
                case "exit":
                    exitTask(pathToTasks);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            selectOption(selectOptionTab);
        }
    }

    public static void selectOption(String[] tab1) {
        System.out.println(ConsoleColors.BLUE +"Please select an option: " + ConsoleColors.RESET);
        for (String option : tab1) {
            System.out.println(option);
        }
    }


    public static String[][] readFile(String pathToFile) {
        File file = new File(pathToFile);
        Path path = Paths.get(pathToFile);

        if (!Files.exists(path)) {
            System.out.println("The file does not exist!!!");
        }

        List<String> elements = new ArrayList<>();
//        String[][] arrayTask;

        try {
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                elements.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

       tasks = new String[elements.size()][elements.get(0).split(",").length];

        for (int i = 0; i < elements.size(); i++) {
            String[] arrayToSplit = elements.get(i).split(",");
            for (int j = 0; j < arrayToSplit.length; j++) {
                tasks[i][j] = arrayToSplit[j];
            }
        }
        return tasks;
    }
    public static void addTask() {
        Scanner scan = new Scanner(System.in);
        String line;
        String line2;
        String line3;
        StringBuilder sb = new StringBuilder();


        do {
            System.out.println("Please add task to description (to finish please type 'quit)': ");
            line = scan.nextLine();
            if (line.equalsIgnoreCase("quit")) {
                break;
            }
            sb.append(line);
            System.out.println("Please add task due date: ");
            line2 = scan.nextLine();
            sb.append("~" + line2);
            System.out.println("Is your task is important: true/false");
            line3 = scan.nextLine();
            sb.append("~" + line3);

            String str = sb.toString();
            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length-1] = new String[str.split("~").length];
            String[] tab2 = str.split("~");

            for (int i = 0; i < tab2.length; i++) {
                tasks[tasks.length - 1][i] = tab2[i] + " ";
            }
            sb = sb.delete(0, sb.length());

        } while (!line.equalsIgnoreCase("quit"));

        

    }
    public static void removeTask(String[][] tab) {
        String[][] newTab;
        Scanner scan = new Scanner(System.in);
        String prompt = "Please select number to remove. " + "Max value = " + String.valueOf(tasks.length) + ": ";
        int number = getInt(prompt, scan) - 1;

//        if (number < 0 || number > tasks.length) {
//            throw new IndexOutOfBoundsException("Number has to be greater than 0 and smaller than length of the table.");
//        }
        try {
            tasks = ArrayUtils.remove(tasks, number);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number has to be greater than 0 and smaller than the length of the table." + "Length: " + tasks.length);
        }


    }
//    public static void listTask(String pathToFile) {
//        File file = new File(pathToFile);
//        StringBuilder sb = new StringBuilder();
//        int  i = 0;
//
//
//        try {
//            Scanner scan = new Scanner(file);
//            while (scan.hasNextLine()) {
//                i++;
//                sb.append(i + ". " + scan.nextLine() + "\n");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.print(sb.toString());
//    }

    public static void listTask() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + 1 + ". ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j]);
            }
            System.out.println();
        }
    }
    public static void exitTask(String pathToTask) {
        try (FileWriter fileWriter = new FileWriter(pathToTask, false)) {

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    try {
                        if (j == tasks[i].length - 1) {
                            fileWriter.write(tasks[i][j]);
                        }
                        else {
                            fileWriter.write(tasks[i][j] + ", ");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    fileWriter.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(ConsoleColors.RED +"Bye bye." + ConsoleColors.RESET);

        System.exit(0);
    }

    public static int getInt(String prompt, Scanner scan) {
        System.out.print(prompt);
        while(!scan.hasNextInt()) {
            scan.next();
            System.out.print("It has to be a number, please try again: ");
        }
        int number = scan.nextInt();

        return number;
    }
}
