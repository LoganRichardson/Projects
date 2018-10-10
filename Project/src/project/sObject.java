package project;
//Class:	sObject
//Description:	The sObject class the the main property object class which its default
//              constructor which takes in the id of the entity, its radius, and the
//              position which are all stored in the xyPoint class.  This class contains
//              the get methods for all the entities' paramenters such as getRadius,
//              getMass, getID, etc. 
//debug:        If its set to 1, then it outputs sout statments to help debug
//G:            The global object variable for gravity which has its own class
//epsilon:      Used as a comparator for really small numbers where 0 has to be checked for
//myID:         The variable for the entity id
//myRadius:     The variable for myRadius called by getRadius
//myMass:       The variable for myMass called by getMass
//distance:     Holds the value for current distance given more than 0 entity
//myAcceleration: The acceleration of an entity and its x/y coordinates referenced from xyVector class
//myVelocity:   The velocity of an entity and its x/y coordinates referenced from xyVector class
//myVectorAngle:Global variable for the vector angle 
//myPosition:   The position of an entity and its x/y coordinates referenced from xyPoint class
//myEffectivePosition: Its x/y created from the class xyPoint.
//                      Used for computing the distances of entities from their centers 
//myEffectiveVelocity:  Its x/y created from the class xyVector, its used to help compute the effectiveposition
//                      as well as calculating the new entity after a collision.
//myCollisionTime: The time at which a collision occers, mostly used to return to Project class as indicator
//myColliderID: Stores the ID number of which entity has been in a collision.
//p_C:          The value of a position of myX and myX which is used as a comparator for computing the
//              the difference of two entities and determines if there is a collision
//t_Ain:        The value of how far away it is from being inside the area next entity
//t_Bin:        The value of how far the second comparing entity is from being inside the area of the next

public class sObject
{

  int debug = 0;
  private Gravity G;
  double epsilon = 0.000000001; //TODO:
  private int myID;
  private double myRadius;
  private double myMass;
  private double distance;
  private xyVector myAcceleration;
  private xyVector myVelocity;
  private double myVectorAngle;
  private xyPoint myPosition;
  private xyPoint myEffectivePosition;
  private xyVector myEffectiveVelocity;
  private double myCollisionTime;
  private int myColliderID;
  private xyPoint p_C;
  private double t_Ain;
  private double t_Bin;

//Method:	sObject
//Description:	The default constructor of the sObject class.  Contains all get methods and
//              takes in the id of the entity, its radius as well as the position referenced
//              from xyPoint.
//Parameters:  	id
//              radius
//              position
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	G
//              myID
//              myRadius
//              myMass
//              myVectorAngle
//              myAcceleration
//              myPosition
//              myVelocity
//              myEfectivePosition
//              myEfectiveVelocity
//              p_C
    sObject(int id, double radius, xyPoint position)
  {
    G = new Gravity();
    myID = id;
    myRadius = radius;
    myMass = (Math.PI * (radius * radius));
    myVectorAngle = -0.0;
    myAcceleration = new xyVector();
    myPosition = new xyPoint(position.myX, position.myY);
    myVelocity = new xyVector();
    myEffectivePosition = new xyPoint(0.0, 0.0);
    myEffectiveVelocity = new xyVector();
    p_C = new xyPoint();
  } // end of constructor

//********************************************************************
//Method:	getRadius
//Description:	A constructor method for getting the radius and returning it.
//Parameters:  	None
//Returns:     	myRadius
//Calls:        Nothing
//Globals:	myRadius

    public double getRadius() {
        return myRadius;
    }
//********************************************************************
//Method:	getMass
//Description:	A constructor method for getting the mass and returning it.
//Parameters:  	None
//Returns:     	myMass
//Calls:        Nothing
//Globals:	myMass

    public double getMass() {
        return myMass;
    }
//********************************************************************
//Method:	getID
//Description:	A constructor method for getting the ID and returning it.
//Parameters:  	None
//Returns:     	myID
//Calls:        Nothing
//Globals:	myID

    public int getID() {
        return myID;
    }
//********************************************************************
//Method:	pythagorus
//Description:	This method computes the distances between two entities and computes
//              said calculation throgh returning it.
//Parameters:  	xyPoint p1
//              xyPoint p2
//Returns:     	Math.sqrt((p1.myX-p2.myX)*(p1.myX-p2.myX)+(p1.myY-p2.myY)*(p1.myY-p2.myY))
//Calls:        Nothing
//Globals:	myX
//              myY

    private double pythagorus(xyPoint p1, xyPoint p2) {
        return Math.sqrt((p1.myX - p2.myX) * (p1.myX - p2.myX)
                + (p1.myY - p2.myY) * (p1.myY - p2.myY));
    } // end of  pythagorus
//********************************************************************
//Method:	determineVector
//Description:	determineVector is a method that takes in the parameter sObject other
//              in order to access all parameters to resolve the Vector with
//              our current Vector from its first iteration to each sequential one.
//Parameters:  	sObject other
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myPosition.myX
//              myPosition.myY
//              myVectorAngle
//              myColliderID
//              myAcceleration.x
//              myAcceleration.y
//              myX
//              myY
//              debug
//              distance
//              myMass

     public void determineVector(sObject other)
  {
    xyPoint p2 = other.getPosition();
    Angles AngleObject = new Angles();
    double thisVectorAngle
      = Angles.computeVectorAngle(myPosition.myX, myPosition.myY, p2.myX, p2.myY);
    if (debug == 1)
    {
      System.out.printf("%d - %d %10.8f,%10.8f %10.8f,%10.8f\t",
        myID, other.getID(), myPosition.myX, myPosition.myY, p2.myX, p2.myY);
    }
    //TODO: update myVelocity and myPosition 
    distance = (int) pythagorus(myPosition, p2);
    double thisAcceleration = G.getGravity() * myMass / distance / distance;
    if (debug == 1)
    {
      System.out.printf("%7.4f %5.2f %10.8f %7.2f\n",
        thisVectorAngle, distance, thisAcceleration, thisVectorAngle / Math.PI * 180.0);
    }
    double dx = thisAcceleration * Math.cos(thisVectorAngle);
    double dy = thisAcceleration * Math.sin(thisVectorAngle);
    // resolve vector with current vector;
    if (myVectorAngle < 0) // we are talking about the first time
    {
      myAcceleration.x = dx;
      myAcceleration.y = dy;
    } // end of if first
    else
    {
      myAcceleration.x += dx;
      myAcceleration.y += dy;
    } // end of else on not first
    myVectorAngle
      = Angles.computeVectorAngle(0.0, 0.0, myAcceleration.x, myAcceleration.y);
    if (debug == 1)
    {
      ToPrint();
    }

    myColliderID = -1;
  } // end of updateVector
//********************************************************************
//Method:	slope
//Description:	Takes the absolute value of just dx (passed through) and
//              uses it as a comparator to epsilon which if smaller than, it
//              becomes the value of epsilon.  Is then the divisor to dy to
//              find the slope of two points.
//Parameters:  	xyPoint p1
//              xyPoint p2
//Returns:     	mySlope
//Calls:        Nothing
//Globals:	myX
//              myY

    private double slope(xyPoint p1, xyPoint p2)
  {
    double mySlope;
    double dx = (p1.myX - p2.myX);
    double dy = (p1.myY - p2.myY);
    double epsilon = 0.00001;
    if (Math.abs(dx) < epsilon)
    {
      dx = epsilon; //TODO: think
    }
    mySlope = dy / dx;
    return mySlope;
  } // end of slope
//********************************************************************
//Method:	quadratic
//Description:	This method computes the preliminary values for the quadratic (a, b, c)
//              and prepares them for the method it passes it to.  That method is 'leastPositiveRoot'
//              and it takes the values computed from 'quadratic' for the quadratic formula.
//Parameters:  	double a
//              double b
//              double c
//Returns:     	t
//Calls:        leastPositiveRoot
//Globals:	None

    private double quadratic(double a, double b, double c)
  {   // not really the quadratic eqn solver
    double t = 0;
    double radicand = (b * b) + 2 * a * c;
    if (radicand >= 0.0)
    {
      t = leastPositiveRoot(b, radicand, a);
    } else
    {
      //println("TODO: radicand is less than 0...");
    }
    return t;

  } // end of quaradtic
//********************************************************************
//Method:	time2circle
//Description:	This method makes sure the values from 'quadratic' and 'leastPositiveRoot'
//              put together to implement the quadratic formula are viable by making sure
//              its greater than epsilon.
//Parameters:  	double d_C
//              double angle
//              xyVector a
//              xyVector v
//Returns:     	t
//Calls:        quadratic
//Globals:	x
//              y
//              epsilon

    private double time2circle(double d_C, double angle, xyVector a, xyVector v)
  {
    double t = 0.0;
    double dx = d_C * Math.cos(angle);
    double dy = d_C * Math.sin(angle);

    if (Math.abs(a.x) > epsilon)
    {
      t = quadratic(a.x, v.x, dx);
    }//end of math.abs if statment
    else
    {
      if (Math.abs(v.x) > epsilon) // != 0)
      {
        t = dx / v.x;
      } else if (Math.abs(a.y) > epsilon)
      {
        t = quadratic(a.y, v.y, dy);
      }//end of math.abs if statment
      else
      {
        t = dy / v.y;
      }
    }
    return t;
  }
//********************************************************************
//Method:	leastPositiveRoot
//Description:	leastPositiveRoot takes the values that were quality checked in time2circle
//              which were passed to 'quadratic' and computes the quadratic formula to calculate
//              the smallest root.  It is assured to find the smallest root by testing if the
//              min of both roots (quadratic + and quadratic -) were less than zero that the
//              return of t would be assigned to that value for the lowest time.
//Parameters:  	double b
//              double radicand
//              double divisor
//Returns:     	t
//Calls:        Nothing
//Globals:	None

   private double leastPositiveRoot(double b, double radicand, double divisor)
  {
    double t;
    double squareroot = Math.sqrt(radicand);
    double root1 = (-b - squareroot) / divisor;
    double root2 = (-b + squareroot) / divisor;

    if (Math.min(root1, root2) < 0)
    {
      t = Math.max(root1, root2);
    } else
    {
      t = Math.min(root1, root2);
    }
    return t;
  } // end of leastPositiveRoot
//********************************************************************
//Method:	determineIfCollision
//Description:	This method calculates whether or not we have a intersecting collision
//              by comparing and computing the distance of two entities' centers
//              points.  If the max value of the two center distances going in is less
//              than the minimum of two values' distance going out, then our colliding time
//              because the minimum value of either values distance to the center.
//Parameters:  	sObject other
//Returns:     	CollisionTime
//Calls:        ToPrint
//              slope
//              time2circle
//              pythagorus
//Globals:	p_C
//              x
//              y
//              t_Ain
//              t_Bin
//              myX
//              myY
//              debug
//              epsilon
//              myEffectivePosition
//              myPosition
//              myCollisionTime
//              myRadius
//              myVectorAngle
//              myVelocity
//              myAcceleration

    public double determineIfCollision(sObject other, double wTime)
  {

    double crowsflyDistance = pythagorus(myPosition, other.getPosition());
    //println("CrowsflyDistance:" + crowsflyDistance);
      if (crowsflyDistance < (other.getRadius() + myRadius))
      {
        myCollisionTime = epsilon;
        myColliderID = other.getID();
        return myCollisionTime;
      }    // must compute new, temporary velocity vector for everyone
    p_C = new xyPoint();

    xyPoint p_A = new xyPoint(myEffectivePosition);

    xyPoint p_B = other.getEffectivePosition();
    //println("-----");
    if (debug == 1)
    {
      ToPrint();
      other.ToPrint();
    }

    double s_A = slope(myPosition, p_A);
    double s_B = slope(other.getPosition(), p_B);
    if (debug == 1)
    {
      //println(s_A + "  " + s_B);
    }
    double dSlope = s_A - s_B;
    myCollisionTime = 999;
    if (Math.abs(dSlope) > epsilon)
    // ==================================
    { // Case 1
      p_C.myX
        = (s_A * p_A.myX - s_B * p_B.myX + p_B.myY - p_A.myY)
        / dSlope;
      p_C.myY
        = s_A * (p_C.myX - p_A.myX) + p_A.myY;
      if (debug == 1)
      {
        p_C.ToPrint();
      }
      double d_AC = pythagorus(p_A, p_C);
      double d_BC = pythagorus(p_B, p_C);
      double d_Ain = d_AC - myRadius;
      double d_Aout = d_AC + myRadius;
      double d_Bin = d_BC - other.getRadius();
      double d_Bout = d_BC + other.getRadius();

      t_Ain = time2circle(d_Ain, myVectorAngle, myAcceleration, myVelocity);
      double t_Aout = time2circle(d_Aout, myVectorAngle, myAcceleration, myVelocity);

      xyVector hisAcceleration = other.getAcceleration();
      xyVector hisVelocity = other.getEffectiveVelocity();
      double hisVectorAngle = other.getAngle();

      t_Bin = time2circle(d_Bin, hisVectorAngle, hisAcceleration, hisVelocity);
      double t_Bout = time2circle(d_Bout, hisVectorAngle, hisAcceleration, hisVelocity);
      if (debug == 0)
      {
        //println(t_Ain + " " + t_Aout + "  " + t_Bin + " " + t_Bout);
      }
//      if((t_Ain >= t_Bin && t_Aout <= t_Bout) || (t_Bin >=t_Ain && t_Bout <= t_Aout))
      if (Math.max(t_Ain, t_Bin) < Math.min(t_Aout, t_Bout))
      {
        myCollisionTime = Math.min(t_Ain, t_Bin);
      }
      // update positions and display
      if (myCollisionTime < wTime)
      {
        myColliderID = other.getID();
      }
      // yes we only get the last one
      return myCollisionTime;

    } else
    // ==================================
    { // Case 2
      System.out.println("PARALLEL!!!");
      //System.out.println("The circles are " + crowsflyDistance + " apart.");

      double apart = Math.sin(myVectorAngle - other.getAngle()) * crowsflyDistance;
      //System.out.println("The circle's paths are " + apart + " apart.");

      if (apart > (other.getRadius() + myRadius))
      {
        //println("Paths are greater than sum of radii.");
        return myCollisionTime; // might not be zero because of some other
      }

      double d_apart
        = Math.sqrt((crowsflyDistance * crowsflyDistance) - (apart * apart));
      //System.out.println("The circle's d_apart is " + d_apart + " apart.");

      // need x & y for distances
      double d_a = pythagorus(myPosition, myEffectivePosition);
      double d_b = pythagorus(other.myPosition, other.getEffectivePosition());
      //println("distances: " + d_a + " " + d_b + " " + d_apart);
      xyPoint d_slowMax;
      xyPoint d_fastMax;
      sObject slow; // N.B. no new
      sObject fast;
      if (d_a > d_b)
      { // A is faster
        fast = this;
        slow = other;
      } else
      { // B is faster
        fast = other;
        slow = this;
      }
      d_fastMax = new xyPoint(
        fast.getPosition().myX - fast.getEffectivePosition().myX,
        fast.getPosition().myY - fast.getEffectivePosition().myY);
      d_slowMax = new xyPoint(
        slow.getPosition().myX - slow.getEffectivePosition().myX,
        slow.getPosition().myY - slow.getEffectivePosition().myY);

      //System.out.print("Fast: ");
      fast.ToPrint();
      //System.out.print("Slow: ");
      slow.ToPrint();
      //System.out.print("d_slowMax: ");
      d_slowMax.ToPrint();
      //print("d_fastMax: ");
      d_fastMax.ToPrint();

      boolean sameDirection = false;

      if ((Math.signum(d_slowMax.myX) == Math.signum(d_fastMax.myX))
        && (Math.signum(d_slowMax.myY) == Math.signum(d_fastMax.myY)))
      {
        sameDirection = true;
      }

      double t = 0.0;
      xyVector vf = new xyVector(fast.getEffectiveVelocity());
      xyVector vs = new xyVector(slow.getEffectiveVelocity());
      double dVx = vf.x - vs.x;
      double dVy = vf.y - vs.y;
      xyVector af = new xyVector(fast.getAcceleration());
      xyVector as = new xyVector(slow.getAcceleration());
      double dAx = af.x - as.x;
      double dAy = af.y - as.y;
      double dSpeed = Math.sqrt(dVx * dVx + dVy * dVy);
      double dAcceleration = Math.sqrt(dAx * dAx + dAy * dAy);
      double radicand = 0;
      // Vf-Vs is always positive; may have arbitr ...
      if (dAx <= 0)
      {
        t = d_apart / dSpeed;
      } else
      {
        radicand = dSpeed * dSpeed + 2 * dAcceleration * d_apart;
        t = leastPositiveRoot(dSpeed, radicand, dAx);
      }
      //System.out.println("time: " + t + "   " + radicand + " " + d_apart + " " + dSpeed + " " + dAcceleration);
      /*
       {
       double radicand = dVx * dVx + 2 * dAx * apart; // d_apart
       t = leastPositiveRoot(dVx, radicand, dAx);
       System.out.println("time: " + t + "..." + dVx + " " + dAx + " " + d_apart);

       }
       */
      if (t > wTime) // workingT
      {
        if (sameDirection)
        {
          //System.out.println("The circles are traveling in the same direction.");
          // If the entities are moving in the same direction, compute time to intersect ...
        } // end of same direction
        else
        {
          //System.out.println("The circles are NOT traveling in the same direction.");
          // so are they going away from each other?
          // could use d_apart against effective d_apart
          // but the crowsflydistance would also be greater
          double effCrowsflyDistance
            = pythagorus(myEffectivePosition, other.getEffectivePosition());
          if (effCrowsflyDistance > crowsflyDistance)
          {
            //System.out.println("Moving away from each other");
          }

        } // end of else NOT same direction
        return myCollisionTime;
      } // end of sorry no collision
      myCollisionTime = t;

    } // end of Case 2

    // update positions and display
    if (myCollisionTime < wTime)
    {
      myColliderID = other.getID();
    }
    // yes we only get the last one
    return myCollisionTime;
  } // end of findTimeOfCollision
//********************************************************************
//Method:	resolveCollision
//Description:	resolveCollision computes the result of what happens after a collision
//              has occured.  This is only for crossing collisions, not parallel ones.
//Parameters:  	sObject other
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myColliderID
//              myMass
//              x
//              y
//              myX
//              myY
//              myEffectiveVelocity
//              myPosition
//              myVelocity
//              myAcceleration
//              myRadius

    
//********************************************************************
//Method:	resolveCollision
//Description:	The overload version of resolveCollision to compute the result of what happens
//              after a collision for a parallel instance.  This does not work for an intersection
//              collision.
//Parameters:  	double m3
//              double x
//              double y
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myColliderID
//              myMass
//              p_C
//              x
//              y
//              myX
//              myY
//              myPosition
//              myVelocity
//              myAcceleration
//              myRadius

   public void resolveCollision(double m3, xyPoint pos, xyVector vel, xyVector acc)
  {
    myColliderID = -1;
    // TODO: compute velocitites at collision time
    myPosition.myX = pos.myX;
    myPosition.myY = pos.myY;

    myMass = m3;
    myVelocity.x = vel.x / m3;
    myVelocity.y = vel.y / m3;
    myAcceleration.x = acc.x;
    myAcceleration.y = acc.y;
    myRadius = Math.sqrt(myMass / Math.PI);  // m=pi()*r*r
  } // end of resolveCollision

//********************************************************************
//Method:	updatePosition
//Description:	Computes the new position of the entities with its new parameters.
//              The velocities are summed to its corresponding acceleration values
//              which were the product of the working timestep.
//Parameters:   double wTime
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myX
//      	myY
//      	x
//      	y
//              myPosition
//              myVelocity
//              myAcceleration

    public void updatePosition(double wTime)
  {
    myPosition.myX += (myVelocity.x + 0.5 * myAcceleration.x * wTime) * wTime;
    myPosition.myY += (myVelocity.y + 0.5 * myAcceleration.y * wTime) * wTime;
    myVelocity.x += myAcceleration.x * wTime;
    myVelocity.y += myAcceleration.y * wTime;
    myAcceleration.x = 0;
    myAcceleration.y = 0;
  } // end of updatePosition
//********************************************************************
//Method:	effectivePosition
//Description:	Method for reassigning myEffectivePosition.myY and myEffectivePosition.myX
//              after computing the Vectors of a potential collision but before call to
//              test new entities with new vectors for collisions.
//Parameters:  	double wTime
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myEffectiveVelocity
//              myVelocity
//              myAcceleration
//              myPosition
//              x
//              y
//              myX
//              myY

    public void effectivePosition(double wTime)
  { // this is to not destroy velocity or position
    myEffectiveVelocity.x = myVelocity.x + myAcceleration.x * wTime;
    myEffectiveVelocity.y = myVelocity.y + myAcceleration.y * wTime;
    myEffectivePosition.myX
      = myPosition.myX + (myEffectiveVelocity.x + 0.5 * myAcceleration.x * wTime) * wTime;
    myEffectivePosition.myY
      = myPosition.myY + (myEffectiveVelocity.y + 0.5 * myAcceleration.y * wTime) * wTime;

  } // end of effectivePosition
//********************************************************************
//Method:	getColliderID
//Description:	The constructor method for returning 'myColliderID'
//Parameters:  	None
//Returns:     	myColliderID
//Calls:        Nothing
//Globals:	myColliderID

    public int getColliderID() {
        return myColliderID;
    }
//********************************************************************
//Method:	getWhoCollided
//Description:  Tests if the difference of each time is still less than epsilon,
//              if it is it returns a myColliderID which breaks the while loop
//              in the main project class
//Parameters:  	cTime
//Returns:     	myColliderID
//              -1
//Calls:        Nothing
//Globals:	myCollisionTime
//              myColliderID

    public int getWhoCollided(double cTime) {

        if (Math.abs(cTime - myCollisionTime) < 0.001) {
            return myColliderID;
        } else {
            return -1;
        }
    }
//********************************************************************
//Method:	getEffectiveVelocity
//Description:	The constructor method for returning myEffectiveVelocity
//Parameters:  	None
//Returns:     	myEffectiveVelocity
//Calls:        Nothing
//Globals:	myEffectiveVelocity

    public xyVector getEffectiveVelocity() {

        return myEffectiveVelocity;
    }
//********************************************************************
//Method:	getAngle
//Description:	The constructor method for returning myVectorAngle
//Parameters:  	None
//Returns:     	myVectorAngle
//Calls:        Nothing
//Globals:	myVectorAngle

    public double getAngle() {
        return myVectorAngle;
    }
//********************************************************************
//Method:	getAcceleration
//Description:	The constructor method for returning myAcceleration
//Parameters:  	None
//Returns:     	myAcceleration
//Calls:        Nothing
//Globals:	myAcceleration

    public xyVector getAcceleration() {
        return myAcceleration;
    }
//********************************************************************
//Method:	getPosition
//Description:	The constructor method for returning myPosition
//Parameters:  	None
//Returns:     	myPosition
//Calls:        Nothing
//Globals:	myPosition

    public xyPoint getPosition() {
        return myPosition;
    } // end of getPosition
//********************************************************************
//Method:	getEffectivePosition
//Description:	The constructor method for returning myEffectivePosition
//Parameters:  	None
//Returns:     	myEffectivePosition
//Calls:        Nothing
//Globals:	myEffectivePosition

    public xyPoint getEffectivePosition() {
        return myEffectivePosition;
    } // end of getPosition
//********************************************************************
//Method:	remove
//Description:	Method to be called when after a new entity has been created from a collision
//              and the other entity or entities that participated still remain.
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myRadius

    public void remove() {
        myRadius = 0;

    } // end of zeroOutRadius
//********************************************************************
//Method:	ToPrint
//Description:	Method to be called when wanting to output all the properties of all
//              your entities.
//Parameters:  	None
//Returns:     	Nothing
//Calls:        Nothing
//Globals:	myID
//              myRadius
//              myVectorAngle
//              myAcceleration
//              myVelocity
//              myPosition
//              myMass

    public void ToPrint() {
        //System.out.printf("
        System.out.printf("%d. radius:%8.2f | Angle:%8.5f(%8.3f) | Acc:%8.5f,%8.5f | Vel:%8.3f,%8.3f  | Pos:%8.2f,%8.2f | Mass:%8.2f\n",
                myID, myRadius, myVectorAngle, myVectorAngle / Math.PI * 180,
                myAcceleration.x, myAcceleration.y, myVelocity.x, myVelocity.y, myPosition.myX, myPosition.myY, myMass
        );
    } // end of ToPrint
    //********************************************************************
}//End of sObject class
//*******************************************************************************
//*******************************************************************************
