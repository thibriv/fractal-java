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
/** Main est une classe qui ouvre le fichier cree 
 *par joular, le transmet a  Interprete et lance l'application.
 * @author Theo Borrini
 */
public class Main extends Application{
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
			TextInBox root = new TextInBox(GreenTree.root.getData().getNom(), 40, 20);
			
			DefaultTreeForTreeLayout<TextInBox> tree = new DefaultTreeForTreeLayout<TextInBox>(root);		
			TreeForTreeLayout<TextInBox> arbreModele = interprete.createTree(GreenTree.root, tree, root, true);
			
			DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(
					80, 10, Location.Bottom, AlignmentInLevel.Center);
			TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
			
			TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(arbreModele,
					nodeExtentProvider, configuration);
			arbreGraphique = new ArbreGraphique(treeLayout);
			
			String SVGfinal = arbreGraphique.getSVG();
			System.out.println(SVGfinal);
	        arbreGraphique.setLayoutX(100);
		    arbreGraphique.setLayoutY(100);
		    group.getChildren().add(arbreGraphique);
		    
		    @SuppressWarnings("unused")
			PanManager panManager = new PanManager(arbreGraphique);
		    @SuppressWarnings("unused")
		    ZoomManager zoomManager = new ZoomManager(arbreGraphique);
		    interprete.getScanner().close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    SwitchButton btnAff = new SwitchButton(arbreGraphique);
	    group.getChildren().add(btnAff);
	    btnAff.setLayoutX(220);
	    btnAff.setLayoutY(55);
	    btnAff.toBack();
	    
	    Text titre = new Text("Mode d'affichage de la consommation: ");
	    titre.setLayoutY(40);
	    titre.setLayoutX(90);
		titre.setFont(javafx.scene.text.Font.font(null, FontWeight.BOLD, 12));
		group.getChildren().add(titre);
		titre.toBack();
		
		Text text1 = new Text("en % de consommation totale");
		text1.setLayoutY(67);
		text1.setLayoutX(32);
		text1.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 12));
		group.getChildren().add(text1);
		text1.toBack();
		
		Text text2 = new Text("en valeur absolue (W)");
		text2.setLayoutY(67);
		text2.setLayoutX(260);
		text2.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, 12));
		group.getChildren().add(text2);
		text2.toBack();

	    Scene scene = new Scene(group ,1600, 900);
		stage.setTitle("Arbre Joular");
		stage.setScene(scene);
		stage.show();
	}
}