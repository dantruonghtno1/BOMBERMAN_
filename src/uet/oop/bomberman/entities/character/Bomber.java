package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import uet.oop.bomberman.BombermanGame;
public class Bomber extends Character {
    private boolean dropBomb = false;
    private final List<Bomb> bombs = new ArrayList<>();
    private int countBomb;
    private KeyCode direction ;
    private boolean isBombItem, isFlameItem, isSpeedItem;
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        set_moveLength(2);
        setCountBomb(1);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public void update() {

        chooseSprite();
        if (dropBomb) {
            placeBomb();
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (!bomb.is_alive()) {
                bombs.remove(bomb);
                countBomb++;
                // System.out.println(bombRemain);
            } else {
                //System.out.println(bombRemain);
            }
        }
        animate();
    }

    public void keyReleased(KeyCode keyCode) {
        if (direction == keyCode) {
            if (direction == KeyCode.LEFT) {
                _img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.RIGHT) {
                _img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.UP) {
                _img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.DOWN) {
                _img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode == KeyCode.SPACE) {
            dropBomb = false;
        }
    }

    public void placeBomb() {
        if (countBomb > 0) {
            int xB = (int) Math.round((_x + 4) / (double) Sprite.SCALED_SIZE);
            int yB = (int) Math.round((_y + 4) / (double) Sprite.SCALED_SIZE);
            for (Bomb bomb : bombs) {

                if (xB * Sprite.SCALED_SIZE == bomb.get_x() && yB * Sprite.SCALED_SIZE == bomb.get_y()) return;
            }
            bombs.add(new Bomb(xB, yB, Sprite.bomb.getFxImage()));
            countBomb--;
        }
    }

    public int getCountBomb() {
        return countBomb;
    }

    public void setCountBomb(int countBomb) {
        this.countBomb = countBomb;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }
    public void dead() {
        _img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                Sprite.player_dead3, _animated, 30).getFxImage();

        _alive = false;

    }

    public Rectangle getRectangle() {
        return new Rectangle(_moveX + 4, _moveY + 4, Sprite.SCALED_SIZE * 3 / 4, Sprite.SCALED_SIZE * 3 / 4);
    }

    public void chooseSprite() {
        if (direction == KeyCode.LEFT) {
            super.left();
            _img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, _animated++, 20).getFxImage();

        }
        if (direction == KeyCode.RIGHT) {
            super.right();
            _img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, _animated++, 20).getFxImage();

        }
        if (direction == KeyCode.UP) {
            super.up();
            _img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, _animated++, 20).getFxImage();

        }
        if (direction == KeyCode.DOWN) {
            super.down();
            _img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, _animated++, 20).getFxImage();
        }
    }
    public void keyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT|| keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) {
            this.direction = keyCode;
        }
        if (keyCode == KeyCode.SPACE) {
            dropBomb = true;
        }
    }

    public void collisions() {
        List<Bomb> bombs = this.getBombs();
        Rectangle r1 = this.getRectangle();
        //Bomber vs Bombs
        for (Bomb bomb : bombs) {
            Rectangle r2 = bomb.getRectangle();
            if (!bomb.collide(this) && r1.intersects(r2)) {
                System.out.println("collisions bomb");
                break;
            }
        }
        //Bomber vs StillObjects
        for (Entity stillObject : BombermanGame.stillObjects) {
            Rectangle r2 = stillObject.getRectangle();
            if (r1.intersects(r2)) {
                if (this.getPriority() >= stillObject.getPriority()) {
                    this.active();
                } else {
                    this.stand();
                }
                break;
            }
        }
        //Bomber vs Enemies
        for (Enemy enemy : BombermanGame.enemies) {
            Rectangle r2 = enemy.getRectangle();
            if (r1.intersects(r2)) {
                this.dead();
                this.set_y(32);
                this.set_x(32);
            }
        }
    }
}
