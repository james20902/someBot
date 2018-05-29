package jPham.someBot.bot;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Constants {

    public static Scanner FILE;

    public static String readToken(){
        try {
            FILE = new Scanner(new File("token.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return FILE.nextLine();
    }

    public static String prefix = "!";
    public static ArrayList<String> filter = new ArrayList<>();

    public static void updateFilter(){
        String line;

        try {
            FileReader fileReader = new FileReader("filter.txt");

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                filter.add(line);
            }
            // Always close files.
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



}
