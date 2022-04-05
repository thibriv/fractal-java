package javafxdragpanzoom.view.views;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
     * @param globalScale facteur d'échelle global pour le zoom différencié
     */
    public TranslatableHomotheticPaneRect(DoubleProperty globalScale) {
        super();
        
        // Créer un rectangle de 100px par 100px, positionné en (0, 0)
        Rectangle rect = new Rectangle(100, 100);
        rect.setStroke(Color.BLUE);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
        super.getChildren().add(rect);
        
        // S'abonner aux changements du facteur de zoom global pour gérer le zoom différencié
        globalScale.addListener(zoomDiff);
    }
    
    /**
     * Ecouteur des changements du facteur de zoom global pour conserver une taille visuellement invariante
     */
    private final ChangeListener zoomDiff = new ChangeListener<Double>() {
        @Override
        public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
            // Appliquer à ce noeud un facteur de zoom inverse du facteur global
            setScale(1 / newValue);
        }
    };
}
