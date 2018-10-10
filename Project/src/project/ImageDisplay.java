package project;

//Program:      ImageDisplay.java
//Description:  Generic graphics routine(s)
//Author:       Steve Donaldson
//Revised:      1/28/09
import java.awt.*;
import java.io.*;
import java.awt.event.*;
//**************************************************************************************************************************
//**************************************************************************************************************************
//Class:		ImageDisplay
//Description:	Controls graphic output to a window.
public class ImageDisplay extends Frame {
	public int rowCount,columnCount;						//image array dimensions
	public int startRow, startColumn, endRow, endColumn;	//start and end coords of the block to display
	public int scaleFactor;									//image is enlarged by this factor
	Color image[][];										//the image data (Color values)
	public static int windowHeaderOffset = 30;				//space for window title bar
	public static int windowSideOffset = 4;					//space for window side bar(s)
	public static int windowBotttomOffset = 4;				//space for window bottom bar
	public boolean showContinuously;						//controls continuous processing in paint() routine
	//**********************************************************************************************************************
	ImageDisplay(int numberOfRows, int numberOfColumns, int scaleValue, String windowTitle) {
		rowCount = numberOfRows;
		columnCount = numberOfColumns;
		image = new Color[rowCount][columnCount];
		scaleFactor = scaleValue;
		showContinuously = true;

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
                
		setTitle(windowTitle);
		int imageHeight = rowCount * scaleFactor;
		int imageWidth = columnCount * scaleFactor;
		setSize(imageWidth + 2 * windowSideOffset, imageHeight + windowHeaderOffset + windowBotttomOffset);
		setVisible(true);
	}
	//**********************************************************************************************************************
	//Method:		closeImageDisplay
	//Description:	Terminates continuous display (if applicable) and closes the graphics
	//				window.
	//Parameters:	none
	//Returns:		nothing
	//Calls:		Java setVisible
	public void closeImageDisplay() {
		showContinuously = false;				//exit endless loop in paint()
		setVisible(false);
	}
	//**********************************************************************************************************************
	//Displays the image (possibly enlarged by scaleFactor). Loops continuously (assuming updates will be made to image[][]
	//by an external routine) as long as showContinuously=true.
	public void paint(Graphics g) {
		int row,column;
		Color pixel=new Color(0,0,0);
		int newRow,newColumn,r,c;

		while (showContinuously) {	
			newRow = 0;
			for (row = 0; row < rowCount; row++) {
				newColumn = 0;
				for (column = 0; column < columnCount; column++) {
					pixel = image[row][column];
					for (r = newRow; r < newRow + scaleFactor; r++) {//set all pixels in block to same color
						for (c = newColumn; c < newColumn + scaleFactor; c++) {
							g.setColor(pixel);
							g.drawLine(c + windowSideOffset, r + windowHeaderOffset, c + windowSideOffset, r + windowHeaderOffset);
						}
					}
					newColumn += scaleFactor;
				}
				newRow += scaleFactor;
			}
		}
	}
	//**********************************************************************************************************************

    void paint(Color color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}	//end ImageDisplayClass
//**************************************************************************************************************************
//**************************************************************************************************************************