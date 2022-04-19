package tree.io;
import java.io.FileInputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

import TreeLayout.TextInBox;
import TreeLayout.TreeForTreeLayout;
import TreeLayout.internal.util.DefaultTreeForTreeLayout;
import tree.modele.*;
/** Interpret est une classe qui prend en entrée
 * un fichier fourni par main et donne en sortie
 * a GreenTree un scanner.
 * La methode createTree permet de traduire l'arbre
 * logique de TreeNode<Donnee> en TreeForTreeLayout<TextInBox> 
 * @author Théo Borrini
 */
public class Interprete { 
	private FileInputStream file;
	private Scanner scanner;
	
	public Interprete(FileInputStream file) {
		this.file = file;
		this.scanner = new Scanner(this.file);
		this.scanner.useDelimiter("[,\\n]");
	}
	
	public TreeForTreeLayout<TextInBox> createTree(TreeNode<Donnee> tree, DefaultTreeForTreeLayout<TextInBox> treeSVG, TextInBox root , boolean pourcent) {
		DecimalFormat df = new DecimalFormat("0.00##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		List<TreeNode<Donnee>> children = tree.getChildren();
		
		for (int i=0; i<children.size(); i++) {
			String nom;
			children.get(i).getData().setPourcentage((children.get(i).getData().getConso()*100.0 / GreenTree.Somme));
			if (pourcent) {
				nom = children.get(i).getData().getNom() +", "+ (String) df.format(children.get(i).getData().getPourcentage()) + "%"; //affichage %
			}
			else {
				nom = children.get(i).getData().getNom() +", "+(String) df.format(children.get(i).getData().getConso()); //affichage valeur
			}
			treeSVG.addChildren(root, new TextInBox(nom, 20 + nom.length()*8, 20));
			createTree(children.get(i), treeSVG, treeSVG.getChildrenList(root).get(i), pourcent);
		}
		return treeSVG;
	}
  
	public Scanner getScanner() {
		return this.scanner;
	}
}