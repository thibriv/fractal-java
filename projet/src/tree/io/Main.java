package tree.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import TreeLayout.Configuration.AlignmentInLevel;
import TreeLayout.Configuration.Location;
import TreeLayout.TextInBox;
import TreeLayout.TextInBoxNodeExtentProvider;
import TreeLayout.TreeForTreeLayout;
import TreeLayout.TreeLayout;
import TreeLayout.internal.util.DefaultConfiguration;
import TreeLayout.internal.util.DefaultTreeForTreeLayout;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxdragpanzoom.view.controls.PanManager;
import javafxdragpanzoom.view.controls.ZoomManager;
import tree.modele.*;
import tree.view.ArbreGraphique;
import tree.view.SwitchButton;

/**
 * Main est une classe qui ouvre le fichier cree par joular, le transmet a
 * Interprete et lance l'application.
 *
 * @author Theo Borrini
 */
public class Main extends Application {

    public static Group group;
    public ArbreGraphique arbreGraphique;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FileInputStream file;
        try {
            file = new FileInputStream("../joularJX-416227-methods-energy.csv");
            group = new Group();
            Interprete interprete = new Interprete(file);

            @SuppressWarnings("unused")
            GreenTree greenTree = new GreenTree(interprete.getScanner());
            TextInBox root = new TextInBox(GreenTree.root.getData().getNom(), 0, 0);

            DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(root);
            TreeForTreeLayout<TextInBox> arbreModele = interprete.createTree(GreenTree.root, tree, root, true);
            
            DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
                    100, 100, Location.Bottom, AlignmentInLevel.Center);
            TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();

            TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(arbreModele,
                    nodeExtentProvider, configuration);
            //treeLayout.dumpTree(System.out);
            arbreGraphique = new ArbreGraphique(treeLayout);

            String SVGfinal = arbreGraphique.getSVG();
            System.out.println(SVGfinal);
            arbreGraphique.setLayoutX(100); //à ajuster
            arbreGraphique.setLayoutY(100); //idem
            group.getChildren().add(arbreGraphique);

            @SuppressWarnings("unused")
            PanManager panManager = new PanManager(arbreGraphique); //jamais utilisé
            @SuppressWarnings("unused")
            ZoomManager zoomManager = new ZoomManager(arbreGraphique); //jamais utilisé
            interprete.getScanner().close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(group, 1600, 900);
        stage.setTitle("Arbre Joular");
        stage.setScene(scene);
        stage.show();
    }
}
