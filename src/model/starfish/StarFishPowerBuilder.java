package model.starfish;

import java.awt.*;

public class StarFishPowerBuilder extends StarFishBuilder {
    @Override
    public void buildShape() {
        int[] x1 = {(int)this.getStar().location.x+15, (int)this.getStar().location.x+23, (int)this.getStar().location.x+30 , (int)this.getStar().location.x+45
                , (int)this.getStar().location.x+36, (int)this.getStar().location.x+39 , (int)this.getStar().location.x+23
                , (int)this.getStar().location.x+8 , (int)this.getStar().location.x+12, (int)this.getStar().location.x+0};
        int[] y1 = {(int)this.getStar().location.y+20-5,(int)this.getStar().location.y+0,(int)this.getStar().location.y+20-5 ,(int)this.getStar().location.y+20-5
                ,(int)this.getStar().location.y+38-9,(int)this.getStar().location.y+59-14 ,(int)this.getStar().location.y+43-10
                ,(int)this.getStar().location.y+59-14,(int)this.getStar().location.y+38-9 ,(int)this.getStar().location.y+20-5};
        this.getStar().starbodyPolygon = new Polygon(x1,y1,10);

        this.getStar().starbodyOval1x = (int)this.getStar().location.x+(6-1-1+2)*3/4-1+1;
        this.getStar().starbodyOval1y = (int)this.getStar().location.y+(6+1-1+2)*3/4-1;
        this.getStar().starbodyOval1w = (53-3)*3/4+2;
        this.getStar().starbodyOval1h = (52-4)*3/4+2;

        this.getStar().starbodyOval2x = (int)this.getStar().location.x+(6-1-1+2)*3/4-1+1+3;
        this.getStar().starbodyOval2y = (int)this.getStar().location.y+(6+1-1+2)*3/4-1+3;
        this.getStar().starbodyOval2w = (53-3)*3/4-4;
        this.getStar().starbodyOval2h = (52-4)*3/4-4;

        this.getStar().starcoreOval1x = (int)this.getStar().location.x+14*3/4;
        this.getStar().starcoreOval1y = (int)this.getStar().location.y+14*3/4;
        this.getStar().starcoreOval1w = 35*3/4+1;
        this.getStar().starcoreOval1h = 35*3/4+1;

        this.getStar().starcoreOval2x = (int)this.getStar().location.x+22*3/4;
        this.getStar().starcoreOval2y = (int)this.getStar().location.y+22*3/4;
        this.getStar().starcoreOval2w = 18*3/4+1+1;
        this.getStar().starcoreOval2h = 18*3/4+1+1;
    }

    @Override
    public void buildColor() {
        this.getStar().color1 = new Color(141,50,243);
        this.getStar().color2 = new Color (50,136,243);
        this.getStar().color3 = new Color(198,127,235);
        this.getStar().whitecolor = new Color(233,230,224);
    }

    @Override
    public void buildStrategy() {
        this.getStar().starType = this.getStar().TYPE_STARFISHPOWER;
        StarFish.starPowerExisting = true;
    }
}
