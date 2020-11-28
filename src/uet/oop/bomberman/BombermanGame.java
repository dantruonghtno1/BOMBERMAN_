package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.BomBang;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.LoadLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static int level = 1;
    
    public static GraphicsContext gc;
    private Canvas canvas;
    private Scanner scanner;
    public static final List<Enemy> enemies = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static Bomber player;
    public static final List<BomBang> bomBangs = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        LoadLevel loadLevel = new LoadLevel();
        loadLevel.load(level);

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();


        scene.setOnKeyPressed(event -> player.keyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> player.keyReleased(event.getCode()));
    }

    public void update() {
        for(int i = 0; i < enemies.size(); i++)
            enemies.get(i).update();
        for (int i = 0; i < bomBangs.size(); i++)
            bomBangs.get(i).update();
        player.update();
        List<Bomb> bombs = player.getBombs();
       for(Bomb bomb : bombs) {
            bomb.update();
        }

        for(int i = 0; i < stillObjects.size(); i++)
            stillObjects.get(i).update();
        player.collisions();
        for (Enemy enemy : enemies) {
            enemy.collisionsBomb();
            enemy.collisionsStillObject();
        }
        for (BomBang bomBang :bomBangs){
            bomBang.isCollision();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = stillObjects.size() - 1; i >= 0; i--) {
            stillObjects.get(i).render(gc);
        }
        enemies.forEach(g -> g.render(gc));
        List<Bomb> bombs = player.getBombs();
        for(Bomb bomb : bombs) {
            bomb.render(gc);
        }
        player.render(gc);
        bomBangs.forEach(g -> g.render(gc));
    }

}

