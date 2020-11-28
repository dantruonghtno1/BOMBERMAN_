package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;

public class Grass extends Tile{
    public Grass(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void update() {

    }
}
