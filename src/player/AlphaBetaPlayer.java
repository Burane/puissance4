package player;

import dataStructures.ListeChainee;
import modele.BitBoard;
import modele.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

public class AlphaBetaPlayer {
	private final BitBoard game;
	private final Player AIPlayer;
	private final int depth;
	private int nb;
	HashMap<Long, P4Entry> transpositionTable = new HashMap();

	public AlphaBetaPlayer(BitBoard game, Player player, int depth) {
		this.game = game;
		this.AIPlayer = player;
		this.depth = depth;
	}

	/*
		1 = Y
		2 = R
	 */

	public long alphaBetaSearch(long alpha, long beta, int depth, Player currentPlayer) {
		nb++;

		P4Entry entry = transpositionTable.get(game.getHash());
		if (entry != null) {
			if (entry.flag == Flag.EXACT)
				return entry.score;
			if (entry.flag == Flag.LOWERBOUND && entry.score > alpha)
				alpha = entry.score;
			if(entry.flag == Flag.UPPERBOUND && entry.score < beta)
				beta = entry.score;
		}

		if (depth == 0 || game.isFull()){
			long score = game.evaluate();
			if(score <= alpha)
				transpositionTable.put(game.getHash(),new P4Entry(score,depth,Flag.LOWERBOUND));
			else if(score >= beta)
				transpositionTable.put(game.getHash(),new P4Entry(score,depth,Flag.UPPERBOUND));
			else
				transpositionTable.put(game.getHash(),new P4Entry(score,depth,Flag.EXACT));
			return score;
		}

		ListeChainee<Integer> moves = game.listNextMoves();
		orderMoves(moves);

		// maximise player
		if (this.AIPlayer == currentPlayer) {
			for (int move : moves) {
				game.move(move);
				long score = alphaBetaSearch(alpha, beta, depth - 1, Player.getOpponnent(currentPlayer));
				game.undo();
				alpha = Math.max(alpha, score);
				if (alpha >= beta) {
					break; // coupure beta
				}
			}
			return alpha;
		}
		// minimise player
		else {
			for (int move : moves) {
				game.move(move);
				long score = alphaBetaSearch(alpha, beta, depth - 1, Player.getOpponnent(currentPlayer));
				game.undo();
				beta = Math.min(beta, score);
				if (alpha >= beta)
					break; // coupure alpha
			}
			return beta;
		}
	}

	public int getBestMove() {
		ListeChainee<Integer> moves = game.listNextMoves();

		System.out.println("moves = " + moves);
		orderMoves(moves);
		System.out.println("moves ordered = " + moves);
		int bestMove = -1;
		long bestScore = Integer.MIN_VALUE;
		//		System.out.println("moves.getLength() == 0 "+(moves.getLength() == 0) +"   game.isFull() "+ (game.isFull()));
		if (moves.getLength() == 0 || game.isFull()) {
			System.out.println("DRAW");
			return -1;
		}
		// all move have 0 score TODO
		long startTime = System.nanoTime();
		for (int move : moves) {
			game.move(move);
			long currentScore = alphaBetaSearch(Integer.MIN_VALUE, Integer.MAX_VALUE, this.depth, (this.AIPlayer));
			if (this.depth % 2 == 0)
				currentScore *= -1;
			System.out.println("processing move : " + move + " score : " + currentScore);
			game.undo();
			if (currentScore > bestScore) {
				bestMove = move;
				bestScore = currentScore;
			}
		}
		long stopTime = System.nanoTime();
		NumberFormat df = NumberFormat.getNumberInstance(Locale.getDefault());
		System.out.println("nombre de situations évaluées : " + df
				.format(nb) + " en " + (stopTime - startTime) / 1000000000.0 + " s");
		nb = 0;

		if (bestMove == -1)
			System.out.println("WARING BESTMOVE = -1");

		return bestMove;
	}

	private void orderMoves(ListeChainee<Integer> moves) {
		moves.mergeSort((o1, o2) -> {
			int d1 = Math.abs(3 - o1);
			int d2 = Math.abs(3 - o2);
			return Integer.compare(d1, d2);
		});
	}

	class P4Entry {
		long score;
		int depth;
		Flag flag;

		public P4Entry(long score, int depth, Flag flag) {
			this.score = score;
			this.depth = depth;
			this.flag = flag;
		}
	}

	enum Flag {
		LOWERBOUND, UPPERBOUND, EXACT
	}

	;

}
