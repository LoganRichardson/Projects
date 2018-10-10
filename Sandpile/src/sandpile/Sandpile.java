//Program:	CyrstalCA
//Course:	COSC460
//Description:  This program represents a sandpile model which will cause avalances if the any of the side sand locations
//              exceed the criticalPoint which is user defined.  It will then calculate the avalance size and count then proceed
//              to find the power law exponent of the said values.     
//Author:	Logan Richardson
//Revised:	11/3/2016
//Language:	Java
//IDE:		NetBeans 8.1
//Notes:	No notes
//*******************************************************************************
//*******************************************************************************
package sandpile;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//Class:	CrystalCA
//Description:	The main class that contains all my globals.  These globals are being defined in the 
//              main class because it makes it easier to access when using them in my methods of
//              specified neighbors.  I chose these variables to be global because, in particular, they
//              make the code, that when werent declared, made passing them through methods clutter-some.
//              Making them global eases the strain of passing them through and makes it easier to read.
//scoreBoard:   Tracks the score for each place in the sandpile which is compared to the criticalPoint      
//criticalPoint:Stores the value that represents the climax of the sandpile and avalances when compard values = it         
//startingrow:  Stores the value for the starting row position    
//startingcol:  Stores the value for the starting column position
//neighborG:    Holds the value for what neighbor hood we're going to use
//randomSand:   Holds the value for if were going to place random grains of sand or not
//distribution: Holds the values for avalance size and count
//globalY:      Stores the values for the sum of largest numbers in the first bin
//trackY:       Stores the values for the sum of smallest numbers in bin
//globalsize:   Tracks and counts the number of values > than 0 there are in distribution
//rowsG:        Holds the value for number of rows
//colsG:        Holds the value for number of columns
//scaleG:        Holds the values for the scale of the image
public class Sandpile {

    public static int scoreBoard[][];
    public static int criticalPoint;
    public static int[][] lastboard;
    public static int startingrow;
    public static int startingcol;
    public static int neighborG;
    public static boolean randomSand = false;
    public static int[] distribution = new int[100];
    public static int[] updatedDistribution;
    public static double globalY = 0;
    public static double globalX = 0;
    public static double lowestY = 0;
    public static double lowestX = 0;
    public static int trackY;
    public static int globalsize;
    public static int rowsG;
    public static int colsG;
    public static int scaleG;
    public static int size;
    public static int fulldepth = 0;
    public static ImageConstruction cons;

//Method:	copyboard
//Description:	Takes in the lastboard to act as the new board have all contents of oldboard copied from.
//              Its primary use is to create a new board from an old board to keep track of our location.
//Parameters:  	newboard:	The acting 2D array for our newboard to be made
//            	oldboard: 	The board before any steps or changes were made to have contents copied to newboard
//             	rows:   	The number of rows in our CA
//             	cols:   	The number of columns in our CA
//Returns:     	Nothing
//Calls:       	Nothing
//Globals:	None
//********************************************************************
//Method:	menuMethod
//Description:	The method menuMethod is where everything happens.  The primary reason for having all this in another 
//              method instead of the main method is because when having to exit back to the main menu if the user
//              types in '0', its easier to just call menuMethod again to bring the user to the beginning again.
//              This method also declares our color values in colorArray and sets up the image class
//Parameters:  	None
//Returns:     	Nothing
//Calls:        menuPriority
//Globals:	startingRow
//              startingCol
    public static void menuMethod() {
        int[][] colorArray = new int[20][20];
        colorArray[0][0] = 0;
        colorArray[0][1] = 0;
        colorArray[0][2] = 0;

        colorArray[1][0] = 0;
        colorArray[1][1] = 255;
        colorArray[1][2] = 0;

        colorArray[2][0] = 0;
        colorArray[2][1] = 0;
        colorArray[2][2] = 255;

        colorArray[3][0] = 147;
        colorArray[3][1] = 112;
        colorArray[3][2] = 219;

        colorArray[4][0] = 0;
        colorArray[4][1] = 255;
        colorArray[4][2] = 255;

        colorArray[5][0] = 127;
        colorArray[5][1] = 127;
        colorArray[5][2] = 127;

        colorArray[6][0] = 230;
        colorArray[6][1] = 230;
        colorArray[6][2] = 250;

        colorArray[7][0] = 148;
        colorArray[7][1] = 0;
        colorArray[7][2] = 211;

        colorArray[8][0] = 128;
        colorArray[8][1] = 0;
        colorArray[8][2] = 128;

        colorArray[9][0] = 255;
        colorArray[9][1] = 0;
        colorArray[9][2] = 255;

        colorArray[10][0] = 221;
        colorArray[10][1] = 160;
        colorArray[10][2] = 221;

        colorArray[11][0] = 255;
        colorArray[11][1] = 192;
        colorArray[11][2] = 203;

        colorArray[12][0] = 125;
        colorArray[12][1] = 38;
        colorArray[12][2] = 87;

        colorArray[13][0] = 218;
        colorArray[13][1] = 112;
        colorArray[13][2] = 214;

        colorArray[14][0] = 0;
        colorArray[14][1] = 0;
        colorArray[14][2] = 128;

        colorArray[15][0] = 65;
        colorArray[15][1] = 105;
        colorArray[15][2] = 225;

        colorArray[16][0] = 51;
        colorArray[16][1] = 161;
        colorArray[16][2] = 201;

        colorArray[17][0] = 0;
        colorArray[17][1] = 255;
        colorArray[17][2] = 127;

        colorArray[18][0] = 0;
        colorArray[18][1] = 201;
        colorArray[18][2] = 87;

        colorArray[19][0] = 152;
        colorArray[19][1] = 251;
        colorArray[19][2] = 152;

        System.out.println("Main menu");
        int[][] red = new int[50][50];
        int[][] green = new int[50][50];
        int[][] blue = new int[50][50];
        int width = 100;
        int height = 100;
        size = width * height;
        cons = new ImageConstruction(height, width, 1, 101, 1, 101, 4);
        cons.displaySetup();
        cons.setPixelValues();
        KeyboardInputClass numbervalue = new KeyboardInputClass();
        int ourNumber = 0;
        System.out.println();

        while (ourNumber < 0 || ourNumber >= 12);
        int temprow = startingrow * 2;
        int tempcol = startingcol * 2;
        menuPriority(cons, numbervalue, colorArray, temprow, tempcol);

    }//End of menuMethod
//********************************************************************
    //Method:	menuPriority
//Description:	This method is where the options layout is introduced.  Based off whta the user selects, the program will either
//              start a new run, continue the run, show what we currently have for the run, or exit the program entirely.
//Parameters:  	cons
//              numbervalue
//              colorArray
//              temprow
//              tempcol
//Returns:     	Nothing
//Calls:        powerlawMethod
//              newRun
//              continueRun
//Globals:	distribution
//              globalsize
//              neighborG
//              startingrow
//              startingcol

    public static void menuPriority(ImageConstruction cons, KeyboardInputClass numbervalue, int[][] colorArray, int temprow, int tempcol) {
        System.out.println("1. Initialize new run.");
        System.out.println("2. Continue current run.");
        System.out.println("3. Show summary results of current run.");
        System.out.println("4. Exit");
        int optionChoice = numbervalue.getInteger(true, 1, 1, 5, "Choose your option.");

        if (optionChoice == 1) {
            newRun(cons, numbervalue, colorArray);
        }
        if (optionChoice == 2) {
            continueRun(cons, numbervalue, colorArray, startingrow, startingcol, temprow, tempcol, neighborG);
        }
        if (optionChoice == 3) {

            System.out.println("Avalance Distribution:");
            System.out.println("Size     -     Count");
            System.out.println("---------------------");
            for (int ix = 0; ix < distribution.length; ix++) {
                if (distribution[ix] != 0) {
                    System.out.println(" " + ix + "\t\t" + distribution[ix]);
                } // end of if
            }
            System.out.println();
            int bin = numbervalue.getInteger(true, 10, 1, 10000, "Enter the bin size - [default: 10]");
            int[] size = new int[globalsize];
            int highest = 0;
            int[] count = new int[globalsize];
            int temp = 0;
            for (int ix = 0; ix < 100; ix++) {
                if (distribution[ix] != 0) {
                    size[temp] = ix;
                    if (highest < size[temp]) {
                        highest = size[temp];
                    }
                    count[temp] = distribution[ix];
                    temp++;
                } // end of if
                if (temp == globalsize) {
                    powerlawMethod(bin, size, count, highest, ix);
                    ix = 100;
                }
            }
        }
        if (optionChoice == 4) {
            cons.closeDisplay();
            System.exit(0);
        }
    }// end of menuPriority
//********************************************************************
    //Method:	powerlawMethod
//Description:	This method computes the power law for the avalance size and the avalance count with the given run.  It will then
//              output the power law exponent based off its calcluations.
//Parameters:  	bin
//              size
//              count
//              highest
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	distribution
//              globalsize
//              globalY
//              trackY

    public static void powerlawMethod(int bin, int[] size, int[] count, int highest, int ix) {
        findHighest(size, highest, bin);
        findLowest(size, highest, bin);
        int ylog = (int) Math.log10(globalY) - (int) Math.log10(lowestY);
        int xlog = (int) Math.log10(bin / 2) - (int) Math.log10(lowestX);
        double slope = ylog / xlog;
        System.out.println("Power Law Exponent: " + slope);
    }
//********************************************************************
//Method:	findHighest
//Description:	This method finds the highest values of avalanche sizes within the range of the UI
//              given bin.  Doesnt return anything but changes global values which are to be used in
//              the powerLaw method
//Parameters:  	bin
//              size
//              highestValue
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	distribution
//              globalY
//              globalX

    public static void findHighest(int[] size, int highestValue, int bin) {
        highestValue = 0;
        int highestCount = 0;
        int[] tempArray = new int[distribution.length];
        ArrayList<Integer> aList = new ArrayList<Integer>();
        ArrayList<Integer> cList = new ArrayList<Integer>();
        for (int i = 0; i < distribution.length; i++) {
            aList.add(distribution[i]);
            cList.add(i);
        }
        for (int i = 0; i < aList.size(); i++) {
            if (!aList.get(i).equals(0)) {
                if (cList.get(i) < bin) {
                    //if (aList.get(i) > highestValue) {
                    highestValue = aList.get(i);
                    highestCount = cList.get(i);
                    globalY += highestValue;
                    globalX += highestCount;
                    //System.out.println(">" + highestCount + "\t\t" + highestValue);
                    //aList.remove(i);
                    //cList.remove(i);
                    //}
                }
            }
        }
        //System.out.println("Y: " + globalY);
        //System.out.println("X: " + globalX);
    }
//********************************************************************
//Method:	findLowest
//Description:	This method finds the lowest values of avalanche sizes within the range of the UI
//              given bin.  Doesnt return anything but changes global values which are to be used in
//              the powerLaw method
//Parameters:  	bin
//              size
//              lowestValue
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	distribution
//              lowestY
//              lowestX

    public static void findLowest(int[] size, int lowestValue, int bin) {
        int tempbin = bin;
        lowestValue = 0;
        int lowestCount = 0;
        int countPoint = 0;
        ArrayList<Integer> aList = new ArrayList<Integer>();
        ArrayList<Integer> cList = new ArrayList<Integer>();
        for (int i = 0; i < distribution.length; i++) {
            aList.add(distribution[i]);
            cList.add(i);
            if (distribution[i] != 0) {
                countPoint = i;
            }
        }
        int inc = cList.indexOf(distribution.length);
        //System.out.println("bin values: " + countPoint / bin);
        for (int i = 0; i < aList.size(); i++) {
            if (!aList.get(i).equals(0)) {
                if (tempbin + bin < countPoint) {
                    tempbin += 10;
                }
                if (cList.get(i) <= countPoint && cList.get(i) > tempbin) {
                    //if (aList.get(i) > highestValue) {
                    lowestValue = aList.get(i);
                    lowestCount = cList.get(i);
                    lowestY += lowestValue;
                    lowestX += lowestCount;
                    //System.out.println(">" + lowestCount + "\t\t" + lowestValue);
                    //aList.remove(i);
                    //cList.remove(i);
                    //}
                }
            }

        }
        //System.out.println("Y: " + lowestY);
        //System.out.println("X: " + lowestX);
    }
//********************************************************************
//Method:	optionFour
//Description:	Simply carries out option fours request to exit the current run and go back tot he main menu
//Parameters:  	cons
//              numbervalue
//              colorArray
//              rows
//              columns
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	Nothing

    public static void optionFour(ImageConstruction cons, KeyboardInputClass numbervalue, int[][] colorArray, int rows, int columns) {
        cons.closeDisplay();
        menuPriority(cons, numbervalue, colorArray, rows, columns);
    }
//********************************************************************
//Method:	newRun
//Description:	Creates a new run under option fours request by getting image requirements, setting the start rows and columns
//              and declaring our starting position in the sand model, then passes said values to the next method to run it.
//Parameters:  	cons
//              numbervalue
//              colorArray
//Returns:     	Nothing
//Calls:        continueRun
//Globals:	rowsG
//      	colsG
//              scaleG
//              criticalPoint
//              neighborG
//              randomSane
//              scoreBoard

    public static void newRun(ImageConstruction cons, KeyboardInputClass numbervalue, int[][] colorArray) {

        char window = numbervalue.getCharacter(true, 'n', "y,n", 0, "Do you want to specify window dimensions? [y/n]");
        if (window == 'y') {
            rowsG = numbervalue.getInteger(true, 100, 1, 10000, "Enter the number of rows - [default: 100]");
            colsG = numbervalue.getInteger(true, 100, 1, 10000, "Enter the number of columns - [default: 100]");;
            scaleG = numbervalue.getInteger(true, 4, 1, 10000, "Enter the scale size - [default: 4]");
        }
        if (window == 'n') {
            rowsG = 100;
            colsG = 100;
            scaleG = 4;
        }
        distribution = new int[rowsG * colsG];
        //cons.clearImage(rowsG, size, size);
        //cons = new ImageConstruction(colsG, rowsG, 1, colsG, 1, rowsG, 4);

        criticalPoint = numbervalue.getInteger(true, 3, 1, 20, "Enter the critical value - [default: 3]");
        neighborG = numbervalue.getInteger(true, 1, 1, 2, "Von Neuman Neighborhood [1]  :  Moore Neighborhood [2] - [default: 1]");
        int sandPlacement = numbervalue.getInteger(true, 1, 1, 2, "Add sand at center? [1]  -  Add sand randomly? [2]  :  [default: 1]");
        if (sandPlacement == 1) {
            randomSand = false;
        }
        if (sandPlacement == 2) {
            randomSand = true;
        }
        int neighbor = neighborG;

        scoreBoard = new int[rowsG][colsG];
        for (int i = 0; i < scoreBoard.length; i++) {
            for (int j = 0; j < scoreBoard.length; j++) {
                scoreBoard[i][j] = 0;
                cons.insertBox(i, j, i, j, 0, 0, 0, true);

            }

        }
        int startRow = rowsG / 2;
        int startColumn = colsG / 2;
        startingrow = startRow;
        startingcol = startColumn;
        System.out.printf("startRow: " + startRow + "\nstartColumn: " + startColumn + "\n");

        continueRun(cons, numbervalue, colorArray, startRow, startColumn, rowsG, colsG, neighbor);
    }//end of choice if
//********************************************************************
//Method:	continueRun
//Description:	The rest of the run code that was not put in newRun is put in here for the purpose of delivering options 2's request
//              to coninue the run after being exited out of.  The code in here picks right off from where the user will have exited
//Parameters:  	cons
//              numbervalue
//              colorArray
//              startingrow
//              startingcol
//              rows
//              columns
//              neighbor
//Returns:     	Nothing
//Calls:        optionFour
//              paramMethod
//Globals:	Nothing

    public static void continueRun(ImageConstruction cons, KeyboardInputClass numbervalue, int[][] colorArray, int startRow, int startColumn, int rows, int columns, int neighbor) {
        for (int ndx = 0; ndx < 99999; ndx++) {
            //int temp = numbervalue.getInteger(true, -1, 0, 1000, "Number of updates: (0 to go to main menu)");
            int temp = numbervalue.getInteger(true, -1, 0, 1000000, "Number of updates: (0 to go to main menu)");
            if (temp < 0) {
                paramMethod(startRow, startColumn, cons, colorArray, rows, columns, neighbor);
                //cons.displayImage(true, "test", true);
            }

            if (temp == 0) {
                optionFour(cons, numbervalue, colorArray, rows, columns);
            }
            if (temp > 0) {

                for (int i = 1; i <= temp; i++) {
                    //copyboard(lastboard, scoreBoard, rows, columns); // dest<--src
                    paramMethod(startRow, startColumn, cons, colorArray, rows, columns, neighbor);
                    //cons.displayImage(true, "test", true);
                }
            }
            cons.displayImage(true, "test", true);

        } // end of for
    } // end for
//********************************************************************
//Method:       main
//Description:	Has no other purose other than to call menuPriority to be called to carry out the program.
//Parameters:  	Nothing
//Returns:     	Nothing
//Calls:        menuPriority
//Globals:	Nothing

    public static void main(String[] args) {
        menuMethod();

    }//End of main method
//********************************************************************
//Method:	paramMethod
//Description:	paramMethod checks all the parameters, or the neighborhood, around each cell folling Moores rule or Neuman rule.
//              It checks all surrounding parameters and adds a counter to the scoreBoard location everytime a 
//              neighbor cell is changed.  If the neighbor count matches the criticalPoint value,then we make the current pixel/cell its respective color
//              developing by checking the border cells by counting the cells on the other side of the CA.
//Parameters:  	startrow
//              startcol
//              cons
//              colorArray
//              rows
//              cols
//              neighbor
//Returns:     	Nothing
//Calls:        vonneumanMethod
//              mooreMethod
//Globals:	randomSand
//              scoreBoard
//              criticalPoint
//              distribution

    public static void paramMethod(int startrow, int startcol, ImageConstruction cons, int[][] colorArray, int rows, int cols, int neighbor) {
        //System.out.println("do we get here");
        //cons.insertBox(startrow, startcol, startrow, startcol, 255, 0, 0, true);

        int totalrows = rows;
        int totalcols = cols;
        //scoreBoard[startrow][startcol] = scoreBoard[startrow][startcol] + 1;
        //cons.insertBox(startrow, startcol, startrow, startcol, 255, 0, 0, true);
        Random ran = new Random();
        int x = ran.nextInt(totalrows) + 1;
        int y = ran.nextInt(totalrows) + 1;
        for (int i = 0; i < totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {

                if (randomSand == true) {
                    if (scoreBoard[i][j] <= criticalPoint && i == x && j == y) {

                        scoreBoard[i][j]++;
                        cons.insertBox(i, j, i, j, colorArray[scoreBoard[i][j]][0], colorArray[scoreBoard[i][j]][1], colorArray[scoreBoard[i][j]][2], true);
                    }
                }
                if (randomSand == false) {
                    if (scoreBoard[i][j] <= criticalPoint && i == startrow && j == startcol) {

                        scoreBoard[i][j]++;
                        cons.insertBox(i, j, i, j, colorArray[scoreBoard[i][j]][0], colorArray[scoreBoard[i][j]][1], colorArray[scoreBoard[i][j]][2], true);
                    }
                }
                if (scoreBoard[i][j] > criticalPoint && neighbor == 1) {
                    fulldepth = vonneumanMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                    vonneumanMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                }
                if (scoreBoard[i][j] > criticalPoint && neighbor == 2) {
                    fulldepth = mooreMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                    mooreMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                }

            }
        }
        if (fulldepth != 0) {
            System.out.println("Full Depth:" + (fulldepth - 1));
            distribution[fulldepth] += 1;
        }
    }//End of paramMethod method
//********************************************************************
//Method:	vonneumanMethod
//Description:	Adds colors/values to scoreBoard based off its Von Neuman neighbors and tracks the depth of the avalance
//Parameters:  	cons
//              colorArray
//              i
//              j
//              startrow
//              startcol
//              neighbor
//Returns:     	depth + 1'
//Calls:        Nothing
//Globals:	scoreBoard
//      	criticalPoint

    public static int vonneumanMethod(ImageConstruction cons, int[][] colorArray, int i, int j, int startrow, int startcol, int neighbor) {
        int rows = startrow * 2;
        int cols = startcol * 2;
        int depth = 0;
        if (neighbor == 1) {
            for (int t = 0; t < criticalPoint; t++) {
                for (int k = 0; k < rows; k++) {
                    for (int p = 0; p < cols; p++) {

                        if (scoreBoard[k][p] > criticalPoint && scoreBoard[k][p] > 0 && scoreBoard[k][p] < rows) {
                            //if(scoreBoard[k][p])
                            scoreBoard[k][p] = 0;
                            scoreBoard[k - 1][p]++;
                            cons.insertBox(k - 1, p, k - 1, p, colorArray[scoreBoard[k - 1][p]][0], colorArray[scoreBoard[k - 1][p]][1], colorArray[scoreBoard[k - 1][p]][2], true);

                            scoreBoard[k + 1][p]++;
                            cons.insertBox(k + 1, p, k + 1, p, colorArray[scoreBoard[k + 1][p]][0], colorArray[scoreBoard[k + 1][p]][1], colorArray[scoreBoard[k + 1][p]][2], true);

                            scoreBoard[k][p - 1]++;
                            cons.insertBox(k, p - 1, k, p - 1, colorArray[scoreBoard[k][p - 1]][0], colorArray[scoreBoard[k][p - 1]][1], colorArray[scoreBoard[k][p - 1]][2], true);

                            scoreBoard[k][p + 1]++;
                            cons.insertBox(k, p + 1, k, p + 1, colorArray[scoreBoard[k][p + 1]][0], colorArray[scoreBoard[k][p + 1]][1], colorArray[scoreBoard[k][p + 1]][2], true);

                            cons.insertBox(k, p, k, p, colorArray[scoreBoard[k][p]][0], colorArray[scoreBoard[k][p]][1], colorArray[scoreBoard[k][p]][2], true);
                            //depth += vonneumanMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                            //depth++;
                            fulldepth = depth++;
                        }// end of if
                    }// end of p loop
                }// end of k loop
            }// end of neighbor if
        }
        return depth + 1;
    } // end of VN
//********************************************************************
//Method:	mooreMethod
//Description:	Adds colors/values to scoreBoard based off its Moore neighbors and tracks the depth of the avalance
//Parameters:  	cons
//              colorArray
//              i
//              j
//              startrow
//              startcol
//              neighbor
//Returns:     	depth + 1'
//Calls:        Nothing
//Globals:	scoreBoard
//      	criticalPoint

    public static int mooreMethod(ImageConstruction cons, int[][] colorArray, int i, int j, int startrow, int startcol, int neighbor) {
        int rows = startrow * 2;
        int cols = startcol * 2;
        int depth = 0;
        for (int k = 1; k < rows; k++) {
            for (int p = 1; p < cols; p++) {
                if (scoreBoard[k][p] > criticalPoint && k < scoreBoard.length - 1 && p < scoreBoard.length - 1 && scoreBoard[k][p] <= colorArray.length) {
                    //if(scoreBoard[k][p])
                    scoreBoard[k][p] = 0;

                    for (int dcol = -1; dcol < 2; dcol++) {
                        for (int drow = -1; drow < 2; drow++) {
                            if (dcol != 0 && drow != 0) {
                                scoreBoard[k + drow][p + dcol]++;
                                cons.insertBox(k + drow, p + dcol, k + drow, p + dcol, colorArray[scoreBoard[k + drow][p + dcol]][0], colorArray[scoreBoard[k + drow][p + dcol]][1], colorArray[scoreBoard[k + drow][p + dcol]][2], true);
                            }
                        }
                    }
                    cons.insertBox(k, p, k, p, colorArray[scoreBoard[k][p]][0], colorArray[scoreBoard[k][p]][1], colorArray[scoreBoard[k][p]][2], true);
                    fulldepth = depth++;

                    //depth += mooreMethod(cons, colorArray, i, j, startrow, startcol, neighbor);
                }// end of if

            }// end of p loop
            //System.out.println();

        }// end of k loop
        return depth + 1;
    }// end of neighbor if

//********************************************************************
}//End of class

//*******************************************************************************
//*******************************************************************************

