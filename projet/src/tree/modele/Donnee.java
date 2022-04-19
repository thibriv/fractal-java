package tree.modele;
/** Donnee représente une ligne de données de Joular.
 * Une Donnee peut etre affichée.
 * @author Théo Borrini
 */
public class Donnee {
	public enum Type {RACINE, PACKAGE, CLASS, METHOD, UNKNOWN};
	public Type type;
	private String nom;
	private double conso;
	private double pourcentage;
	
	/** Construire une donnée à partir de son type, son nom,
	 *  et sa consommation en watts lors de l'exécution de Joular.
	 * @param type type (package, classe, méthode) 
	 * @param nom nom package.classe.méthode
	 * @param conso sa consommation en watts
	 * @param conso sa consommation en % de conso totale
	 */
	public Donnee(Type type, String nom, double conso, double pourcentage) {
		this.type = type;
		this.nom = nom;
		this.conso = conso;
		this.pourcentage = pourcentage;
	}
	public Donnee() {
		this(Type.UNKNOWN, "", 0.0, 0.0);
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public double getConso() {
		return conso;
	}
	public void setConso(double conso) {
		this.conso = conso;
	}

	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	/** Afficher la donnée. On affiche sa consomation 
	 * seulement si la donnée est de type méthode. */
	public String toString() {
		if (this.getType() == Type.METHOD) {
			return this.getType() +" " + this.getNom()+ " " + this.getConso();
		}
		else {
			return this.getType() +" " + this.getNom();
		}
		
	}
	public double getPourcentage() {
		return pourcentage;
	}
	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}
}
