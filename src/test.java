import dataStructures.Pile;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class test {
	public static void main(String[] args) {
		Pile<Integer> p = new Pile<>();

		System.out.println(p);
		p.add(10);
		p.add(10);
		p.add(13);
		p.add(13);
		p.add(3);
		p.add(0);
		p.add(-5);
		p.add(15);
		p.add(16);
		System.out.println(p);
		System.out.println("sorting");

		p.mergeSort((Comparator<Integer>) (o1, o2) -> o1 - o2);
		System.out.println(p);
	}
}
