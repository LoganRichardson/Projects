/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

public class sObject {
    
    private class xyVector {
        double x;
        double y;
        
        xyVector ()
        {
            x=0.0;
            y=0.0;
        } // end of constructor
    } // end of class xyVector
    
    private class xyPoint {
        int x;
        int y;
    } // end of class xyPoint
    
    int myRadius;
    int myMass;
    xyVector myAcceleration;
    xyVector myVelocity;
    xyPoint myPosition;
    
    //}

public int getMethod(){
    return myRadius;
}

public int getMass(){
    return myMass;
}

public xyVector getAcceleration(){
    return myAcceleration;
}

public void setAcceleration(xyVector accel){
   myAcceleration = accel;
}

public void update(){
   //TODO: update myVelocity abd myPosition 
}

public xyVector getVelocity(){
    return myVelocity;
}

public xyPoint getPosition(){
    return myPosition;
}

sObject (int radius, int mass, xyPoint position)
{
    myRadius = radius;
    myMass = mass;
    myPosition = position;
    myAcceleration = new xyVector();
    myVelocity = new xyVector();
    
} // end of constructor

}//End of sObject class