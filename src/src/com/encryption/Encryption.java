package com.encryption;

import com.algorithm.AES128;
import com.algorithm.RSA;

import java.io.*;
import java.util.Scanner;

public class Encryption {
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
        String input_File = ".\\test.txt";
        String output_File = ".\\test_encrypted.txt";
        String RSA_Public_File = ".\\RSA_pub.txt";
        String RSA_Private_File = ".\\RSA_pri.txt";
        String AES_KEY_File = ".\\AES_KEY.txt";

        // Initialize input
        Scanner sc = new Scanner(System.in);

        // Read the file
        FileReader fileReader = new FileReader(input_File);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        // Encrypt
        System.out.println("\nPlease choose the algorithm (default AES128 + CBC):");
        System.out.println("1.AES128 + CBC\n2.AES128 + ECB\n3.RSA + AES128 + CBC");
        int al_config = sc.nextInt();
        String encoded = "";
        if (al_config == 1) {
            // func 1
            encoded = AES128.encryptAES(line, "CBC", AES128.getKey());

        }else if (al_config == 2) {
            // func 2
            encoded = AES128.encryptAES(line, "ECB", AES128.getKey());

        }else if (al_config == 3) {
            // fun 3
            RSA.genKeyPair();
            System.out.println("Public Key:" + RSA.get_publickey());
            System.out.println("Secret Key:" + RSA.get_privatekey());
            String encoded_key = RSA.encrypt(AES128.getKey(), RSA.get_publickey());

            File file1 = new File(RSA_Public_File);
            PrintStream ps1 = new PrintStream(new FileOutputStream(file1));
            ps1.println(RSA.get_publickey());
            File file2 = new File(RSA_Private_File);
            PrintStream ps2 = new PrintStream(new FileOutputStream(file2));
            ps2.println(RSA.get_privatekey());
            File file3 = new File(AES_KEY_File);
            PrintStream ps3 = new PrintStream(new FileOutputStream(file3));
            ps3.println(encoded_key);
            System.out.println("The key is saved to text.");

            encoded = AES128.encryptAES(line, "CBC", AES128.getKey());

        }
        else {
            System.out.println("Setting as default.\n");
            encoded = AES128.encryptAES(line, "CBC", AES128.getKey());
        }
//        System.out.println(line);
//        System.out.println(encoded);
//        System.out.println(Algorithm.decryptAES(encoded));

        // Write in text file
        File file = new File(output_File);
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        ps.println(encoded);

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

