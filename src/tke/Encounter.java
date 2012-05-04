/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tke;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

/**
 *
 * @author jiri
 */
public class Encounter extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Scene canvas = new Scene(root, 800, 540, Color.BLACK);
        stage.setScene(canvas);
        
        Stop[] rgStops = {new Stop(.83, Color.WHITE), new Stop(1, Color.BLACK)};
        final RadialGradient rg = RadialGradientBuilder.create()
                /*.centerX(400).centerY(270)*/
                .radius(60).proportional(false)
                .stops(rgStops)
                .build();
        
        //cX, cY, radius, color
        final Circle finder = new Circle(400f, 270f, 50f, Color.WHITE);
        finder.setStroke(rg);
        finder.setStrokeWidth(10.);
        finder.setStrokeType(StrokeType.OUTSIDE);
        
        Rectangle rogueFog = new Rectangle(0, 0, 800, 540);
        rogueFog.setFill(Color.BLACK);
        root.getChildren().add(rogueFog);
        
        Image background = new Image("background.jpg", false);
        ImageView terrain = new ImageView();
        terrain.setImage(background);
        terrain.setClip(finder);
        terrain.setCursor(Cursor.CROSSHAIR); //no mouse cursor
        root.getChildren().add(terrain);
        
        final GaussianBlur gb = new GaussianBlur();
        gb.setRadius(9);
        terrain.setEffect(gb);
        
        //let the circle finder follow the mouse
        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                //System.out.println("x: "+ me.getX() +", y: "+ me.getY());
                finder.setCenterX(me.getX());
                finder.setCenterY(me.getY());
            }
        });
        
        //zoom-in/zoom-out with scrollwheel
        canvas.setOnScroll(new EventHandler<ScrollEvent>() {            
            @Override public void handle(ScrollEvent se) {
                //System.out.println("x: "+ se.getDeltaX() +", y: "+ se.getDeltaY());
                System.out.println("radius: "+ gb.getRadius());
                if (se.getDeltaY() >= 0) {
                    finder.setRadius(finder.getRadius() + 2);
                    gb.setRadius(gb.getRadius() + .5);
                } else if (se.getDeltaY() < 0) {
                    finder.setRadius(finder.getRadius() - 2);
                    gb.setRadius(gb.getRadius() - .5);
                }
            }
        });
        
        stage.show();
    }
}
