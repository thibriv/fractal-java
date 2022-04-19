/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package fractaltreee;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 *
 * @author antoine
 */
public class FractalTreee extends Application {
    
    public void makeTree(double iter, double x, double y, double angle, StackPane root){
        double xmove = Math.sin(Math.toRadians(angle))*iter;
        double ymove = Math.cos(Math.toRadians(angle))*iter;
        Line ligne = new Line();
        ligne.setStartX(x); 
        ligne.setStartY(y); 
        ligne.setEndX(x + xmove); 
        ligne.setEndY(y + ymove); 
        root.getChildren().add(ligne);
        System.out.println(ligne.toString());
        if (iter > 250){
            makeTree(iter/2 ,x + xmove,y + ymove, angle + 5,root);
            //makeTree(iter/2 ,x + xmove,y + ymove, angle - 5,root);
        }
        
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root = new StackPane();
        
        makeTree(1000, 0, 0, 0, root);
                
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
