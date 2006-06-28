package mtg.knottytom.profile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.TreeMap;


/**
 *  Description of the Class
 *
 *@author     tom
 *@created    3. August 2005
 */
public class ProfileParser {

   private String fname;


   /**
    *  Constructor for the ProfileParser object
    *
    *@param  fname  Description of the Parameter
    */
   public ProfileParser(String fname) {
      this.fname = fname;
   }


   /**
    *  Description of the Method
    */
   public void parse(TreeMap<Integer,ProfileSection> sections, HashMap<String, String> config) throws IOException {
      String key;
      String value;
      Properties properties = new Properties();
      properties.load(new FileInputStream(fname));
      Enumeration keys = properties.keys();
      while(keys.hasMoreElements()) {
         key = (String) keys.nextElement();
         value = properties.getProperty(key);
         if(key.startsWith("section")) {
            handleSection(key, value, sections);
         }
         if(key.startsWith("conf")) {
            handleConf(key, value, config);
         }
      }
   }
   
   private void handleSection(String key, String val, TreeMap<Integer, ProfileSection> secs) {
      // System.out.println("Handle sections: " + val);
      // System.out.println("Key: " + key);
      String spl[];
      String spl2[];
      boolean isCrossPoint = true;
      boolean addToLegend  = true;
      
      spl = val.split(",");
      // System.out.println("0:" + spl[0]);
      // System.out.println("1:" + spl[1]);
      // System.out.println("2:" + spl[2]);
      int dist = Integer.parseInt(spl[1]);
      int height = Integer.parseInt(spl[2]);
      spl2 = key.split("-");
      // System.out.println("Spl2[1]: " + spl2[1]);
      int pos = Integer.parseInt(spl2[1]);
      // System.out.println("Pos: " + pos);
      if(spl[3].equals("0")) {
    	  isCrossPoint = false;
      }
      if(spl[4].equals("0")) {
    	  addToLegend =false;
      }
      secs.put(pos, new ProfileSection(spl[0], dist, height, isCrossPoint, addToLegend));
   }
   
   private void handleConf(String key, String val, HashMap<String,String> conf) {
      // System.out.println("Handle conf: " + val);
      conf.put(key, val);
   }

}
// -- end class ProfileParser

