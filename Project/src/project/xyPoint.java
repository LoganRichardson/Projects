
package project;
//Class:	xyPoint
//Description:	This class stores the x / y coordinates of the positions in the sObject class.
//myX:          The x value of a position 
//myY:          The y value of a position 
public class xyPoint
{
  double myX;
  double myY;
//Method:	xyPoint
//Description:	The default constructor.  Has nothing passed through it and assigns
//              both myX and myY to 0.0
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myX
//              myY
  xyPoint()
  {
    myX=0.0;
    myY=0.0;
  } // end of constructor
//********************************************************************
//Method:	xyPoint
//Description:	The second constructor handling the case if one value gets passed
//Parameters:  	xyPoint p
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myX
//              myY
  xyPoint(xyPoint p)
  {
    myX=p.myX;
    myY=p.myY;
  } // end of constructor
//********************************************************************
//Method:	xyPoint
//Description:	The third constructor handling the case if two values get passed
//Parameters:  	double xx
//              double yy
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myX
//              myY
  xyPoint(double xx, double yy)
  {
    myX=xx;
    myY=yy;
  } // end of constructor
//********************************************************************
//Method:	ToPrint
//Description:	Method to print myX and myY for debugging purposes
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myX
//              myY
  void ToPrint()
  {
    //System.out.println("("+myX+","+myY+")");
  } // end of ToPrint
//********************************************************************
} // end of class xyPoint
//*******************************************************************************
//*******************************************************************************

