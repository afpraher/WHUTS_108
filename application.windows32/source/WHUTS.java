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

public class WHUTS extends PApplet {

/*
Drag mouse to rotate
Scroll to zoom in/out
Click to continue to next stage
*/

float camRX = 0;
float camRY = 0;
float camD = 500;

int c_unfolding = color(255,140,0);
int c_tiling_x0 = color(50,205,50);
int c_tiling_x1 = color(0,191,255);
int c_tiling_z = color(186,85,211);
int c_tiling_y = color(178,34,34);

int stage = 0;

public void setup() {
  
  //noLoop();
}

public void draw() {
  background(168);
  camera(camD*sin(camRX) + width/2, camD*cos(camRY) + height/2, camD*cos(camRX), width/2, height/2, 0, 0, 1, 0);
  
  translate(width/2, height/2, 0);
  switch(stage) {
    case 0:
      unfolding(c_unfolding);
      break;
    case 1:
      tiling_x0(c_unfolding, c_tiling_x0);
      break;
    case 2:
      tiling_x1(c_unfolding, c_tiling_x0, c_tiling_x1);
      break;
    case 3:
      tiling_z(c_unfolding, c_tiling_x0, c_tiling_x1, c_tiling_z);
      break;
    case 4:
      tiling_y(c_unfolding, c_tiling_x0, c_tiling_x1, c_tiling_z, c_tiling_y);
      break;
  }
}

public void mouseDragged() {
  camRX += (pmouseX - mouseX)*0.01f;
  camRY = constrain(camRY+(pmouseY - mouseY)*0.01f, -PI, PI);
}

public void mouseWheel(MouseEvent event) {
  float c = event.getCount();
  camD += c*50;
}

public void mouseClicked() {
  stage = (stage+1)%5;
}

public void cube(float tx, float ty, float tz) {
  push();
  translate(tx*100, -tz*100, -ty*100);
  box(80);
  pop();
}

public void unfolding(int c) {
  beginShape();
  push();
  fill(c);
  cube(0, 0, 0);
  cube(0, -1, 0);
  cube(0, 0, 1);
  cube(1, 0, 1);
  cube(0, 1, 1);
  cube(0, 2, 1);
  cube(0, 2, 2);
  cube(-1, 2, 2);
  pop();
  endShape();
}

public void tiling_x0(int c1, int c2) {
  beginShape();
  unfolding(c1);
  translate(100, 0, -100);
  unfolding(c2);
  endShape();
}

public void tiling_x1(int c1, int c2, int c3) {
  beginShape();
  tiling_x0(c1, c2);
  translate(100, 0, -100);
  tiling_x0(c3, c3);
  translate(-500, 0, 500);
  tiling_x0(c3, c3);
  endShape();
}

public void tiling_z(int c1, int c2, int c3, int c4) {
  beginShape();
  tiling_x1(c1, c2, c3);
  translate(100, -200, -200);
  tiling_x1(c4, c4, c4);
  translate(100, 400, 100);
  tiling_x1(c4, c4, c4);
}

public void tiling_y(int c1, int c2, int c3, int c4, int c5) {
  beginShape();
  tiling_z(c1, c2, c3, c4);
  translate(-100, -200, -400);
  tiling_z(c5, c5, c5, c5);
  translate(500, -200, 200);
  tiling_z(c5, c5, c5, c5);
  endShape();
}

  public void settings() {  size(500, 500, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "WHUTS" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
