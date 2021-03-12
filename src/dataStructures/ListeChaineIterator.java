package dataStructures;

import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ListeChaineIterator<T> implements Iterator<T> {
	private SimpleNode<T> current;

	public ListeChaineIterator(SimpleNode first) {
		current = first;
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public T next() {
		SimpleNode<T> temp = current;
		current = current.getNext();
		return temp.getData();
	}
}
