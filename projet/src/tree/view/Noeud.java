package tree.view;
import java.util.ArrayList;
/**CLASSE NON UTILISEE DANS LA VERSION ACTUELLE
 * vue graphique d'un noeud, version "a la main" jfx
 *
 * @author  Theo Borrini
 */

public class Noeud {
//	public enum Sens {
//		BAS, HAUT
//	}
	
	private ArrayList<Integer> espace;
	private int ecartX; 
	private int ecartY;
	private int nb_pts;
	//private Sens Sens;
	public Noeud(int nb_pts, ArrayList<Integer> espace) { //, Sens sens) {


		this.nb_pts = nb_pts; 
		this.espace = espace;
		if (!(this.nb_pts == 0)) {
			this.ecartX = (espace.get(1) / this.nb_pts); 
		}


		System.out.print("ecartX ");
		System.out.print(this.ecartX);
		System.out.print("ecartY ");
		System.out.print(this.ecartY);
	}
	
	public ArrayList<Integer> getEspace() {
		return this.espace;
	}
	
	public ArrayList<Integer> setEspace(ArrayList<Integer> espace) {
		this.espace.set(0, espace.get(0));
		this.espace.set(1, espace.get(1));
		return this.espace;
	}
	
	public ArrayList<String> getCheminNoeud(int ecartX, Courbe courbe,int nb_pts) {
		//(int nb_pts, Sens sens, int espace, Point pOrigine)
		ArrayList<String> CheminNoeud = new ArrayList<String>();
		//FOR TOUTES LES COURBES
		for (int i=0;i< nb_pts ; i++) {
			int swap;
			
			if (i % 2 == 0) {
				swap = -1;
			}
			else {
				swap = 1;
			}
			String path = Courbe.getCheminCourbe(courbe); //, this.sens, i);
			CheminNoeud.add(path);

			int translation = swap*i*this.ecartX;
			System.out.print("translation: ");
			System.out.print(translation + "\n");
			courbe.translater(translation, 0);
		}
		return CheminNoeud;
	}

	public int getecartX() {
		return ecartX;
	}

	public void setecartX(int ecartX) {
		this.ecartX = ecartX;
	}
	public int getecartY() {
		return ecartY;
	}

	public void setecartY(int ecartY) {
		this.ecartY = ecartY;
	}
	public int getnb_pts() {
		return nb_pts;
	}

	public void setnb_pts(int nb_pts) {
		this.nb_pts = nb_pts;
	}
}
