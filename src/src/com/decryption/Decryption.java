package com.decryption;

import com.algorithm.AES128;
import com.algorithm.RSA;

import java.io.*;
import java.util.Scanner;


public class Decryption {
    public static void main(String[] args) throws Exception {

        System.out.println(" /$$      /$$  /$$$$$$  /$$$$$$$  /$$$$$$$$       /$$$$$$$  /$$     /$$\n" +
                "| $$$    /$$$ /$$__  $$| $$__  $$| $$_____/      | $$__  $$|  $$   /$$/\n" +
                "| $$$$  /$$$$| $$  \\ $$| $$  \\ $$| $$            | $$  \\ $$ \\  $$ /$$/ \n" +
                "| $$ $$/$$ $$| $$$$$$$$| $$  | $$| $$$$$         | $$$$$$$   \\  $$$$/  \n" +
                "| $$  $$$| $$| $$__  $$| $$  | $$| $$__/         | $$__  $$   \\  $$/   \n" +
                "| $$\\  $ | $$| $$  | $$| $$  | $$| $$            | $$  \\ $$    | $$    \n" +
                "| $$ \\/  | $$| $$  | $$| $$$$$$$/| $$$$$$$$      | $$$$$$$/    | $$    \n" +
                "|__/     |__/|__/  |__/|_______/ |________/      |_______/     |__/    \n" +
                "                                                                       \n" +
                "                                                                       \n" +
                " /$$$$$$$$ /$$   /$$ /$$$$$$$$        /$$$$$$  /$$$$$$ /$$   /$$       \n" +
                "|__  $$__/| $$  | $$| $$_____/       /$$__  $$|_  $$_/| $$  / $$       \n" +
                "   | $$   | $$  | $$| $$            | $$  \\__/  | $$  |  $$/ $$/       \n" +
                "   | $$   | $$$$$$$$| $$$$$         |  $$$$$$   | $$   \\  $$$$/        \n" +
                "   | $$   | $$__  $$| $$__/          \\____  $$  | $$    >$$  $$        \n" +
                "   | $$   | $$  | $$| $$             /$$  \\ $$  | $$   /$$/\\  $$       \n" +
                "   | $$   | $$  | $$| $$$$$$$$      |  $$$$$$/ /$$$$$$| $$  \\ $$       \n" +
                "   |__/   |__/  |__/|________/       \\______/ |______/|__/  |__/       \n" +
                "                                                                       \n");
        System.out.println("Made By 其实我在第五层\n");
        // Initialize the relative path
        String input_File = ".\\test_encrypted.txt";
        String output_File = ".\\test_raw.txt";
        String RSA_Private_File = ".\\RSA_pri.txt";
        String AES_KEY_File = ".\\AES_KEY.txt";

        // Initialize input
        Scanner sc = new Scanner(System.in);

        // Read the file
        FileReader fileReader = new FileReader(input_File);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        // Decrypt
        System.out.println("\nPlease choose the algorithm (default AES128 + CBC):");
        System.out.println("1.AES128 + CBC\n2.AES128 + ECB\n3.RSA + AES128 + CBC");
        int al_config = sc.nextInt();
        String encoded = "";
        if (al_config == 1) {
            // func 1
            encoded = AES128.decryptAES(line, "CBC", AES128.getKey());

        }else if (al_config == 2) {
            // func 2
            encoded = AES128.decryptAES(line, "ECB", AES128.getKey());

        }else if (al_config == 3) {
            // func 3
            FileReader keyReader = new FileReader(RSA_Private_File);
            BufferedReader keybuffered = new BufferedReader(keyReader);
            String RSA_pri = keybuffered.readLine();  // Get the private key from file
            keyReader = new FileReader(AES_KEY_File);
            keybuffered = new BufferedReader(keyReader);
            String AES = keybuffered.readLine();  // Get the encrypted AES key from file
            bufferedReader.close();
            fileReader.close();

            String AES_KEY = RSA.decrypt(AES, RSA_pri);  // Decrypted and get the original AES key

            encoded = AES128.decryptAES(line, "CBC", AES_KEY);  // Decrypt file using AES algorithm

        }
        else {
            System.out.println("Setting as default.\n");
            encoded = AES128.decryptAES(line, "CBC", AES128.getKey());
        }
//        System.out.println(encoded);

        // Write to a text file
        File file = new File(output_File);
        if (!file.exists())
        {
            file.createNewFile();
        }
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
        BufferedWriter writer=new BufferedWriter(write);
        writer.write(encoded);
        writer.close();

        System.out.println("\n================");
        System.out.println("Encryption Done.");
        System.out.println("================");
        System.out.println("Press Enter to exit.");
        while (true) {
            if (System.in.read() == '\n')
                System.exit(0);
        }
    }
}