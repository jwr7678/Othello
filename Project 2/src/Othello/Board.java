package Othello;

import java.util.*;

public class Board {
	final int LENGTH = 8;
	final int WIDTH = 8;
	public Piece[][] board;
	private Piece empty;
	String divider = "-----------------------------------"; // Used for making the board
	public char[] rowHeader = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };
	ArrayList<String> validMoves = new ArrayList<String>();

	Board(Player blackPlayer, Player whitePlayer) {
		board = new Piece[LENGTH][WIDTH];
		empty = new Piece(' ');
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				board[i][j] = empty;
			}
		}
		board[3][3] = whitePlayer.color;
		board[4][4] = whitePlayer.color;
		board[3][4] = blackPlayer.color;
		board[4][3] = blackPlayer.color;
	}

	public void printBoard() {
		System.out.print(" ");
		for (int k = 0; k < WIDTH; k++) {
			System.out.printf(" |%2s", rowHeader[k]);
		}
		System.out.print(" |\n");
		for (int i = 0; i < LENGTH; i++) {
			System.out.println(divider);
			System.out.print(i + 1 + " "); // Prints the row number for each row
			// Prints each row on the the board
			for (int j = 0; j < WIDTH; j++) {
				System.out.printf("|%2s ", board[i][j].value);
			}
			System.out.println("|");
		}
		System.out.println(divider); // Prints the very last horizontal line for the board

	}

	public boolean boardEdge(int x, int y) {
		return x < 0 || x > 7 || y < 0 || y > 7;
	}

	private boolean checkDirection(int row, int col, Player currentPlayer, int dr, int dc) {
		if (board[row][col] != empty) {
			return false;
		} else {
			int currRow = row + dr;
			int currCol = col + dc;
			if (boardEdge(currRow, currCol)) {
				return false;
			}
			if (board[currRow][currCol] == currentPlayer.color) {
				return false;
			}
			for (; !boardEdge(currRow, currCol); currRow += dr, currCol += dc) {
				if (board[currRow][currCol] == currentPlayer.color) {
					return true;
				}
				if (board[currRow][currCol] == empty) {
					return false;
				}
			}
			return false;
		}
	}

	public boolean checkValidMove(int row, int col, Player currentPlayer) {
		boolean isvalidMove;
		for (int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				if (r == 0 && c == 0) {
					continue;
				} else {
					isvalidMove = checkDirection(row, col, currentPlayer, r, c);
					if (isvalidMove == true) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Move flip to player class
	 */
	public void flip(int row, int col, Player currentPlayer) {

		for (int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				if (r == 0 && c == 0) {
					continue;
				} else {
					if (checkDirection(row, col, currentPlayer, r, c)) {
						int currRow = row + r;
						int currCol = col + c;
						for (; board[currRow][currCol] != currentPlayer.color; currRow += r, currCol += c) {
							board[currRow][currCol] = currentPlayer.color;
						}
						// System.out.println(currRow + " " + currCol);
					}
				}
			}
		}
		board[row][col] = currentPlayer.color;
	}

	public boolean checkForMoves(Player currentPlayer) {
		boolean validPosition;
		int x, y;
		for (x = 0; x < LENGTH; x++) {
			for (y = 0; y < WIDTH; y++) {
				if (board[x][y] == empty) {
					validPosition = checkValidMove(x, y, currentPlayer);
					if (validPosition == true) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Pick either this one or the above to keep
	private void getValidMoves(Player currentPlayer) {
		validMoves.clear();
		boolean validPosition;
		for (int x = 0; x < LENGTH; x++) {
			for (int y = 0; y < WIDTH; y++) {
				if (board[x][y] == empty) {
					validPosition = checkValidMove(x, y, currentPlayer);
					if (validPosition == true) {
						validMoves.add(Integer.toString(y) + Integer.toString(x)); // column then number
					}
				}
			}
		}
		// if (!validMoves.isEmpty()) {
		// return true;
		// }
		// return false;
	}

	public boolean hasValidMoves(Player thisPlayer) {
		getValidMoves(thisPlayer);
		if (!validMoves.isEmpty()) {
			return true;
		}
		return false;
	}

	public int getCount(Player thisPlayer) {
		int count = 0;
		for (int i = 0; i < LENGTH; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (board[i][j] == thisPlayer.color) {
					count++;
				}
			}
		}
		return count;
	}
}
