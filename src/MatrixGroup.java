import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

public class MatrixGroup extends Group {

	private int dim;
	private int fieldOrder;
	private final int multiplicativeGenerator = 2;
	private Matrix generatorScale;
	private Matrix generatorRotate;

	public MatrixGroup(int rank, String type, boolean canPass, int numStones, int dim, int fieldOrder) {

		super(rank, type, canPass, numStones);
		this.dim = dim;
		this.fieldOrder = fieldOrder;
		this.generators = new Matrix[2];
		this.ConstructVertices();
		super.ConstructEdges();

	}

	public int getDim() {

		return this.dim;

	}

	public int getFieldOrder() {

		return this.fieldOrder;

	}

	private void ConstructVertices() {

		Matrix tempMatrix;
		Matrix tempElements;
		Matrix preimage;
		Matrix matrixAction;
		String elementKey = "";
		int power1;
		int power2;
		this.order = 1;

		for (int i = 0; i < this.dim; i++) {

			power1 = 1;
			power2 = 1;

			for (int j = 0; j < this.dim; j++) {

				power1 = power1*this.fieldOrder;

			}

			for (int j = 0; j < i; j++) {

				power2 = power2*this.fieldOrder;

			}

			this.order = this.order*(power1 - power2);

		}

		int[][] generatorIdentityElements = new int[this.dim][this.dim];
		int[][] generatorScaleElements = new int[this.dim][this.dim];
		int[][] generatorRotateElements = new int[this.dim][this.dim];

		int numGenerated = 2;
		ArrayList<Matrix> generatedMatrices;
		Set<String> currentMatrixKeys = this.elements.keySet();

		if (this.dim == 2) {

			generatorIdentityElements[0][0] = 1;
			generatorIdentityElements[0][1] = 0;
			generatorIdentityElements[1][0] = 0;
			generatorIdentityElements[1][1] = 1;
			this.identity = new Matrix(this.numStones, 2, 2, this.fieldOrder, generatorIdentityElements);
			elementKey = this.identity.generateKey();
			this.elements.put(elementKey, this.identity);

			generatorScaleElements[0][0] = this.multiplicativeGenerator;
			generatorScaleElements[0][1] = 0;
			generatorScaleElements[1][0] = 0;
			generatorScaleElements[1][1] = 1;
			this.generatorScale = new Matrix(this.numStones, 2, 2, this.fieldOrder, generatorScaleElements);
			elementKey = this.generatorScale.generateKey();
			this.elements.put(elementKey, this.generatorScale);

			generatorRotateElements[0][0] = 2;
			generatorRotateElements[0][1] = 1;
			generatorRotateElements[1][0] = 2;
			generatorRotateElements[1][1] = 0;
			this.generatorRotate = new Matrix(this.numStones, 2, 2, this.fieldOrder, generatorRotateElements);
			elementKey = this.generatorRotate.generateKey();
			this.elements.put(elementKey, this.generatorRotate);

		} else {

			generatorIdentityElements[0][0] = 1;
			generatorIdentityElements[0][1] = 0;
			generatorIdentityElements[0][2] = 0;
			generatorIdentityElements[1][0] = 0;
			generatorIdentityElements[1][1] = 1;
			generatorIdentityElements[1][2] = 0;
			generatorIdentityElements[2][0] = 0;
			generatorIdentityElements[2][1] = 0;
			generatorIdentityElements[2][2] = 1;
			this.identity = new Matrix(this.numStones, 2, 3, this.fieldOrder, generatorIdentityElements);
			elementKey = this.identity.generateKey();
			this.elements.put(elementKey, this.identity);

			generatorRotateElements[0][0] = this.multiplicativeGenerator;
			generatorRotateElements[0][1] = 0;
			generatorRotateElements[0][2] = 0;
			generatorRotateElements[1][0] = 0;
			generatorRotateElements[1][1] = 1;
			generatorRotateElements[1][2] = 0;
			generatorRotateElements[2][0] = 0;
			generatorRotateElements[2][1] = 0;
			generatorRotateElements[2][2] = 1;
			this.generatorRotate = new Matrix(this.numStones, 2, 3, this.fieldOrder, generatorRotateElements);
			elementKey = this.generatorRotate.generateKey();
			this.elements.put(elementKey, this.generatorRotate);

			generatorScaleElements[0][0] = this.fieldOrder - 1;
			generatorScaleElements[0][1] = 0;
			generatorScaleElements[0][2] = 1;
			generatorScaleElements[1][0] = this.fieldOrder - 1;
			generatorScaleElements[1][1] = 0;
			generatorScaleElements[1][2] = 0;
			generatorScaleElements[2][0] = 0;
			generatorScaleElements[2][1] = this.fieldOrder - 1;
			generatorScaleElements[2][2] = 0;
			this.generatorScale = new Matrix(this.numStones, 2, 3, this.fieldOrder, generatorScaleElements);
			elementKey = this.generatorScale.generateKey();
			this.elements.put(elementKey, this.generatorScale);

		}

		this.generators[0] = this.generatorScale;
		this.generators[1] = this.generatorRotate;
		this.generateElements();

	}

}
