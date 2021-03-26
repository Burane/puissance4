package dataStructures.Pile;

import dataStructures.listeChainee.ListeChainee;
import dataStructures.listeChainee.SimpleNode;

@SuppressWarnings("unchecked")
public class Pile<T> extends ListeChainee {

	public Pile(SimpleNode root) {
		super(root);
	}

	public Pile() {
	}

	public T get(){
		SimpleNode<T> last = super.getLast();
		super.deleteLast();
		return last.getData();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
