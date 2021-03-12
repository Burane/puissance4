package dataStructures;

import java.util.Comparator;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ListeChainee<T> implements Iterable<T> {
	private SimpleNode<T> root;
	private SimpleNode<T> last;
	private int length;

	public ListeChainee(SimpleNode<T> root) {
		this.root = root;
	}

	public ListeChainee() {
		this.root = null;
	}

	public void add(T data) {
		SimpleNode<T> node = new SimpleNode<>(data);
		if (root == null) {
			root = node;
		} else {
			SimpleNode n = root;
			while (n.getNext() != null) {
				n = n.getNext();
			}
			n.setNext(node);
		}
		last = node;
		length++;
	}

	public void addRoot(T data) {
		SimpleNode<T> node = new SimpleNode<>(data);
		node.setNext(root);
		root = node;
		length++;
	}

	public void addAt(int index, T data) {
		if (index == 0) {
			addRoot(data);
		} else if (index - 1 == length) {
			add(data);
		} else {
			SimpleNode<T> node = new SimpleNode<>(data);

			SimpleNode currentSimpleNode = node;
			for (int i = 0; i < index - 1; i++) {
				currentSimpleNode = currentSimpleNode.getNext();
			}
			node.setNext(currentSimpleNode.getNext());
			currentSimpleNode.setNext(node);
		}
		length++;
	}

	public void deleteLast() {
		SimpleNode newLast = last.getPrevious();
		if (newLast != null)
			newLast.setNext(null);
		else
			root = null;

		last = newLast;
		length--;
	}

	public void deleteAt(int index) {
		if (index == 0) {
			root = root.getNext();
		} else if (index - 1 == length) {
			deleteLast();
		} else {
			SimpleNode currentSimpleNode = root;
			for (int i = 0; i < index - 1; i++) {
				currentSimpleNode = currentSimpleNode.getNext();
			}
			currentSimpleNode.setNext(currentSimpleNode.getNext().getNext());
		}
		length--;
	}

	public T get(int index) {
		SimpleNode<T> currentSimpleNode = root;
		for (int i = 0; i < index - 1; i++) {
			currentSimpleNode = currentSimpleNode.getNext();
		}
		return currentSimpleNode.getData();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		SimpleNode currentSimpleNode = root;
		while (currentSimpleNode != null) {
			sb.append(currentSimpleNode.getData());
			if (currentSimpleNode.getNext() != null)
				sb.append(", ");
			currentSimpleNode = currentSimpleNode.getNext();
		}
		sb.append("]");
		return sb.toString();
	}

	public SimpleNode<T> getRoot() {
		return root;
	}

	public void setRoot(SimpleNode<T> root) {
		this.root = root;
	}

	public SimpleNode<T> getLast() {
		return last;
	}

	public void setLast(SimpleNode<T> last) {
		this.last = last;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	private SimpleNode<T> sortedMerge(SimpleNode<T> a, SimpleNode<T> b, Comparator<? super T> c) {
		SimpleNode<T> result;
		if (a == null)
			return b;
		if (b == null)
			return a;

		if (c.compare(a.getData(), b.getData()) <= 0) {
			result = a;
			result.setNext(sortedMerge(a.getNext(), b, c));
		} else {
			result = b;
			result.setNext(sortedMerge(a, b.getNext(), c));
		}
		return result;
	}

	public void mergeSort(Comparator<? super T> c) {
		root = mergeSort(root, c);
	}

	private SimpleNode<T> mergeSort(SimpleNode<T> h, Comparator<? super T> c) {

		if (h == null || h.getNext() == null)
			return h;

		SimpleNode<T> middle = getMiddle(h);
		SimpleNode<T> nextofmiddle = middle.getNext();
		middle.setNext(null);
		SimpleNode<T> left = mergeSort(h, c);
		SimpleNode<T> right = mergeSort(nextofmiddle, c);
		return sortedMerge(left, right, c);
	}

	// Utility function to get the middle of the linked list
	public SimpleNode<T> getMiddle(SimpleNode<T> h) {
		if (h == null)
			return null;

		SimpleNode<T> slow = h, fast = h;

		while (fast.getNext() != null && fast.getNext().getNext() != null) {
			slow = slow.getNext();
			fast = fast.getNext().getNext();
		}
		return slow;
	}

	@Override
	public Iterator iterator() {
		return new ListeChaineIterator(root);
	}
}

