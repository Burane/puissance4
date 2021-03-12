package modele;

import dataStructures.ListeChainee;
import dataStructures.Pile;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class BitBoard implements Cloneable {

	private static final int ROW = 6;
	private static final int COLUMN = 7;

	private static final int[][] evaluationTable = { { 3, 4, 5, 7, 5, 4, 3 }, { 4, 6, 8, 10, 8, 6, 4 },
			{ 5, 8, 11, 13, 11, 8, 5 }, { 5, 8, 11, 13, 11, 8, 5 }, { 4, 6, 8, 10, 8, 6, 4 }, { 3, 4, 5, 7, 5, 4, 3 } };

	private final long[] scores = new long[2];

	private long[][] hashTable = new long[2][ROW * COLUMN];

	public long getHash() {
		return hash;
	}

	private long hash = 0L;

	/*
	 * avoir une bitboard permet d'effectuer efficacement des actions sur le plateau
	 *
	 *   6 13 20 27 34 41 48   55 62 Additional row
	 * +---------------------+
	 * | 5 12 19 26 33 40 47 | 54 61 top row
	 * | 4 11 18 25 32 39 46 | 53 60
	 * | 3 10 17 24 31 38 45 | 52 59
	 * | 2 09 16 23 30 37 44 | 51 58
	 * | 1 08 15 22 29 36 43 | 50 57
	 * | 0 07 14 21 28 35 42 | 49 56 63 bottom row
	 * +---------------------+
	 */
	/*
	 * les plateau du point de vue de chaque joueur
	 */

	private long[] bitBoards = new long[2];
	/*
	 * sert à savoir qui joue en fonction de si il est paire ou impaire
	 */

	private int whoIsPlaying = 0;
	/*
	 * on sauvegarde à chaque index combien de pion on a deja joué
	 */

	private final int[] numberOfPieceInColumn = { 0, 0, 0, 0, 0, 0, 0 };
	/*
	 * on sauvegarde la liste des coups pour pouvoir les annuler
	 * ex: 3301 : on a joué sur la colonne 3 puis 3 puis 0 et enfin 1
	 * | | | | | | | |
	 * | | | | | | | |
	 * | | | | | | | |
	 * | | | | | | | |
	 * | | | |x| | | |
	 * |o|x| |o| | | |
	 *
	 */

	private final int[] moves = new int[42];

	public BitBoard() {
		reset();
	}

	public void reset() {
		bitBoards = new long[2];
		Arrays.fill(numberOfPieceInColumn, 0);
		Arrays.fill(moves, -1);
		Arrays.fill(scores, 0);
		generateKeys();
	}

	public void move(int column) {
		scores[getPlayerWhoMovesNext()] += evaluationTable[numberOfPieceInColumn[column]][column];
		calculateHash(column);
		long move = 1L << (numberOfPieceInColumn[column]++ + ((column) * COLUMN));
		bitBoards[getPlayerWhoMovesNext()] ^= move;
		moves[whoIsPlaying++] = column;
		System.out.println(this);
	}

	public void undo() {
		int column = moves[--whoIsPlaying];
		System.out.println("undo at " + column);
		moves[whoIsPlaying] = -1;
		long move = 1L << (--numberOfPieceInColumn[column] + ((column) * COLUMN));
		calculateHash(column);
		scores[getPlayerWhoMovesNext()] -= evaluationTable[numberOfPieceInColumn[column]][column];
		bitBoards[whoIsPlaying & 1] ^= move;
		System.out.println(this);
	}

	public boolean isWin(long bitboard) {
		/*
		 * ~whoIsPlaying & 1 car besoin de tester le dernier joueur qui vient de jouer
		 */
		int[] directions = { 1, 7, 6, 8 };
		for (int direction : directions)
			if ((bitboard & (bitboard >> direction) & (bitboard >> (2 * direction)) & (bitboard >> (3 * direction))) != 0)
				return true;
		return false;
	}

	public long getFullBoard() {
		return bitBoards[0] | bitBoards[1];
	}

	public static String getBits(long l) {
		return String.format("%032d", new BigInteger(Long.toBinaryString(l)));
	}

	public ListeChainee<Integer> listNextMoves() {
		ListeChainee<Integer> moves = new ListeChainee<>();
		for (int col = 0; col < COLUMN; col++) {
			if (numberOfPieceInColumn[col] < ROW)
				moves.add(col);
		}
		return moves;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BitBoard other = (BitBoard) obj;
		if (!Arrays.equals(bitBoards, other.bitBoards))
			return false;
		return Arrays.equals(moves, other.moves);
	}

	public boolean isFull() {
		long topRowFullMask = 0b0100000_0100000_0100000_0100000_0100000_0100000_0100000L;
		return ((getFullBoard() & topRowFullMask) == topRowFullMask);
	}

	public int getPlayerWhoMovesNext() {
		return whoIsPlaying & 1;
	}

	public int getPlayerWhoHaveJustMove() {
		return ~whoIsPlaying & 1;
	}

	public long getBoardOfPlayerNameWhoMovesNext() {
		return bitBoards[getPlayerWhoMovesNext()];
	}

	public long getBoardOfPlayerWhoHaveJustMove() {
		return bitBoards[getPlayerWhoHaveJustMove()];
	}

	public Player getPlayerNameWhoMovesNext() {
		return (getPlayerWhoMovesNext()) == 0 ? Player.YELLOW : Player.RED;
	}
	public Player getPlayerNameWhoHaveJustMove() {
		return (getPlayerWhoMovesNext()) == 1 ? Player.YELLOW : Player.RED;
	}

	/*
		évalue le score d'une position
	 */
	public long evaluate() {
		long curentBoard = getBoardOfPlayerNameWhoMovesNext();
		long opponentBoard = getBoardOfPlayerWhoHaveJustMove();

		if (isWin(curentBoard))
			return Integer.MAX_VALUE;

		if (isWin(opponentBoard))
			return Integer.MIN_VALUE;

		long currentScore = scores[getPlayerWhoMovesNext()];
		long opponentScore = scores[getPlayerWhoHaveJustMove()];

		//System.out.println("currentScore " + currentScore + " opponentScore " + opponentScore);
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = ROW - 1; i >= 0; i--) {
			for (int j = i; j < 48; j += 7) {
				Player curr = Player.EMPTY;
				if (((bitBoards[0] >> j) & 1) == 1)
					curr = Player.YELLOW;

				if (((bitBoards[1] >> j) & 1) == 1)
					curr = Player.RED;

				sb.append("[").append(curr.getLetter()).append("]");
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public void generateKeys() {
		Random rand = new Random();

		for (int i = 0; i < hashTable.length; i++) {
			for (int j = 0; j < hashTable[i].length; j++) {
				hashTable[i][j] = rand.nextLong();
			}
		}
	}

	private void calculateHash(int column) {
		long zobristHashing = hashTable[getPlayerWhoHaveJustMove()][COLUMN * numberOfPieceInColumn[column] + column];
		hash ^= zobristHashing;
	}


}
