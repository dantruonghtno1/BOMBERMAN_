package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;

import java.awt.*;


public abstract class Enemy extends Character {

    public Enemy(int x, int y, Image image) {
        super(x, y, image);;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public void collisionsBomb() {
            Rectangle r2 = this.getRectangle();
            for (Bomb bomb : BombermanGame.player.getBombs()) {
                Rectangle r3 = bomb.getRectangle();
                if (!bomb.collide(this) && r2.intersects(r3)) {
                    this.stand();
                    break;
                }
            }
    }

    public void collisionsStillObject() {
        Rectangle r2 = this.getRectangle();
        for (Entity stillObject : BombermanGame.stillObjects) {
            Rectangle r3 = stillObject.getRectangle();
            if (r2.intersects(r3)) {
                if (this.getPriority() >= stillObject.getPriority()) {
                    this.active();
                } else {
                    this.stand();
                }
                break;
            }
        }
    }
}
