//Author:		Steve Donaldson
//Date:			06/09/07
//Class			Regions
//Description:	Implements a connected components algorithm based on Algorithm 2.2 in Shapiro
//				and Stockman) but simulates the recursion of their algorithm in order to
//				avoid potential stack overflow. Assumes use of a binary image (pixel values
//				0 or 255) or a grayscale image (pixel values 0-255). For binary images, the
//				background is assumed to be black, but can be labeled or not at the user's
//				discretion (based on the setting for the "includeBlackPixels" parameter).
//				For gray images, one can specify a gray tolerance within which to consider
//				pixels part of the same region. The program labels all regions of contiguous
//				values with successive integer labels beginning at 1. Output (from
//				findRegions) is a labeled image (in grayscale format).This program keeps
//				track of the # of pixels in each region for the first maxCount regions, but
//				the only practical limit to the # of regions that can be found is heap space.
//				The program also maintains a list of the coordinates of the first pixel
//				found in each region and whether each region is on the border of the image
//				(up to maxCount regions in both cases).
//
//				In addition, provides several utility routines for region extraction and
//				manipulation.
import java.awt.*;
import java.io.*;
import java.awt.event.*;
//******************************************************************************************
//******************************************************************************************
public class Regions {
	public int imageType;				//2=256 level gray scale;3=binary (b/w). Type 1
										//(24 bit color) is not supported by this routine.
	public int imageWidth;				//image width in pixels
	public int imageHeight;				//image height in pixels
	public int sourceImage[][];			//image for which regions are to be found
	public int neighborhood;			//4 or 8-neighborhood for determining neighbors
	public boolean includeBlackPixels;	//for binary images only: true=label all black
										//pixels; false=do not label black pixels
										//(note the assumption that black is the typical
										//background color)
	public int grayTolerance;			//for grayscale images only: the number of
										//grayvalues to include on either side of a pixel
										//value when determining if a given pixel belongs
										//to the region under construction
	public int labelCount;				//actual # of regions calculated
	public int labeledImage[][];		//labeled image (after the algorithm completes)
	public int maxCount;				//maximum # of regions for which a count of the
										//pixels in those regions will be accumulated
	public int pixelsInRegion[];		//the # of pixels in each region (up to maxCount)
	public int firstPixelInRegion[][];	//the row and column coords of the first pixel found
										//in a new region
	public int borderPixelCount[];		//the # of pixels in each region on the border of
										//the image space. (Regions on the border may not
										//be entirely within the image space)
	public int sumOfRows[];				//sum of all row values for each region (used for
										//computing region centroids)
	public int sumOfColumns[];			//sum of all column values for each region
	public int centroids[][];			//row and column values for centroids of each region
	public double bestAxes[];			//angle in radians of the best axis for each labeled
										//region with respect to the x-axis
	//**************************************************************************************
	//Method:		Regions
	//Description:	Initializes the parameters needed to do connected components labeling
	//Parameters:	Just the variables corresponding to the object variables
	//Returns:		nothing
	//Calls:		nothing
	Regions(int type, int width, int height, int image[][], int pixelNeighborhood, boolean includeBlack, int tolerance) {
		imageType = type;
		imageWidth = width;
		imageHeight = height;
		sourceImage = image;
		neighborhood = pixelNeighborhood;
		if (imageType == 3)
			includeBlackPixels = includeBlack;
		else
			includeBlackPixels = true;
		if (imageType==2)
			grayTolerance = tolerance;
		else
			grayTolerance = 0;
		labelCount = 0;
		labeledImage = new int[imageHeight][imageWidth];
		if (imageType == 3)
			grayTolerance = 0;
		maxCount = 50000;
		pixelsInRegion = new int[maxCount+2];			//position 0 not used in these arrays
		firstPixelInRegion = new int[maxCount+2][2];
		borderPixelCount = new int[maxCount+2];
		sumOfRows = new int[maxCount+2];
		sumOfColumns = new int[maxCount+2];
		centroids = new int[maxCount+2][2];
		bestAxes = new double[maxCount+2];

		for (int r = 0; r < imageHeight; r++)			//set-up by negating pixel values
			for (int c = 0; c < imageWidth; c++)
				labeledImage[r][c] = -sourceImage[r][c];
	}
	//**************************************************************************************
	//Method:		Regions
	//Description:	Initializes the parameters needed to do connected components labeling
	//Parameters:	Just the variables corresponding to the object variables
	//Returns:		labeledImage[][] - see object variable description
	//Calls:		nothing
//	Regions(ImageClass imageObject, int pixelNeighborhood, boolean includeBlack, int tolerance) {
//		this(imageObject.imageType, imageObject.imageWidth, imageObject.imageHeight, imageObject.pixels, pixelNeighborhood, includeBlack, tolerance);
//	}
	//**************************************************************************************
	public int[][] findRegions() {
		StackInterface stack = new LinkedStack();
		SearchNode currentSearchObject,nextSearchObject;
		int row, column, r, c, k, unlabeledPixelValue, pixel, kStart, baseRow, baseColumn;
		int nextRow, nextColumn, nextK;
		int imageHeightMinus1 = imageHeight - 1;
		int imageWidthMinus1 = imageWidth - 1;
		if (neighborhood == 4) kStart = 0; else kStart = 1;
		int searchCutoff = 0;
		if ((includeBlackPixels)||(imageType==2))
			searchCutoff = 1;
		int label = 0;
		for (row = 0; row < imageHeight; row++)
			for (column = 0; column < imageWidth; column++)
				if (labeledImage[row][column] < searchCutoff) {	//found a new region to label
					unlabeledPixelValue = labeledImage[row][column];
					label++;
					labeledImage[row][column] = label;	//label first pixel found in region
					if (label < maxCount) {
						pixelsInRegion[label]++;
						firstPixelInRegion[label][0] = row;
						firstPixelInRegion[label][1] = column;
						sumOfRows[label]+=row;
						sumOfColumns[label]+=column;
						if ((row == 0) || (row == imageHeightMinus1) || (column == 0) || (column == imageWidthMinus1))
							borderPixelCount[label]++;
					}

					//simulate recursive connected components
					currentSearchObject = new SearchNode(row, column, row-1, column-1, kStart);
					stack.push(currentSearchObject);
					while (!stack.isEmpty()) {
						currentSearchObject=(SearchNode)stack.peek();
						baseRow = currentSearchObject.row;
						baseColumn = currentSearchObject.column;
						r = currentSearchObject.r;
						c = currentSearchObject.c;
						k = currentSearchObject.k;

						//determine the row and column of the next potential neighbor
						nextColumn = c + 1;
						nextRow = r;
						if (nextColumn > baseColumn + 1) {
							nextColumn = baseColumn - 1;
							nextRow = r + 1;
						}
						//is the row in the allowable range for neighbors?
						if (nextRow <= baseRow + 1) {
							//prepare to skip diagonal pixels for 4-neighborhood
							if (neighborhood == 4)
								nextK = 1 - k;
							else
								nextK = k;
							//update parent values on stack for neighborhood processing
							currentSearchObject.r = nextRow;
							currentSearchObject.c = nextColumn;
							currentSearchObject.k = nextK;
						}
						else
							stack.pop();

						//is pixel at (r,c) legitimate for the neighborhood and in bounds?
						if ((k == 1) && (r >= 0) && (r < imageHeight) && (c >= 0) && (c < imageWidth) && ((r != baseRow) || (c != baseColumn))) {
							pixel = labeledImage[r][c];
							//is the pixel unlabeled and within the specified tolerance?
							if ((pixel < searchCutoff) && (Math.abs(unlabeledPixelValue - pixel) <= grayTolerance)) {
								labeledImage[r][c] = label;		//label the pixel
								if (label < maxCount) {
									pixelsInRegion[label]++;
									sumOfRows[label]+=r;
									sumOfColumns[label]+=c;
									if ((r == 0) || (r == imageHeightMinus1) || (c == 0) || (c == imageWidthMinus1))
										borderPixelCount[label]++;
								}
								//search neighbors of pixel at (r,c)
								nextSearchObject = new SearchNode(r, c, r - 1, c - 1, kStart);
								stack.push(nextSearchObject);
							}
						}
					}
				}
		labelCount = label;
		return labeledImage;
	}
	//**************************************************************************************
	//Method:		getSingleRegion
	//Description:	Returns (from a labeled image) an array containing a specified region
	//				labeled with 255 and having background set to 0. Assumes that
	//				findRegions() has been previously called.
	//Parameters:	regionID			 - identification of the region to be singled out
	//Returns:		singleRegionImage[][]- array having only pixels in the specified region
	//										set to non-zero values (255). Array is the same
	//										size as labeledImage.
	//Calls:		nothing
	public int[][] getSingleRegion(int regionID) {
		int singleRegionImage[][] = new int[imageHeight][imageWidth];	//initialized to 0
		for (int r = 0; r < imageHeight; r++)
			for (int c = 0; c < imageWidth; c++)
				if (labeledImage[r][c] == regionID)
					singleRegionImage[r][c] = 255;
		return singleRegionImage;
	}
	//**************************************************************************************
	//Method:		filterRegions
	//Description:	Removes regions whose size is outside specified lower and upper bounds.
	//				Optionally removes regions lying on the boundary of the image space.
	//				(The logic behind use of this feature is that a region lying on the
	//				boundary is quite possibly only a portion of a complete region and
	//				might not necessarily be reliably used in an image recognition task.)
	//				Note: regions are NOT renumbered and labelCount is NOT adjusted. 
	//				Consequently, routines that use region object data after this method
	//				has run must take into account the fact that there can be 0 values
	//				in pixelsInRegion[] and firstPixelInRegion[][] between the remaining
	//				legitimate region entries. Pixels are removed from the labeled image
	//				by setting them to the background color, which is assumed to be 0
	//				(for both binary and grayscale images). Note that this routine
	//				removes at most maxCount regions.
	//Parameters:	lowerBound	- regions with fewer than this # of pixels are eliminated
	//				upperBound	- regions with more than this # of pixels are eliminated
	//				omitBoundaryRegions	- true=remove any region having one or more pixels
	//										that lie on the boundary of the image space
	//				borderThreshold		- if omitBoundaryRegions is true, regions having a
	//										# of boundary pixels > this amount will be
	//										eliminated
	//Returns:		labeledImage[][]
	//				Also updates pixelsInRegion[], firstPixelInRegion[][], and
	//				borderPixelCount[].
	//Calls:		nothing
	public int[][] filterRegions(int lowerBound, int upperBound, boolean omitBoundaryRegions, int borderThreshold) {
		int label, pixelCount, r, c;
		int oldLabelCount = labelCount;
		if (oldLabelCount > maxCount) oldLabelCount = maxCount;

		//remove pixels in regions not meeting specified size criteria
		for (r = 0; r < imageHeight; r++)
			for (c = 0; c < imageWidth; c++) {
				label = labeledImage[r][c];
				pixelCount=pixelsInRegion[label];
				if ((pixelCount < lowerBound) || (pixelCount > upperBound) || (omitBoundaryRegions && (borderPixelCount[label]>borderThreshold))) {
					labeledImage[r][c] = 0;
				}
			}
		for (label = 1; label <= oldLabelCount; label++) {
			pixelCount = pixelsInRegion[label];
			if ((pixelCount < lowerBound) || (pixelCount > upperBound) || (omitBoundaryRegions && (borderPixelCount[label]>borderThreshold))) {
				pixelsInRegion[label] = 0;
				firstPixelInRegion[label][0] = 0;
				firstPixelInRegion[label][1] = 0;
				borderPixelCount[label] = 0;
			}
		}
		return labeledImage;
	}
	//**************************************************************************************
	//Method:		computeRegionProperties
	//Description:	Computes specific properties for all regions in a labeled image and
	//				updates (for the region object under consideration) the relevant object
	//				parameters (arrays, etc.) storing those property values. Current
	//				properties computed and saved are centroids and best axes.
	//Parameters:	none
	//Returns:		nothing (but updates object parameters)
	//Calls:		nothing
	public void computeRegionProperties() {
		int maxLabels=labelCount;
		if (labelCount>maxCount)
			maxLabels=maxCount;
		for (int label = 1; label <= maxLabels; label++) {
			if (pixelsInRegion[label] > 0) {
				centroids[label][0] = sumOfRows[label] / pixelsInRegion[label];
				centroids[label][1] = sumOfColumns[label] / pixelsInRegion[label];
				bestAxes[label] = findBestAxisForRegion(label);
			}
		}
		return;
	}
	//**************************************************************************************
	//Method:		findBestAxisForRegion
	//Description:	Computes the best axis for a region in a binary image using the
	//				technique provided in Shapiro and Stockman (2001).
	//Parameters:	regionID - identification of the region for which the axis is calculated
	//Returns:		bestAxis	- angle (with the horizontal) of the best axis in radians
	//Calls:		nothing
	//Authors:		Greg Brazda, Ben Dennis, Steve Donaldson
	public double findBestAxisForRegion(int regionID) {
		double piOverTwo = Math.PI / 2;
		int area = pixelsInRegion[regionID];
		int centroidRow = centroids[regionID][0];
		int centroidCol = centroids[regionID][1];
		double mrr = 0.0;
		double mrc = 0.0;
		double mcc = 0.0;
		int deltaR, deltaC;

		for (int row = 0; row < imageHeight; row++) {
			for (int column = 0; column < imageWidth; column++) {
				if (labeledImage[row][column] == regionID) {
					deltaR = row - centroidRow;
					deltaC = column - centroidCol;
					mrr += (deltaR * deltaR);
					mrc += ((deltaR) * (deltaC));
					mcc += (deltaC * deltaC);
				}
			}
		}
		mrr /= area;
		mrc /= area;
		mcc /= area;

		double bestAxis = 0;
		if (mrr - mcc != 0) {
			bestAxis = (2 * mrc) / (mrr - mcc);
			bestAxis = Math.atan(bestAxis) / 2;
		}
		else
			bestAxis = piOverTwo;
		//At this point "bestAxis" may actually be the worst axis, so we must check to see
		//if it needs to be adjusted by PI/2 radians.
		if (mcc < mrr)	//instead of "if(mcc>mrr)" because Shapiro uses vertical column axis
			bestAxis += piOverTwo;
		//if not already there, place angle in range -PI/2 to +PI/2
		if (bestAxis > piOverTwo)
			bestAxis -= Math.PI;
		if (bestAxis < -piOverTwo)
			bestAxis += Math.PI;

		return bestAxis;
	}
	//**************************************************************************************
}	//end Regions class
//******************************************************************************************
//******************************************************************************************
public class SearchNode {
	public int row;			//row for a pixel that has been labeled
	public int column;		//column for a pixel that has been labeled
	public int r;			//row of a pixel neighboring the pixel at (row,column)
	public int c;			//column of a pixel neighboring the pixel at (row,column)
	public int k;			//flag to control how pixels in 4 or 8 neighborhood are processed.
							//Value is 0 or 1. For a 4-neighborhood, only every other pixel
							//in an 8 neighborhood is considered.)

	SearchNode(int row, int column, int r, int c, int k) {
		this.row = row;
		this.column = column;
		this.r = r;
		this.c = c;
		this.k = k;
	}
}
//******************************************************************************************
//******************************************************************************************
