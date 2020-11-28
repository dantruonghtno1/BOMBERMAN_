package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;


public class BomBang extends Entity {
    private int radius = 1, lengthExplode;
    private int direction, vertical, horizontal;
    private boolean last;

    public BomBang(int x, int y, Image image, int direction) {
        super(x, y);
        this._img = image;
        this.direction = direction;
    }

    public BomBang(int x, int y, Image image) {
        super(x, y);
        this._img = image;
    }

    public BomBang(int x, int y) {
        super(x, y);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void update() {
        if (_animated < 20) {
            _animated++;
            if (vertical == 0 && horizontal == 0) {
                _img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                        Sprite.bomb_exploded2, _animated, 20).getFxImage();
            } else if (vertical == 0 && horizontal == 1) {
                _img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1
                        , Sprite.explosion_horizontal2, _animated, 20).getFxImage();
            } else if (vertical == 0 && horizontal == 2) {
                _img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1
                        , Sprite.explosion_horizontal_right_last2, _animated, 20).getFxImage();
            } else if (vertical == 0 && horizontal == -2) {
                _img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1
                        , Sprite.explosion_horizontal_left_last2, _animated, 20).getFxImage();
            } else if (vertical == 1 && horizontal == 0) {
                _img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1
                        , Sprite.explosion_vertical2, _animated, 20).getFxImage();
            } else if (vertical == 2 && horizontal == 0) {
                _img = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1
                        , Sprite.explosion_vertical_top_last2, _animated, 20).getFxImage();
            } else if (vertical == -2 && horizontal == 0) {
                _img = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1
                        , Sprite.explosion_vertical_down_last2, _animated, 20).getFxImage();

            }
        } else
            BombermanGame.bomBangs.remove(this);
    }

    public void render() {
        FlameSegment();
    }

    private void FlameSegment() {

        for (int i = 0; i < radius; i++) {
            Rectangle rectangle = new Rectangle(_x + 32 * (i + 1), _y, 32, 32);
            if (collisionWall(rectangle)) {
                lengthExplode = i;
            } else {
                lengthExplode = i + 1;
            }

        }
        BombermanGame.bomBangs.add(new BomBang(_x, _y, Sprite.bomb_exploded.getFxImage(), 0));
        for (int i = 0; i < lengthExplode; i++) {
            BomBang bomBang = new BomBang(_x + 32 * (i + 1), _y);
            if (i == lengthExplode - 1) {
                bomBang._img = Sprite.explosion_horizontal_right_last.getFxImage();
                bomBang.vertical = 0;
                bomBang.horizontal = 2;
            } else {
                bomBang._img = Sprite.explosion_horizontal.getFxImage();
                bomBang.vertical = 0;
                bomBang.horizontal = 1;
            }
            BombermanGame.bomBangs.add(bomBang);
        }


        for (int i = 0; i < radius; i++) {
            Rectangle rectangle = new Rectangle(_x - 32 * (i + 1), _y, 32, 32);
            if (collisionWall(rectangle)) {
                lengthExplode = i;
            } else {
                lengthExplode = i + 1;

            }
        }


        for (int i = 0; i < lengthExplode; i++) {
            BomBang bomBang = new BomBang(_x - 32 * (i + 1), _y);
            if (i == lengthExplode - 1) {
                bomBang._img = Sprite.explosion_horizontal_left_last.getFxImage();
                bomBang.vertical = 0;
                bomBang.horizontal = -2;
            } else {
                bomBang._img = Sprite.explosion_horizontal.getFxImage();
                bomBang.vertical = 0;
                bomBang.horizontal = 1;
            }
            BombermanGame.bomBangs.add(bomBang);
        }

        for (int i = 0; i < radius; i++) {
            Rectangle rectangle = new Rectangle(_x, _y - 32 * (i + 1), 32, 32);

            if (collisionWall(rectangle)) {
                lengthExplode = i;
            } else {
                lengthExplode = i + 1;
            }

        }

        for (int i = 0; i < lengthExplode; i++) {
            BomBang bomBang = new BomBang(_x, _y - 32 * (i + 1));
            if (i == lengthExplode - 1) {
                bomBang._img = Sprite.explosion_vertical_top_last.getFxImage();
                bomBang.vertical = 2;
                bomBang.horizontal = 0;
            } else {
                bomBang._img = Sprite.explosion_vertical.getFxImage();
                bomBang.vertical = 1;
                bomBang.horizontal = 0;
            }
            BombermanGame.bomBangs.add(bomBang);
        }


        for (int i = 0; i < radius; i++) {
            Rectangle rectangle = new Rectangle(_x, _y + 32 * (i + 1), 32, 32);
            if (collisionWall(rectangle)) {
                lengthExplode = i;
            } else {
                lengthExplode = i + 1;
            }
        }
        for (int i = 0; i < lengthExplode; i++) {
            BomBang bomBang = new BomBang(_x, _y + 32 * (i + 1));
            if (i == lengthExplode - 1) {
                bomBang._img = Sprite.explosion_vertical_down_last.getFxImage();
                bomBang.vertical = -2;
                bomBang.horizontal = 0;
            } else {
                bomBang._img = Sprite.explosion_vertical.getFxImage();
                bomBang.vertical = 1;
                bomBang.horizontal = 0;
            }
            BombermanGame.bomBangs.add(bomBang);
        }
    }

    public void isCollision() {
        Rectangle r1 = this.getRectangle();
        for (int j = 0; j < BombermanGame.stillObjects.size(); j++) {
            Rectangle r2 = BombermanGame.stillObjects.get(j).getRectangle();
            if (r1.intersects(r2))
                BombermanGame.stillObjects.get(j).set_alive(false);
        }
        for (int j = 0; j < BombermanGame.enemies.size(); j++) {
            Rectangle r2 = BombermanGame.enemies.get(j).getRectangle();
            if (r1.intersects(r2))
                BombermanGame.enemies.get(j).set_alive(false);
        }
        Rectangle r2 = BombermanGame.player.getRectangle();
        if (r1.intersects(r2)) {
            BombermanGame.player.set_alive(false);
            BombermanGame.player.dead();
            BombermanGame.player = new Bomber(32, 32, Sprite.player_right.getFxImage());
        }
    }

}
