package dataStructures.listeChainee;

public class SimpleNode<T> {
	private T data;
	private SimpleNode next;
	private SimpleNode previous;

	public SimpleNode(T data, SimpleNode next, SimpleNode previous) {
		this.data = data;
		this.next = next;
		this.previous = previous;
	}

	public SimpleNode(T data) {
		this.data = data;
	}

	public SimpleNode() {

	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public SimpleNode getNext() {
		return next;
	}

	public void setNext(SimpleNode next) {
		this.next = next;
		if (next != null)
			next.previous = this;
	}

	public SimpleNode getPrevious() {
		return previous;
	}

	public void setPrevious(SimpleNode previous) {
		this.previous = previous;
		previous.next = this;
	}

	@Override
	public String toString() {
		return "SimpleNode{" + "data=" + ((data != null) ? data : "null") + ", next=" + ((next != null) ?
				(next.data != null ? next.data : "null") :
				"null") + ", previous=" + ((previous != null) ?
				(previous.data != null ? previous.data : "null") :
				"null") + '}';
	}
}
