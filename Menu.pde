public static final int TENBYTEN = 0;
public static final int TWENTYBYTWENTY = 1;
public static final int FORTYBYFORTY = 2;

public static final int CANADA = 0;
public static final int IRELAND = 1;
public static final int HONDURAS = 2;
public static final int SWEDEN = 3;
public static final int TRI = 4;

public class Menu{

  private int gridSize;
  private int flagChoice;
  
  public Menu(){
    this.gridSize = TENBYTEN;
    this.flagChoice = CANADA;
  }

  public void update(){
    background(50, 0, 200);
 
    fill(255);
    textAlign(CENTER);
    textSize(72);
    text("MINESWEEPER", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 8);
    
    textSize(40);
    text("BY BRIAN JACKSON", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 5);
    
    textSize(36);
    text("BOARD SIZE:", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 3);
    
    strokeWeight(5);
    fill(255, 0, 255);
    switch(gridSize){
    case TENBYTEN: rect(SCREEN_WIDTH / 4 - 100, SCREEN_HEIGHT / 2 - 100, 200, 200); break;
    case TWENTYBYTWENTY: rect(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 100, 200, 200); break;
    case FORTYBYFORTY: rect(SCREEN_WIDTH * 3 / 4 - 100, SCREEN_HEIGHT / 2 - 100, 200, 200); break;
    }
    
    fill(255);
    text("10x10", SCREEN_WIDTH / 4, SCREEN_HEIGHT / 2);
    text("20x20", SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
    text("40x40", SCREEN_WIDTH * 3 / 4, SCREEN_HEIGHT / 2);
    
    text("CHOOSE A FLAG:", SCREEN_WIDTH / 2, SCREEN_HEIGHT * 43 / 64);
    
    fill(255, 0, 255);
    switch(flagChoice){
    case CANADA: rect(SCREEN_WIDTH / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
    case IRELAND: rect(SCREEN_WIDTH * 2 / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
    case HONDURAS: rect(SCREEN_WIDTH * 3 / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
    case SWEDEN: rect(SCREEN_WIDTH * 4 / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
    case TRI: rect(SCREEN_WIDTH * 5 / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
    }
    
    image(canadaFlagImage50, SCREEN_WIDTH / 6 - 25, SCREEN_HEIGHT * 3 / 4 - 40);
    image(hondurasFlagImage50, SCREEN_WIDTH * 2 / 6 - 25, SCREEN_HEIGHT * 3 / 4 - 40);
    image(irelandFlagImage50, SCREEN_WIDTH * 3 / 6 - 25, SCREEN_HEIGHT * 3 / 4 - 40);
    image(swedenFlagImage50, SCREEN_WIDTH * 4 / 6 - 25, SCREEN_HEIGHT * 3 / 4 - 40);
    image(triangleFlagImage50, SCREEN_WIDTH * 5 / 6 - 25, SCREEN_HEIGHT * 3 / 4 - 40);
    
    fill(0, 250, 0);
    rect(SCREEN_WIDTH / 2 - 75, SCREEN_HEIGHT * 5 / 6, 150, 100);
    
    fill(255);
    textSize(36);
    text("START", SCREEN_WIDTH / 2, SCREEN_HEIGHT * 71 / 80);
  }
  
  public void leftClick(int x, int y){
  
    // choosing grid size
    if(x <= SCREEN_WIDTH / 4 + 100 && x >= SCREEN_WIDTH / 4 - 100 && y <= SCREEN_HEIGHT / 2 + 100 && y >= SCREEN_HEIGHT / 2 - 100){
      this.gridSize = TENBYTEN;
    }
    
    if(x <= SCREEN_WIDTH / 2 + 100 && x >= SCREEN_WIDTH / 2 - 100 && y <= SCREEN_HEIGHT / 2 + 100 && y >= SCREEN_HEIGHT / 2 - 100){
      this.gridSize = TWENTYBYTWENTY;
    }
    
    if(x <= SCREEN_WIDTH * 3 / 4 + 100 && x >= SCREEN_WIDTH * 3 / 4 - 100 && y <= SCREEN_HEIGHT / 2 + 100 && y >= SCREEN_HEIGHT / 2 - 100){
      this.gridSize = FORTYBYFORTY;
    }
    
    // choosing flag
    if(x <= SCREEN_WIDTH / 6 + 50 && x >= SCREEN_WIDTH / 6 - 50 && y <= SCREEN_HEIGHT* 3 / 4 + 50 && y >= SCREEN_HEIGHT * 3 / 4 - 50){
      this.flagChoice = CANADA;
    }
    
    if(x <= SCREEN_WIDTH * 2 / 6 + 50 && x >= SCREEN_WIDTH * 2 / 6 - 50 && y <= SCREEN_HEIGHT* 3 / 4 + 50 && y >= SCREEN_HEIGHT * 3 / 4 - 50){
      this.flagChoice = IRELAND;
    }
    
    if(x <= SCREEN_WIDTH * 3 / 6 + 50 && x >= SCREEN_WIDTH * 3 / 6 - 50 && y <= SCREEN_HEIGHT* 3 / 4 + 50 && y >= SCREEN_HEIGHT * 3 / 4 - 50){
      this.flagChoice = HONDURAS;
    }
    
    if(x <= SCREEN_WIDTH * 4 / 6 + 50 && x >= SCREEN_WIDTH * 4 / 6 - 50 && y <= SCREEN_HEIGHT* 3 / 4 + 50 && y >= SCREEN_HEIGHT * 3 / 4 - 50){
      this.flagChoice = SWEDEN;
    }
    
    if(x <= SCREEN_WIDTH * 5 / 6 + 50 && x >= SCREEN_WIDTH * 5 / 6 - 50 && y <= SCREEN_HEIGHT* 3 / 4 + 50 && y >= SCREEN_HEIGHT * 3 / 4 - 50){
      this.flagChoice = TRI;
    }
    
    if (x <= SCREEN_WIDTH / 2 + 75 && x >= SCREEN_WIDTH / 2 - 75 && y <= SCREEN_HEIGHT * 5 / 6 + 100 && y >= SCREEN_HEIGHT * 5 / 6){
      
      flagImage = flagImageList[this.flagChoice][this.gridSize];
      mineImage = mineImageList[this.gridSize];
      
      mode = "grid";
      
      switch(this.gridSize){
      case TENBYTEN: g = new Grid(10, 10, 13); break;
      case TWENTYBYTWENTY: g = new Grid(20, 20, 75); break;
      case FORTYBYFORTY: g = new Grid(40, 40, 300); break;
      default: g = new Grid(10, 10, 13); break;
      }
      
      
      TILE_SIZE = SCREEN_WIDTH / g.getCols();
      
      g.generateTiles();
    }
    
  }
  
  public int gridSize(){
    return this.gridSize();
  }
}
