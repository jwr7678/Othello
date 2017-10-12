package Othello;

import java.util.Scanner;
import java.util.*;

public class Game {
	Scanner scan = new Scanner(System.in);

	private String input;

	public Game() {

	}

	private String getInput() {
		input = scan.nextLine();
		return input;
	}

	private int getSpread(int blackCount, int whiteCount) {
		return blackCount - whiteCount;
	}

	private boolean repeatGame(boolean isSimulation, int trialCount, int currTrial) {
		if (!isSimulation) { // Needs to update to get user input
			return false;
		}
		if (currTrial > trialCount) {
			return false;
		}
		return true;
	}

	private void printSimStats(int blackCount, int whiteCount) {

	}

	private double getAverage(List<Integer> spreadList) {
		Integer sum = 0;
		if (!spreadList.isEmpty()) {
			for (Integer item : spreadList) {
				sum += item;
			}
			return sum.doubleValue() / spreadList.size();
		}
		return sum;
	}

	public static void main(String[] args) {
		Game game = new Game();
		while (true) {
		String input;
		int selection = 0;
		boolean isBlackAi = false;
		boolean isWhiteAi = false;
		boolean isSimulation = false;
		boolean continueGameSession = true;
		ArrayList<Integer> spreadList = new ArrayList<Integer>();
		System.out.println("The Othello Boardgame");
		System.out.println("Please select one of the following options:");
		System.out.println("1: Start new two Player Game." + "\n2: Start new single player game as Black."
				+ "\n3: Run Monte Carlo Simulation." + "\n4: Exit game.");
		do {
			input = game.getInput();
			try {
				selection = Integer.parseInt(input);
			} catch (NumberFormatException nfe) {
			}
			if (selection != 1 && selection != 2 && selection != 3 && selection != 4) {
				System.out.println("Invalid Entry. Please enter 1, 2, 3, or 4.");
			} else {
				break;
			}
		} while (true);

		int trialCount = 0;
		switch (selection) {
		case 1:
			break;
		case 2:
			/**
			 * Add prompt to let player pick their color
			 */
			isWhiteAi = true;
			break;
		case 3:
			isBlackAi = true;
			isWhiteAi = true;
			isSimulation = true;

			do {
				System.out.print("Please specify a number of test runs:");
				input = game.getInput();
				try {
					trialCount = Integer.parseInt(input);
				} catch (NumberFormatException nfe) {
				}
				if (trialCount > 0) {
					break;
				}
			} while (true);
			break;
		case 4:
			System.exit(0);
			break;
		}
		int currTrial = 1;
		long startTime = System.nanoTime();
		while (continueGameSession) {
			Player blackPlayer = new Player("Black", isBlackAi);
			Player whitePlayer = new Player("White", isWhiteAi);
			Player currentPlayer = blackPlayer;
			Board b = new Board(blackPlayer, whitePlayer);
			boolean endGameCondition = false;
			while (!endGameCondition) {
				if (b.hasValidMoves(currentPlayer)) {
					boolean validInput = false;
					int row = -1;
					int col = -1;
					if (!currentPlayer.isAi) {
						b.printBoard();
						System.out.println(currentPlayer.name + "'s turn."
								+ "\nPlease enter your move in the form Column Row." + "\n Example: A1");
					}
					while (!validInput) {

						input = currentPlayer.getInput(currentPlayer, b).replaceAll(",", "").replaceAll(" ", "");
						if (currentPlayer.isAi) {
							try {
								col = Integer.parseInt(input.substring(0, 1));
								row = Integer.parseInt(input.substring(1));
								validInput = true;
							} catch (NumberFormatException nfe) {
							}

						} else if (input.length() == 2) {
							for (int i = 0; i < b.rowHeader.length; i++) {
								if (input.toUpperCase().charAt(0) == b.rowHeader[i]) {
									col = i;
									break;
								}
							}

							try {
								row = Integer.parseInt(input.substring(1)) - 1;
							} catch (NumberFormatException nfe) {
							}

							if (b.validMoves.contains(Integer.toString(col) + Integer.toString(row))) {
								validInput = true;
								break;
							}
							// if (row >= 0 && row < 8 && col != -1) {
							// if (b.checkValidMove(row, col, currentPlayer)) {
							// validInput = true;
							// break;
							// }
							// }

						}

						if (!currentPlayer.isAi) {
							System.out.println("Invalid input. Please enter in the form A1.");
						}
					}
					b.flip(row, col, currentPlayer);
				} else {
				}
				currentPlayer = currentPlayer.changePlayer(blackPlayer, whitePlayer, currentPlayer);
				if (b.checkForMoves(blackPlayer) == false && b.checkForMoves(whitePlayer) == false) {
					endGameCondition = true;
				}
			}
			// b.printBoard();
			int blackCount = b.getCount(blackPlayer);
			int whiteCount = b.getCount(whitePlayer);
			int spread = game.getSpread(blackCount, whiteCount);
			spreadList.add(spread);
			//System.out.println("Trial Number: " + currTrial);
			// System.out.println("Spread: " + spread);
			currTrial++;
			continueGameSession = game.repeatGame(isSimulation, trialCount, currTrial);
			if (!isSimulation) {
				if (blackCount > whiteCount) {
					System.out.println("Black wins.");
				} else if (whiteCount > blackCount) {
					System.out.println("White wins.");
				} else {
					System.out.println("It's a tie!");
				}
			}
		}
		if (isSimulation) {
			System.out.println("Simulation Results");
			System.out.println("Spread, Occurrences");
			for (int i = -64; i <= 64; i++) {
				if (spreadList.contains(i)) {
					System.out.println(i + "," + Collections.frequency(spreadList, i));
				}
			}
			System.out.println("Average: " + game.getAverage(spreadList));
			double duration = (double) (System.nanoTime() - startTime) / 1000000000;
			System.out.println("Time: " + duration + "\n");

		}
	}
	}
}
