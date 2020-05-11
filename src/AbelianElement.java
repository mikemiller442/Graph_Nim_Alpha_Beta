public class AbelianElement extends Node {

	private int[] integers;
	private int[] products;

	/*
	* In Abelian groups, each element is effectively a direct product of integers
	* that form a group under addition modulo certain integers.
	*/
	public AbelianElement(int weight, int rank, int[] integers, int[] products) {

		super(weight, rank);
		this.integers = integers;
		this.products = products;
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

		return this.integers;

	}

	public int[][] getComponents(boolean error) {

		throw new IllegalArgumentException();

	}

	public String generateKey() {

		String key = "";
		for (int i = 0; i < this.integers.length; i++) {

			key = key + Integer.toString(this.integers[i]);

		}

		return key;

	}

	// the group operation is addition modulo the integer for that cyclic group
	public Node compose(Node generator, int numStones) {

		int[] imageEntries = new int[this.integers.length];
		int[] generatorIntegers = generator.getComponents();
		for (int i = 0; i < this.integers.length; i++) {

			imageEntries[i] = (this.integers[i] + generatorIntegers[i]) % products[i];

		}

		AbelianElement image = new AbelianElement(numStones, this.rank, imageEntries, this.products);
		return image;

	}
}
