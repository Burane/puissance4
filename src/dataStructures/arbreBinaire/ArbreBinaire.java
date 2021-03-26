package dataStructures.arbreBinaire;

public class ArbreBinaire<T extends Comparable<T>> {

	Noeud<T> base;

	public ArbreBinaire() {
		base = null;
	}

	public boolean rechercher(T val) {
		return rechercher(base, val);
	}

	private boolean rechercher(Noeud<T> n, T val) {
		T v = n.getValue();
		int cmp = val.compareTo(v);
		if (cmp > 0)
			return n.getRightChild() != null && rechercher(n.getRightChild(), val);

		else if (cmp < 0)
			return n.getLeftChild() != null && rechercher(n.getLeftChild(), val);

		else
			return true;
	}

	public T getMaximum() {
		return getMaximum(base);
	}

	private T getMaximum(Noeud<T> n) {
		if (n.getRightChild() == null)
			return n.getValue();

		return getMaximum(n.getRightChild());
	}

	public T getMinimum() {
		return getMinimum(base);
	}

	private T getMinimum(Noeud<T> n) {
		if (n.getLeftChild() == null)
			return n.getValue();

		return getMinimum(n.getLeftChild());
	}

	public int getHauteur() {
		return getHauteur(base);
	}

	private int getHauteur(Noeud n) {
		if (n == null)
			return 0;

		return 1 + Math.max(getHauteur(n.getLeftChild()), getHauteur(n.getRightChild()));
	}

	public int getTaille() {
		return getTaille(base);
	}

	private int getTaille(Noeud n) {
		if (n == null)
			return 0;

		return 1 + getTaille(n.getLeftChild()) + getTaille(n.getRightChild());
	}

	public void ajouter(T val) {
		if (base == null) {
			base = new Noeud(val);
		} else {
			ajouter(base, val);
		}
	}

	private Noeud ajouter(Noeud<T> n, T val) {

		if (val.compareTo(n.getValue()) > 0)
			if (n.getRightChild() == null)
				n.setRightChild(new Noeud(val));
			else
				ajouter(n.getRightChild(), val);

		else if (n.getLeftChild() == null)
			n.setLeftChild(new Noeud(val));
		else
			ajouter(n.getLeftChild(), val);

		return n;
	}

	public void remove(T val) {
		base = removeNode(base, val);
	}

	private Noeud removeNode(Noeud<T> n, T val) {

		if (n == null)
			return null;

		T v = n.getValue();
		int cmp = val.compareTo(v);

		if (cmp < 0)
			n.setLeftChild(removeNode(n.getLeftChild(), val));
		else if (cmp > 0)
			n.setRightChild(removeNode(n.getRightChild(), val));
		else {
			if(n.getLeftChild() == null && n.getRightChild() == null)
				return null;
			if (n.getLeftChild() == null)
				n = n.getRightChild();
			else if (n.getRightChild() == null)
				n = n.getLeftChild();
			else {
				T min = getMinimum(n.getRightChild());
				n.setValue(min);
				n.setRightChild(removeNode(n.getRightChild(), min));
			}
		}

		return n;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		AfficherNoeud(base, 0, sb);
		return sb.toString();
	}

	private void AfficherNoeud(Noeud n, int prof, StringBuffer sb) {
		sb.append("   ".repeat(prof));
		if (n == null) {
			sb.append("|->  \n");
		} else {
			sb.append("|-> ").append(n).append("\n");
			AfficherNoeud(n.getLeftChild(), prof + 1, sb);
			AfficherNoeud(n.getRightChild(), prof + 1, sb);
		}
	}

	public String parcoursPrefix() {
		StringBuffer sb = new StringBuffer();
		parcoursPrefix(base, sb);
		return sb.toString();
	}

	private void parcoursPrefix(Noeud n, StringBuffer sb) {
		sb.append("→").append(n);
		if (n.getLeftChild() != null)
			parcoursPrefix(n.getLeftChild(), sb);
		if (n.getRightChild() != null)
			parcoursPrefix(n.getRightChild(), sb);
	}

	public String parcoursInfixe() {
		StringBuffer sb = new StringBuffer();
		parcoursInfixe(base, sb);
		return sb.toString();
	}

	private void parcoursInfixe(Noeud n, StringBuffer sb) {
		if (n.getLeftChild() != null)
			parcoursInfixe(n.getLeftChild(), sb);
		sb.append("→").append(n);
		if (n.getRightChild() != null)
			parcoursInfixe(n.getRightChild(), sb);
	}

	public String parcoursSuffixe() {
		StringBuffer sb = new StringBuffer();
		parcoursSuffixe(base, sb);
		return sb.toString();
	}

	private void parcoursSuffixe(Noeud n, StringBuffer sb) {
		if (n.getLeftChild() != null)
			parcoursSuffixe(n.getLeftChild(), sb);
		if (n.getRightChild() != null)
			parcoursSuffixe(n.getRightChild(), sb);
		sb.append("→").append(n);
	}

	public static void main(String[] args) {
		ArbreBinaire<Integer> tree = new ArbreBinaire();

        /*
              50
            /   \
           /     \
          30      70
         /  \    /  \
        20   40 60   80 */
		tree.ajouter(50);
		tree.ajouter(30);
		tree.ajouter(20);
		tree.ajouter(40);
		tree.ajouter(70);
		tree.ajouter(60);
		tree.ajouter(80);

		System.out.println(tree);
		tree.remove(20);
		System.out.println(tree);
		tree.remove(70);
		System.out.println(tree);
		tree.remove(50);
		System.out.println(tree);
	}
}


