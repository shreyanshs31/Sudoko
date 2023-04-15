import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //asking player to enter thier name
        System.out.print("Enter your name: ");
        Scanner in = new Scanner(System.in);
        String player = in.next();

        //Initialising the sudoko board that is 9x9 with all the values to 0.
        int[][] board = new int[9][9];

        display_board(board);
    }


    //This function displayes board when called
    static void display_board(int[][] board) {
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}