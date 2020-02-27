/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.util.Scanner;

/**
 *
 * @author minna
 */
public class Utils {
    static Scanner scanner = new Scanner(System.in);
    
        public static void line() {
        System.out.println("-------------------------------------------------------------------");
    }
        
        public static void stars() {
            System.out.println("******************************************************************");
        }

    public static int askForInt(String question) {
        int answer = 0;
        boolean showQuestion = true;
        while (showQuestion) {
            System.out.print(question);
            try {
                answer = scanner.nextInt();
                scanner.nextLine();
                showQuestion = false;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Input should be number, try again");
            }
        }
        return answer;
    }

   
    
    public static String askForString(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }

    static Long askForLong(String question) {
        long answer = 0L;
        boolean showQuestion = true;
        while (showQuestion) {
            System.out.print(question);
            try {
                answer = scanner.nextLong();
                scanner.nextLine();
                showQuestion = false;
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Input should be a number, try again");
            }
        }
        return answer;    
    }
    
     public static String fixLengthString(String start, int length) {
        if (start.length() >= length) {
            return start.substring(0, length);
        } else {
            while (start.length() < length) {
                start += " ";
            }
            return start;
        }
    }

    public static String fixLengthString(int start, int length) {
        String startString = String.valueOf(start);
        return fixLengthString(startString, length);
    }
    
    
    
    
}
