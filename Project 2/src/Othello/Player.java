package Othello;

import java.util.Scanner;
import java.util.Random;

public class Player {
	Piece color;
	boolean isAi;
	String name;
	String input;
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();

	public Player(String n, boolean ai) {
		name = n;
		color = new Piece(name.charAt(0));
		isAi = ai;
	}

	public Player() {
	}

	public Player changePlayer(Player blackPlayer, Player whitePlayer, Player currentPlayer) {
		if (currentPlayer == blackPlayer) {
			currentPlayer = whitePlayer;
		} else {
			currentPlayer = blackPlayer;
		}
		return currentPlayer;
	}

	public String getInput(Player currentPlayer, Board b) {
		if (!currentPlayer.isAi) {
			input = scan.nextLine();
			return input;
		}
		// int r = rand.nextInt(9);
		// int c = rand.nextInt(8);
		// input = b.rowHeader[c] + Integer.toString(r);
		// b.getValidMoves(currentPlayer);
		int r = rand.nextInt(b.validMoves.size());
		input = b.validMoves.get(r);
		return input;

	}

}
