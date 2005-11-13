package mtg.knottytom.profile;

import mtg.knottytom.profile.ProfileParser;
import mtg.knottytom.profile.ProfileImageGenerator;

import java.io.IOException;
import java.util.HashMap;
// import java.util.Vector;
import java.util.TreeMap;

public class ProfileImageMain 
{
   
   
	public static void main(String[] args) {
		System.out.println(args[0]);
      ProfileParser pp = new ProfileParser(args[0]);
      TreeMap<Integer, ProfileSection> sections = new TreeMap<Integer, ProfileSection>();
      HashMap<String,String> config = new HashMap<String,String>();
      
      try {
         pp.parse(sections, config);
      } catch(IOException e) {
         e.printStackTrace();
         System.exit(1);
      }
      
      ProfileImageGenerator pig = new ProfileImageGenerator(sections);
      try {
         pig.genImage(args[1]);
      } catch (IOException e) {
         e.printStackTrace();
      }
      
	} // -- main()
   
} // -- end class ProfileImageMain

