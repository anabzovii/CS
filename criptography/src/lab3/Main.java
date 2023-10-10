package lab3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Character> getAlphabet() {
        // The Romanian alphabet and special characters are stored as a single string, which is then converted to a List<Character>.
        return "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ".chars().mapToObj(i -> (char) i).toList();
    }

    // This method encrypts a given plaintext using a Vigenère cipher.
    public static String encrypt(String plaintext, String key) {
        // Check if the key meets a certain length requirement.
        check(key);

        // Convert the plaintext and key to uppercase, remove spaces, and convert them to List<Character>.
        List<Character> plainList = plaintext.toUpperCase().replaceAll(" ", "").chars().mapToObj(i -> (char) i).toList();
        List<Character> keyList = key.toUpperCase().replaceAll(" ", "").chars().mapToObj(i -> (char) i).toList();

        // Create a keyFullList by repeating the key characters to match the length of the plaintext.
        List<Character> keyFullList = new ArrayList<>();
        int i = 0;
        int j;
        int k = 1;
        while (i < plainList.size()) {
            j = i - (keyList.size() * (k - 1));
            keyFullList.add(keyList.get(j));
            if ((i + 1) / k == keyList.size()) {
                k++;
            }
            i++;
        }

        // Map the characters of the plaintext and keyFullList to their positions in the alphabet.
        List<Integer> plainPosition = plainList.stream().map(character -> getAlphabet().indexOf(character)).toList();
        List<Integer> keyFullPosition = keyFullList.stream().map(character -> getAlphabet().indexOf(character)).toList();

        // Calculate the difference between the positions of plaintext and keyFullList characters.
        List<Integer> difference = new ArrayList<>();
        int s = 0;
        while (s < plainPosition.size()) {
            difference.add((plainPosition.get(s) + keyFullPosition.get(s)) % getAlphabet().size());
            s++;
        }

        // Convert the differences back to characters and join them to form the ciphertext.
        return difference.stream()
                .map(integer -> getAlphabet().get(integer))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    // This method decrypts a Vigenère ciphertext using the provided key.
    public static String decrypt(String ciphertext, String key) {
        // Check if the key meets a certain length requirement.
        check(key);

        // Initialize a StringBuilder to store the decrypted plaintext.
        StringBuilder plaintext = new StringBuilder();

        // Convert the ciphertext and key to uppercase.
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();

        // Iterate through the characters in the ciphertext.
        for (int i = 0, j = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);
            if (getAlphabet().contains(currentChar)) {
                // Calculate the shift based on the key character at position j.
                int shift = getAlphabet().indexOf(key.charAt(j % key.length()));

                // Decrypt the current character and append it to the plaintext.
                char decryptedChar = getAlphabet().get((getAlphabet().indexOf(currentChar) - shift + getAlphabet().size()) % getAlphabet().size());
                plaintext.append(decryptedChar);
                j++;
            } else {
                // If the character is not in the alphabet, append it as is.
                plaintext.append(currentChar);
            }
        }

        // Convert the StringBuilder to a String and return the decrypted plaintext.
        return plaintext.toString();
    }

    // This method checks if the provided key meets a certain length requirement and throws an exception if it doesn't.
    public static void check(String key) {
        if (key.length() < 7) {
            System.out.println("Is less than 7");
            throw new RuntimeException();
        }
    }

    public static void main(String[] args) {
//        String plaintext = "Per aspera ad astra";
        String plaintext = "Mama a cumaprat lapte";
        String key = "CopacVerde";

        String encryptedText = encrypt(plaintext, key);
        String decryptedText = decrypt(encryptedText, key);

        System.out.println("Original Text: " + plaintext);
        System.out.println("Encrypted Text: " + encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);
    }
}
