public abstract class Node {

	protected String key;
	protected int weight;
	protected int rank;
	protected String[] images;
	protected String[] preimages;
	protected int imageCount;
	protected int preimageCount;
	protected boolean discovered = false;
	protected Node BFSparent;

	public Node(int weight, int rank) {

		this.weight = weight;
		this.rank = rank;
		this.images = new String[rank];
		this.preimages = new String[rank];
		this.imageCount = 0;
		this.preimageCount = 0;

	}

	// implemented in each derived class of AbelianElement, Matrix, and Permutation
	public abstract Node compose(Node in, int numStones);

	public abstract int[] getComponents();

	public abstract int[][] getComponents(boolean matrix);

	//generates the key of the element, used to insert it in the HashMap of elements
	public abstract String generateKey();

	public abstract int determinant();

	public String getKey() {

		return this.key;

	}

	public void setDiscovered(boolean isDiscovered) {

		this.discovered = isDiscovered;

	}

	public boolean isDiscovered() {

		return this.discovered;

	}

	public void setBFSparent(Node parent) {

		this.BFSparent = parent;

	}

	public Node getBFSparent() {

		return this.BFSparent;

	}

	public void printImages() {

		for (int i = 0; i < this.rank; i++) {

			System.out.println(this.images[i]);

		}

	}

	public boolean containsImage(String key) {

		boolean contains = false;
		for (int i = 0; i < this.rank; i++) {

			if (this.images[i].equals(key)) {

				contains = true;
				break;

			}

		}

		return contains;

	}

	public void decreaseWeight(int stones) {

		if (this.weight - stones >= 0) {

			this.weight = this.weight - stones;

		} else {

			System.out.println("node key");
			System.out.println(this.key);
			System.out.println("node had this many stones");
			System.out.println(this.weight);
			System.out.println("bot tried to take this many stones below");
			System.out.println(stones);
			throw new IllegalArgumentException("You are taking too many stones!");

		}

	}

	public void increaseWeight(int stones) {

		if (this.weight + stones <= 10) {

			this.weight = this.weight + stones;

		} else {

			System.out.println("node key");
			System.out.println(this.key);
			System.out.println("node had this many stones");
			System.out.println(this.weight);
			System.out.println("bot tried to give this many stones below");
			System.out.println(stones);
			throw new IllegalArgumentException("You are giving too many stones!");

		}

	}

	public void addImage(String imageKey) {

		this.images[this.imageCount] = imageKey;
		this.imageCount++;

	}

	public void addPreimage(String preimageKey) {

		this.preimages[this.preimageCount] = preimageKey;
		this.preimageCount++;

	}

	public int getWeight() {

		return this.weight;

	}

	public String[] getImages() {

		return this.images;

	}

	public String[] getPreimages() {

		return this.preimages;

	}

}
