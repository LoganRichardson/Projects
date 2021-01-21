//Program:	Project
//Course:	COSC460
//Description:  This program replicates entities in an infinate 2D space.  These entites will attract to other
//              entities or move away depending on each corresponding entities properties such as mass position
//              velocity etc.  It will satisfy the conditions if they collide in either a parallel field or
//              by crossing paths.
//Revised:	12/9/2016
//Language:	Java
//IDE:		NetBeans 8.1
//Notes:	No notes
//*******************************************************************************
//*******************************************************************************
package project;

//import java.util.ArrayList;
import java.util.Random;
//Class:	Project
//Description:	This is the main class.  This class contains all my original calls to other classes
//              and creates multiple globals such as the ones for storing the users inputs as the defaults
//              for their next run if they reset.  It also contains the methods for coloring the shapes
//              and it contains all the UI information and input.
//shapeColor:   Stores the UI value for if they want the shapes outlines or filled     
//timeStep:     The value held for the standard time step        
//workingT:     Holds the value for the current working time.  
//width:        The width value for the image created
//height:       The height value for the image created
//circlesUIDefault: The value holding the default of the circle amount from user input
//positionUIDefault: The value holding the default of the position from user input
//radiusUIDefault: The value holding the default of the radius from user input
//iterationUIDefault: The value holding the default of the iteration from user input
//pLeft:        The Left viewing window value
//pRight:       The Right viewing window value
//pBottom:      The Bottom viewing window value
//pTop:         The Top viewing window value
//xxUIDefault:  The value holding the default x entity location from the user
//yyUIDefault:  The value holding the default y entity location from the user
//rrUIDefault:  The value holding the default radius entity location from the user
//identicalRandom: Random number stored in a global
//graphicsDelay:The amount of which the graphics will be delayed before displaying
//cons:         The ImageConstruction reference.
//MaxNoOfObjects:Object holding value for maximum number of entities
//sObject:      The sObject reference
//G:            The Gravity reference

public class Project {

    public static int additionalEntities = 0;
    public static boolean addEntity = false;
    public static char shapeColor = 'o';
    public static double timeStep = 1;
    public static double workingT;
    public static int width;
    public static int height;
    public static int circlesUIDefault = 2;
    public static int positionUIDefault = 2;
    public static int radiusUIDefault = 2;
    public static int iterationUIDefault = 0;
    public static int pLeft;
    public static int pRight;
    public static int pBottom;
    public static int pTop;
    public static int circles = 2;
    public static double xxUIDefault = 20;
    public static double yyUIDefault = 20;
    private static double rrUIDefault = 20;
    public static int identicalRandom;
    public static int graphicsDelay = 0;
    public static ImageConstruction cons;
    private static int MaxNoOfObjects = 1000; // will get from config
    public static sObject[] myObjects = new sObject[MaxNoOfObjects];
    public static Gravity G;
//Method:	programStart
//Description:	The begining of the program which assignes the boundary values and coordinates the
//              setup of the image.  All this code was being set up in main but chose to move it all to
//              a new method to make resetting easier by a simple call of this method.
//Parameters:  	None
//Returns:     	Nothing
//Calls:        menuContents
//              moveEntityFunction
//Globals:	width
//              height
//              pLeft
//              pRight
//              pBottom
//              pTop
//              G
//              workingT
//              timeStep
//              cons
//              circles

    public static void programStart() {
        //for (int i = 0; i < myObjects.length; i++) {
        //    xyPoint t = new xyPoint(0, 0);
        //    myObjects[i] = new sObject(0, 0, t);
        // }
        int value = 1;
        width = 800;
        height = 800;
        pLeft = -400;
        pRight = 400;
        pBottom = -400;
        pTop = 400;
        G = new Gravity();
        System.out.println("Gravity: " + G.getGravity());
        workingT = timeStep;
        KeyboardInputClass numbervalue = new KeyboardInputClass();
        char q = numbervalue.getCharacter(true, 'n', "yn", 2, "Enter window size manually? [y/n]");
        if (q == 'y') {
            width = numbervalue.getInteger(true, 800, -10000, 1000000, "Enter Width Amount.");
            height = numbervalue.getInteger(true, 800, -10000, 1000000, "Enter Height Amount.");
            pLeft = numbervalue.getInteger(true, -400, -10000, 1000000, "Enter Left Dimensions.");
            pRight = numbervalue.getInteger(true, -400, -10000, 1000000, "Enter Right Dimensions.");
            pBottom = numbervalue.getInteger(true, -400, -10000, 1000000, "Enter Bottom Dimensions.");
            pTop = numbervalue.getInteger(true, -400, -10000, 1000000, "Enter Top Dimensions.");
        }
        while (true) {

            int size = width * height;
            cons = new ImageConstruction(width, height, pLeft, pRight, pBottom, pTop, 1);

            if (value == 1) {
                cons.displaySetup();
                cons.setPixelValues();
                cons.clearImage(0, 0, 0);
                cons.displayImage(false, "Logan's Wonderful Gravity Program", false);
                try {                                 //wait a moment for smoother graphics
                    Thread.sleep(0);
                } catch (Exception e) {
                }

                size = width * height;
                circles = menuContents(numbervalue, size, 1);
            }
            int innerLoopControl = 0;
            while (innerLoopControl == 0) {
                innerLoopControl = moveEntityFunction(numbervalue);

            } // end of while no menu update
        }
    }

//********************************************************************
//Method:	main
//Description:	This is main, its only purpose is to call programStart and to 
//              begin the program
//Parameters:  	None
//Returns:     	Nothing
//Calls:        programStart
//Globals:	None
    public static void main(String[] args) {
        programStart();
    }//End of main method
//********************************************************************
//Method:	menuContents
//Description:	This method is the main menu where most of the user interface is handled.
//              It is both used for brand new runs as well as when requested to add additional entities,
//              which the handling is then passed over to addEntityMethod.
//Parameters:  	numbervalue
//              size
//              value    
//Returns:     	circles
//Calls:        addEntityMethod
//              refreshAllCircles
//Globals:	addEntity
//              circles
//              MaxNoOfObjects
//              additionalEntities
//              positionUIDefault
//              radiusUIDefault
//              iterationUIDefault
//              worktingT
//              timeStep
//              myObjects[]

    public static int menuContents(KeyboardInputClass numbervalue, int size, int value) {
        Random ran = new Random();
        //if (value != 2)
        {
            if (addEntity == false) {
                circles = numbervalue.getInteger(true, circles, 1, MaxNoOfObjects,
                        "Enter of the number of circles to create (default " + circles + ") 1-"
                        + MaxNoOfObjects + ": ");
            } else {
                int iDefault = 2;
                additionalEntities = numbervalue.getInteger(true, iDefault, 1, MaxNoOfObjects - circles,
                        "Enter of the number of circles to create (default " + iDefault + ") 1-"
                        + (MaxNoOfObjects - circles) + ": ");

            }
            positionUIDefault = numbervalue.getInteger(true, positionUIDefault, 1, 3,
                    "Enter how you want the position (default " + positionUIDefault + ") "
                    + "[1] Random - [2] Grouped - [3] Specify: ");
            radiusUIDefault = numbervalue.getInteger(true, radiusUIDefault, 1, 1000,
                    "Enter how you want the radius (default " + radiusUIDefault + ")"
                    + "[1] Random - [2] Identical - [3] Specify: ");
            if (addEntity == true) {
                addEntityMethod(numbervalue, ran, circles, additionalEntities);

                // addEntityMethod(numbervalue, ran, circles);
                circles += additionalEntities;
                //refreshAllCircles("Added Display", circles, shapeColor);
                iterationUIDefault = 0;
            }
            if (addEntity == false) {
                addEntityMethod(numbervalue, ran, 0, circles);
                refreshAllCircles("Initial Display", circles, shapeColor);
                iterationUIDefault = numbervalue.getInteger(true, iterationUIDefault, 0, 20,
                        "Enter number of time steps before collision detection (default "
                        + iterationUIDefault + "): ");
            }
            workingT = timeStep;
            for (int iteration = 0; iteration <= iterationUIDefault; iteration++) {
                for (int ix = 0; ix < circles; ix++) {
                    // first update of vectors
                    for (int jx = 0; jx < circles; jx++) {
                        if (ix != jx) {
                            myObjects[ix].determineVector(myObjects[jx]);
                        } // end of if ix!=jx
                    } // end of for in jx
                    if (addEntity == false) {
                        myObjects[ix].updatePosition(workingT);
                    }
                } // end of for in ix

                refreshAllCircles("Initial Run Display", circles, shapeColor);
            } // end of for in iterations      

        } // end of if value != 2

        System.out.println("circle value is: " + circles);
        for (int i = 0; i < circles; i++) {
            myObjects[i].ToPrint();
        }
        return circles;
    }//End of menuContents method
//********************************************************************
//Method:	refreshAllCircles
//Description:	This method is called whenever the board needs to be redrawn or recolored.
//              It calculates 'mm' and 'ration' which are computed to find the proper values
//              for declaring which entity will be which color based off the area of the begining
//              window size.
//Parameters:  	title
//              nCircles
//              choice   
//Returns:     	Nothing
//Calls:        colorMethod
//Globals:	cons
//              circles
//              myObjects[]
//              pRight
//              pTop   

    public static void refreshAllCircles(String title, int nCircles, char choice) {
        cons.clearImage(0, 0, 0);
        for (int ix = 0; ix < circles; ix++) {
            if (myObjects[ix].getRadius() != 0) {
                int rColor = 0;
                int gColor = 0;
                int bColor = 0;
                double rr = myObjects[ix].getRadius();
                xyPoint pp = myObjects[ix].getPosition();
                double mm = pRight * pTop;
                double ratio = myObjects[ix].getMass() / mm;
                colorMethod(ratio, (int) rr, rColor, gColor, bColor, cons, pp, choice);
                myObjects[ix].ToPrint();
            } // end of if
        }// end of for on circles

    } // end of refresh

//********************************************************************
//Method:	colorMethod
//Description:	The colorMethod method takes the values calculated in the refreshAllCircles method
//              and uses the results to determine which color will be assigned to the current body.
//Parameters:  	ratio
//              rr
//              rColor   
//              gColor   
//              bColor   
//              cons   
//              pp   
//              choice   
//Returns:     	0
//Calls:        None
//Globals:	myX
//          	myY
    public static int colorMethod(double ratio, int rr, int rColor, int gColor, int bColor, ImageConstruction cons, xyPoint pp, char choice) {
        if (ratio <= .00001) {
            rColor = 255;
            gColor = 255;
            bColor = 255;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .00004) {
            rColor = 0;
            gColor = 255;
            bColor = 255;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .00016) {
            rColor = 30;
            gColor = 144;
            bColor = 255;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .00064) {
            rColor = 0;
            gColor = 0;
            bColor = 255;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .00256) {
            rColor = 138;
            gColor = 43;
            bColor = 226;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .01024) {
            rColor = 192;
            gColor = 192;
            bColor = 192;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .04096) {
            rColor = 0;
            gColor = 255;
            bColor = 0;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .16384) {
            rColor = 244;
            gColor = 164;
            bColor = 96;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio <= .65536) {
            rColor = 255;
            gColor = 0;
            bColor = 0;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        if (ratio > .65536) {
            rColor = 255;
            gColor = 255;
            bColor = 0;
            if (choice == 's') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, true);
            }
            if (choice == 'o') {
                cons.insertCircle(pp.myX, pp.myY, rr, rColor, gColor, bColor, false);
            }
            return 0;
        }
        return 0;
    }//End of colorMethod method
//********************************************************************
//Method:	addEntityMethod
//Description:	addEntityMethod is the method for adding entities to our board (or space)
//              based off the choice of the user.  It promts them with multiple options
//              for whether you want the entities to be placed randomly, how many, at which
//              x and y coordinate, and lastly what the ratio of the entity should be.
//              It also handles adding new entities to the board space at any point during
//              the run by taking advantage of the values passed through as nFirst and nNew.
//Parameters:  	numbervalue
//              nFirst
//              nNew 
//Returns:     	0
//Calls:        None
//Globals:	positionUIDefault
//          	xxUIDefault
//          	yyUIDefault
//          	rrUIDefault
//          	radiusUIDefault
//          	width
//          	myObjects[]

    public static int addEntityMethod(KeyboardInputClass numbervalue, Random ran, int nFirst, int nNew) {
        double rr = 0;
        double xx = 0;
        double yy = 0;
        for (int ix = nFirst; ix < nFirst + nNew; ix++) {
            switch (positionUIDefault) {
                case 1:
                    xx = ran.nextInt(400 - (-400)) + -400;
                    yy = ran.nextInt(400 - (-400)) + -400;
                    break;
                case 2:
                    double slice = 2 * Math.PI / circles;
                    double angle = slice * ix;
                    xx = (int) (0 + 100 * Math.sin(angle));
                    yy = (int) (0 + 100 * Math.cos(angle));
                    //System.out.println("X: " + xx);
                    //System.out.println("Y: " + yy);
                    break;
                case 3:
                    xx = xxUIDefault = numbervalue.getDouble(true, xxUIDefault, -width, width,
                            "Enter x coordinate for entity [Default: " + xxUIDefault + "]");
                    yy = yyUIDefault = numbervalue.getDouble(true, yyUIDefault, -width, width,
                            "Enter y coordinate for entity: [Default: " + yyUIDefault + "]");
                    break;
                default:
                    System.out.println("Error");
            }// end of switch

            switch (radiusUIDefault) {
                case 1:
                    rr = ran.nextInt(width / 10) + 3;
                    break;
                case 2:
                    //rr = identicalRandom / 8;
                    rr = 20; // TODO:
                    break;
                case 3:

                    rr = rrUIDefault = numbervalue.getDouble(true, rrUIDefault, 1, width - 1,
                            "Enter radius for entity " + ix + "[Default: " + rrUIDefault + "]");
                    break;
                default:
                    System.out.println("Error");
            }// end of switch

            xyPoint p = new xyPoint(xx, yy);
            myObjects[ix] = new sObject(ix, rr, p);
            //System.out.println("Preliminary: ");
            //myObjects[ix].ToPrint();
        }// end of 2nd for
        System.out.println("--------------");
        return 0;
    } // end of AddEntityMethod
//********************************************************************
//Method:	moveEntityFunction
//Description:	As the method name implies, moveEntityFunction handles moving the entities
//              to one spot on the board space to another with the use of calling refreshAllCircles.
//              This method is where the primary menu dialog box is.  It handles and calculates
//              panning to the left,right,top, and bottom as well as zooming in or zooming out.
//              Other options it contains can be seen at the bottom which include showing all
//              entity properties, resting the program, changing primary shape color to either
//              filled or outlined only, as well as choosing how many time increment to go by.
//              Most of this method is contained under the if statement for 'Iterate' (I) for
//              the user input.  When in the if statement, the method makes calls and assigns
//              property values to the entities.  At the same time, its constantly checking for
//              collisions by constantly checking if an entity has a radius > 0.  Once found,
//              it gets assigned a colliderID which is used to track and handle multiple collisions
//              in that given time step.
//Parameters:  	numbervalue
//Returns:     	exitIndicator
//Calls:        refreshAllCircles
//              menuContents
//              programStart
//Globals:	graphicsDelay
//          	workingT
//          	timeStep
//          	myObjects[]
//          	shapeColor
//          	myX
//          	myY
//          	cons
//          	xRange
//          	yRange
//          	xLeft
//          	xRight
//          	yBottom
//          	yTop
//          	addEntity
//          	G

    public static int moveEntityFunction(KeyboardInputClass numbervalue) {
        int exitIndicator = -1;
        while (exitIndicator == -1) {

            String menuUI = numbervalue.getString("I",
                    "Iterate\tI (default)"
                    + "\nZoom\tZI=Zoom In; ZO=ZoomOut"
                    + "\nPan\tPU=to pan up,PD to pan down;PL to pan left;PR to pan right;H=Home"
                    + "\nTime\tT"
                    + "\nChange\tC"
                    + "\nShow\tS"
                    + "\nRestart\tR"
                    + "\nExit\tE"
            );

            // Iterate
            if ("I".equals(menuUI)) // Option I lets the user step through the simulation (by simply pressing the ENTER key) 
            // or run a specified number of iterations. 
            //Keep the iteration prompt open until the user enters 0 for the number of iterations
            {
                int nIterations = 1;
                while (nIterations == 1) {
                    nIterations = numbervalue.getInteger(true, nIterations, 0, 99,
                            "Number of iterations: ");
                    if (nIterations == 1) {
                        break;
                    }
                } // end of while getting # of iterations
                //cons.displayImage(false, "MyProgram", false);
                for (int iteration = 0; iteration <= nIterations; iteration++) {
                    try {                                 //wait a moment for smoother graphics
                        Thread.sleep(graphicsDelay);
                        //time increment here
                        workingT = timeStep;
                        // determine acceperation vectors for all
                        for (int ix = 0; ix < circles; ix++) {
                            if (myObjects[ix].getRadius() != 0) {
                                for (int jx = 0; jx < circles; jx++) {
                                    if ((ix != jx) && (myObjects[jx].getRadius() != 0)) {
                                        myObjects[ix].determineVector(myObjects[jx]);
                                    } // end of if ix!=jx
                                } // end of for in jx
                            } // end of if on ix radius
                        } // end of for in ix
                        System.out.println("--*--");
                        // now that acel has been determined
                        // determine expected poisition at end of workingT
                        for (int ix = 0; ix < circles; ix++) {
                            myObjects[ix].effectivePosition(workingT);
                        }
                        System.out.println("-***-");
                        double collisionTime = workingT;
                        int idOfCollider = -1;
                        do {
                            for (int ix = 0; ix < circles; ix++) {
                                if (myObjects[ix].getRadius() != 0) {
                                    for (int jx = (ix + 1); jx < circles; jx++) {
                                        if ((myObjects[jx].getRadius() != 0)) {
                                            double t = myObjects[ix].determineIfCollision(myObjects[jx], workingT);
                                            if (t != 999) {
                                                System.out.println(ix + "-" + jx + "  " + t);
                                                collisionTime = Math.min(collisionTime, t);
                                            }
                                        } // end of if ix!=jx
                                    } // end of for in jx
                                }
                            } // end of for in ix

                            System.out.println("***" + collisionTime + "**");

                            // resolve the collision
                            for (int ix = 0; ix < circles; ix++) {
                                idOfCollider = -1;
                                if ((myObjects[ix].getRadius() != 0)
                                        && (idOfCollider = myObjects[ix].getWhoCollided(collisionTime)) != -1) {
                                    System.out.println("Collison of " + ix + " with " + idOfCollider);
                                    myObjects[ix].ToPrint();
                                    int kxLast = 0;

                                    //myObjects[idOfCollider].ToPrint();
                                    //if (myObjects[idOfCollider].getColliderID() == -1) {
                                    //    myObjects[ix].resolveCollision(myObjects[idOfCollider]);
                                    //} else 
                                    {
                                        Collision CollisionB = new Collision();
                                        for (int kx = 0; kx < circles; kx++) {
                                            if (myObjects[kx].getRadius() != 0 && myObjects[kx].getColliderID() == idOfCollider) {
                                                CollisionB.addObject(myObjects[kx]);
                                                kxLast = kx;
                                                //if (idOfCollider == -1)
                                                //{
                                                //  break;
                                                //}// end of if
                                                myObjects[kx].remove(); // does not really remove
                                            }// end of getRadius if
                                        }// end of kx for loop
                                        CollisionB.addObject(myObjects[idOfCollider]);
                                        CollisionB.finishCollision(myObjects[idOfCollider]);
                                    }// end of else
                                    myObjects[idOfCollider].ToPrint();
                                    System.out.println("**");
                                    //cons.closeDisplay();
                                    //cons = new ImageConstruction(width, height, pLeft, pRight, pBottom, pTop, 1);
                                    refreshAllCircles("Collision Update", circles, shapeColor);
                                    System.out.println("X: " + myObjects[idOfCollider].getPosition().myX);
                                    System.out.println("Y: " + myObjects[idOfCollider].getPosition().myY);
                                    //cons.insertCircle(myObjects[idOfCollider].getPosition().myX, myObjects[idOfCollider].getPosition().myY, 10, 0, 0, 0, true);

                                    //testMethod();
                                    break;
                                }
                            } // end of for on ix

                        } while (idOfCollider != -1);

                        workingT = collisionTime; // so everyone else will update to that time

                        for (int ix = 0; ix < circles; ix++) {
                            if (myObjects[ix].getRadius() != 0);
                            {
                                myObjects[ix].updatePosition(workingT);
                            }
                        } // enn of for on ix

                        //cons.closeDisplay();
                        //cons = new ImageConstruction(width, height, pLeft, pRight, pBottom, pTop, 1);
                        refreshAllCircles("Gravity Update", circles, shapeColor);

                    } catch (Exception e) {
                    }
                } // end of for on iterations
                exitIndicator = 0;
            } // Zoom
            //Option Z allows the user to zoom in or out. 
            // ??? Default is a factor of 2.
            else if ("ZO".equals(menuUI)) {
                cons.xRange += 100;
                cons.yRange += 100;
                refreshAllCircles("Zoom Out", circles, shapeColor);

            } else if ("ZI".equals(menuUI) || "6".equals(menuUI)) {
                cons.xRange += -100;
                cons.yRange += -100;
                refreshAllCircles("Zoom In", circles, shapeColor);

            } else if ("PU".equals(menuUI)) // Option P lets the user pan vertically or horizontally by some fraction 
            // of the current window size. 
            // The default for this is 1, but let the user specify a smaller or larger value if desired. 
            // The Home option returns a view where the origin of the absolute coordinate system is centered in the window.
            {
                //cons = new ImageConstruction(width, height, pLeft, pRight, pBottom += 50, pTop += 50, 1);
                cons.yBottom += 100;
                cons.yTop += 100;
                refreshAllCircles("Pan Up", circles, shapeColor);
            } else if ("PD".equals(menuUI)) {
                //cons.closeDisplay();
                //cons = new ImageConstruction(width, height, pLeft, pRight, pBottom += -50, pTop += -50, 1);
                cons.yBottom += -100;
                cons.yTop += -100;
                refreshAllCircles("Pan Down", circles, shapeColor);
            } else if ("PL".equals(menuUI)) {
                //cons.closeDisplay();
                //cons = new ImageConstruction(width, height, pLeft += -50, pRight += -50, pBottom, pTop, 1);
                cons.xLeft += -100;
                cons.xRight += -100;
                refreshAllCircles("Pan Left", circles, shapeColor);
            } else if ("PR".equals(menuUI)) {
                //cons.closeDisplay();
                //ImageConstruction cons = new ImageConstruction(width, height, pLeft += 50, pRight += 50, pBottom, pTop, 1);
                cons.xLeft += 100;
                cons.xRight += 100;
                refreshAllCircles("Pan Right", circles, shapeColor);
            } //Time
            else if ("T".equals(menuUI)) // either by doubling or halving the duration of the current value. 
            // Show the current value of the duration of a standard time step when prompting for whether to double or halve the current value.
            {
                System.out.println("Standard TimeStep: " + timeStep);
                String timeChange = numbervalue.getString("d", "[h] Half Time Step\n[d] Double Time Step\n (default d)");
                if (timeChange == "d") {
                    timeStep = timeStep * 2;
                }
                if (timeChange == "h") {
                    timeStep = timeStep / 2;
                }
                // break;
            } //Misc
            else if ("C".equals(menuUI)) //Option C permits the user to change certain parameters during a run. 
            {
                int change = numbervalue.getInteger(true, 2, 1, 4, "[1]Create more entities\n[2]Change value for G\n[3]Solid colored entities or outline only\n[4]Change graphics update delay (default 0).");
                if (change == 1) {
                    //Random ran = new Random();
                    addEntity = true;
                    menuContents(numbervalue, width * height, 1);

                    //menuContents(numbervalue, (width * height), 1);
                }
                if (change == 2) {
                    double c_G = G.getGravity();
                    c_G = numbervalue.getDouble(true, c_G, 0.1, 10000.0,
                            "Enter new value for G (" + c_G + "): ");
                    G.setGravity(c_G);
                }
                if (change == 3) {
                    shapeColor = numbervalue.getCharacter(true, 'o', "os", 2, "[s] To use solid colored shapes.\n[o] To use colored outlined shapes.");
                    refreshAllCircles("Title", circles, shapeColor);
                }
                if (change == 4) {
                    int g_D = numbervalue.getInteger(true, 0, 0, 100000, "Enter graphics window update amount...[default 0]");
                    graphicsDelay = g_D;
                }
                //break;
            } else if ("S".equals(menuUI)) {
                for (int tx = 0; tx < circles; tx++) {
                    myObjects[tx].ToPrint();
                }
                //break;
            } else if ("R".equals(menuUI)) //Option R verifies that the user wants to initialize a new simulation and, 
            //if that is the case, starts over from the beginning.
            {
                addEntity = false;
                cons.clearImage(0, 0, 0);
                cons.closeDisplay();
                System.out.println("RESTART");
                programStart();

                break;
            } else if ("E".equals(menuUI)) {
                System.exit(0);
            } else if ("RE".equals(menuUI)) {
                //interactionMethod(cons, bigArray, numbervalue, size, 1);
            } else {
                System.out.println("Unacceptable entry.");
            }

        }// end of for (adjustment if)
        return exitIndicator;
    }//End of moveEntity method

//********************************************************************
}//End of class
//*******************************************************************************
//*******************************************************************************

