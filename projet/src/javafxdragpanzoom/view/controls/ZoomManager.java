package javafxdragpanzoom.view.controls;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafxdragpanzoom.view.views.TranslatableHomotheticPane;

/**
 * Gestionnaire de Zoom d'un TranslatableHomotheticPane. 
 * @author saporito
 */
public class ZoomManager {
    public ZoomManager(TranslatableHomotheticPane pane) {
        
        EventHandler<ScrollEvent> eventScroll = new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                pane.appendScale(Math.pow(1.01,event.getDeltaY() ));
            }

        };

        
        pane.addEventFilter(ScrollEvent.SCROLL, eventScroll);
        
    }
}