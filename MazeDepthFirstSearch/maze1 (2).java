package maze;

public class maze1 {

    public static int currentrow;
    public static int currentcolumn;
    public static int currentlocation[][];
    public static int originallocation[][];
    public static int pathvalue = 0;
    public static int temppathvalue = 0;

    public static void main(String[] args) {
        TextFileClass textFile = new TextFileClass();
        textFile.getFileName("Input the name of maze file: ");
        int linecount = textFile.getFileContents();
        int rows = Integer.parseInt(textFile.text[0]);
        int columns = Integer.parseInt(textFile.text[1]);
        int temprow = Integer.parseInt(textFile.text[2]);
        int tempcolumn = Integer.parseInt(textFile.text[3]);
        currentrow = temprow;
        currentcolumn = tempcolumn;
        int tempint2array[][] = new int[rows][columns];
        tempint2array = createMaze(tempint2array, rows, columns, textFile);
        tempint2array[currentrow][currentcolumn] = 2;
        for (int[] tempint2array1 : tempint2array) {
            for (int j = 0; j < tempint2array1.length; j++) {
                System.out.print(tempint2array1[j]);
            }
            System.out.println();
        }
        System.out.println("____________");
        System.out.println();
        currentlocation = tempint2array;
        originallocation = currentlocation;
        if (currentlocation[currentrow][currentcolumn] == 2 || currentlocation[currentrow][currentcolumn] == 1 || currentlocation[currentrow][currentcolumn] == 0) {
            moveLocation1(currentlocation);
            for (int[] currentlocation1 : currentlocation) {
                for (int j = 0; j < currentlocation1.length; j++) {
                    System.out.print(currentlocation1[j]);
                }
                System.out.println();
            }
        }
        
        System.out.println(pathvalue);

    }//End of main Method

    public static int[][] createMaze(int int2array[][], int nrows, int ncolumns, TextFileClass textFile) {
        char char2array[][] = new char[nrows][ncolumns];
        if (textFile.fileName.length() > 0) {
            for (int i = 0; i < textFile.fileName.length() - 4; i++) {
                String temp = textFile.text[i + 4];
                for (int j = 0; j < ncolumns; j++) {
                    char2array[i][j] = temp.charAt(j);
                    int2array[i][j] = Character.getNumericValue(char2array[i][j]);
                }
            }
            for (int[] int2array1 : int2array) {
                for (int j = 0; j < int2array1.length; j++) {
                    System.out.print(int2array1[j]);
                }
                System.out.println();
            }
            System.out.println("_______________");
            System.out.println();
        }
        return int2array;
    }//End of createMaze Method

    public static int[][] moveLocation1(int move2array[][]) {

        currentlocation = move2array;
        for (int i = 0; i < currentlocation.length; i++) {

            checkforJunction();
            checkforPath();
        }
        return move2array;
    }

    public static int[][] checkforJunction() {
        try {
            if (currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2) {
                currentlocation[currentrow][currentcolumn] = 3;

            }//End of junction test #1
            else if (currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2) {
                currentlocation[currentrow][currentcolumn] = 3;

            }//End of junction test #2
            else if (currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    || currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    || currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    || currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    || currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    || currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 2
                    || currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    || currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn - 1] == 2) {
                currentlocation[currentrow][currentcolumn] = 3;
            }//End of junction test #3
            else if (currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2) {
                currentlocation[currentrow][currentcolumn] = 3;
            }//End of junction test #4
            else if (currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 2
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    || currentlocation[currentrow][currentcolumn - 1] == 2
                    && currentlocation[currentrow - 1][currentcolumn] == 2
                    && currentlocation[currentrow][currentcolumn + 1] == 2
                    && currentlocation[currentrow + 1][currentcolumn] == 2) {
                currentlocation[currentrow][currentcolumn] = 3;

            }//End of junction test #5
            
            else if (
                       currentlocation[currentrow][currentcolumn - 1] == 3
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 3
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    
                    || currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 3
                    
                    ) {
                currentlocation[currentrow][currentcolumn] = 3;
            }
            else if (
                       currentlocation[currentrow + 1][currentcolumn] == 3
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    
                    || currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 3
                    && currentlocation[currentrow - 1][currentcolumn] == 0
                    
                    || currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    && currentlocation[currentrow - 1][currentcolumn] == 3                    
                    ){
                currentlocation[currentrow][currentcolumn] = 3;
            }
            else if (
                       currentlocation[currentrow][currentcolumn + 1] == 3
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    
                    || currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 3
                    && currentlocation[currentrow][currentcolumn - 1] == 0
                    
                    || currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn - 1] == 3
                    ){
                currentlocation[currentrow][currentcolumn] = 3;
            }
            else if (
                       currentlocation[currentrow - 1][currentcolumn] == 3
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 3
                    && currentlocation[currentrow + 1][currentcolumn] == 0
                    
                    || currentlocation[currentrow - 1][currentcolumn] == 0
                    && currentlocation[currentrow][currentcolumn + 1] == 0
                    && currentlocation[currentrow + 1][currentcolumn] == 3 
                    ){
                currentlocation[currentrow][currentcolumn] = 3;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("This is an exit");
        }

        printMaze();
        return currentlocation;
    }

    public static int[][] checkforPath() {
        try {
            for (int i = 0; i < currentlocation.length; i++) {
                if(deadEnd() == true){
                    traverseLocation();
                }
                checkforJunction();

                if (currentlocation[currentrow][currentcolumn - 1] == 0) {//Going left
                    currentlocation[currentrow][currentcolumn - 1] = 2;
                    currentcolumn = currentcolumn - 1;
                    pathvalue++;
                    checkforExit();
                    printMaze();
                } else if (currentlocation[currentrow - 1][currentcolumn] == 0) {//Going up
                    currentlocation[currentrow - 1][currentcolumn] = 2;
                    currentrow = currentrow - 1;
                    pathvalue++;
                    checkforExit();
                    printMaze();
                } else if (currentlocation[currentrow][currentcolumn + 1] == 0) {//Going right
                    currentlocation[currentrow][currentcolumn + 1] = 2;
                    currentcolumn = currentcolumn + 1;
                    pathvalue++;
                    checkforExit();
                    printMaze();
                } else if (currentlocation[currentrow + 1][currentcolumn] == 0) {//Going down
                    currentlocation[currentrow + 1][currentcolumn] = 2;
                    currentrow = currentrow + 1;
                    pathvalue++;
                    checkforExit();
                    printMaze();
                }
        
            }

        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return currentlocation;
    }

    public static void checkforExit() {
        try {
            if (currentlocation[currentrow][currentcolumn - 1] != 0
                    && currentlocation[currentrow - 1][currentcolumn] != 0
                    && currentlocation[currentrow][currentcolumn + 1] != 0
                    && currentlocation[currentrow + 1][currentcolumn] != 0
                    && currentlocation[currentrow][currentcolumn - 1] != 1
                    && currentlocation[currentrow - 1][currentcolumn] != 1
                    && currentlocation[currentrow][currentcolumn + 1] != 1
                    && currentlocation[currentrow + 1][currentcolumn] != 1
                    && currentlocation[currentrow][currentcolumn - 1] != 2
                    && currentlocation[currentrow - 1][currentcolumn] != 2
                    && currentlocation[currentrow][currentcolumn + 1] != 2
                    && currentlocation[currentrow + 1][currentcolumn] != 2
                    && currentlocation[currentrow][currentcolumn - 1] != 3
                    && currentlocation[currentrow - 1][currentcolumn] != 3
                    && currentlocation[currentrow][currentcolumn + 1] != 3
                    && currentlocation[currentrow + 1][currentcolumn] != 3)
            {
                
            System.out.println("We found an exit!");
            temppathvalue = pathvalue;
            traverseLocation();
            } 
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("We found an exit!");
            traverseLocation();
        }

    }//End of exitFound method

    public static void traverseLocation() {
        if(currentlocation[currentrow][currentcolumn] == 2){
            currentlocation[currentrow][currentcolumn] = 4;
        }
        printMaze();
        for (int i = 0; i < currentlocation.length; i++) {
            checkforJunction();
            if (currentlocation[currentrow][currentcolumn + 1] == 2) {//Going right
                currentlocation[currentrow][currentcolumn + 1] = 4;
                currentcolumn = currentcolumn + 1;
                pathvalue--;
                printMaze();

            } else if (currentlocation[currentrow + 1][currentcolumn] == 2) {//Going down
                currentlocation[currentrow + 1][currentcolumn] = 4;
                currentrow = currentrow + 1;
                pathvalue--;
                printMaze();

            } else if (currentlocation[currentrow][currentcolumn - 1] == 2) {//Going left
                currentlocation[currentrow][currentcolumn - 1] = 4;
                currentcolumn = currentcolumn - 1;
                pathvalue--;
                printMaze();

            } else if (currentlocation[currentrow - 1][currentcolumn] == 2) {//Going up
                currentlocation[currentrow - 1][currentcolumn] = 4;
                currentrow = currentrow - 1;
                pathvalue--;
                printMaze();

            }
            traversefromJunction();
        }

    }
    
    public static void traversefromJunction(){
                    if (currentlocation[currentrow][currentcolumn + 1] == 3) {//Going right

                currentlocation[currentrow][currentcolumn + 1] = 3;
                currentcolumn = currentcolumn + 1;
                pathvalue--;
                printMaze();
                //traverseLocation();
            } else if (currentlocation[currentrow + 1][currentcolumn] == 3) {//Going down

                currentlocation[currentrow + 1][currentcolumn] = 3;
                currentrow = currentrow + 1;
                pathvalue--;
                printMaze();
                //traverseLocation();
            } else if (currentlocation[currentrow][currentcolumn - 1] == 3) {//Going left

                currentlocation[currentrow][currentcolumn - 1] = 3;
                currentcolumn = currentcolumn - 1;
                pathvalue--;
                printMaze();
                //traverseLocation();
            } else if (currentlocation[currentrow - 1][currentcolumn] == 3) {//Going up

                currentlocation[currentrow - 1][currentcolumn] = 3;
                currentrow = currentrow - 1;

                pathvalue--;
                printMaze();
                //traverseLocation();
            }
    }    

    public static boolean deadEnd() {
        
        if (
                   currentlocation[currentrow][currentcolumn - 1] == 1
                && currentlocation[currentrow - 1][currentcolumn] == 1
                && currentlocation[currentrow][currentcolumn + 1] == 1
                || currentlocation[currentrow - 1][currentcolumn] == 1
                && currentlocation[currentrow][currentcolumn - 1] == 1
                && currentlocation[currentrow + 1][currentcolumn] == 1
                || currentlocation[currentrow][currentcolumn - 1] == 1
                && currentlocation[currentrow + 1][currentcolumn] == 1
                && currentlocation[currentrow][currentcolumn + 1] == 1
                || currentlocation[currentrow + 1][currentcolumn] == 1
                && currentlocation[currentrow][currentcolumn + 1] == 1
                && currentlocation[currentrow - 1][currentcolumn] == 1) {
            return true;

        } else {
            return false;
        }
    }

    public static int[][] printMaze() {

        for (int[] currentlocation1 : currentlocation) {
            for (int j = 0; j < currentlocation1.length; j++) {
                System.out.print(currentlocation1[j]);

            }
            System.out.println();
        }
        System.out.println("_______________");
        System.out.println();
        return currentlocation;
    }//End of printMaze Method

}//End of class

/*
 To Do:
 -Finish printMaze Method

 */
