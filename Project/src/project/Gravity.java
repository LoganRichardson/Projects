package project;
//Class:	Gravity
//Description:	Class handles the gravity
//myG:          The gravity value set to be equal to the amount and squared
//y:          The y value of the position 
public class Gravity
{
  private double myG=1.0;
//********************************************************************
//Method:	Gravity
//Description:	Default constructor
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	None
  Gravity()
  {

  } // end of constructor
//********************************************************************
//Method:	setGravity
//Description:	Method that sets myG to the value of gravity 'mPerSecSquared'
//Parameters:  	mPerSecSquared
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myG
  public void setGravity(double mPerSecSquared)
  {
    // could/should validate
    myG = mPerSecSquared;
  } // end of setGravity
//********************************************************************
//Method:	getGravity
//Description:	Method that simply returns 'myG' the gravity value
//Parameters:  	Nothing
//Returns:     	myG
//Calls:        Nothing
//Globals:	myG
  public double getGravity()
  {
    return myG;
  } // end of setGravity
//********************************************************************
}
//*******************************************************************************
//*******************************************************************************