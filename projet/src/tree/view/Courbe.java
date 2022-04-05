package tree.view;
import tree.modele.Point;
/**CLASSE NON UTILISEE DANS LA VERSION ACTUELLE
 * vue graphique d'une feuille, version "a la main" jfx
 *
 * @author  Theo Borrini
 */
public class Courbe {
	private Point pOrigine; 
	private Point pArrivee;
	private Point pBcp1;
	private Point pBcp2;
	private int deltax;
	private int deltay;
	private int ecartY;
	
	public Courbe(Point pOrigine,Point pArrivee, Point pBcp1,Point pBcp2, int ecartY) {
		this.pOrigine = pOrigine;
		this.pArrivee = pArrivee;
		this.pBcp1 = pBcp1;
		this.pBcp2 = pBcp2;
	}
	public Courbe(Point pOrigine,int deltax,int deltay, int ecartY) {
		this.pOrigine = pOrigine;
		this.pArrivee = new Point(pOrigine.getX()-deltax, pOrigine.getY());
		this.pBcp1 = new Point(pOrigine.getX() - deltay,pOrigine.getY()+ecartY);
		this.pBcp2 = new Point (pOrigine.getX(), pArrivee.getY()+ecartY); //500+deltax
	}
	
	public static String getCheminCourbe(Courbe courbe) {

		//Cubic Bezier <path D="M x0,y0  C x1,y1  x2,y2  x3,y3" />
		String path = "M " + courbe.pOrigine.getX() + "," + courbe.pOrigine.getY() + 
			"C " + courbe.pArrivee.getX() + "," + courbe.pArrivee.getY() +
			"  " + courbe.pBcp1.getX() + "," + courbe.pBcp1.getY() +
			"  " + courbe.pBcp2.getX() + "," + courbe.pBcp2.getY();  
		return path;
		
//		if  (sens.BAS == 1 && sens.HAUT == 0) {
//			svgPath.setFill(Color.BROWN);
//		}
//		else if (sens.BAS == 0 && sens.HAUT == 1){
//			svgPath.setFill(Color.GREEN);
//		}
//		else System.out.print("erreur sens");
		
		//Group root = new Group();
	}
		

	
	public Point getpOrigine() {
		return pOrigine;
	}
	public void setpOrigine(Point pOrigine) {
		this.pOrigine = pOrigine;
	}
	public Point getpArrivee() {
		return pArrivee;
	}
	public void setpArrivee(Point pArrivee) {
		this.pArrivee = pArrivee;
	}
	public Point getpBcp1() {
		return pBcp1;
	}
	public void setpBcp1(Point pBcp1) {
		this.pBcp1 = pBcp1;
	}
	public Point getpBcp2() {
		return pBcp2;
	}
	public void setpBcp2(Point pBcp2) {
		this.pBcp2 = pBcp2;
	}
	public int getdeltax() {
		return this.deltax;
	}
	public void setdeltax(int deltax) {
		this.deltax = deltax;
	}
	public int getdeltay() {
		return this.deltay;
	}
	public void setdeltay(int deltay) {
		this.deltay = deltay;
	}
	public void translater(int dx, int dy) {
		pBcp1.translater(dx, dy);
		pBcp2.translater(dx, dy);
		
	}
	public int getecartY() {
		return ecartY;
	}
	public void setecartY(int ecartY) {
		this.ecartY = ecartY;
	}
	
}
