public class Matrix extends Node {

	private int dim;
	private int fieldOrder;
	private int[][] entries;

	/*
	* For the matrix group to be finite, we construct them over a finite field.
	* The dimensions are the number of rows/columns, and the fielder order is the
	* order of the finite field.
	*/
	public Matrix(int weight, int rank, int dim, int fieldOrder, int[][] entries) {

		super(weight, rank);
		this.dim = dim;
		this.fieldOrder = fieldOrder;
		this.entries = entries;
		this.key = this.generateKey();

	}

	public String[] getImages() {

		return this.images;

	}

	public String[] getPreimages() {

		return this.preimages;

	}

	public int getDim() {

		return this.dim;

	}

	public int getFieldOrder() {

		return this.fieldOrder;

	}

	public int[] getComponents() {

		throw new IllegalArgumentException();

	}

	public int[][] getComponents(boolean isMatrix) {

		return this.entries;

	}

	public String generateKey() {

		String elementString = "";
		for (int i = 0; i < this.dim; i++) {

			for (int j = 0; j < this.dim; j++) {

				elementString = elementString + Integer.toString(this.entries[i][j]);

			}

		}

		return elementString;

	}

	public int determinant() {

		int determinant = 0;
		int[][] in = this.entries;
		if (in.length == 2) {

			determinant = in[0][0]*in[1][1] - in[0][1]*in[1][0];

		} else {

			determinant = in[0][0]*(in[1][1]*in[2][2]-in[1][2]*in[2][1])-in[1][0]*(in[0][1]*in[2][2]-in[2][1]*in[0][2])+in[2][0]*(in[0][1]*in[1][2]-in[0][2]*in[1][1]);

		}

		return determinant;

	}

	//performs matrix multiplication, the group operation for invertible matrices
	public Node compose(Node generator, int numStones) {

		int dotProduct;
		int[][] generatorEntries = generator.getComponents(true);
		int[][] imageEntries = new int[this.dim][this.dim];
		for (int i = 0; i < this.dim; i++) {

			for (int j = 0; j < this.dim; j++) {

				dotProduct = 0;
				for (int k = 0; k < this.dim; k++) {

					dotProduct += this.entries[i][k]*generatorEntries[k][j];

				}

				imageEntries[i][j] = dotProduct % this.fieldOrder;

			}

		}

		Matrix image = new Matrix(numStones, this.rank, this.dim, this.fieldOrder, imageEntries);
		return image;

	}

}
