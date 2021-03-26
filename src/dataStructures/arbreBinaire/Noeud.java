package dataStructures.arbreBinaire;

public class Noeud<T extends Comparable<T>> {
	private T value;
	private Noeud<T> leftChild;
	private Noeud<T> rightChild;

	public Noeud(T value) {
		this.value = value;
	}

	public Noeud(T value, Noeud leftChild, Noeud rightChild) {
		this.value = value;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Noeud<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Noeud leftChild) {
		this.leftChild = leftChild;
	}

	public Noeud<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(Noeud rightChild) {
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return ""+value;
	}

}
