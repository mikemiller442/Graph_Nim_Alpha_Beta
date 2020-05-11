import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

public class SymmetricGroup extends Group {

	private int degree;
	private Permutation generatorTranspose;
	private Permutation generatorCycle;

	public SymmetricGroup(int rank, String type, boolean canPass, int numStones, int degree) {

		super(rank, type, canPass, numStones);
		this.degree = degree;
		this.generators = new Permutation[2];
		this.ConstructVertices();
		super.ConstructEdges();

	}

	private void ConstructVertices() {

		boolean isBijection = true;
		Permutation tempPermutation;
		int[] permutationMappings = new int[4];
		String elementString = "";
		Permutation preimage;
		Permutation matrixAction;
		String elementKey = "";

		int[] generatorTransposeEntries = new int[this.degree];
		int[] generatorCycleEntries = new int[this.degree];
		int[] identityEntries = new int[this.degree];
		int[] copiedEntries;

		ArrayList<Node> generatedPermutations;
		Set<String> currentMatrixKeys = this.elements.keySet();
		this.order = 1;

		for (int i = 1; i <= this.degree; i++) {

			this.order = this.order*i;

		}

		generatorTransposeEntries[0] = 1;
		generatorTransposeEntries[1] = 0;
		for (int i = 2; i < this.degree; i++) {

			generatorTransposeEntries[i] = i;

		}

		generatorCycleEntries[this.degree-1] = 0;
		for (int i = 0; i < this.degree-1; i++) {

			generatorCycleEntries[i] = i+1;

		}

		for (int i = 0; i < this.degree; i++) {

			identityEntries[i] = i;

		}

		this.generatorTranspose = new Permutation(this.numStones, 2, this.degree, generatorTransposeEntries);
		this.generatorCycle = new Permutation(this.numStones, 2, this.degree, generatorCycleEntries);
		this.identity = new Permutation(this.numStones, 2, this.degree, identityEntries);

		elementKey = this.generatorTranspose.generateKey();
		this.elements.put(elementKey, this.generatorTranspose);

		elementKey = this.generatorCycle.generateKey();
		this.elements.put(elementKey, this.generatorCycle);

		elementKey = this.identity.generateKey();
		this.elements.put(elementKey, this.identity);

		this.generators[0] = this.generatorTranspose;
		this.generators[1] = this.generatorCycle;

		this.generateElements();

	}

}
