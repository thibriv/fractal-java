package tree.modele;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** GreenTree est une classe qui prend en entrée les données produites par un
 * objet interprete et contruit un arbre représentant l'architecture du projet
 *  ainsi que la consommation de chacune de ses méthode.
 * @author Théo Borrini
 */
public class GreenTree {
	public static TreeNode<Donnee>root;
	public static double Somme = 0;
	
	/** Construit un nouvel arbre a partir d'un scanner fourni par interprete */
	public GreenTree(Scanner scanner) {
		root = new TreeNode<Donnee>(new Donnee(Donnee.Type.RACINE, "root", 0, 0.0));
		while(scanner.hasNext()) {
			String nom = scanner.next();
			double conso = Double.parseDouble(scanner.next());
			ajouterNoeud(root,new Donnee(Donnee.Type.PACKAGE, nom, conso, conso), null);
		}
		//TreeNode.printTree(this.getRoot(), "-");
		for (TreeNode<Donnee> child : root.getChildren()) {
			Somme = (Somme + child.getData().getConso());
		}
	}
	
	/** Ajouter un nouveau Noeud à notre arbre quand on sait où le placer.
	 * @param node le noeud auquel on va ajouter notre donnée
	 * @param data La ligne de donnée a ajouter
	 * @param reste la chaine de caractère qu'il restera apres une exécution
	 */
	public static void creerNoeud(TreeNode<Donnee> node,Donnee data,String reste) {
		Donnee newData =new Donnee(data.getType(),data.getNom(),data.getConso(), data.getPourcentage());
		TreeNode<Donnee> child= new TreeNode<Donnee>(newData);
		node.addChild(child);
		if (reste == null) {
			newData.setType(Donnee.Type.METHOD);
		}
		else {
			if (reste.contains(".")) {
				data.setNom(reste.split("\\.")[0]);
				reste = reste.substring(reste.indexOf(".")+1, reste.length());
				creerNoeud(child,data,reste);
			}
			else {
				Donnee newData2 = new Donnee(data.getType(),reste,data.getConso(), data.getPourcentage());
				newData2.setType(Donnee.Type.METHOD);
				TreeNode<Donnee> child2= new TreeNode<Donnee>(newData2);
				child.addChild(child2);	
			}
		}
	}
	
	/** Ajouter un nouveau Noeud à notre arbre quand on n'a pas d'informations.
	 *  Il sera placé à la racine de l'arbre si il ne possède
	 *  aucune branche commune à l'arbre actuel, sinon on cherche son parent
	 *  le plus proche et on appelle nouveauNoeud
	 * @param root la racine de l'arbre
	 * @param data La ligne de donnée a ajouter
	 * @param reste la chaine de caractère qu'il restera apres une exécution
	 */
	public static void ajouterNoeud(TreeNode<Donnee> root, Donnee data, String reste){
		String nom = data.getNom();
		List<String> liste = new ArrayList<String>();
		for(TreeNode<Donnee> child : root.getChildren()) {
			liste.add(child.getData().getNom());
		}
		if (nom.contains(".")) {
			int index = nom.indexOf(".")+1;
			reste = nom.substring(index, nom.length());
			data.setNom(nom.substring(0,index-1));
			nom = data.getNom();
	   }	
		if (liste.contains(nom)) {
			int indexNode = liste.indexOf(nom);
			root.getChildren().get(indexNode).getData().setConso(root.getChildren().get(indexNode).getData().getConso() + data.getConso());
			if (nom.contains(".")) {
				ajouterNoeud(root.getChildren().get(indexNode), new Donnee(data.getType(),reste,data.getConso(), data.getPourcentage())
						,reste.substring(reste.indexOf("."), reste.length()));
				
			}
			else {
				ajouterNoeud(root.getChildren().get(indexNode), new Donnee(data.getType(),reste, data.getConso(), data.getPourcentage()),null);
			}
		}
		else {
			creerNoeud(root, data, reste);
		}
	}

}
