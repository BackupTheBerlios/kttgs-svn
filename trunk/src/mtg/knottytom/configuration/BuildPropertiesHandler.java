package mtg.knottytom.configuration;

import java.util.Properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BuildPropertiesHandler extends java.lang.Object
{
   
   String fileName = "build.properties";
   Properties props = new Properties();
   
   static final String TOUR_NAME = "tour.name";
   static final String LANG = "lang";
   
	public BuildPropertiesHandler() {
      try {
        props.load(new FileInputStream(fileName));
      } catch (IOException e) {
      }
	} // -- Constructor
   
   public void store() throws IOException {
      props.store(new FileOutputStream(fileName), null);
   }
   
   public void setTourName(String tn) {
      props.setProperty(TOUR_NAME, tn);
   }
   
   public void setLang(String l) {
      props.setProperty(LANG, l);
   }
   
} // -- end class BuildPropertiesHandler

