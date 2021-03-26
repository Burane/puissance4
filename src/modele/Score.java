package modele;

import dataStructures.arbreBinaire.ArbreBinaire;

public class Score {
	private static Score instance;
	ArbreBinaire<Entry> scores = new ArbreBinaire<>();

	private Score() {
		fillExample();
	}

	public static Score getInstance() {
		if (instance == null)
			instance = new Score();

		return instance;
	}

	public void add(String name, int score) {
		scores.ajouter(new Entry(name, score));
	}

	public void fillExample() {
		scores.ajouter(new Entry("jean",16));
		scores.ajouter(new Entry("jean",8));
		scores.ajouter(new Entry("jean",24));
		scores.ajouter(new Entry("marie",13));
		scores.ajouter(new Entry("marie",17));
		scores.ajouter(new Entry("marie",20));
	}

	@Override
	public String toString() {
		return scores.toString();
	}
}

class Entry implements Comparable<Entry> {
	String name;
	int score;

	public Entry(String name, int score) {
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(Entry o) {
		return this.score - o.score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "[" + score + " : " + name + "]";
	}
}