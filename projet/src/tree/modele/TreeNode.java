package tree.modele;
import java.util.ArrayList;
import java.util.List;
 
/**
* Modèle de l arbre logique
* @author w w w. j a v a g i s t s . c o m
*
*/
public class TreeNode<T> {
 
private T data = null;
 
private List<TreeNode<T>> children = new ArrayList<>();
 
private TreeNode<T> parent = null;
 
public TreeNode(T data) {
this.data = data;
}
 
public TreeNode<T> addChild(TreeNode<T> child) {
child.setParent(this);
this.children.add(child);
return child;
}
 
public void addChildren(List<TreeNode<T>> children) {
children.forEach(each -> each.setParent(this));
this.children.addAll(children);
}
 
public List<TreeNode<T>> getChildren() {
return children;
}
 
public T getData() {
return data;
}

public void setData(T data) {
this.data = data;
}
 
private void setParent(TreeNode<T> parent) {
this.parent = parent;
}
 
public TreeNode<T> getParent() {
return parent;
}
	
public static <T> void printTree(TreeNode<T> node, String appender) {
  System.out.println(appender + node.getData());
  node.getChildren().forEach(each ->  printTree(each, appender + appender));
}

}