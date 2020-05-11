public class Permutation extends Node {

	private int degree;
	private int[] mappings;

	/*
	* The Symmetric group of n elements, where n is the degree, is the group of
	* all permutations on n objects.
	*/
	public Permutation(int weight, int rank, int degree, int[] mappings) {

		super(weight, rank);
		this.images = new String[rank];
		this.preimages = new String[rank];
		this.imageCount = 0;
		this.preimageCount = 0;
		this.degree = degree;
		this.mappings = mappings;
		this.key = generateKey();

	}

	public String[] getImages() {

		return this.images;

	}

	public String[] getPreimages() {

		return this.preimages;

	}

	public int determinant() {

		throw new IllegalArgumentException();

	}

	public int[] getComponents() {

		return this.mappings;

	}

	public int[][] getComponents(boolean error) {

		throw new IllegalArgumentException();

	}

	public String generateKey() {

		String elementString = "";
		for (int i = 0; i < this.degree; i++) {

			elementString = elementString + Integer.toString(this.mappings[i]);

		}

		return elementString;

	}

	// for the Symmetric group, the group operation is composition of permutations
	public Node compose(Node generator, int numStones) {

		int[] generatorMappings = generator.getComponents();
		int[] imageMappings = new int[this.degree];

		for (int i = 0; i < this.degree; i++) {

			imageMappings[this.mappings[generatorMappings[i]]] = i;

		}

		Permutation image = new Permutation(numStones, this.rank, this.degree, imageMappings);

		return image;

	}

}
