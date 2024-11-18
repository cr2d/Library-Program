package src;
import java.io.*;
public class userDatabase {
    //A class meant to simulate a database that stores
    //user information on a text file for log in purposes.

    private static final String file_path = "userDatabase.txt";

    public static void generateDatabase() {
        try {
            FileWriter fileWriter = new FileWriter(file_path, true);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to create the user database text file.");
        }
    }

    public static boolean userLogin(String username, String password) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String[] userData = currLine.split(":");
                //Order for writing to file is:
                //ID, username, password, address, phoneNumber, age, numOfBooksCheckedOut, overdueFee
                if (userData.length == 8) {
                    String currUsername = userData[1];
                    String currPassword = userData[2];
                    if (currUsername.equals(username) && currPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to locate the user on the text file.");
        }
        return false;
    }

    public static void userRegister(String username, String password, String address, String phoneNumber, String age) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
            int currID = getNextID();
            int noOfBooksCheckedOut = 0;
            double overdueFees = 0.0;
            String signingUpUser = currID + ":" + username + ":" + password + ":" +  address + ":" + phoneNumber + ":" + age + ":" +
                    noOfBooksCheckedOut + ":" + overdueFees + System.lineSeparator();
            writer.write(signingUpUser);
        } catch (IOException e) {
            System.out.println("Unable to register the user on the text file.");
        }
    }

    public static String getNumOfBooksCheckedOut(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String currLine;
            while ((currLine = reader.readLine()) != null) {
                String[] userData = currLine.split(":");
                if (userData.length == 8) {
                    String currUsername = userData[1];
                    if (currUsername.equals(username)) {
                        String numOfBooks  = userData[6];
                        return numOfBooks;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to view file data (value).");
        }
        return null;
    }

    private static int getNextID() {
        int nextID = 1;

        try(BufferedReader reader = new BufferedReader(new FileReader(file_path))) {
            String currLine;
            while((currLine = reader.readLine()) != null) {
                String[] userData = currLine.split(":");
                if(userData.length > 0) {
                    int ID = Integer.parseInt(userData[0]);
                    nextID = Math.max(nextID, ID + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to generate an ID for the current user.");
        }
        return nextID;
    }


}