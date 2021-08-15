import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Minesweeper extends PApplet {

public static final int SCREEN_WIDTH = 1000;
public static final int SCREEN_HEIGHT = 1000;

public int TILE_SIZE;

public boolean win = false;
public boolean lose = false;

public String mode = "menu";

public PImage[][] flagImageList = new PImage[5][3];
public PImage[] mineImageList = new PImage[3];

public PImage canadaFlagImage25;
public PImage canadaFlagImage50;
public PImage canadaFlagImage100;
public PImage hondurasFlagImage100;
public PImage hondurasFlagImage50;
public PImage hondurasFlagImage25;
public PImage irelandFlagImage25;
public PImage irelandFlagImage50;
public PImage irelandFlagImage100;
public PImage swedenFlagImage25;
public PImage swedenFlagImage50;
public PImage swedenFlagImage100;
public PImage triangleFlagImage25;
public PImage triangleFlagImage50;
public PImage triangleFlagImage100;

public PImage mineImage25;
public PImage mineImage50;
public PImage mineImage100;

public PImage flagImage;
public PImage mineImage;

Menu m;
Grid g;

public void setup(){
  
  
  
  frameRate(60);
  
  m = new Menu();

  loadImages();
  

}

public void draw(){
  
  switch(mode){
  case "menu": m.update(); break;
  case "grid": g.update(); break;
  }
  
}

public void mousePressed(){

  switch(mode){
  case "menu": {
    
    if(mouseButton == LEFT){
      m.leftClick(mouseX, mouseY);
    }
    
    break;
  }
  case "grid": {
    int tileCol = mouseX / TILE_SIZE;
    int tileRow = mouseY / TILE_SIZE;
    
    if (mouseButton == LEFT){
      g.getTile(tileCol, tileRow).leftClick();
    }
    
    if (mouseButton == RIGHT){
      g.getTile(tileCol, tileRow).rightClick();
    }
    
    break;
  }
  }
}

public void keyPressed(){
  if (key == ' '){
    win = false;
    lose = false;
    mode = "menu";
  }
}

public void loadImages(){

  canadaFlagImage25 = loadImage("canada flag 25.png");
  canadaFlagImage50 = loadImage("canada flag 50.png");
  canadaFlagImage100 = loadImage("canada flag 100.png");
  hondurasFlagImage25 = loadImage("honduras flag 25.png");
  hondurasFlagImage50 = loadImage("honduras flag 50.png");
  hondurasFlagImage100 = loadImage("honduras flag 100.png");
  irelandFlagImage25 = loadImage("ireland flag 25.png");
  irelandFlagImage50 = loadImage("ireland flag 50.png");
  irelandFlagImage100 = loadImage("ireland flag 100.png");
  swedenFlagImage25 = loadImage("sweden flag 25.png");
  swedenFlagImage50 = loadImage("sweden flag 50.png");
  swedenFlagImage100 = loadImage("sweden flag 100.png");
  triangleFlagImage25 = loadImage("triangle flag 25.png");
  triangleFlagImage50 = loadImage("triangle flag 50.png");
  triangleFlagImage100 = loadImage("triangle flag 100.png");
    
  mineImage25 = loadImage("me 25.jpg");
  mineImage50 = loadImage("me 50.jpg");
  mineImage100 = loadImage("me 100.jpg");
  
  flagImageList[0][0] = canadaFlagImage100;
  flagImageList[0][1] = canadaFlagImage50;
  flagImageList[0][2] = canadaFlagImage25;
  flagImageList[1][0] = hondurasFlagImage100;
  flagImageList[1][1] = hondurasFlagImage50;
  flagImageList[1][2] = hondurasFlagImage25;
  flagImageList[2][0] = irelandFlagImage100;
  flagImageList[2][1] = irelandFlagImage50;
  flagImageList[2][2] = irelandFlagImage25;
  flagImageList[3][0] = swedenFlagImage100;
  flagImageList[3][1] = swedenFlagImage50;
  flagImageList[3][2] = swedenFlagImage25;
  flagImageList[4][0] = triangleFlagImage100;
  flagImageList[4][1] = triangleFlagImage50;
  flagImageList[4][2] = triangleFlagImage25;
  
  mineImageList[0] = mineImage100;
  mineImageList[1] = mineImage50;
  mineImageList[2] = mineImage25;
}
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
public static final int TENBYTEN = 0;
public static final int TWENTYBYTWENTY = 1;
public static final int FORTYBYFORTY = 2;

public static final int CANADA = 0;
public static final int IRELAND = 1;
public static final int HONDURAS = 2;
public static final int SWEDEN = 3;
public static final int TRANS = 4;

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
    case TRANS: rect(SCREEN_WIDTH * 5 / 6 - 50, SCREEN_HEIGHT * 3 / 4 - 50, 100, 75); break;
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
      this.flagChoice = TRANS;
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
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Minesweeper" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
