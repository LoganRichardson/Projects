package project;
//Class:	xyVector
//Description:	This class stores the x / y positions for the parameters including acceleration
//              and velocity.
//x:          The x value of a vector parameter 
//y:          The y value of a vector parameter 
public class xyVector
{

  double x;
  double y;
//Method:	xyVector
//Description:	The default constructor.  Zeros out x and y.
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	x
//              y
  xyVector()
  {
    x = 0.0;
    y = 0.0;
  } // end of constructor
//********************************************************************
//Method:	xyVector
//Description:	The second constructor which handles a case of an xyVector value 
//              being passed in.
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	x
//              y
  xyVector(xyVector v)
  {
    x = v.x;
    y = v.y;
  } // end of constructor
//********************************************************************
} // end of class xyVector
//*******************************************************************************
//*******************************************************************************