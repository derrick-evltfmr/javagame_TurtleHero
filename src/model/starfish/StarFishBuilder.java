package model.starfish;

public abstract class StarFishBuilder {
    protected StarFish star;

    public StarFish getStar() {
        return star;
    }

    public void createStar(int x, int y) {
        star = new StarFish(x, y);
    } //create object

    public abstract void buildShape();
    public abstract void buildColor();
    public abstract void buildStrategy();
}
