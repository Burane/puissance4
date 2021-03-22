package modele;

import java.util.Random;

public class ZobristHashing {

	/*
		voir principe du zobristHashing https://en.wikipedia.org/wiki/Zobrist_hashing
	 */

	private long hash = 0L;
	private BitBoard bitboard;
	private long[][] hashKeysTable = new long[2][BitBoard.ROW * BitBoard.COLUMN];

	public ZobristHashing(BitBoard bitBoard) {
		this.bitboard = bitBoard;
		generateKeys();
	}

	private void generateKeys() {
		Random rand = new Random();
		for (int i = 0; i < hashKeysTable.length; i++) {
			for (int j = 0; j < hashKeysTable[i].length; j++) {
				hashKeysTable[i][j] = rand.nextLong();
			}
		}
	}

	public void calculateHash(int column) {
		long zobristHashing = hashKeysTable[bitboard.getPlayerWhoHaveJustMove()][BitBoard.COLUMN * bitboard
				.getNumberOfPieceInColumn()[column] + column];
		hash ^= zobristHashing;
	}

	public long getHash() {
		return hash;
	}

}
