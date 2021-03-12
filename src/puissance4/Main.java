package puissance4;

import modele.BitBoard;
import modele.Player;
import player.AlphaBetaPlayer;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		BitBoard bb = new BitBoard();

		System.out.println(bb);
		long curentBoard = bb.getBoardOfPlayerNameWhoMovesNext();

		System.out.println("current player : " + bb.getPlayerNameWhoMovesNext());
		System.out.println("Is current player win : " + bb.isWin(bb.getBoardOfPlayerNameWhoMovesNext()));
		System.out.println("bitboard bits representation : " + BitBoard.getBits(bb.getBoardOfPlayerNameWhoMovesNext()));
		System.out.println("bitboard bits shifting representation : " + BitBoard
				.getBits(curentBoard & (curentBoard >> 1) & (curentBoard >> 2)));
		System.out.println("nb of 3 in a row : " + bb.checkNbOf3InARow(bb.getBoardOfPlayerNameWhoMovesNext()));
		System.out.println("nb of 2 in a row : " + bb.checkNbOf2InARow(bb.getBoardOfPlayerNameWhoMovesNext()));
		System.out.println("bb hash : " + bb.getHash());
		AlphaBetaPlayer ai = new AlphaBetaPlayer(bb, Player.RED, 13);

		bb.generateKeys();

		Scanner sc = new Scanner(System.in);
		do {
//			System.out.println("\n =====================1======================== \n");
			System.out.println("current player : " + bb.getPlayerNameWhoMovesNext());
//			System.out.println(bb);
			//			System.out.println("bb hash " + bb.getHash());
			System.out.println("Quel colonne voulez vous jouer ? [0-6]");
			int move = sc.nextInt();
			if (move == -1) {
				bb.undo();
			}
			else {
				bb.move(move);
			}
			if(bb.isWin(bb.getBoardOfPlayerWhoHaveJustMove())){
				System.out.println(bb.getPlayerNameWhoHaveJustMove() + " WIN");
				return;
			}
//			System.out.println("\n ======================2======================= \n");
			System.out.println(bb);
			bb.evaluate();
			System.out.println("AI is playing");
			int aiMove = ai.getBestMove();
			System.out.println("aiMove = " + aiMove);
			bb.move(aiMove);
			if(bb.isWin(bb.getBoardOfPlayerWhoHaveJustMove())){
				System.out.println(bb.getPlayerNameWhoHaveJustMove() + " WIN");
				return;
			}

		} while (!bb.isFull());

	}



}
