package javafxdragpanzoom.view.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

/**
 * Rectangle translatable et dont la mise à l'échelle est homothétique.
 * @author Nicolas Saporito - ENAC
 */
public class TranslatableHomotheticPaneRect extends TranslatableHomotheticPane {

    /**
     * Initialiser le composant et créer le rectangle
     */
    public TranslatableHomotheticPaneRect() {
        super();
        
        // Créer un rectangle de 100px par 100px, positionné en (0, 0)
        Rectangle rect = new Rectangle(100, 100);
        rect.setStroke(Color.BLUE);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        super.getChildren().add(rect);
    }
}
