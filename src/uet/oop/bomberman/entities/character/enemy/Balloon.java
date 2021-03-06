package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.ai.AI;
import uet.oop.bomberman.entities.character.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    private int direction;

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        _alive =true;
        set_moveLength(1);
    }

    @Override
    public void update() {
        if(is_alive()) {
            switch (direction) {
                case 0:
                    super.left();
                    _img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animated++, 18).getFxImage();
                    break;
                case 1:
                    super.right();
                    _img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animated++, 18).getFxImage();
                    break;
                case 2:
                    super.up();
                    _img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animated++, 18).getFxImage();
                    break;
                case 3:
                    super.down();
                    _img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, _animated++, 18).getFxImage();
                    break;
            }
        }
        else if(_animated < 30){
            _animated++;
            _img = Sprite.balloom_dead.getFxImage();
        }else
            BombermanGame.enemies.remove(this);
    }

    public void stand() {
        super.stand();
        AI _ai = new AILow();
        direction = _ai.calculateDirection();
    }

}
