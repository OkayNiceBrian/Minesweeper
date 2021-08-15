
public class Tile{

  private int x;
  private int y;
  private boolean mine;
  private int neighborCount;
  private boolean revealed;
  private boolean flagged;
  private Grid parent;
  
  public Tile(int x, int y, boolean mine, Grid parent){
    this.x = x;
    this.y = y;
    this.mine = mine;
    this.neighborCount = 0;
    this.revealed = false;
    this.flagged = false;
    this.parent = parent;
  }
  
  // Default tile used for checking edges
  public Tile(){
    this.x = -1;
    this.y = -1;
    this.mine = false;
    this.neighborCount = -1;
    this.revealed = false;
    this.flagged = false;
  }
  
  public void leftClick(){
  
    if (!this.isRevealed()){
      
      this.reveal();
      
      if (this.isMine()){
        this.parent.gameOver();
      } else {
        this.parent.checkWin();
      }  
    }
  }
  
  public void rightClick(){
  
    this.flag();
  }
  
  public void draw(){
      
    strokeJoin(BEVEL);
    
      if (this.isRevealed()){
        
        fill(255);
        strokeWeight(1);
        rect(this.x * TILE_SIZE, this.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        
        if (this.neighborCount > 0){
          textAlign(CENTER);
          textSize(16);
          fill(0);
          text(neighborCount, this.x * TILE_SIZE + 12, this.y * TILE_SIZE + 18);
        }
        
        if (this.isMine()){
          image(mineImage, this.x * TILE_SIZE, this.y * TILE_SIZE);
        }
        
      } else{
        
        fill(150);
        strokeWeight(2);
        rect(this.x * TILE_SIZE, this.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        
        if (this.isFlagged()){
          image(flagImage, this.x * TILE_SIZE, this.y * TILE_SIZE);
        }
      }
      
  }
  
  public void countNeighbors(Grid grid){
    
    if (!this.isMine()){
    
      if (grid.getTile(this.x - 1, this.y - 1).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x, this.y - 1).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x + 1, this.y - 1).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x - 1, this.y).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x + 1, this.y).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x - 1, this.y + 1).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x, this.y + 1).isMine()){
        this.neighborCount++;
      }
      
      if (grid.getTile(this.x + 1, this.y + 1).isMine()){
        this.neighborCount++;
      }
    }
  }
  
  public boolean equals(Tile that){
    if (this.x == that.x && this.y == that.y){
      return true;
    }
    return false;
  }
  
  public int x(){
    return this.x;
  }
  
  public int y(){
    return this.y;
  }
  
  public void setMine(){
    this.mine = true;
    this.neighborCount = -1;
  }
  
  public boolean isMine(){
    return this.mine;
  }
  
  public int neighborCount(){
    return this.neighborCount;
  }
  
  public void reveal(){
    
    if(!this.isRevealed()){
      
      this.revealed = true;
      this.flagged = false;
      
      if (this.neighborCount == 0){
          parent.flood(this.x, this.y);
      }
    }
  }
  
  public boolean isRevealed(){
    return this.revealed;
  }
  
  public void flag(){
    if (!this.isRevealed()){
      this.flagged = !this.isFlagged();
    }
  }
  
  public boolean isFlagged(){
    return this.flagged;
  }
  
  public Grid getParent(){
    return this.parent;
  }
}
