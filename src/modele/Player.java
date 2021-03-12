package modele;

public enum Player {

	RED("R"), YELLOW("Y"), EMPTY(" ");

	private final String letter;

	Player(String letter) {
		this.letter = letter;
	}

	public String getLetter() {
		return letter;
	}

	public static Player getOpponnent(Player curr) {
		Player opp = EMPTY;
		switch (curr) {
		case RED -> opp = YELLOW;
		case YELLOW -> opp = RED;
		}
		return opp;
	}

}
