
//Program:      Othello
//Course:       COSC470
//Description:  Permits two programs, each using this control structure (but each with additional
//              customized classes and/or methods)to play Othello (i.e, against each other).
//Author:       Logan Richardson
//Revised:      5/5/2016
//Notes:        What I need to do now is an incrementing value to so that methods can check to further ends of
//              the array column/row without just stopping.  Perhaps create a global that increments after every
//              method call?
//*******************************************************************************
//*******************************************************************************

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
//***************************************************************************************************
//***************************************************************************************************
//Class:        Othello
//Description:  Main class for the program. Allows set-up and plays one side.
//              myColor: Keeps hold of my color character
//              opponentColor: Keeps hold of my opponents color character
//              validmove: Contains the array index of of a valid move if any
//              depth: Used for the UI for how deep they want to go
//              end: Only used to check for the end of the game.
public class Othello {

    public static char myColor = '?';           //B (black) or W (white) - ? means not yet selected
    public static char opponentColor = '?';     //ditto but opposite

    //INSERT ANY ADDITIONAL GLOBAL VARIABLES HERE
    //===========================================
    //===========================================

    public static int[][] validmove = new int[4][64];
    public static int depth = 5;
    public static boolean end = false;

    //===========================================
    //===========================================
    //***************************************************************************************************
    //Method:		main
    //Description:	Calls routines to play Othello
    //Parameters:	none
    //Returns:		nothing
    //Calls:        loadBoard, saveBoard, showBoard, constructor in Board class
    //              getCharacter, getInteger, getKeyboardInput, constructor in KeyboardInputClass
    public static void main(String args[]) {
        //INSERT ANY ADDITIONAL CONTROL VARIABLES HERE
        //============================================
        //============================================

        //============================================
        //============================================
        KeyboardInputClass keyboardInput = new KeyboardInputClass();
        int pollDelay = 250;
        long moveStartTime, moveEndTime, moveGraceTime = 10000;     //times in milliseconds
        Board currentBoard = Board.loadBoard();
        String myMove = "", myColorText = "";
        System.out.println("--- Othello ---");
        System.out.println("Player: Logan Richardson\n");
        if (currentBoard != null) {                                 //board found, make sure it can be used
            if (currentBoard.status == 1) {                          //is a game in progress?   
                if (keyboardInput.getCharacter(true, 'Y', "YN", 1, "A game appears to be in progress. Abort it? (Y/N (default = Y)") == 'Y') {
                    currentBoard = null;
                } else {
                    System.out.println("Exiting program. Try again later...");
                    System.exit(0);
                }
            }
        }
        if ((currentBoard == null) || (currentBoard.status == 2)) {   //create a board for a new game
            int rows = 8;
            int cols = 8;
            if (keyboardInput.getCharacter(true, 'Y', "YN", 1, "Use standard board? (Y/N: default = Y):") == 'N') {
                rows = keyboardInput.getInteger(true, rows, 4, 26, "Specify the number of rows for the board (default = " + rows + "):");
                cols = keyboardInput.getInteger(true, cols, 4, 26, "Specify the number of columns for the board (default = " + cols + "):");
            }
            int maxTime = 60;
            maxTime = keyboardInput.getInteger(true, maxTime, 10, 600, "Max time (seconds) allowed per move (Default = " + maxTime + "):");
            currentBoard = new Board(rows, cols, maxTime);
            while (currentBoard.saveBoard() == false) {
            }               //try until board is saved (necessary in case of access conflict)
        }

        //INSERT CODE HERE FOR ANY ADDITIONAL SET-UP OPTIONS
        //==================================================
        //==================================================
        
        int run = keyboardInput.getInteger(true, 1, 1, 3, "(Smart, Manual, Random)...[1,2,3], default = 1");
        int depth = keyboardInput.getInteger(true, 2, 1, 10, "What depth do you want to search?  [Min: 1 | Max: 10], default = 2");
        

        //==================================================
        //==================================================
        //To do: Check for length of String UI to be 2...
        //	     Also check to make sure its in bounds...
        //==================================================
        //==================================================
        //At this point set-up must be in progress so colors can be assigned
        if (currentBoard.colorSelected == '?') {                    //if no one has chosen a color yet, choose one (player #1)
            myColor = keyboardInput.getCharacter(true, 'B', "BW", 1, "Select color: B=Black; W=White (Default = Black):");
            currentBoard.colorSelected = myColor;

            while (currentBoard.saveBoard() == false) {
            }               //try until the board is saved
            System.out.println("You may now start the opponent's program...");
            while (currentBoard.status == 0) {                      //wait for other player to join in
                currentBoard = null;                                //get the updated board
                while (currentBoard == null) {
                    currentBoard = Board.loadBoard();
                }
            }
        } else {                                                      //otherwise take the other color (this is player #2)
            if (currentBoard.colorSelected == 'B') {
                myColor = 'W';
            } else {
                myColor = 'B';
            }
            currentBoard.status = 1;                                //by now, both players are engaged and play can begin
            while (currentBoard.saveBoard() == false) {
            }               //try until the board is saved
        }

        if (myColor == 'B') {
            myColorText = "Black";
            opponentColor = 'W';
        } else {
            myColorText = "White";
            opponentColor = 'B';
        }
        System.out.println("This player will be " + myColorText + "\n");

        //INSERT CODE HERE FOR ANY ADDITIONAL OUTPUT OPTIONS
        //==================================================
        //==================================================
        //==================================================
        //==================================================
        //Now play can begin. (At this point each player should have an identical copy of currentBoard.)
        while (currentBoard.status == 1) {
            if (currentBoard.whoseTurn == myColor) {
                if (currentBoard.whoseTurn == 'B') {
                    System.out.println("Black's turn to move...");
                } else {
                    System.out.println("White's turn to move");
                }
                currentBoard.showBoard();
                String previousMove = currentBoard.move;
                moveStartTime = System.currentTimeMillis();

                //CALL METHOD(S) HERE TO SELECT AND MAKE A VALID MOVE
                //===================================================
                //===================================================
                if (run == 1) {

                    String best = queueMethod(myColor, 1, 0, 0, depth);
                    if(best == "00000 00 00"){
                        myMove = "";
                    }else{
                    Scanner b = new Scanner(best).useDelimiter(" ");
                    int bestmove = b.nextInt();
                    int row = b.nextInt();
                    int col = b.nextInt();
                    currentBoard.board[row][col] = myColor;
                    //System.out.println("Board value: " + currentBoard.board[a][b]);
                    updateBoard(currentBoard, myColor, row, col);
                    currentBoard.showBoard();
                    row = row | 0x40 + 1;
                    col = col | 0x40 + 1;
                    myMove = Character.toString((char) row) + Character.toString((char) col);
                    }
                }//End of run 1 
                if (run == 2) {
                    myMove = keyboardInput.getString("AA", "Type in the location of your next move.");
                    int row = (myMove.charAt(0) & 0x0f) - 1;
                    int col = (myMove.charAt(1) & 0x0f) - 1;
                    currentBoard.board[row][col] = myColor;
                    updateBoard(currentBoard, myColor, row, col);

                }
                if (run == 3) {

                    int length = currentBoard.boardRows;
                    int nmoves = 0;
                    //currentBoard.saveBoard();
                    for (int ir = 0; ir < currentBoard.boardRows; ir++) {
                        for (int jc = 0; jc < currentBoard.boardCols; jc++) {
                            if (currentBoard.board[ir][jc] == ' ') {
                                Board newboard = Board.loadBoard();
                                //newboard.showBoard();

                                //newboard = copyBoard(currentBoard,newboard);
                                newboard.board[ir][jc] = myColor;
                                int grade = updateBoard(newboard, myColor, ir, jc);
                                if (grade != 0) {
                                    validmove[0][nmoves] = ir;
                                    validmove[1][nmoves] = jc;
                                    validmove[2][nmoves] = grade;
                                    nmoves++;
                                }
                            }
                        }
                    }
                    if (nmoves == 0) {
                        myMove = "";
                    } else {
                        Random irandom = new Random();
                        //irandom.setSeed(seed);
                        int range = (nmoves - 0);
                        int choice = (int) (Math.random() * range);
                        int row = validmove[0][choice];
                        int col = validmove[1][choice];
                        currentBoard.board[row][col] = myColor;
                        updateBoard(currentBoard, myColor, row, col);
                        currentBoard.showBoard();
                        row = row | 0x40 + 1;
                        col = col | 0x40 + 1;
                        myMove = Character.toString((char) row) + Character.toString((char) col);

                    }//End of else
                }//End of run = 3

                //===================================================
                //===================================================
                //YOU MAY ADD NEW CLASSES AND/OR METHODS BUT DO NOT
                //CHANGE ANY EXISTING CODE BELOW THIS POINT
                moveEndTime = System.currentTimeMillis();
                if ((moveEndTime - moveStartTime) > (currentBoard.maxMoveTime * 1000 + moveGraceTime)) {
                    System.out.println("\nMaximum allotted move time exceeded--Opponent wins by default...\n");
                    keyboardInput.getKeyboardInput("\nPress ENTER to exit...");
                    currentBoard.status = 2;
                    while (currentBoard.saveBoard() == false) {
                    }       //try until the board is saved
                    System.exit(0);
                }

                if (myMove.length() != 0) {
                    System.out.println(myColorText + " chooses " + myMove + "\n");
                    currentBoard.showBoard();
                    System.out.println("Waiting for opponent's move...\n");
                } else if (previousMove.length() == 0) {               //neither player can move
                    currentBoard.status = 2;                    //game over...
                    System.out.println("\nGame over!");
                    int blackScore = 0;
                    int whiteScore = 0;
                    for (int r = 0; r < currentBoard.boardRows; r++) {
                        for (int c = 0; c < currentBoard.boardCols; c++) {
                            if (currentBoard.board[r][c] == 'B') {
                                blackScore++;
                            } else if (currentBoard.board[r][c] == 'W') {
                                whiteScore++;
                            }
                        }
                    }
                    if (blackScore > whiteScore) {
                        System.out.println("Blacks wins " + blackScore + " to " + whiteScore);
                    } else if (whiteScore > blackScore) {
                        System.out.println("White wins " + whiteScore + " to " + blackScore);
                    } else {
                        System.out.println("Black and White tie with scores of " + blackScore + " each");
                    }
                } else {
                    System.out.println("No move available. Opponent gets to move again...");
                }
                currentBoard.move = myMove;
                currentBoard.whoseTurn = opponentColor;
                while (currentBoard.saveBoard() == false) {
                }           //try until the board is saved
            } else {                                                   //wait a moment then poll again
                try {
                    Thread.sleep(pollDelay);
                } catch (Exception e) {
                }
            }
            currentBoard = null;                                    //get the updated board
            while (currentBoard == null) {
                currentBoard = Board.loadBoard();
            }
        }
        keyboardInput.getKeyboardInput("\nPress ENTER to exit...");
    }
    //***************************************************************************************************
    //Method:           updateBoard
    //Description:	Updates the board on every iteration of something chaning.
    //                  Check up,down,left,right,top-left,top-right,bot-left, and bot-right spots for moves
    //		
    //Parameters:  	testboard	The board for testing for moves with the currentboard being passed for this instance
    //            	color           Used to rotate turns if need be
    //             	row             Current row of the board
    //             	col             Current col of the board
    //Returns:     	grade           The grade is what I'm measuring each move by, the higher the grade the better the move.
    //Calls:       	-
    //Globals:          -
    public static int updateBoard(Board testboard, char color, int row, int col) {
        int totalcount = 0;
        int grade = 0;
        char localopponentColor = 'B';
        if (color == 'B') {
            localopponentColor = 'W';
        }
        for (int drow = -1; drow <= 1; drow++) {
            int frow = row + drow;
            if (frow >= 0 && frow < testboard.boardRows) {
                for (int dcol = -1; dcol <= 1; dcol++) {
                    int count = 0;
                    int r = frow;
                    int c = col + dcol;
                    if (row == 2 && col == 2) {

                    }
                    if (c >= 0 && c < testboard.boardCols) {

                        if (testboard.board[r][c] == localopponentColor) {
                            while (testboard.board[r][c] == localopponentColor) {
                                count++;
                                totalcount++;
                                r = r + drow;
                                c = c + dcol;
                                if (r < 0 || r >= testboard.boardRows) {
                                    break;
                                }
                                if (c < 0 || c >= testboard.boardCols) {
                                    break;
                                }
                                //System.out.println(count + " " + r + " " + c);
                                if (testboard.board[r][c] == color) {
                                    //Found one, now change it
                                    grade = totalcount;
                                    int xr = row;
                                    int xc = col;
                                    //System.out.println(count + " " + r + " " + c);
                                    for (int i = 0; i < count; i++) {
                                        xr = xr + drow;
                                        xc = xc + dcol;
                                        testboard.board[xr][xc] = color;

                                    }//End of for loop
                                }//End of if on myColor
                            }//End of while loop
                        }//End of if statement for == opponentColor
                    }//End of limit checking if statement of the cols
                }//End of dcol for loop
            }//End of limit checking if statement of the rows
        }//End of drow for loop

        return grade;
    }//End of updateBoard
    //***************************************************************************************************
    //Method:           queueMethod
    //Description:	This is the method that is used only for our "1" option.  In other words, the queueMethod contains
    //                  the open queue and the closed queue for diving deeper in the tree which it does recursivly and wont
    //                  stop UNLESS the depth (which is being deducted each successful iteration) becomes 0, at then will
    //                  the method return a pseudo String value.
    //		
    //Parameters:  	newcolor	The new color of the board, which will be sent to the updateBoard method
    //            	grade           The way I'm measuring how good a move is
    //             	row             The current row I'm at
    //             	col             The current col I'm at
    //Returns:     	localdepth      The depth at which point I'm currently checking AT A PARTICULAR DEPTH LEVEL.
    //                                  If I'm at a global depth of 2 and I still have more Strings to check in my queue,
    //                                  the local depth keeps track what queue position I'm at in the queue at that specific
    //                                  depth level.
    //Calls:       	openPQ          Open priority Q
    //                  closedPQ        Closed priority Q
    //Globals:          -
    public static String queueMethod(char newcolor, int grade, int row, int col, int localdepth) {

        Queue<String> openPQ = new PriorityQueue<>(20);
        Queue<String> closedPQ = new PriorityQueue<>(20);
        Board currentBoard = Board.loadBoard();
        if (localdepth == 0) {

            return ("00000 00 00");
            //String gpq1 = closedPQ.poll();
            //return(gpq1);//This is the grade
        }
        localdepth--;
        int nmoves = 0;
        //Load openQ
        for (int ir = 0; ir < currentBoard.boardRows; ir++) {
            for (int jc = 0; jc < currentBoard.boardCols; jc++) {
                if (currentBoard.board[ir][jc] == ' ') {
                    Board newboard = Board.loadBoard();
                    newboard.board[ir][jc] = newcolor;
                    grade = updateBoard(newboard, newcolor, ir, jc);
                    if (grade != 0) {
                        nmoves++;
                        openPQ.add(String.format("%05d %02d %02d", grade, ir, jc));
                    }
                }
            }
        }
        //Exiting when/if were @ the bot of the tree or @ depth
        if (nmoves == 0) {
            return ("00000 00 00");
            //String gpq1 = closedPQ.poll();
            //return(gpq1);//This is the grade
        }
        //Processing the open Q
        while (openPQ.size() != 0) {
            String move = openPQ.poll();
            Scanner s = new Scanner(move).useDelimiter(" ");
            grade = s.nextInt();
            row = s.nextInt();
            col = s.nextInt();
            if (newcolor == 'B') {
                newcolor = 'W';
            } else {
                newcolor = 'B';
            }
            String newgrade = queueMethod(newcolor, grade, row, col, localdepth);
            Scanner ng = new Scanner(move).useDelimiter(" ");
            int grade2 = ng.nextInt();
            grade = grade - grade2;
            closedPQ.add(String.format("%05d %02d %02d", grade, row, col));
        }//End of while

        return closedPQ.poll();
    }//End of queueMethod method
    //***************************************************************************************************

    //***************************************************************************************************
    //***************************************************************************************************
}

//*******************************************************************************************************
//*******************************************************************************************************
//Class:        Board
//Description:  Othello board and related parms
class Board implements Serializable {

    char status;        //0=set-up for a new game is in progress; 1=a game is in progress; 2=game is over
    char whoseTurn;     //'?'=no one's turn yet--game has not begun; 'B'=black; 'W'=white
    String move;        //the move selected by the current player (as indicated by whoseTurn)
    char colorSelected; //'B' or 'W' indicating the color chosen by the first player to access the file
    //for a new game ('?' if neither player has yet chosen a color)
    //Note: this may or may not be the color for the player accessing the file
    int maxMoveTime;    //maximum time allotted for a move (in seconds)
    int boardRows;      //size of the board (allows for variations on the standard 8x8 board)
    int boardCols;
    char board[][];     //the board. Positions are filled with: blank = no piece; 'B'=black; 'W'=white
    //***************************************************************************************************
    //Method:       Board
    //Description:  Constructor to create a new board object
    //Parameters:	rows - size of the board
    //              cols
    //              time - maximum time (in seconds) allowed per move
    //Calls:		nothing
    //Returns:		nothing

    Board(int rows, int cols, int time) {
        int r, c;
        status = 0;
        whoseTurn = 'B';        //Black always makes the first move
        move = "*";
        colorSelected = '?';
        maxMoveTime = time;
        boardRows = rows;
        boardCols = cols;
        board = new char[boardRows][boardCols];
        for (r = 0; r < boardRows; r++) {
            for (c = 0; c < boardCols; c++) {
                board[r][c] = ' ';
            }
        }
        r = boardRows / 2 - 1;
        c = boardCols / 2 - 1;
        board[r][c] = 'W';
        board[r][c + 1] = 'B';
        board[r + 1][c] = 'B';
        board[r + 1][c + 1] = 'W';
    }

    //***************************************************************************************************
    //Method:       saveBoard
    //Description:  Saves the current board to disk as a binary file named "OthelloBoard"
    //Parameters:	none
    //Calls:		nothing
    //Returns:		true if successful; false otherwise
    public boolean saveBoard() {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("OthelloBoard"));
            outStream.writeObject(this);
            outStream.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //***************************************************************************************************
    //Method:       loadBoard
    //Description:  Loads the current Othello board and data from a binary file
    //Parameters:   none
    //Calls:        nothing
    //Returns:      a Board object (or null if routine is unsuccessful)
    public static Board loadBoard() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream("OthelloBoard"));
            Board boardObject = (Board) inStream.readObject();
            inStream.close();
            return boardObject;
        } catch (Exception e) {
        }
        return null;
    }

    //***************************************************************************************************
    //Method:       showBoard
    //Description:  Displays the current Othello board using extended Unicode characters. Looks fine
    //               in a command window but may not display well in the NetBeans IDE...
    //Parameters:   none
    //Calls:        nothing
    //Returns:      nothing
    public void showBoard() {
        int r,c;
        System.out.print("  ");                         //column identifiers
        for (c = 0; c < boardCols; c++){
            System.out.print(" "+(char)(c+65));
        }
        System.out.println();
        
        //top border
        System.out.print("  "+(char)9484);                   //top left corner \u250C
        for (c = 0; c < boardCols - 1; c++){
            System.out.print((char)9472);               //horizontal \u2500
            System.out.print((char)9516);               //vertical T \u252C
        }
        System.out.print((char)9472);                   //horizontal \u2500
        System.out.println((char)9488);                 //top right corner \u2510
       
        //board rows
        for (r = 0; r < boardRows; r++) {
            System.out.print(" "+(char)(r+65));         //row identifier
            System.out.print((char)9474);               //vertical \u2502
            for (c = 0; c < boardCols; c++){
                System.out.print(board[r][c]);
                System.out.print((char)9474);           //vertical \u2502
            }
            System.out.println();
            
            //insert row separators
            if (r < boardRows - 1) {
                System.out.print("  "+(char)9500);           //left T \u251C
                for (c = 0; c < boardCols - 1; c++){
                    System.out.print((char)9472);       //horizontal \u2500
                    System.out.print((char)9532);       //+ (cross) \u253C
                }
                System.out.print((char)9472);           //horizontal \u2500
                System.out.println((char)9508);         //right T \u2524
            }
        }

        //bottom border
        System.out.print("  "+(char)9492);                   //lower left corner \u2514
        for (c = 0; c < boardCols - 1; c++){
            System.out.print((char)9472);               //horizontal \u2500
            System.out.print((char)9524);               //upside down T \u2534
        }
        System.out.print((char)9472);                   //horizontal \u2500
        System.out.println((char)9496);                 //lower right corner \u2518
        
        return;
    }
    //***************************************************************************************************
}
//*******************************************************************************************************
//*******************************************************************************************************

