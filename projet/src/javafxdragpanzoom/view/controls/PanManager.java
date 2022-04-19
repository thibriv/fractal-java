/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxdragpanzoom.view.controls;

import fr.liienac.statemachine.DragStateMachine;
import fr.liienac.statemachine.event.Move;
import fr.liienac.statemachine.event.Press;
import fr.liienac.statemachine.geometry.Point;
import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafxdragpanzoom.view.views.TranslatableHomotheticPane;
import javafxdragpanzoom.view.views.TranslatableHomotheticPaneGrid;

/**
 *
 * @author erber
 */
public class PanManager {
    
    private double xPress;
    private double yPress;
    private final DragStateMachine dragMachine;
    

    public PanManager(TranslatableHomotheticPane pane) {
        
        dragMachine = new DragStateMachine();
        
        EventHandler<MouseEvent> eventPress = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xPress = event.getX();
                yPress = event.getY();
                Point p = new Point(xPress,yPress);
                dragMachine.handleEvent(new Press(p,pane));
            }

        };
        EventHandler<MouseEvent> eventDrag = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double xDrag = event.getX();
                double yDrag = event.getY();
                Point p = new Point(xDrag-xPress,yDrag-yPress);
                dragMachine.handleEvent(new Move(p,pane));
                //pane.translate(xDrag-xPress, yDrag-yPress);
            }

        };
        
        pane.addEventFilter(MouseEvent.MOUSE_PRESSED, eventPress);
        pane.addEventFilter(MouseEvent.MOUSE_DRAGGED, eventDrag);
        
    }

}
