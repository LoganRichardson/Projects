package project;
//Class:	Collision
//Description:	This class handles collision for parallel lines
//x:          The x value of the position 
//y:          The y value of the position 
public class Collision {

    private double mass;
    private xyVector vel_Cont;
    private xyVector new_acc;
    private xyPoint new_pos;
    private int howMany;
//********************************************************************
//Method:	Collision
//Description:	Default constructor which resets the parameters while counting how many there were
//Parameters:  	None
//Returns:     	Nothing
//Calls:        menuPriority
//Globals:	mass
//              vel_Cont
//              new_pos
//              new_acc
//              howMany
    Collision() {
        mass = 0;
        vel_Cont = new xyVector();
        new_pos = new xyPoint();
        new_acc = new xyVector();
        howMany=0;
    } // end of constructor
//********************************************************************
//Method:	addObject
//Description:	Second constructor that adds collision object paramenters 
//Parameters:  	obj
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	mass
//              vel_Cont
//              new_pos
//              new_acc
//              howMany
    public void addObject(sObject obj) {
        
        double MassObj = obj.getMass();
        mass = mass + MassObj;
        xyVector VelObj = new xyVector(obj.getEffectiveVelocity());
        vel_Cont.x += MassObj * VelObj.x;
        vel_Cont.y += MassObj * VelObj.y;
        new_pos.myX += MassObj * obj.getPosition().myX;
        new_pos.myY += MassObj * obj.getPosition().myY;
        new_acc.x += obj.getAcceleration().x;
        new_acc.y+= obj.getAcceleration().y;
        howMany++;
    }
//********************************************************************
//Method:	finalCollision
//Description:	Calculates the collision given 'x' amount of entities involved
//Parameters:  	obj
//Returns:     	Nothing
//Calls:        resolveCollision
//Globals:	mass
//              vel_Cont
//              new_pos
//              new_acc
//              howMany
    public void finishCollision(sObject obj) {        
        vel_Cont.x = vel_Cont.x / mass;
        vel_Cont.y = vel_Cont.y / mass;
        new_pos.myX = new_pos.myX/mass; //howMany;
        new_pos.myY = new_pos.myY/mass; //howMany;
        obj.resolveCollision(mass, new_pos, vel_Cont, new_acc);
    }
//********************************************************************
}
//*******************************************************************************
//*******************************************************************************