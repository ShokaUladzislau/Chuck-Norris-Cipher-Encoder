package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Please input operation (encode/decode/exit):");
            String str = scan.nextLine();
            switch (str) {
                case "encode":
                    encode();
                    break;
                case "decode":
                    decode();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("There is no '" + str + "' operation");
                    break;
            }
        } while (!exit);
        System.out.println("Bye!");
        scan.close();
    }

    private static void encode() {
        System.out.println("Input string:");
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            binary.append(String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0'));
        }

        StringBuilder result = new StringBuilder();
        char current = binary.charAt(0);
        int count = 0;

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == current) {
                count++;
            } else {
                result.append(current == '1' ? "0 " : "00 ");
                for (int j = 0; j < count; j++) {
                    result.append("0");
                }
                result.append(" ");
                count = 1;
                current = binary.charAt(i);
            }
        }

        result.append(current == '1' ? "0 " : "00 ");
        for (int j = 0; j < count; j++) {
            result.append("0");
        }

        System.out.println("Encoded string:");
        System.out.println(result.toString());
        sc.close();
    }

    private static void decode() {
        System.out.println("Input encoded string:");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();

        if (isValid(str)) {
            StringBuilder binary = new StringBuilder();
            boolean flag = false;

            String[] valores = str.split(" ");
            for (int i = 0; i < valores.length; i += 2) {
                flag = valores[i].length() == 1;
                binary.append(flag ? "1".repeat(valores[i + 1].length()) : "0".repeat(valores[i + 1].length()));
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < binary.length(); i += 7) {
                String s = binary.substring(i, i + 7);

                result.append((char) Integer.parseInt(s, 2));
            }

            System.out.println("Decoded string:");
            System.out.println(result);
        } else {
            System.out.println("Encoded string is not valid.");
        }
        sc.close();
    }

    private static boolean isValid(String str) {
        boolean valid = true;
        valid = str.matches("^[0\s]*$");
        String[] valores = null;
        if (valid) {
            valores = str.split(" ");
            valid = valores.length % 2 == 0;
        }
        boolean valid0 = true;
        StringBuilder result = new StringBuilder();
        if (valid) {
            for (int i = 0; i < valores.length; i += 2) {
                if (valores[i].length() > 2) {
                    valid0 = false;
                }
                result.append("1".repeat(valores[i + 1].length()));
            }
        }
        if (!valid0) {
            valid = valid0;
        }

        if (valid) {
            valid = result.length() % 7 == 0;
        }
        return valid;
    }
}