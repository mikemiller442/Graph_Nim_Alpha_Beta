import java.util.Set;

public class AbelianGroup extends Group {

	private int[] products;

	public AbelianGroup(int rank, String type, boolean canPass, int numStones, int[] products) {

		super(rank, type, canPass, numStones);
		this.products = products;
		this.generators = new AbelianElement[products.length];
		this.ConstructVertices();
		super.ConstructEdges();

	}

	private void ConstructVertices() {

		String elementKey = "";
		AbelianElement tempElement;
		int[] identityEntries = new int[this.products.length];
		int[] tempIntegers = new int[this.products.length];
		int[] copiedEntries;
		this.order = 1;

		for (int i = 0; i < this.products.length; i++) {

			this.order = this.order*this.products[i];

		}

		for (int i = 0; i < this.products.length; i++) {

			identityEntries[i] = 0;

		}

		copiedEntries = new int[this.products.length];
		for (int k = 0; k < this.products.length; k++) {
			copiedEntries[k] = identityEntries[k];
		}

		this.identity = new AbelianElement(this.numStones, this.products.length, copiedEntries, this.products);
		elementKey = this.identity.generateKey();
		this.elements.put(elementKey, this.identity);

		for (int i = 0; i < this.products.length; i++) {

			copiedEntries = new int[this.products.length];

			for (int j = 0; j < this.products.length; j++) {

				if (i == j) {

					tempIntegers[j] = 1;
					copiedEntries[j] = 1;

				} else {

					tempIntegers[j] = 0;
					copiedEntries[j] = 0;

				}

			}

			tempElement = new AbelianElement(this.numStones, this.products.length, copiedEntries, this.products);
			this.generators[i] = tempElement;
			elementKey = this.generators[i].generateKey();
			System.out.println(elementKey);
			this.elements.put(elementKey, this.generators[i]);

		}

		System.out.println(this.order);

		this.generateElements();

	}

}
