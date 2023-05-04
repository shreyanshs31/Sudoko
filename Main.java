/* Created By: Shreyansh Shukla
 * Date: 03-05-2023
 * Project Name: Sudoku Game
 */


import java.util.Random;
import java.util.Scanner;
import java.lang.Math;


public class Main {
    public static void main(String[] args) {

        //Initialise the board
        int[][] board = new int[9][9];


        /* Filling of board */
        filldiagonals(board);
        fillremaining(board, 0, 3);
        int[][] BoardB = make_copy(board);
        removeelements(board);
        display_board(board);

        /* Starting Game */
        play_game(board, BoardB);

    }


    /******************* This function displays the board when called */
    static void display_board(int[][] board) {
        System.out.println("***** Board *****");
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                System.out.print(board[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }
    }


    /****************** This function takes out the random from 1 to 9 */
    static int random() {
        while(true) {
            int rand = (int) (Math.random()*10);
            if(rand != 0 && rand != 10) {
                return rand;
            }
        }
    }


    /************* This function fills the diagonal of the sudoku board */
    static void filldiagonals(int[][] board) {
        for(int i = 0; i < 9; i = i + 3) {
            fillbox(board, i, i);
        }
    }
    

    /**************************** This function fills the box */
    static void fillbox(int[][] board, int row, int col) {
        int num;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                do {
                    num = random();
                } while (!unused_in_box(board, row, col, num));
                // adding the number if it is not present in box
                board[row+i][col+j] = num;
            }
        }
    }


    /******************** This function fills the rest of the places */
    static boolean fillremaining(int[][] board, int row, int col) {

        //after every row is completed make col = 0 and incriment row by 1
        if(col >= 9 && row < 8) {
            col = 0;
            row = row + 1;
        }

        //when all the rows and cols are completly checked
        //this is the end condition
        if(row >= 9 && col >= 9) {
            return true;
        }

        //if row is less than 3 then skip the box 1(first box)
        if(row < 3) {
            if(col < 3) {
                col = 3;
            }
        }
        //if row is less than 6 and greater than 3 than skip the 5 box(middle box)
        else if(row < 6) {
            if(col == 3) {
                col += 3;
            }
        }
        //if row is greater than 6 and less than 8 than skip the 9 box(last box)
        else {
            if(col == 6) {
                col = 0;
                row += 1;
                if(row >= 9) {
                    return true;
                }
            }
        }

        //this will run every time from 1 to 9 and check if that number is safe to put or not
        for(int num = 1; num <= 9; num++) {
            if(is_safe(board, row, col, num)) {
                board[row][col] = num;
                if(fillremaining(board, row, col + 1)) {
                    return true;
                }
                //if condition returns false then make the previous assigned position as 0
                board[row][col] = 0;
                
            }
        }
        return false;

    }


    /******** This function returns true if it is safe for a number to be put place */
    static boolean is_safe(int[][] board, int row, int col, int num){
        return unused_in_row(board, num, row) && unused_in_col(board, num, col) && unused_in_box(board, row-row%3, col-col%3, num);
    }


    /****************** This function checks if a number is valid in row */
    static boolean unused_in_row(int[][] board,int num,int row) {
        for(int j = 0; j < 9; j++) {
            if(board[row][j] == num) {
                return false;
            }
        }
        return true;
    }


    /****************** This function checks if a number is valid in col */
    static boolean unused_in_col(int[][] board,int num, int col) {
        for(int i = 0; i < 9; i++) {
            if(board[i][col] == num) {
                return false;
            }
        }
        return true;
    }


    /****************** This function checks if a number is valid in box */
    //returns false if num is present in the box
    static boolean unused_in_box(int[][] board,int srow, int scol, int num) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[srow + i][scol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }


    /***************** This function delets k elements from the sudoku board */
    static void removeelements(int[][] board) {
        //enter how many elements to be removed
        int k;
        Scanner elements = new Scanner(System.in);
        while(true) {
            System.out.print("Enter how many elements to be removed: ");
            k = elements.nextInt();
            if(k != 0) {
                break;
            }
            else{
                System.out.println("Enter number above 0");
            }
        }

        Random random = new Random();

        while(k != 0) {
            int row = random.nextInt(9); // this bound is between 0 to 9
            int col = random.nextInt(9); // this bound is between 0 to 9

            //checks if the position already contains 0 or not
            //if yes then leave that place and move fordword
            if(board[row][col] == 0){
                continue;
            }
            else {
                board[row][col] = 0;
                k-=1;
            }
            
        }
    }


    /* This function makes a copy of the board */
    static int[][] make_copy(int[][] board) {
        int[][] boardB = new int[9][9];
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9;  j++) {
                boardB[i][j] = board[i][j];
            }
        }
        return boardB;
    }


    /********************This function enables user to play game */ 
    static void play_game(int[][] board,int[][] mirror_board) {
        boolean game_ends = false;
        while(!game_ends) {

            //takes row and col
            Scanner in = new Scanner(System.in);
            System.out.print("Enter row: ");
            int row = in.nextInt();
            System.out.print("Enter col: ");
            int col = in.nextInt();

            //checks if the postion already has a number of not
            if(board[row][col] != 0) {
                System.out.println("Enter different position, this position already has number");
            }
            else{
                //takes ans for the position
                System.out.print("Enter the number for the position: ");
                int ans = in.nextInt();

                //checks if entered answer is correct or not
                if(ans == mirror_board[row][col]) {
                    System.out.println("That was the correct ans");
                    board[row][col] = ans;
                }
                else{
                    System.out.println("Opps! that was not the correct ans");
                    System.out.println("Try again");
                }
            }
            //display the board
            display_board(board);

            //checks if game ended or not
            if(game_ends(board) == true) {
                System.out.println("xxx Game ended xxx");
                game_ends = true;
            }
            in.close();            
        }
    }
    static boolean game_ends(int[][] board) {
        //checks if game ends
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
