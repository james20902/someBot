package jPham.someBot.bot;


import java.io.File;
import java.io.FileNotFoundException;
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

    public static ArrayList<String> nonowords = new ArrayList<>();



}
