package modele;

import dataStructures.ListeChainee;

import java.math.BigInteger;
import java.util.Arrays;

public class BitBoard implements Cloneable {

	public static final int ROW = 6;
	public static final int COLUMN = 7;
	private ZobristHashing zobristHashing;
	private Heuristique heuristique;

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
		heuristique = new Heuristique(this);
		zobristHashing = new ZobristHashing(this);
	}

	public void move(int column) {
		heuristique.addScore(getPlayerWhoMovesNext(), column);
		zobristHashing.calculateHash(column);
		long move = 1L << (numberOfPieceInColumn[column]++ + ((column) * COLUMN));
		bitBoards[getPlayerWhoMovesNext()] ^= move;
		moves[whoIsPlaying++] = column;
	}

	public void undo() {
		int column = moves[--whoIsPlaying];
		moves[whoIsPlaying] = -1;
		long move = 1L << (--numberOfPieceInColumn[column] + ((column) * COLUMN));
		zobristHashing.calculateHash(column);
		heuristique.subScore(getPlayerWhoMovesNext(), column);
		bitBoards[whoIsPlaying & 1] ^= move;
	}

	public boolean isWin(long bitboard) {
		int[] directions = { 1, 7, 6, 8 };
		for (int direction : directions)
			if ((bitboard & (bitboard >> direction) & (bitboard >> (2 * direction)) & (bitboard >> (3 * direction))) != 0)
				return true;
		return false;
	}

	public ListeChainee<Integer> listNextMoves() {
		ListeChainee<Integer> moves = new ListeChainee<>();
		for (int col = 0; col < COLUMN; col++) {
			if (numberOfPieceInColumn[col] < ROW)
				moves.add(col);
		}
		return moves;
	}

	public boolean isFull() {
		long topRowFullMask = 0b0100000_0100000_0100000_0100000_0100000_0100000_0100000L;
		return ((getFullBoard() & topRowFullMask) == topRowFullMask);
	}

	public int[] getNumberOfPieceInColumn() {
		return numberOfPieceInColumn;
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

	public long getFullBoard() {
		return bitBoards[0] | bitBoards[1];
	}

	public static String getBits(long l) {
		return String.format("%032d", new BigInteger(Long.toBinaryString(l)));
	}

	public ZobristHashing getZobristHashing() {
		return zobristHashing;
	}

	public Heuristique getHeuristique() {
		return heuristique;
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

}
