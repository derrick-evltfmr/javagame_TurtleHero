package model.starfish;

public class StarFishBuildDirector {

    private StarFishBuilder builder;

    public void setStarBuilder(StarFishBuilder builder) {
        this.builder = builder;
    }

    public StarFish getStar() {
        return builder.getStar();
    }

    public void createStar(int x, int y) {
        builder.createStar(x,y); // add parameter x,y
        builder.buildShape();
        builder.buildColor();
        builder.buildStrategy();
    }
}
