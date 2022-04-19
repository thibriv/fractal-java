/*
 * [The "BSD license"]
 * Copyright (c) 2011, abego Software GmbH, Germany (http://www.abego.org)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the abego Software GmbH nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 *    
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tree.view;

import static TreeLayout.SVGUtil.doc;
import static TreeLayout.SVGUtil.svg;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import TreeLayout.TextInBox;
import TreeLayout.TreeForTreeLayout;
import TreeLayout.TreeLayout;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafxdragpanzoom.view.controls.DragManager;
import javafxdragpanzoom.view.views.TranslatableHomotheticPane;
import javafxdragpanzoom.view.views.TranslatableHomotheticPaneRect;
import tree.io.Main;
import tree.modele.*;

/**
 * j'ai modifié cette classe pour qu'elle produise du jfx et plus du svg, les
 * methodes correspondant au svg y sont commentées. Generates SVG for a given
 * {@link TreeLayout} of {@link TextInBox} nodes.
 * <p>
 * 
 * @author Udo Borkowski (ub@abego.org)
 */
@SuppressWarnings("unused")
public class ArbreGraphique extends TranslatableHomotheticPane {
	private final TreeLayout<TextInBox> treeLayout;
	private String svgText;
	public ArrayList<Text> ListeNoms;

	private TreeForTreeLayout<TextInBox> getTree() {
		return treeLayout.getTree();
	}

	private Iterable<TextInBox> getChildren(TextInBox parent) {
		return getTree().getChildren(parent);
	}

	private Rectangle2D.Double getBoundsOfNode(TextInBox node) {
		return treeLayout.getNodeBounds().get(node);
	}

	/**
	 * @param treeLayout the {@link TreeLayout} to be rendered as SVG
	 */
	public ArbreGraphique(TreeLayout<TextInBox> treeLayout) {
		super();
		this.treeLayout = treeLayout;
	}

	/**
	 * changer l affichage de % a watts.
	 * 
	 * @param pourcent le boolean representant l etat du bouton
	 */
	public void togglePercentWatt(boolean pourcent) {
		DecimalFormat df = new DecimalFormat("0.00##");
		df.setRoundingMode(RoundingMode.HALF_UP);

		for (int i = 0; i < ListeNoms.size(); i++) {
			String[] nom = ListeNoms.get(i).getText().split(", ");
			nom[1] = nom[1].replaceFirst("%", "");
			String valeur;
			if (pourcent) {
				valeur = (String) df.format((Double.parseDouble(nom[1]) / 100.0 * GreenTree.Somme));
			} else {
				valeur = (String) df.format((Double.parseDouble(nom[1]) * 100.0 / GreenTree.Somme)) + "%";
			}
			ListeNoms.get(i).setText(nom[0] + ", " + valeur);
		}
	}

	/**
	 * générer les branches de l arbre
	 * 
	 * @param parent le parent associé au trait qu'on dessine
	 * @param root   le container jfx
	 */
	private void generateEdgesFX(TextInBox parent, Group root) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (TextInBox child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				SVGPath svg = new SVGPath();
				String str = "M" + String.valueOf(x1) + " " + String.valueOf(y1) + " L"
						+ String.valueOf(b2.getCenterX()) + " " + String.valueOf(b2.getCenterY());
				svg.setContent(str); // améliorationTODO : generer Lines et binding (startX StartY / endX endY)
				svg.setStroke(Color.rgb(78, 22, 9));
				svg.setStrokeWidth(4);
				root.getChildren().add(svg);
				// Ajouter le groupe contenant la grille au graphe de scène de ce composant
				super.getChildren().add(svg);

				// Rendre ce groupe transparent aux événements souris
				svg.setMouseTransparent(true);
				generateEdgesFX(child, root);
			}
		}
	}

	private void generateBoxFX(TextInBox textInBox, Group root) {
                TranslatableHomotheticPaneRect rect = new TranslatableHomotheticPaneRect();
		Rectangle2D.Double box;
		String[] lines = textInBox.text.split("[\n]");
		List<Double> seuil;
		SVGPath svg = new SVGPath();
		Rectangle rectangle;
		if (lines[0].contains("%")) {
			seuil = Arrays.asList(20.0, 40.0, 60.0);
		} else {
			seuil = Arrays.asList(GreenTree.Somme * 0.2, GreenTree.Somme * 0.4, GreenTree.Somme * 0.6);
		}
		String[] pourcent = textInBox.text.split("[,%]");
		box = getBoundsOfNode(textInBox);
		if (pourcent.length > 1) {
			String str = "M" + String.valueOf(box.x + 1) + " " + String.valueOf(box.y + 1);
			rectangle = new Rectangle(box.x + 1, box.y + 1, box.width - 2, box.height - 2);
			svg.setContent(str);
			rectangle.setStroke(Color.BLACK);
			rectangle.setArcHeight(15);
			rectangle.setArcWidth(15);
			// Ajouter le groupe contenant la grille au graphe de scène de ce composant
			super.getChildren().add(rectangle);
//                        rect.getChildren().add(rectangle);
//                        root.getChildren().add(rect);
//                        new DragManager(rect);

			// Rendre ce groupe transparent aux événements souris
			rectangle.setMouseTransparent(true);
			if (Double.parseDouble(pourcent[1]) < seuil.get(0)) {
				rectangle.setFill(Color.rgb(0, 200, 0));
			} else if (Double.parseDouble(pourcent[1]) < seuil.get(1)
					&& Double.parseDouble(pourcent[1]) > seuil.get(0)) {
				rectangle.setFill(Color.rgb(255, 165, 0));
			} else {
				rectangle.setFill(Color.rgb(165, 38, 10));
			}
			int fontSize = 14;
			int x = (int) box.x + fontSize / 2 + 2;
			int y = (int) box.y + fontSize + 1;
			Text text = new Text(lines[0]);
			text.setLayoutX(x);
			text.setLayoutY(y);
			text.setFont(javafx.scene.text.Font.font(null, FontWeight.MEDIUM, fontSize));
//			text.setOnMousePressed( // add event listener
//					e -> {
//						Point2D offset = new Point2D(e.getX() - rectangle.getLayoutX(),
//								e.getY() - rectangle.getLayoutY());
//						rectangle.setUserData(offset);
//						e.consume();
//					});
//			text.setOnMouseDragged(e -> {
//				Point2D offset = (Point2D) rectangle.getUserData();
//				.rectangle.setTranslateX(e.getX() - offset.getX());
//				rectangle.setTranslateY(e.getY() - offset.getY());
//				text.setTranslateX(e.getX() - offset.getX());
//				text.setTranslateY(e.getY() - offset.getY());
//				e.consume();
//			});
			super.getChildren().add(text);
			ListeNoms.add(text);
			svg.setMouseTransparent(true);
		}
	}

	public String generateDiagram() {
		// StringBuilder result = new StringBuilder();
		this.ListeNoms = new ArrayList<Text>();
		// generate the edges and boxes (with text)
		// generateEdges(result, getTree().getRoot());
		generateEdgesFX(getTree().getRoot(), Main.group);
		for (TextInBox textInBox : treeLayout.getNodeBounds().keySet()) {
			generateBoxFX(textInBox, Main.group);
			// generateBox(result, textInBox);
		}

		// generate the svg containing the diagram items (edges and boxes)
		// Dimension size = treeLayout.getBounds().getBounds().getSize();
		// return svg(size.width, size.height, result.toString());
		return ("la classe est en mode vue jfx !");
	}

	/**
	 * @return the rendered tree, described in SVG format.
	 */
	public String getSVG() {
		if (svgText == null) {
			svgText = doc(generateDiagram());
		}
		return svgText;
	}
}