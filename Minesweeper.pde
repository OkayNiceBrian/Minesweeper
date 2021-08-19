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

void setup(){
  
  size(1000, 1000);
  
  frameRate(60);
  
  m = new Menu();

  loadImages();
  

}

void draw(){
  
  switch(mode){
  case "menu": m.update(); break;
  case "grid": g.update(); break;
  }
  
}

void mousePressed(){

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

void keyPressed(){
  if (key == ' '){
    win = false;
    lose = false;
    mode = "menu";
  }
}

void loadImages(){

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
    
  mineImage25 = loadImage("mine 25.jpg");
  mineImage50 = loadImage("mine 50.jpg");
  mineImage100 = loadImage("mine 100.jpg");
  
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
