import dataStructures.Pile;

import java.util.Comparator;

@SuppressWarnings("unchecked")
public class test {
	public static void main(String[] args) {
		Pile<Integer> p = new Pile<>();

		System.out.println(p);
		p.add(1);
		System.out.println(p);
		System.out.println(p.get());
		System.out.println(p);
		////		new LinkedList().get();
		//		p.mergeSort(new Comparator<Integer>() {
		//			@Override
		//			public int compare(Integer o1, Integer o2) {
		//				return o1-o2;
		//			}
		//		});
		//		System.out.println(p);
	}
}
