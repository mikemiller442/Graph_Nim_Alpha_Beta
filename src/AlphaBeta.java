import java.util.Queue;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;
import java.util.Scanner;

public class AlphaBeta {

	private int alpha;
	private int beta;
	private int playerToMove;

	public AlphaBeta(int playerToMove) {

		this.playerToMove = playerToMove;
		this.resetAlphaBeta();

	}

	private void resetAlphaBeta() {

		this.alpha = Integer.MIN_VALUE;
		this.beta = Integer.MAX_VALUE;

	}

	private int BFS(Group g) {

		Queue<Node> q = new LinkedList<>();
		HashMap<String, Node> vertices = g.getElements();
		Node token = g.getToken();
		Node tempNode;
		Node tempImage;
		String[] images;
		int distance = 0;

		token.setDiscovered(true);
		q.add(token);
		System.out.println(token.getKey());
		System.out.println("size of queue");
		System.out.println(q.size());
		while (!q.isEmpty() || distance != 0) {

			System.out.println(token.getKey());
			System.out.println("size of queue during the search");
			System.out.println(q.size());
			tempNode = q.remove();
			System.out.println("current node key");
			System.out.println(tempNode.getKey());
			if (tempNode.getWeight() == 0) {

				while(tempNode != token) {

					tempNode = tempNode.getBFSparent();
					distance++;

				}

				break;

			} else {

				images = tempNode.getImages();
				for (int i = 0; i < images.length; i++) {

					tempImage = vertices.get(images[i]);
					if (!tempImage.isDiscovered()) {

						tempImage.setDiscovered(true);
						tempImage.setBFSparent(tempNode);
						q.add(tempImage);

					}

				}

			}

		}

		g.setDiscoveredStatus(false);
		return distance;

	}

	private int bestPath(int heroToMove, int minDistToZeroVertex, ArrayList<String> discovered, String tokenKey, Group g) {

		Node token = g.getToken();
		int[] bestDistances = new int[g.getRank()];
		String[] images = token.getImages();
		discovered.add(token.getKey());
		for (int i = 0; i < images.length; i++) {

			if (!discovered.contains(images[i])) {

				if (minDistToZeroVertex == 2) {

					System.exit(0);

					return 0;

				} else {

					bestDistances[i] = heroToMove*token.getWeight() + bestPath(-1*heroToMove, minDistToZeroVertex - 1, discovered, images[i], g);

				}

			}

		}

		int max = bestDistances[0];

		for (int i = 1; i < bestDistances.length; i++) {

			max = Math.max(max, bestDistances[i]);

		}

		return max;

	}

	private int heuristic(Group g) {

		int distToZeroVertex = BFS(g);
		int playerNumber = g.getPlayerToMove();
		int[] stoneCount = g.getPlayerStones();
		ArrayList<String> discovered = new ArrayList<String>(Math.min(distToZeroVertex, 5));
		String tokenKey;
		if (distToZeroVertex == 1) {

			return Integer.MAX_VALUE;

		} else if (distToZeroVertex == 2) {

			return stoneCount[playerNumber] - stoneCount[(playerNumber + 1) % 2];

		} else {

			tokenKey = g.getToken().getKey();
			return stoneCount[playerNumber] - stoneCount[(playerNumber + 1) % 2];
			// return bestPath(1, distToZeroVertex, discovered, tokenKey, g);

		}

	}

	public int findBestMove(Group g) {

		System.out.println("in findBestMove");
		// System.exit(0);

		int depth = 0;
		ArrayList<Move> moves = g.getMoves();
		int[] moveScores = new int[moves.size()];
		Move mv;

		for (int i = 0; i < moves.size(); i++) {

			mv = moves.get(i);
			g.makeMove(mv);
			moveScores[i] = min(g, this.alpha, this.beta, depth + 1);
			if (moveScores[i] > this.alpha) {

				this.alpha = moveScores[i];

			}
			g.undoMove();

		}

		int max = Integer.MIN_VALUE;
		int maxIndex = 0;

		for (int i = 0; i < moveScores.length; i++) {

			if (moveScores[i] > max) {

				maxIndex = i;
				max = moveScores[i];

			}

		}

		return maxIndex;

	}

	public int max(Group g, int alpha, int beta, int depth) {

		System.out.println("node key at max call");
		System.out.println(g.getToken().getKey());

		ArrayList<Move> moves = g.getMoves();
		int[] moveScores = new int[moves.size()];
		Move mv;

		if (depth == 6) {

			return this.heuristic(g);

		}

		if (moves.size() == 0) {

			System.out.println("bot in max found that there were zero moves");
			System.out.println(g.getPlayerToMove());
			return Integer.MIN_VALUE;

		}

		for (int i = 0; i < moves.size() && alpha <= beta; i++) {

			mv = moves.get(i);
			g.makeMove(mv);
			moveScores[i] = min(g, alpha, beta, depth + 1);
			if (moveScores[i] > alpha) {

				alpha = moveScores[i];

			}
			g.undoMove();

		}

		return alpha;

	}

	public int min(Group g, int alpha, int beta, int depth) {

		System.out.println("node key at min call");
		System.out.println(g.getToken().getKey());

		ArrayList<Move> moves = g.getMoves();
		int[] moveScores = new int[moves.size()];
		Move mv;

		if (depth >= 6) {

			throw new IllegalArgumentException("min should not be called at depth 6 or greater");

		}

		if (moves.size() == 0) {

			System.out.println("bot in min found that there were zero moves");
			System.out.println(g.getPlayerToMove());
			return Integer.MAX_VALUE;

		}

		for (int i = 0; i < moves.size() && alpha <= beta; i++) {

			mv = moves.get(i);

			System.out.println("trying to make a move");
			System.out.println(mv.getWeight());
			System.out.println(mv.getImage());

			g.makeMove(mv);
			moveScores[i] = max(g, alpha, beta, depth + 1);
			if (moveScores[i] < beta) {

				beta = moveScores[i];

			}
			g.undoMove();

		}

		return beta;

	}

	public static void main(String[] args) {


		System.out.println("Hello World!");
		boolean isValid = false;
		Move move;
		int moveNum;
		Node image;
		String imageKey = "";
		String[] images;
		int stoneDecrement = 0;
		ArrayList<Move> moves;
		Scanner scn = new Scanner(System.in);

		/*
		* You can take one of the games in Group.java and put them here in order
		* to play them against the AlphaBeta player. Right now the only game
		* I have listed here is the Abelian version, but I also recommend
		* playing the Symmetric Group version as well.
		*/

		 // Symmetric Group game
         Group graphNim = new SymmetricGroup(2, "Symmetric", true, 4, 4);
        
		graphNim.startGame(graphNim);

		/*
		* Assigns which player will move first. If playerToMove is 1, then AlphaBeta
		* will move second.
		*/
		int playerToMove = 0;
		AlphaBeta ab = new AlphaBeta(playerToMove);

		while (!graphNim.getMoves().isEmpty()) {

			if (graphNim.playerToMove == playerToMove) {

				System.out.println("bot is making a move");

				moves = graphNim.getMoves();
				moveNum = ab.findBestMove(graphNim);
				move = moves.get(moveNum);
				graphNim.makeMove(move);

			} else {

				graphNim.PrintGraph();
				while (!isValid) {

					isValid = false;
					System.out.println("Number of available moves");
					System.out.println(graphNim.getMoves().size());

					if (!graphNim.canPass) {
						System.out.println("Make a move by first entering the key of the vertex");
					} else {
						System.out.println("Make a move by first entering the key of the vertex OR enter 'pass' if you have taken more stones than your opponent:");
					}
					System.out.println("Available vertices to move to: ");
					graphNim.token.printImages();
					imageKey = scn.nextLine();
					if (imageKey.equals("pass") && graphNim.canPass) {

						if (graphNim.playerStones[graphNim.playerToMove] > graphNim.playerStones[(graphNim.playerToMove + 1) % 2]) {

							stoneDecrement = 0;
							imageKey = graphNim.token.getKey();
							isValid = true;

						} else {

							System.out.println("You do not have more stones than your oppponent and therefore cannot pass.");

						}

					} else {

						// graphNim.PrintVertices();
						if (graphNim.elements.containsKey(imageKey)) {

							images = graphNim.token.getImages();
							if (graphNim.token.containsImage(imageKey)) {

								System.out.println("Now enter how many stones you would like to remove from your current vertex:");
								stoneDecrement = Integer.parseInt(scn.nextLine());
								System.out.println(stoneDecrement);
								while (stoneDecrement > graphNim.token.getWeight() || stoneDecrement <= 0) {

									System.out.println("Enter a valid amount of stones to remove.");
									stoneDecrement = Integer.parseInt(scn.nextLine());

								}
								isValid = true;

							} else {

								System.out.println("The input image is not adjacent to the token.");

							}

						} else {

							System.out.println("This was not a valid key.");

						}

					}

				}

				move = new Move(graphNim.getPlayerToMove(), stoneDecrement, graphNim.token.getKey(), imageKey);
				System.out.println("image key 1");
				System.out.println(imageKey);
				System.out.println("token key 1");
				System.out.println(graphNim.getToken().getKey());
				System.out.println("About to make a move");
				System.out.println("Number of available moves1");
				System.out.println(graphNim.getMoves().size());
				graphNim.makeMove(move);
				System.out.println("token key 2");
				System.out.println(graphNim.getToken().getKey());
				System.out.println("Number of available moves2");
				System.out.println(graphNim.getMoves().size());
				System.out.println("Successfully made a move");
				isValid = false;

			}

			System.out.println("Number of available moves");
			System.out.println(graphNim.getMoves().size());

		}

		if ((graphNim.playerToMove + 1) % 2 == 0) {

			System.out.println("Congratulations to player one who has won the game!");

		} else {

			System.out.println("Congratulations to player two who has won the game!");

		}

	}

}
