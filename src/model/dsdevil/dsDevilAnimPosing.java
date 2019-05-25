package model.dsdevil;

import model.piranhamon.piranhaMon;
import model.piranhamon.piranhaMonAnimStrategy;

public class dsDevilAnimPosing implements dsDevilAnimStrategy {

    dsDevil context;

    public dsDevilAnimPosing(dsDevil context) {
        this.context = context;
    }

    @Override
    public void animate() {
        context.posingTimeCountOn = true;
        if (context.posingTimeCount >=30) {
            context.eye = context.EYE_NORMAL;

            if (context.posingTimeCount >= 87) context.eye = context.EYE_FLASH_ONCE;
            if (context.posingTimeCount >= 89) context.eye = context.EYE_NORMAL;
            if (context.posingTimeCount >= 91) context.eye = context.EYE_FLASH_ONCE;

            if (context.mouth == context.MOUTH_ENTERPOSE) {
                context.mouth = context.MOUTH_SMALL;
                context.mouthChangeCount = 1;
            }
            if (context.mouthChangeCount % 20 == 0) {
                if (context.mouth == context.MOUTH_SMALL) context.mouth = context.MOUTH_BIG;
                else if (context.mouth == context.MOUTH_BIG) context.mouth = context.MOUTH_SMALL;
            }
        }
    }
}
