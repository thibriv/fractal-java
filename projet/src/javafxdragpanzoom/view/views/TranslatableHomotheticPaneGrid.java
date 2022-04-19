package javafxdragpanzoom.view.views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Grille translatable et dont la mise à l'échelle est homothétique.
 * @author Nicolas Saporito - ENAC
 */
public class TranslatableHomotheticPaneGrid extends TranslatableHomotheticPane {
    
    // Style et tailles
    private final static String STYLE = "-fx-background-color: lightgrey; -fx-border-color: blue;";
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final static int GRID_OFFSET = 50;
    
    /** Group contenant la grille */
    protected final Group grid = new Group();
    
    public TranslatableHomotheticPaneGrid() {
        super();
        
        // Ajouter le groupe contenant la grille au graphe de scène de ce composant
        super.getChildren().add(grid);
        
        // Rendre ce groupe transparent aux événements souris
        grid.setMouseTransparent(true);
        
        // Créer la grille
        setStyle(STYLE);
        super.setWidth(WIDTH);
        super.setHeight(HEIGHT);
        for (double i = GRID_OFFSET; i < WIDTH; i += GRID_OFFSET) {
            Line hl = new Line(0, i, WIDTH, i);
            hl.setStroke(Color.GRAY);
            Line vl = new Line(i, 0, i, WIDTH);
            vl.setStroke(Color.GRAY);
            grid.getChildren().addAll(hl, vl);
        }
        
        // S'abonner aux changements du facteur de zoom pour gérer le zoom différencié
        scaleProperty().addListener(zoomDiff);
    }

    /**
     * Ecouteur des changements du facteur de zoom pour conserver une épaisseur constante aux contours de la grille
     */
    private final ChangeListener zoomDiff = new ChangeListener<Double>() {
        @Override
        public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
            for (Node n : grid.getChildren()) {
                if (n instanceof Line) {
                    ((Line) n).setStrokeWidth(1 / getScale());
                }
            }
        }
    };
}
