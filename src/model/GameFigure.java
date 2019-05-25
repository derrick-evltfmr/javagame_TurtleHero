package model;

import controller.PlayerInputEventQueue;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class GameFigure { // since it cannot define anything until we have a concrete one there

    public Point2D.Float location;
    public boolean done = false; // means the object is done and should be removed from the game scene

    public int hitCount = 0;

    public int hitByStrongCount = 0;
    public int ex9ufoExplodeCount = 10;

    public int blockedByShield = 0;

    public int friendINDEX; //1=TurtleHero 2=bubble 3=starfish 4=waterbeam 5=sparklingbubble 6=whirlpoolshield
    public int enemyINDEX; //1=Lob 2=Piranha 3=IceBerg/Dark Crystal 4=DSDevil 5=DBall 6=Tentacles

    public int enemyHP;
    public int hitTheTurtleHero = 0;
    public int hitTheVillage = 0;

    public int energyValue;
    public int chargeCount;

    public int characterLife;
    public int villageLife;

    public int starType = 0;

    public GameFigure hitThisGameFigure;
    public boolean hitThisGameFigureAlready = false;

    public boolean canHurt = true;


    public GameFigure(float x, float y) { // change types into float
        location = new Point2D.Float(x, y);
    }

    public GameFigure() { // when this is empty, it will return (0,0)
        this(0,0);  // then it'll trigger the constructor above
    }

    public void setLocation(float x, float y) {
        location.x = x;
        location.y = y;
    }


    public boolean collideWith(GameFigure o) {

//        Point2D.Float enemyCenter = new Point2D.Float(o.location.x, o.location.y);
//        System.out.println(o);
        //Starfish energy
        if (o.friendINDEX == 3) { //starfish
            Point2D.Float starCenter = new Point2D.Float(o.location.x +31, o.location.y +31);

            double distStarCen=100;

            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y + 59);
                distStarCen = turtleCenter.distance(starCenter);
            }



            if (distStarCen <= this.getCollisionRadius() + o.getCollisionRadius()) {
                return true;
            } else {
                return false;
            }
        }

        //Lobster Mon
        if (o.enemyINDEX == 1) { //lobm
            Point2D.Float lobCenter = new Point2D.Float(o.location.x - 19, o.location.y - 12);
            Point2D.Float lobTop = new Point2D.Float(o.location.x - 19, o.location.y - 50);
            Point2D.Float lobBtm = new Point2D.Float(o.location.x - 19, o.location.y + 16);

            double distLobCen;
            double distLobTop;
            double distLobBtm;


            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y +59);
                distLobCen = turtleCenter.distance(lobCenter);
                distLobTop = turtleCenter.distance(lobTop);
                distLobBtm = turtleCenter.distance(lobBtm);
            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x-40+40,this.location.y+39-10-this.chargeCount*2);
                distLobCen = waterBeamTop.distance(lobCenter);
                distLobTop = waterBeamTop.distance(lobTop);
                distLobBtm = waterBeamTop.distance(lobBtm);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //lob center
                if (whirlpoolShieldLeft.distance(lobCenter) <= whirlpoolShieldTop.distance(lobCenter)
                        && whirlpoolShieldLeft.distance(lobCenter) <= whirlpoolShieldRight.distance(lobCenter)){ // LEFT CLOSER
                    distLobCen = whirlpoolShieldLeft.distance(lobCenter);
                } else if (whirlpoolShieldRight.distance(lobCenter) <= whirlpoolShieldTop.distance(lobCenter)
                        && whirlpoolShieldRight.distance(lobCenter) <= whirlpoolShieldLeft.distance(lobCenter)) { // RIGHT CLOSER
                    distLobCen = whirlpoolShieldRight.distance(lobCenter);
                } else { // CENTER CLOSER
                    distLobCen = whirlpoolShieldTop.distance(lobCenter);
                }

                //lob top
                if (whirlpoolShieldLeft.distance(lobTop) <= whirlpoolShieldTop.distance(lobTop)
                        && whirlpoolShieldLeft.distance(lobTop) <= whirlpoolShieldRight.distance(lobTop)){ // LEFT CLOSER
                    distLobTop = whirlpoolShieldLeft.distance(lobTop);
                } else if (whirlpoolShieldRight.distance(lobTop) <= whirlpoolShieldTop.distance(lobTop)
                        && whirlpoolShieldRight.distance(lobTop) <= whirlpoolShieldLeft.distance(lobTop)) { // RIGHT CLOSER
                    distLobTop = whirlpoolShieldRight.distance(lobTop);
                } else { // CENTER CLOSER
                    distLobTop = whirlpoolShieldTop.distance(lobTop);
                }

                //lob btm
                if (whirlpoolShieldLeft.distance(lobBtm) <= whirlpoolShieldTop.distance(lobBtm)
                        && whirlpoolShieldLeft.distance(lobBtm) <= whirlpoolShieldRight.distance(lobBtm)){ // LEFT CLOSER
                    distLobBtm = whirlpoolShieldLeft.distance(lobBtm);
                } else if (whirlpoolShieldRight.distance(lobBtm) <= whirlpoolShieldTop.distance(lobBtm)
                        && whirlpoolShieldRight.distance(lobBtm) <= whirlpoolShieldLeft.distance(lobBtm)) { // RIGHT CLOSER
                    distLobBtm = whirlpoolShieldRight.distance(lobBtm);
                } else { // CENTER CLOSER
                    distLobBtm = whirlpoolShieldTop.distance(lobBtm);
                }


            } else {// other small case like bubble
                distLobCen = this.location.distance(lobCenter);
                distLobTop = this.location.distance(lobTop);
                distLobBtm = this.location.distance(lobBtm);
            }



            if (distLobCen <= this.getCollisionRadius() + o.getCollisionRadius()
                || distLobTop <= this.getCollisionRadius() + o.getCollisionRadius()
                || distLobBtm <= this.getCollisionRadius() + o.getCollisionRadius()) {
                return true;
            } else {
                return false;
            }
        }

        //Piranha Monster
        if (o.enemyINDEX == 2) { //piranaha
            Point2D.Float piranCenter = new Point2D.Float(o.location.x - 14, o.location.y +72);
            Point2D.Float piranTop = new Point2D.Float(o.location.x - 14, o.location.y +0); // tail
            Point2D.Float piranBtm = new Point2D.Float(o.location.x - 14, o.location.y + 135); // head

            double distPiranCen;
            double distPiranTop;
            double distPiranBtm;


            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y +59);
                distPiranCen = turtleCenter.distance(piranCenter);
                distPiranTop = turtleCenter.distance(piranTop);
                distPiranBtm = turtleCenter.distance(piranBtm);
            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x-40+40,this.location.y+39-10-this.chargeCount*2);
                distPiranCen = waterBeamTop.distance(piranCenter);
                distPiranTop = waterBeamTop.distance(piranTop);
                distPiranBtm = waterBeamTop.distance(piranBtm);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //piran center
                if (whirlpoolShieldLeft.distance(piranCenter) <= whirlpoolShieldTop.distance(piranCenter)
                        && whirlpoolShieldLeft.distance(piranCenter) <= whirlpoolShieldRight.distance(piranCenter)){ // LEFT CLOSER
                    distPiranCen = whirlpoolShieldLeft.distance(piranCenter);
                } else if (whirlpoolShieldRight.distance(piranCenter) <= whirlpoolShieldTop.distance(piranCenter)
                        && whirlpoolShieldRight.distance(piranCenter) <= whirlpoolShieldLeft.distance(piranCenter)) { // RIGHT CLOSER
                    distPiranCen = whirlpoolShieldRight.distance(piranCenter);
                } else { // CENTER CLOSER
                    distPiranCen = whirlpoolShieldTop.distance(piranCenter);
                }

                //piran top
                if (whirlpoolShieldLeft.distance(piranTop) <= whirlpoolShieldTop.distance(piranTop)
                        && whirlpoolShieldLeft.distance(piranTop) <= whirlpoolShieldRight.distance(piranTop)){ // LEFT CLOSER
                    distPiranTop = whirlpoolShieldLeft.distance(piranTop);
                } else if (whirlpoolShieldRight.distance(piranTop) <= whirlpoolShieldTop.distance(piranTop)
                        && whirlpoolShieldRight.distance(piranTop) <= whirlpoolShieldLeft.distance(piranTop)) { // RIGHT CLOSER
                    distPiranTop = whirlpoolShieldRight.distance(piranTop);
                } else { // CENTER CLOSER
                    distPiranTop = whirlpoolShieldTop.distance(piranTop);
                }

                //piran btm
                if (whirlpoolShieldLeft.distance(piranBtm) <= whirlpoolShieldTop.distance(piranBtm)
                        && whirlpoolShieldLeft.distance(piranBtm) <= whirlpoolShieldRight.distance(piranBtm)){ // LEFT CLOSER
                    distPiranBtm = whirlpoolShieldLeft.distance(piranBtm);
                } else if (whirlpoolShieldRight.distance(piranBtm) <= whirlpoolShieldTop.distance(piranBtm)
                        && whirlpoolShieldRight.distance(piranBtm) <= whirlpoolShieldLeft.distance(piranBtm)) { // RIGHT CLOSER
                    distPiranBtm = whirlpoolShieldRight.distance(piranBtm);
                } else { // CENTER CLOSER
                    distPiranBtm = whirlpoolShieldTop.distance(piranBtm);
                }


            } else {// other small case like bubble
                distPiranCen = this.location.distance(piranCenter);
                distPiranTop = this.location.distance(piranTop);
                distPiranBtm = this.location.distance(piranBtm);
            }



            if (distPiranCen <= this.getCollisionRadius() + 95 -25 // PIRANHA CENTER RADIUS 95, 95 too big, minus to 85
                    || distPiranTop <= this.getCollisionRadius() + 62.1 // PIRANHA TOP RADIUS 62.1
                    || distPiranBtm <= this.getCollisionRadius() + 36.1 ) { // PIRANHA BTM RADIUS 36.1
                return true;
            } else {
                return false;
            }
        }


        //Compressed IceBerg
        if (o.enemyINDEX == 3) { //iceberg
            Point2D.Float iceBergCenter = new Point2D.Float(o.location.x, o.location.y);

            double distIceBergCen;

            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y + 59);
                distIceBergCen = turtleCenter.distance(iceBergCenter);

            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x - 40 + 40, this.location.y + 39 - 10 - this.chargeCount * 2);
                distIceBergCen = waterBeamTop.distance(iceBergCenter);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //iceBerg center
                if (whirlpoolShieldLeft.distance(iceBergCenter) <= whirlpoolShieldTop.distance(iceBergCenter)
                        && whirlpoolShieldLeft.distance(iceBergCenter) <= whirlpoolShieldRight.distance(iceBergCenter)){ // LEFT CLOSER
                    distIceBergCen = whirlpoolShieldLeft.distance(iceBergCenter);
                } else if (whirlpoolShieldRight.distance(iceBergCenter) <= whirlpoolShieldTop.distance(iceBergCenter)
                        && whirlpoolShieldRight.distance(iceBergCenter) <= whirlpoolShieldLeft.distance(iceBergCenter)) { // RIGHT CLOSER
                    distIceBergCen = whirlpoolShieldRight.distance(iceBergCenter);
                } else { // CENTER CLOSER
                    distIceBergCen = whirlpoolShieldTop.distance(iceBergCenter);
                }

            } else if(this.friendINDEX == 3) {//starfish
                return false;
            }else {// other small case like bubble
                distIceBergCen = this.location.distance(iceBergCenter);
            }



            if (distIceBergCen <= this.getCollisionRadius() + 15){ // ICEBERG RADIUS 20-5
                return true;
            } else {
                return false;
            }
        }

        //DS Devil
        if (o.enemyINDEX == 4) { //dsDevil
            Point2D.Float dsDevilCenter = new Point2D.Float(o.location.x, o.location.y-90);
            Point2D.Float dsDevilLeft = new Point2D.Float(o.location.x-80-5, o.location.y-2);
            Point2D.Float dsDevilRight = new Point2D.Float(o.location.x+80+5, o.location.y-2);
            Point2D.Float dsDevilFront = new Point2D.Float(o.location.x, o.location.y+50-20-25);

            double distdsDevilCen;
            double distdsDevilLeft;
            double distdsDevilRight;
            double distdsDevilFront;

            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y +59);
                distdsDevilCen = turtleCenter.distance(dsDevilCenter);
                distdsDevilLeft = turtleCenter.distance(dsDevilLeft);
                distdsDevilRight = turtleCenter.distance(dsDevilRight);
                distdsDevilFront = turtleCenter.distance(dsDevilFront);
            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x-40+40,this.location.y+39-10-this.chargeCount*2);
                distdsDevilCen = waterBeamTop.distance(dsDevilCenter);
                distdsDevilLeft = waterBeamTop.distance(dsDevilLeft);
                distdsDevilRight = waterBeamTop.distance(dsDevilRight);
                distdsDevilFront = waterBeamTop.distance(dsDevilFront);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //dsDevil center
                if (whirlpoolShieldLeft.distance(dsDevilCenter) <= whirlpoolShieldTop.distance(dsDevilCenter)
                        && whirlpoolShieldLeft.distance(dsDevilCenter) <= whirlpoolShieldRight.distance(dsDevilCenter)){ // LEFT CLOSER
                    distdsDevilCen = whirlpoolShieldLeft.distance(dsDevilCenter);
                } else if (whirlpoolShieldRight.distance(dsDevilCenter) <= whirlpoolShieldTop.distance(dsDevilCenter)
                        && whirlpoolShieldRight.distance(dsDevilCenter) <= whirlpoolShieldLeft.distance(dsDevilCenter)) { // RIGHT CLOSER
                    distdsDevilCen = whirlpoolShieldRight.distance(dsDevilCenter);
                } else { // CENTER CLOSER
                    distdsDevilCen = whirlpoolShieldTop.distance(dsDevilCenter);
                }

                //dsDevil left
                if (whirlpoolShieldLeft.distance(dsDevilLeft) <= whirlpoolShieldTop.distance(dsDevilLeft)
                        && whirlpoolShieldLeft.distance(dsDevilLeft) <= whirlpoolShieldRight.distance(dsDevilLeft)){ // LEFT CLOSER
                    distdsDevilLeft = whirlpoolShieldLeft.distance(dsDevilLeft);
                } else if (whirlpoolShieldRight.distance(dsDevilLeft) <= whirlpoolShieldTop.distance(dsDevilLeft)
                        && whirlpoolShieldRight.distance(dsDevilLeft) <= whirlpoolShieldLeft.distance(dsDevilLeft)) { // RIGHT CLOSER
                    distdsDevilLeft = whirlpoolShieldRight.distance(dsDevilLeft);
                } else { // CENTER CLOSER
                    distdsDevilLeft = whirlpoolShieldTop.distance(dsDevilLeft);
                }

                //dsDevil right
                if (whirlpoolShieldLeft.distance(dsDevilRight) <= whirlpoolShieldTop.distance(dsDevilRight)
                        && whirlpoolShieldLeft.distance(dsDevilRight) <= whirlpoolShieldRight.distance(dsDevilRight)){ // LEFT CLOSER
                    distdsDevilRight = whirlpoolShieldLeft.distance(dsDevilRight);
                } else if (whirlpoolShieldRight.distance(dsDevilRight) <= whirlpoolShieldTop.distance(dsDevilRight)
                        && whirlpoolShieldRight.distance(dsDevilRight) <= whirlpoolShieldLeft.distance(dsDevilRight)) { // RIGHT CLOSER
                    distdsDevilRight = whirlpoolShieldRight.distance(dsDevilRight);
                } else { // CENTER CLOSER
                    distdsDevilRight = whirlpoolShieldTop.distance(dsDevilRight);
                }

                //dsDevil front
                if (whirlpoolShieldLeft.distance(dsDevilFront) <= whirlpoolShieldTop.distance(dsDevilFront)
                        && whirlpoolShieldLeft.distance(dsDevilFront) <= whirlpoolShieldRight.distance(dsDevilFront)){ // LEFT CLOSER
                    distdsDevilFront = whirlpoolShieldLeft.distance(dsDevilFront);
                } else if (whirlpoolShieldRight.distance(dsDevilFront) <= whirlpoolShieldTop.distance(dsDevilFront)
                        && whirlpoolShieldRight.distance(dsDevilFront) <= whirlpoolShieldLeft.distance(dsDevilFront)) { // RIGHT CLOSER
                    distdsDevilFront = whirlpoolShieldRight.distance(dsDevilFront);
                } else { // CENTER CLOSER
                    distdsDevilFront = whirlpoolShieldTop.distance(dsDevilFront);
                }


            } else {// other small case like bubble
                distdsDevilCen = this.location.distance(dsDevilCenter);
                distdsDevilLeft = this.location.distance(dsDevilLeft);
                distdsDevilRight = this.location.distance(dsDevilRight);
                distdsDevilFront = this.location.distance(dsDevilFront);

            }



            if (distdsDevilCen <= this.getCollisionRadius() + o.getCollisionRadius() // dsDevilHA CENTER RADIUS 95, 95 too big, minus to 85
                    || distdsDevilLeft <= this.getCollisionRadius() + 105// dsDevilHA TOP RADIUS 62.1
                    || distdsDevilRight <= this.getCollisionRadius() + 105
                    || distdsDevilFront <= this.getCollisionRadius() + 130) { // dsDevilHA BTM RADIUS 36.1
                return true;
            } else {
                return false;
            }
        }

        // Dark Energy Ball
        if (o.enemyINDEX == 5) { //DB
            Point2D.Float DBCenter = new Point2D.Float(o.location.x, o.location.y);

            double distDBCen;

            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y + 59);
                distDBCen = turtleCenter.distance(DBCenter);

            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x - 40 + 40, this.location.y + 39 - 10 - this.chargeCount * 2);
                distDBCen = waterBeamTop.distance(DBCenter);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //DB center
                if (whirlpoolShieldLeft.distance(DBCenter) <= whirlpoolShieldTop.distance(DBCenter)
                        && whirlpoolShieldLeft.distance(DBCenter) <= whirlpoolShieldRight.distance(DBCenter)){ // LEFT CLOSER
                    distDBCen = whirlpoolShieldLeft.distance(DBCenter);
                } else if (whirlpoolShieldRight.distance(DBCenter) <= whirlpoolShieldTop.distance(DBCenter)
                        && whirlpoolShieldRight.distance(DBCenter) <= whirlpoolShieldLeft.distance(DBCenter)) { // RIGHT CLOSER
                    distDBCen = whirlpoolShieldRight.distance(DBCenter);
                } else { // CENTER CLOSER
                    distDBCen = whirlpoolShieldTop.distance(DBCenter);
                }

            } else if(this.friendINDEX == 3) {//starfish
                return false;
            }else {// other small case like bubble
                distDBCen = this.location.distance(DBCenter);
            }



            if (distDBCen <= this.getCollisionRadius() + o.getCollisionRadius()){ // ICEBERG RADIUS 20-5
                return true;
            } else {
                return false;
            }
        }

        //Tentacle
        if (o.enemyINDEX == 6) { //tentacle
            Point2D.Float tenCenter = new Point2D.Float(o.location.x - 80+80, o.location.y - 80+80-25);
            Point2D.Float tenTop = new Point2D.Float(o.location.x - 80+80, o.location.y - 80+80-25-40);
            Point2D.Float tenBtm = new Point2D.Float(o.location.x - 80+80, o.location.y -80+80-25+40);

            double distTenCen;
            double distTenTop;
            double distTenBtm;


            if (this.friendINDEX == 1) {// turtle hero hit
                Point2D.Float turtleCenter = new Point2D.Float(this.location.x - 12, this.location.y +59);
                distTenCen = turtleCenter.distance(tenCenter);
                distTenTop = turtleCenter.distance(tenTop);
                distTenBtm = turtleCenter.distance(tenBtm);
            } else if (this.friendINDEX == 4) {// water beam
                Point2D.Float waterBeamTop = new Point2D.Float(this.location.x-40+40,this.location.y+39-10-this.chargeCount*2);
                distTenCen = waterBeamTop.distance(tenCenter);
                distTenTop = waterBeamTop.distance(tenTop);
                distTenBtm = waterBeamTop.distance(tenBtm);
            } else if (this.friendINDEX == 6) {// whirlpool shield
                Point2D.Float whirlpoolShieldTop = new Point2D.Float(this.location.x -12+10+1,
                        this.location.y-10);
                Point2D.Float whirlpoolShieldLeft = new Point2D.Float(this.location.x -12 +10+1-25,
                        this.location.y-10 );
                Point2D.Float whirlpoolShieldRight = new Point2D.Float(this.location.x -12 +10+1+25,
                        this.location.y-10 );
                //turtle hero center y+59, turtle hero nose y+1, the whirlpool shield top y-13, all y+10 bc radius+15

                //ten center
                if (whirlpoolShieldLeft.distance(tenCenter) <= whirlpoolShieldTop.distance(tenCenter)
                        && whirlpoolShieldLeft.distance(tenCenter) <= whirlpoolShieldRight.distance(tenCenter)){ // LEFT CLOSER
                    distTenCen = whirlpoolShieldLeft.distance(tenCenter);
                } else if (whirlpoolShieldRight.distance(tenCenter) <= whirlpoolShieldTop.distance(tenCenter)
                        && whirlpoolShieldRight.distance(tenCenter) <= whirlpoolShieldLeft.distance(tenCenter)) { // RIGHT CLOSER
                    distTenCen = whirlpoolShieldRight.distance(tenCenter);
                } else { // CENTER CLOSER
                    distTenCen = whirlpoolShieldTop.distance(tenCenter);
                }

                //ten top
                if (whirlpoolShieldLeft.distance(tenTop) <= whirlpoolShieldTop.distance(tenTop)
                        && whirlpoolShieldLeft.distance(tenTop) <= whirlpoolShieldRight.distance(tenTop)){ // LEFT CLOSER
                    distTenTop = whirlpoolShieldLeft.distance(tenTop);
                } else if (whirlpoolShieldRight.distance(tenTop) <= whirlpoolShieldTop.distance(tenTop)
                        && whirlpoolShieldRight.distance(tenTop) <= whirlpoolShieldLeft.distance(tenTop)) { // RIGHT CLOSER
                    distTenTop = whirlpoolShieldRight.distance(tenTop);
                } else { // CENTER CLOSER
                    distTenTop = whirlpoolShieldTop.distance(tenTop);
                }

                //ten btm
                if (whirlpoolShieldLeft.distance(tenBtm) <= whirlpoolShieldTop.distance(tenBtm)
                        && whirlpoolShieldLeft.distance(tenBtm) <= whirlpoolShieldRight.distance(tenBtm)){ // LEFT CLOSER
                    distTenBtm = whirlpoolShieldLeft.distance(tenBtm);
                } else if (whirlpoolShieldRight.distance(tenBtm) <= whirlpoolShieldTop.distance(tenBtm)
                        && whirlpoolShieldRight.distance(tenBtm) <= whirlpoolShieldLeft.distance(tenBtm)) { // RIGHT CLOSER
                    distTenBtm = whirlpoolShieldRight.distance(tenBtm);
                } else { // CENTER CLOSER
                    distTenBtm = whirlpoolShieldTop.distance(tenBtm);
                }


            } else {// other small case like bubble
                distTenCen = this.location.distance(tenCenter);
                distTenTop = this.location.distance(tenTop);
                distTenBtm = this.location.distance(tenBtm);
            }



            if (distTenCen <= this.getCollisionRadius() + 80 // cen radius 80
                    || distTenTop <= this.getCollisionRadius() + 60 // top radius 60
                    || distTenBtm <= this.getCollisionRadius() + 65 ) { // btm radius 65 add claw
                return true;
            } else {
                return false;
            }
        }
        


        return false;
    }


        public abstract void render (Graphics2D g2);  // using g2d bc when we call canvas it's on offscreen
        public abstract void update ();
        public abstract int getCollisionRadius ();

}
