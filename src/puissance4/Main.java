package puissance4;

import modele.BitBoard;
import modele.Player;
import modele.Score;
import player.AlphaBetaPlayer;
import utils.WindowsConsole;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		WindowsConsole.changeCMDcodepage();
		WindowsConsole.enableAnsiEscapeCMD();

		System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

		do {
			menu();
		} while (true);

	}


	public static void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Voulez vous jouer[1] / consulter les scores[2] / quitter[3]");
		int choice = sc.nextInt();
		switch (choice){
			case 1 -> new Game();
			case 2 -> showScore();
			case 3 -> System.exit(0);
		}
	}

	private static void showScore() {
		Score score = Score.getInstance();
		System.out.println(score);
	}

}
