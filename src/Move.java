public class Move {

  private int playerToMove;
  private int weight;
  private String preimage;
  private String image;

  public Move(int playerToMove, int weight, String preimage, String image) {

    this.playerToMove = playerToMove;
    this.weight = weight;
    this.preimage = preimage;
    this.image = image;

  }

  public int getPlayerToMove() {

    return this.playerToMove;

  }

  public int getWeight() {

    return this.weight;

  }

  public String getPreimage(){

    return this.preimage;

  }

  public String getImage(){

    return this.image;

  }

}
