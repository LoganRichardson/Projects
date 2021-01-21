package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Random;

public class Project {

    public static int positionX;
    public static int positionY;
    public static int[] radius;
    public static int[] entityX;
    public static int[] entityY;
    public static int[] mass;
    public static int[] velocityX;
    public static int[] velocityY;
    public static double[] DVX;
    public static double[] DVY;
    public static int[] velocity;
    public static int[] distance;
    public static int[] distanceTraveled;
    public static int[] acceleration;
    public static int[] gravitation;
    public static int[] entityDifference;
    public static double[] vectorAngle;
    public static int time = 1;
    public static int width;
    public static int height;
    public static int temp = 0;
    public static int G = 1;
    public static int x1;
    public static int x2;
    public static int y1;
    public static int y2;
    public static int pLeft;
    public static int pRight;
    public static int pBottom;
    public static int pTop;
    public static int moveUI_1;
    public static int moveUI_2;
    public static int UI;
    public static int identicalRandom;

    public static int[][] testarray;

    public static void main(String[] args) {
        int value = 1;
        width = 800;
        height = 800;
        pLeft = -400;
        pRight = 400;
        pBottom = -400;
        pTop = 400;
        ArrayList[][] bigArray = new ArrayList[width][height];
        menuMethod(width, height, 0, value, bigArray);
    }//End of main method

    public static int menuMethod(int width, int height, int circles, int value, ArrayList[][] bigArray) {
        int size = width * height;
        for (int i = 0; i < bigArray.length; i++) {
            for (int j = 0; j < bigArray.length; j++) {
                bigArray[i][j] = new ArrayList();
            }
        }
        ImageConstruction cons = new ImageConstruction(width, height, pLeft, pRight, pBottom, pTop, 1);
        KeyboardInputClass numbervalue = new KeyboardInputClass();
        if (value == 1) {
            cons.displaySetup();
            cons.setPixelValues();
            menuContents(cons, bigArray, numbervalue, size, 1);
        }

        if (value == 2) {

            return 0;
        }
        return 0;
    }//End of menuMethod

    public static void menuContents(ImageConstruction cons, ArrayList[][] bigArray, KeyboardInputClass numbervalue, int size, int value) {
        if (value != 2) {
            int circles = numbervalue.getInteger(true, 5, 1, 20, "Enter of the number of circles to create: ");
            entityX = new int[circles];
            entityY = new int[circles];
            velocityX = new int[circles];
            velocityY = new int[circles];
            velocity = new int[circles];
            radius = new int[circles];
            mass = new int[circles];
            distance = new int[circles];
            distanceTraveled = new int[circles];
            acceleration = new int[circles];
            gravitation = new int[circles];
            entityDifference = new int[circles];
            vectorAngle = new double[circles];
            G = 2;

            int positionUI = numbervalue.getInteger(true, 3, 1, 3, "Enter how you want the position: [1] Random - [2] Grouped - [3] Specify");
            positionValue(cons, numbervalue, positionUI, circles);
            int radiusUI = numbervalue.getInteger(true, 5, 1, 20, "Enter how you want the radius: [1] Random - [2] Identical - [3] Specify");
            Random ran = new Random();
            identicalRandom = ran.nextInt(width) + 1;

            radianValue(cons, numbervalue, radiusUI, circles);

            zeroOut();
            DVT(bigArray, circles);
            gravityMethod(bigArray, circles);
            moveEntityPrompts(cons, numbervalue, bigArray, circles, size, positionUI);
            interactionMethod(cons, bigArray, numbervalue, size, 1);

        }
        if (value == 2) {
            for (int i = 0; i < entityX.length; i++) //System.out.println();
            //System.out.println("VelocityX: " + velocityX[i]);
            //System.out.println("VelocityY: " + velocityY[i]);
            //System.out.println();
            //System.out.println("Distance: " + distance[i]);
            {
                System.out.println("Acceleration: " + acceleration[i]);
            }
            for (int j = 0; j < vectorAngle.length; j++) {
                System.out.print("VA: " + vectorAngle[j]);
                System.out.println();
            }
        }

    }//End of menuMethod method

    public static void radianValue(ImageConstruction cons, KeyboardInputClass numbervalue, int radiusUI, int circles) {
        if (radiusUI == 1) {
            UI = 1;
            Random ran = new Random();
            for (int i = 0; i < circles; i++) {
                radius[i] = ran.nextInt(entityX[i]);
                mass[i] = (int) Math.PI * (radius[i] * radius[i]);
                cons.insertCircle(entityX[i], entityY[i], radius[i] + 1, 0, 0, 0, true);
                cons.insertCircle(entityX[i], entityY[i], radius[i], 255, 0, 0, true);
            }// end of ui == 1 loop
        }// end of ui == 1 if
        if (radiusUI == 2) {
            UI = 2;
            for (int i = 0; i < circles; i++) {
                radius[i] = identicalRandom / 8;
                mass[i] = (int) Math.PI * (radius[i] * radius[i]);
                cons.insertCircle(entityX[i], entityY[i], radius[i] + 1, 0, 0, 0, true);
                cons.insertCircle(entityX[i], entityY[i], radius[i], 255, 0, 0, true);
            }// end of ui == 2 loop
        }// end of ui == 2 if
        if (radiusUI == 3) {
            UI = 3;
            for (int i = 0; i < circles; i++) {
                radius[i] = numbervalue.getInteger(true, width / 8, 1, width - 1, "Enter radius for entity: ");
                mass[i] = (int) Math.PI * (radius[i] * radius[i]);
                cons.insertCircle(entityX[i], entityY[i], radius[i] + 1, 0, 0, 0, true);
                cons.insertCircle(entityX[i], entityY[i], radius[i], 255, 0, 0, true);
            }// end of ui == 3 loop
        }// end of ui == 3 if
    }//End of radianValue method

    public static int positionValue(ImageConstruction cons, KeyboardInputClass numbervalue, int positionUI, int circles) {
        for (int i = 0; i < circles; i++) {

            if (positionUI == 1) {
                Random ran = new Random();
                //System.out.println(entityX[i] = ran.nextInt());
                entityX[i] = ran.nextInt(width) + 1;
                entityY[i] = ran.nextInt(height) + 1;
            }
            if (positionUI == 2) {
                double slice = 2 * Math.PI / circles;
                double angle = slice * i;
                entityX[i] = (int) (200 + 100 * Math.sin(angle));
                entityY[i] = (int) (200 + 100 * Math.cos(angle));
                System.out.println("X: " + entityX[i]);
                System.out.println("Y: " + entityY[i]);
            }
            if (positionUI == 3) {
                entityX[i] = numbervalue.getInteger(true, width / 8, 1, width, "Enter x coordinate for entity: ");
                entityY[i] = numbervalue.getInteger(true, width / 8, 1, width, "Enter y coordinate for entity: ");
            }

        }// end of for loop
        return 0;
    }

    public static int moveEntityFunction(ArrayList[][] bigArray, int moveUI_1, int moveUI_2, ImageConstruction cons, KeyboardInputClass numbervalue, int circles, int size, int value) {
        if (value == 1) {
            cons.insertCircle(entityX[moveUI_1], entityY[moveUI_1], radius[moveUI_1], 0, 0, 0, true);
            ArrayList[][] al = new ArrayList[width][height];
            int pos1 = entityX[moveUI_1];
            int pos2 = entityY[moveUI_1];
            bigArray.equals(cons.redValues);
            cons.insertCircle(entityX[moveUI_1] + moveUI_2, entityY[moveUI_1] + moveUI_2, radius[moveUI_1], 255, 0, 0, true);
            cons.displayImage(true, "new image", true);
        }
        if (value == 2) {
            cons.insertCircle(entityX[moveUI_1], entityY[moveUI_1], radius[moveUI_1], 0, 0, 0, true);
            cons.insertCircle(entityX[moveUI_1] + moveUI_2, entityY[moveUI_1] + moveUI_2, radius[moveUI_1], 255, 0, 0, true);
            return 0;
        }

        for (int i = 1; i < 2; i++) {

            String menuUI = numbervalue.getString("ZO", "Iterate:\n     Press [0] to iterate by one:\nZoom:"
                    + "\n     Press [ZO] to zoom out:"
                    + "\n     Press [ZI] to zoom in:"
                    + "\nPan:"
                    + "\n     Press [PU] to pan up:"
                    + "\n     Press [PD] to pan down:"
                    + "\n     Press [PL] to pan left:"
                    + "\n     Press [PR] to pan right:"
                    + "\nTime:"
                    + "\n     Press [TD] to double time increment:"
                    + "\n     Press [TH] to half time increment:"
                    + "\nMisc:"
                    + "\n     Press [C] to change:"
                    + "\n     Press [R] to restart:"
                    + "\n     Press [E] to exit:"
                    + "\n     Press [RE] to return: ");

            //Zoom
            if ("I".equals(menuUI)) {
                //time increment here
            }
            if ("ZO".equals(menuUI)) {
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft += - 100, pRight += 100, pBottom += - 100, pTop += 100, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Zoom Out", true);
            }
            if ("ZI".equals(menuUI)) {
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft += 100, pRight += - 100, pBottom += 100, pTop += -100, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Zoom In", true);
            }

            //Pan
            if ("PU".equals(menuUI)) {
                /*
                for (int j = 0; j < bigArray.length; j++) {
                    for (int k = 0; k < bigArray.length; k++) {
                        bigArray[j][k].add(cons.redValues[j][k]);
                        testarray[j][k] = cons.redValues[j][k];
                    }
                    //System.out.println();
                }
                 */
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft, pRight, pBottom += 100, pTop += 100, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Pan Up", true);

            }
            if ("PD".equals(menuUI)) {
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft, pRight, pBottom += -100, pTop += -100, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Pan Down", true);
            }
            if ("PL".equals(menuUI)) {
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft += -100, pRight += -100, pBottom, pTop, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Pan Left", true);
            }
            if ("PR".equals(menuUI)) {
                value = 2;
                i = 0;
                ImageConstruction img = new ImageConstruction(width, height, pLeft += 100, pRight += 100, pBottom, pTop, 1);
                radianValue(img, numbervalue, UI, circles);
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, img, numbervalue, circles, size, value);
                img.displaySetup();
                img.displayImage(true, "Pan Left", true);
            }

            //Time
            if ("TD".equals(menuUI)) {
                cons.displayImage(true, "Double Time Increment", true);
                break;
            }
            if ("TH".equals(menuUI)) {
                cons.displayImage(true, "Half Time Increment", true);
                break;
            }

            //Misc
            if ("C".equals(menuUI)) {
                cons.displayImage(true, "Change", true);
                break;
            }
            if ("R".equals(menuUI)) {
                cons.displayImage(true, "Restart", true);
                break;
            }
            if ("E".equals(menuUI)) {
                System.exit(0);
            }
            if ("RE".equals(menuUI)) {
                interactionMethod(cons, bigArray, numbervalue, size, 1);
            }
            //cons.closeDisplay();
            //cons.displayImage(true, "1", true);

        }// end of adjustment if
        return 0;
    }//End of moveEntity method

    public static void moveEntityPrompts(ImageConstruction cons, KeyboardInputClass numbervalue, ArrayList[][] bigArray, int circles, int size, int positionUI) {

        cons.displayImage(true, "windowTitle", true);
        int movePrompt = numbervalue.getInteger(true, 1, 1, 2, "Press [1] to start moving entities or [2] to quit.");
        for (int i = 0; i < 2; i++) {
            if (movePrompt == 1) {
                cons.closeDisplay();
                moveUI_1 = numbervalue.getInteger(true, 1, 1, circles, "Which entity would you like to move? ");
                moveUI_2 = numbervalue.getInteger(true, 10, -size, size, "How many spots would you like to move it?");
                moveEntityFunction(bigArray, moveUI_1, moveUI_2, cons, numbervalue, circles, size, 1);
                int movePrompt2 = numbervalue.getInteger(true, 1, 1, 2, "Press [1] to keep moving entities or [2] to exit the program.");
                if (movePrompt2 == 1) {
                    i = 0;
                }//end of repeat if
            }// end of first movePrompt if
            if (movePrompt == 2) {
                System.exit(0);
            }// end of exit program if
        }// end of for loop
    }//End of moveEntityPrompts Method

    public static void DVT(ArrayList[][] bigArray, int circles) {
        //double[][] DVX = (double)velocity;
        DVX = new double[velocity.length];
        DVY = new double[velocity.length];
        for (int i = 0; i < circles; i++) {
            double ok;
            double ko;
            double slice = 2 * Math.PI / circles;
            double angle = slice * i;
            velocity[i] = (int) (2 * Math.PI) * radius[i];
            distance[i] = (velocity[i] * time);

            DVX[i] = velocity[i] * Math.cos(angle);
            DVY[i] = velocity[i] * Math.sin(angle);

            ok = velocity[i] * Math.cos(angle);
            ko = velocity[i] * Math.sin(angle);
            System.out.println("ok: " + ok);
            System.out.println("ko: " + ko);
            System.out.println("DVX: " + DVX[i]);
            System.out.println("DVY: " + DVY[i]);
            System.out.println();
            acceleration[i] = ((int) DVY[i] - temp) / (int) time;
            temp = velocityX[i];
        }// end of DVT loop
    }//End of DVT method

    public static void gravityMethod(ArrayList[][] bigArray, int circles) {

        for (int j = 0; j < entityY.length; j++) {
            x1 = entityX[j];
            y1 = entityY[j];
            if (x1 != entityX[j] && y1 != entityY[j]) {
                x2 = entityX[j];
                y2 = entityY[j];
            }// end of x2y2 if            
            distance[j] = (int) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            System.out.println("Distance: " + distance[j]);
            gravitation[j] = (G * (mass[j]) / distance[j]);
            System.out.println("gravitation: " + gravitation[j]);
            System.out.println();
            //System.out.println();
        }// end of j loop

    }//End of gravityMethod

    public static void interactionMethod(ImageConstruction cons, ArrayList[][] bigArray, KeyboardInputClass numbervalue, int size, int value) {
        double[] accX = new double[entityX.length];
        double[] accY = new double[entityY.length];
        if (velocityX[time + 1] < velocityX.length - 1 && velocityY[time + 1] < velocityY.length - 1) {
            velocityX[time + 1] = velocityX[time] + acceleration[velocityX[time]] * time;
            velocityY[time + 1] = velocityY[time] + acceleration[velocityY[time]] * time;
            time++;

            for (int i = 0; i < entityX.length; i++) {
                distanceTraveled[i] = (velocity[i] * time) + ((1 / 2) * acceleration[i] * (time * time));
                if (i != 1) {

                    acceleration[i] = gravitation[i] * mass[i] / ((entityX[i] + entityY[i]) * entityX[i] + entityY[i]);
                    //acceleration[i] = ((entityY[h] - entityY[i] / (entityX[h] - entityX[i])));
                    double yi = DVX[1];
                    double ya = DVY[1];
                    double xi = entityX[i];
                    double xa = entityY[i];
                    double acc = acceleration[i];
                    double test = computeVectorAngle(yi, ya, xa, xi);
                    vectorAngle[i] = test;
                    System.out.println("test: " + test);
                    System.out.println("yi: " + yi);
                    System.out.println("ya: " + ya);
                    System.out.println("xi: " + xi);
                    System.out.println("xa: " + xa);
                    System.out.println("acceleration: " + acceleration[i]);
                    System.out.println();
                    accX[i] = acceleration[i] * Math.cos(vectorAngle[i]);
                    accY[i] = acceleration[i] * Math.sin(vectorAngle[i]);
                }
            }
            double aX = 0;
            double aY = 0;
            for (int i = 0; i < entityX.length; i++) {
                aX += accX[i];
                aY += accY[i];
            }
            System.out.println("aX: " + aX);
            System.out.println("aY: " + aY);
        }
        value = 2;
        menuContents(cons, bigArray, numbervalue, size, value);
    }

    public static double computeVectorAngle(double tailX, double tailY, double headX, double headY) {
        double vectorAngle;
        vectorAngle = Math.atan((headY - tailY) / (headX - tailX));
        if (headX >= tailX) {                               //vector is in 1st or 4th quadrant
            if (headY < tailY) //vector is in 4th quadrant
            {
                vectorAngle = 2.0 * Math.PI + vectorAngle;	//angle returned by Math.atan() was negative in this case
            }
        } else //headX < tailX (vector is in 2nd or 3rd quadrant)
        {
            vectorAngle += Math.PI;                     //angle returned by Math.atan() could be positive or negative
        }
        return vectorAngle;
    }

    public static void zeroOut() {
        for (int i = 0; i < velocityX.length; i++) {
            velocityX[i] = 0;
            velocityY[i] = 0;
        }
    }
}//End of class

