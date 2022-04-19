package javafxdragpanzoom.view.views;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Affine;
import javafxdragpanzoom.statemachines.IHomothetic;
import javafxdragpanzoom.statemachines.ITranslatable;

/**
 * Classe décrivant un Pane JavaFX translatable et dont la mise à l'échelle est toujours homothétique.
 * @author Nicolas Saporito - ENAC
 */
public class TranslatableHomotheticPane extends Pane implements IHomothetic, ITranslatable {
    
    // Modèle stockant le facteur de zoom pour gérer le zoom différencié
    // (à mettre à jour lors de chaque mise à l'échelle)
    private final DoubleProperty scale = new SimpleDoubleProperty(1.0);
    
    // Les transformations sont accumulées dans une seule matrice
    private final Affine transforms;

    public TranslatableHomotheticPane() {
        super();
        
        // initialiser la matrice des transformations et l'assigner à ce composant
        transforms = new Affine();
        getTransforms().add(transforms);
    }
    
    /**
     * Accesseur de la property gérant le facteur de zoom
     * @return poignée vers la property gérant le facteur de zoom
     */
    public final DoubleProperty scaleProperty() {
        return scale;
    }
    
    @Override
    public final double getScale() {
        return scale.get();
    }
    
    @Override
    public void setScale(double newScale) {
        // Appliquer le nouveau facteur de zoom
        double appendedScale = newScale / getScale();
        transforms.appendScale(appendedScale, appendedScale);
        // mémoriser le nouveau facteur de zoom dans le modèle
        scaleProperty().set(newScale);
    }
    
    @Override
    public void setScale(double newScale, double pivotX, double pivotY) {
        // Appliquer le nouveau facteur de zoom
        double appendedScale = newScale / getScale();
        transforms.appendScale(appendedScale, appendedScale, pivotX, pivotY);
        // mémoriser le nouveau facteur de zoom dans le modèle
        scaleProperty().set(newScale);
    }

    @Override
    public void appendScale(double deltaScale) {
        // Appliquer la mise à l'échelle supplémentaire
        transforms.appendScale(deltaScale, deltaScale);
        // mémoriser le facteur de zoom résultant dans le modèle
        scaleProperty().set(getScale()*deltaScale);
    }

    @Override
    public void appendScale(double deltaScale, double pivotX, double pivotY) {
        // Appliquer la mise à l'échelle supplémentaire
        transforms.appendScale(deltaScale, deltaScale, pivotX, pivotY);
        // mémoriser le facteur de zoom résultant dans le modèle
        scaleProperty().set(getScale()*deltaScale);
    }

    @Override
    public void translate(double dx, double dy) {
        transforms.appendTranslation(dx, dy);
    }
}
