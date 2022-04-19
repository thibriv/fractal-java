package tree.view;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import tree.modele.*;
/**CLASSE NON UTILISEE DANS LA VERSION ACTUELLE
 * vue graphique de l arbre, version "a la main" jfx
 *
 * @author  Theo Borrini
 */
public class ArbreFx {
	private TreeNode<Donnee> arbre;
	private Courbe courbe;
	private Group root;
	private ArrayList<Point> ListeOrigine;
	ArrayList<Integer> ecart;
	public ArbreFx(TreeNode<Donnee> node, Group root) {
		this.arbre = node;
		this.root = root;
		
//      TranslatableHomotheticPane panAndZoomPane = new TranslatableHomotheticPaneGrid();
//		ZoomManager zoomManager = new ZoomManager(panAndZoomPane);
//		this.root.getChildren().add(panAndZoomPane);
		
		ListeOrigine = new ArrayList<Point>();
		ListeOrigine.add(new Point(500,800));
		this.ecart = new ArrayList<Integer>();
		this.ecart.add(250);
		this.ecart.add(250);
		AfficherArbre(this.arbre, 0);
		for (Point element : ListeOrigine) {
			element.afficher();
		}
	}

	public void AfficherArbre(TreeNode<Donnee> node, int profondeur) {
		if (profondeur < 3) {
		DessinerNoeud(node,this.ListeOrigine.get(profondeur),this.ecart,profondeur);
		}
		System.out.println("profondeur : " +profondeur);
		System.out.println("pOrigine en cours: ");
		this.ListeOrigine.get(profondeur).afficher();
		this.ecart.set(0, (int) (this.ecart.get(0)*0.90));
		this.ecart.set(1, (int) (this.ecart.get(1)*0.90));
		node.getChildren().forEach(each -> AfficherArbre(each, profondeur+1));
	}
	public void DessinerNoeud(TreeNode<Donnee> node, Point pOrigine,ArrayList<Integer> ecart, int profondeur) {
		ArrayList<String> noms = new ArrayList<String>();
		int nb_pts = node.getChildren().size();
		Noeud noeudGraphique = new Noeud(nb_pts,ecart);
		if (!node.getChildren().isEmpty()) {
			System.out.println("\n Liste des noms du noeud : ");
			for (int k =0; k< nb_pts; k++) {
				noms.add(node.getChildren().get(k).getData().getNom());
				System.out.println(node.getChildren().get(k).getData().getNom());
			}
		}
		SVGPath svgPath;
		this.courbe =new Courbe(pOrigine, 10,-10, -ecart.get(1));
		ArrayList<String> path = noeudGraphique.getCheminNoeud(ecart.get(0), this.courbe, nb_pts+1);
		Color color =Color.color(Math.random(), Math.random(), Math.random());
		System.out.println("Paths : ");
		for (int k =1; k< nb_pts+1; k++) {
			System.out.println(path.get(k));
			svgPath = new SVGPath();
			svgPath.setContent(path.get(k));
			svgPath.setFill(color);
			this.root.getChildren().add(svgPath);
			Text text = new Text(noms.get(k-1));
			text.setLayoutY(ListeOrigine.get(profondeur).getY()-this.ecart.get(1)*0.90);
			String[] str = path.get(k).split("\\s+");
			text.setLayoutX(Double.parseDouble(str[3].substring(0, 3))-25);
			this.root.getChildren().add(text);
			this.ListeOrigine.add(new Point(Double.parseDouble(str[3].substring(0, 3)), Double.parseDouble(str[3].split(",")[1])));
		}
	}
}