package puissance4;

import dataStructures.Pile.Pile;
import modele.BitBoard;
import modele.Player;
import modele.Score;
import player.AlphaBetaPlayer;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {
	private Pile<String> review = new Pile<>();
	private BitBoard bb = new BitBoard();
	private AlphaBetaPlayer ai = new AlphaBetaPlayer(bb, Player.YELLOW, 10);
	private String name = "";
	private int nbCoups = 0;

	public Game() {

		review.add(bb.toString());
		Scanner sc = new Scanner(System.in);
		System.out.println("Saisissez votre pseudo :");
		name = sc.nextLine();
		System.out.println(bb);
		do {
			System.out.println("Quel colonne voulez vous jouer ? [0-6]");
			int move = sc.nextInt();

			if (move(move)) {
				replay();
				return;
			}

			bb.getHeuristique().evaluate();
			System.out.println("AI is playing ...");
			int aiMove = ai.getBestMove();

			if (move(aiMove)) {
				replay();
				return;
			}

		} while (!bb.isFull());
		System.out.println("DRAW");
	}

	public boolean move(int column) {
		nbCoups++;
		bb.move(column);
		review.add(bb.toString());
		System.out.println(bb);
		if (bb.isWin(bb.getBoardOfPlayerWhoHaveJustMove())) {
			Player player = bb.getPlayerNameWhoHaveJustMove();
			System.out.println(player + " WIN");
			Score score = Score.getInstance();
			score.add(player == Player.YELLOW ? name : "AI", nbCoups);
			return true;
		}
		return false;
	}

	public void replay() {
		System.out.println("voulez vous revoir la partie ? oui[1] / non[2]");
		Scanner sc = new Scanner(System.in);
		if (sc.nextInt() == 1) {
			int len = review.getLength();
			for (int i = 0; i < len; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(review.get());
			}
		}
	}

}
