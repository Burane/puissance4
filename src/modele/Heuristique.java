package modele;

import java.util.Arrays;

// 333124551255106664
public class Heuristique {

	BitBoard bitBoard;

	/*
		une table d'évalution qui va donner un score à une position
	 */
	private static final int[][] evaluationTable = { { 3, 4, 5, 7, 5, 4, 3 }, { 4, 6, 8, 10, 8, 6, 4 },
			{ 5, 8, 11, 13, 11, 8, 5 }, { 5, 8, 11, 13, 11, 8, 5 }, { 4, 6, 8, 10, 8, 6, 4 }, { 3, 4, 5, 7, 5, 4, 3 } };

	/*
			le score de chaque joueur
		 */
	private final long[] scores = new long[2];

	public Heuristique(BitBoard bitBoard) {
		this.bitBoard = bitBoard;
		Arrays.fill(scores, 0);
	}

	/*
		évalue le score d'une position
	 */
	public long evaluate() {
		long curentBoard = bitBoard.getBoardOfPlayerNameWhoMovesNext();
		long opponentBoard = bitBoard.getBoardOfPlayerWhoHaveJustMove();

		if (bitBoard.isWin(curentBoard))
			return Integer.MAX_VALUE;

		if (bitBoard.isWin(opponentBoard))
			return Integer.MIN_VALUE;

		long currentScore = scores[bitBoard.getPlayerWhoMovesNext()];
		long opponentScore = scores[bitBoard.getPlayerWhoHaveJustMove()];

		return currentScore - opponentScore;
	}

	/*
	 Brian Kernighan’s Algorithm
	 Complexity: O(log(n))
	 Permet de savoir combien de bits sont à 1
	 ex : 00000000000000000100000000000001 -> 2
 */
	public static int countSetBits(long n) {
		int count = 0;
		while (n > 0) {
			n &= (n - 1);
			count++;
		}
		return count;
	}

	public int checkNbOf3InARow(long bitboard) {
		int nb = 0;
		int[] directions = { 1, 7, 6, 8 };
		for (int direction : directions)
			nb += countSetBits(bitboard & (bitboard >> direction) & (bitboard >> (2 * direction)));
		return nb;
	}


	public int checkNbOf2InARow(long bitboard) {
		int nb = 0;
		int[] directions = { 1, 7, 6, 8 };
		for (int direction : directions)
			nb += countSetBits(bitboard & (bitboard >> direction));
		return nb;
	}

	public void addScore(int player, int column){
		scores[player] += evaluationTable[bitBoard.getNumberOfPieceInColumn()[column]][column];
	}
	public void subScore(int player, int column){
		scores[player] -= evaluationTable[bitBoard.getNumberOfPieceInColumn()[column]][column];
	}

	public long[] getScores() {
		return scores;
	}


}
