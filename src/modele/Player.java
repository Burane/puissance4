package modele;

import utils.ConsoleColors;

public enum Player {
// utf-8 chars
//	RED(ConsoleColors.RED+" ■ "+ConsoleColors.RESET),
//	YELLOW(ConsoleColors.YELLOW+" ■ "+ConsoleColors.RESET),
//	EMPTY(ConsoleColors.WHITE+" ■ "+ConsoleColors.RESET);

	RED(ConsoleColors.RED+" ■"+ConsoleColors.RESET),
	YELLOW(ConsoleColors.YELLOW+" ■"+ConsoleColors.RESET),
	EMPTY(ConsoleColors.WHITE+" ■"+ConsoleColors.RESET);

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
