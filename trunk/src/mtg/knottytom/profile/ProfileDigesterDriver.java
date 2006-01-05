package mtg.knottytom.profile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import org.apache.commons.digester.Digester;

public class ProfileDigesterDriver {
   
   public static void main( String[] args ) {

      ProfileSection ps;
      try {
         Digester digester = new Digester();
         digester.setValidating( false );

        
         digester.addObjectCreate( "tour-guide", ProfileDigesterContainer.class );

         
         digester.addObjectCreate( "tour-guide/crosspoints/crosspoint", ProfileSection.class );
         
         digester.addSetProperties( "tour-guide/crosspoints/crosspoint", "distance", "distance");
         digester.addSetProperties( "tour-guide/crosspoints/crosspoint", "elevation", "height");
         // digester.addBeanPropertySetter( "tour-guide/crossspoints/crosspoint", "title" );
         digester.addSetNext( "tour-guide/crosspoints/crosspoint", "addProfileSection" );
         
         digester.addSetProperties( "tour-guide/crosspoints/track-info", "pavement", "pavement");
         /*
         digester.addObjectCreate( "catalog/magazine", Magazine.class );
         digester.addBeanPropertySetter( "catalog/magazine/name", "name" );

         digester.addObjectCreate( "catalog/magazine/article", Article.class );
         digester.addSetProperties( "catalog/magazine/article", "page", "page" );
         digester.addBeanPropertySetter( "catalog/magazine/article/headline" ); 
         digester.addSetNext( "catalog/magazine/article", "addArticle" );

         digester.addSetNext( "catalog/magazine", "addMagazine" );
         */
         
         File input = new File( args[0] );
         ProfileDigesterContainer v = (ProfileDigesterContainer)digester.parse( input );
         
         Iterator itr = v.iterator();
         FileOutputStream fos;
         PrintStream p;
         int step = 0;
         String out = "";
         
         fos = new FileOutputStream(args[1]);
         p = new PrintStream( fos );
         while(itr.hasNext()) {
            out = "";
            ps = (ProfileSection) itr.next();
            out = out + "section-" + step + "=";
            step++;
            out = out + ps.getType() +",";
            out = out + ps.getDist() +",";
            out = out + ps.getHeight() + ",";
            if(ps.isCrossPoint()) {
               out = out + "1";
            } else {
               out = out + "0";	
            }
            // System.out.println("Out: " + out);
            p.println(out);
         }
         p.close();
      } catch( Exception exc ) {
         exc.printStackTrace();
      }
   }
}
