public class Grid{

  private int cols;
  private int rows;
  private int mineCount;
  private Tile[][] tiles;
  private Tile[] mines;
  
  public Grid(int cols, int rows, int mineCount){
    this.cols = cols;
    this.rows = rows;
    this.mineCount = mineCount;
    this.tiles = new Tile[cols][rows];
    this.mines = new Tile[mineCount];
    

  }
  
  public void update(){
    
    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        
        this.tiles[x][y].draw();
        
      }
    }
    
    if (win){
      stroke(20);
      fill(50, 0, 200);
      rect(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
      
      textSize(48);
      fill(0);
      text("YOU WIN", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 3);
    }
  
    if (lose){
      stroke(20);
      fill(200, 0, 50);
      rect(SCREEN_WIDTH / 4, SCREEN_HEIGHT / 4, SCREEN_WIDTH / 2, SCREEN_HEIGHT / 6);
      
      textSize(48);
      fill(0);
      text("YOU LOSE", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 3);
    }
    
  }
  
  public void gameOver(){
  
    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        this.tiles[x][y].reveal();
      }
    }
    
    lose = true;
  }
  
  public void checkWin(){
  
    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        if(!this.tiles[x][y].isRevealed() && !this.tiles[x][y].isMine()){
          return;
        }
      }
    }
    
    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        this.tiles[x][y].reveal();
      }
    }
    
    win = true;
  }
  
  public void flood(int x, int y){
  
    if(this.getTile(x, y).neighborCount == 0){
    
      this.getTile(x - 1, y - 1).reveal();
      
      this.getTile(x, y - 1).reveal();  
      
      this.getTile(x + 1, y - 1).reveal();

      this.getTile(x - 1, y).reveal();

      this.getTile(x + 1, y).reveal();
 
      this.getTile(x - 1, y + 1).reveal();

      this.getTile(x, y + 1).reveal();

      this.getTile(x + 1, y + 1).reveal();

    }
    
  }
  
  public void generateTiles(){

    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        this.tiles[x][y] = new Tile(x, y, false, this);
      }
    }
    
    IntList mineChoices = new IntList();
    for(int i = 0; i < this.cols * this.rows; i++){
      mineChoices.append(i);
    }
    
    IntList chosenMines = new IntList();
    for(int i = 0; i < this.mineCount; i++){
      chosenMines.append(mineChoices.remove(floor(random(mineChoices.size()))));
    }
    
    while (chosenMines.size() > 0){
      int mineIndex = chosenMines.pop();
      Tile newMine = this.tiles[mineIndex % cols][mineIndex / cols];
      newMine.setMine();
      this.mines[chosenMines.size()] = newMine;
    }
    
    for(int x = 0; x < this.cols; x++){
      for(int y = 0; y < this.rows; y++){
        this.tiles[x][y].countNeighbors(this);
      }
    }
    
    
  }
  
  public int getCols(){
    return this.cols;
  }
  
  public int getRows(){
    return this.rows;
  }
  
  public int mineCount(){
    return this.mineCount;
  }
  
  public Tile getTile(int x, int y){
    
    if (x < 0 || x >= this.cols || y < 0 || y >= this.rows){
      return new Tile();
    }
    
    return this.tiles[x][y];
  }
  
  public Tile[] getMines(){
    return this.mines;
  }
}
