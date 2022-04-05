package javafxdragpanzoom.view.controls;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafxdragpanzoom.statemachines.IHomothetic;
import javafxdragpanzoom.view.views.TranslatableHomotheticPane;

/**
 * Gestionnaire de Zoom d'un TranslatableHomotheticPane. 
 * @author saporito
 */
public class ZoomManager {
    
    /**
     * Constructeur
     * @param paneToZoom Objet à gérer (doit au minimum être un Node JavaFX et un IHomothetic, 
     * on prendra donc ici un TranslatableHomotheticPane)
     */
    public ZoomManager(TranslatableHomotheticPane paneToZoom) {
        paneToZoom.addEventFilter(ScrollEvent.ANY, onScroll);
    }

    private final EventHandler<ScrollEvent> onScroll = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent ev) {
            double ds = Math.pow(1.01, ev.getDeltaY());
            IHomothetic homothetic = (IHomothetic) ev.getSource();
            homothetic.appendScale(ds, ev.getX(), ev.getY());
            ev.consume(); // interrompre la propagation de l'événement
        }
    };
}
