package mtg.knottytom.profile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

import javax.imageio.ImageIO;


/**
 *  Description of the Class
 *
 *@author     tom
 *@created    3. August 2005
 */
public class ProfileImageGenerator {
   
   private TreeMap profileSections;

   private static int borders = 20;
   private static int width = 600;
   private static int height = 300;
   private static int legendHeight = 50;
   
   private static int xOri = 0 + borders;
   private static int yOri = height - borders - legendHeight;
   private static int xMax = width - borders;
   private static int yMax = height - 2*borders - legendHeight;
   
   private static int realHeight = 2500;
   private static int realWidth = 0;
   
   private static Stroke strokeRoad;
   private static Stroke strokeForrestRoad;
   private static Stroke strokeSingleTrail;
   private static Stroke strokeMarkLines;
   
   
   public ProfileImageGenerator(TreeMap profileSections) {
      this.profileSections = profileSections;
   }
   
   public void genImage(String fName) throws IOException {
      BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2d = img.createGraphics();
      int totalDist;
      
      calculateStrokes(g2d);
      
      //System.out.println("xOri: " + xOri);
      //System.out.println("yOri: " + yOri);
      
      //System.out.println("xMax: " + xMax);
      // System.out.println("yMax: " + yMax);
      
      g2d.setPaint(Color.BLACK);
      // g2d.drawLine(xOri, yOri, xMax, yOri - yMax);
      g2d.drawRect(borders, borders, width-2*borders, height-2*borders);
      g2d.drawRect(borders, borders, width-2*borders, height-2*borders-legendHeight);
      
      drawLegend(g2d);
      totalDist = calculateDistances();
      realWidth = totalDist;
      mapToSystem();
      plot(g2d);
      plotHeightLines(g2d);
      
      
      ImageIO.write(img, "png", new File(fName));
      
   }
   
   private void drawLegend(Graphics2D g2d) {
      g2d.drawString("Road", 30, 245);
      g2d.drawString("Forrest Road", 30, 260);
      g2d.drawString("Single Trail", 30, 275);
      g2d.setStroke(strokeRoad);
      g2d.drawLine(120,245,200,245);
      g2d.setStroke(strokeForrestRoad);
      g2d.drawLine(120,260,200,260);
      g2d.setStroke(strokeSingleTrail);
      g2d.drawLine(120,275,200,275);
      g2d.setStroke(strokeRoad);
   }
   
   private void calculateStrokes(Graphics2D g2d) {
      strokeRoad = g2d.getStroke();
      float[] dashPattern1 = { 5, 5};
      strokeForrestRoad =
         new BasicStroke(1, BasicStroke.CAP_BUTT,
                         BasicStroke.JOIN_MITER, 10,
                         dashPattern1, 0);
      float[] dashPattern2 = { 2, 3};
      strokeSingleTrail =
         new BasicStroke(1, BasicStroke.CAP_BUTT,
                         BasicStroke.JOIN_MITER, 10,
                         dashPattern2, 0);
                         
      float[] dashPattern3 = { 2, 6};
      strokeMarkLines =
         new BasicStroke(1, BasicStroke.CAP_BUTT,
                         BasicStroke.JOIN_MITER, 10,
                         dashPattern3, 0);
   }
   
   private int calculateDistances() {
      Iterator itr;
      ProfileSection ps;
      int totalDistance = 0;
      
      itr = profileSections.values().iterator();
      
      // System.out.println("Size: " + profileSections.size());
      
      while(itr.hasNext()) {
         ps = (ProfileSection) itr.next();
         // System.out.println("ps: " + ps.getDist());
         totalDistance = totalDistance + ps.getDist();
         // System.out.println("total:" + totalDistance);
         ps.setDist(totalDistance);
      }
      
      return(totalDistance);
         
   }
   
   private void mapToSystem() {
      Iterator itr;
      ProfileSection ps;
      int height;
      int dist;
      
      itr = profileSections.values().iterator();
      
      while(itr.hasNext()) {
         ps = (ProfileSection) itr.next();
         height = ps.getHeight();
         dist = ps.getDist();
         
         // System.out.println("dist: " + dist + ", height: "+height);
         
         if(height != 0) {
            height = (yMax * height) / realHeight;
            height = yOri - height;
         } else {
            height = yOri;
         }
         ps.setHeight(height);
         
         if(dist != 0) {
            dist = (xMax * dist) / realWidth;
         } else {
            dist = xOri;
         }
         ps.setDist(dist);
         // System.out.println("dist: " + dist + ", height: "+height);
      }
   }
   
   private void plot(Graphics2D g2d) {
      Iterator itr;
      ProfileSection ps;
      boolean firstRun = true;
      int xStart=0, yStart=0, xTo, yTo;
      String type = "";
      int run = 0;
      boolean lastWasCrossPoint = true;
      
      itr = profileSections.values().iterator();
      
      while(itr.hasNext()) {
         ps = (ProfileSection) itr.next();
         g2d.setStroke(strokeRoad);
         run++;
         if(firstRun) {
            xStart = ps.getDist();
            yStart = ps.getHeight();
            firstRun = false;
            infoBox(g2d, xStart, borders, run);
         } else {
            type = ps.getType();
            
            if(type.equals("road")) {
               g2d.setStroke(strokeRoad);
            }
            if(type.equals("forrest.road")) {
               g2d.setStroke(strokeForrestRoad);
            }
            if(type.equals("trail")) {
               g2d.setStroke(strokeSingleTrail);
            }
            xTo = ps.getDist();
            yTo = ps.getHeight();
            // System.out.println("("+xStart+", "+yStart+", "+xTo+", "+yTo+")");
            g2d.drawLine(xStart, yStart, xTo, yTo);
            g2d.setStroke(strokeMarkLines);
            if(lastWasCrossPoint) {
               g2d.drawLine(xStart, yOri, xStart, borders);
            }
            lastWasCrossPoint = ps.isCrossPoint();
            if(ps.isCrossPoint()) {
               infoBox(g2d, xTo, borders, run);
            } else {
               // get the numbering correct.
               run--;
            }
            xStart = xTo;
            yStart = yTo;
         }
      }
   }
   
   public void plotHeightLines(Graphics2D g2d) {
      int lineOne = 1000;
      int lineTwo = 2000;
      int heightOne = 0;
      int heightTwo = 0;
      
      heightOne = yOri - (yMax * lineOne) / realHeight;;
      heightTwo = yOri - (yMax * lineTwo) / realHeight;
      
      g2d.setStroke(strokeMarkLines);
      g2d.drawLine(xOri, heightOne, xMax, heightOne);
      g2d.drawLine(xOri, heightTwo, xMax, heightTwo);
      g2d.drawString("1000m", 25, heightOne-5);
      g2d.drawString("2000m", 25, heightTwo-5);
   }
   
   public void infoBox(Graphics2D g2d, int x, int y, int num) {
      
      int width = 18;
      int height = 15;
      String numString;
      
      g2d.setStroke(strokeRoad);
      g2d.drawRect(x, y, width, height);
      if(num < 10) {
         numString = "0" + num;
      } else {
         numString = "" + num;
      }
      g2d.drawString(numString, x+2, y+12);
      
   }


   
}
// -- end class ProfileImageGenerator

