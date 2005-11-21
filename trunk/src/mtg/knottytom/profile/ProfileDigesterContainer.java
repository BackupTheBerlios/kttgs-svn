package mtg.knottytom.profile;

import java.util.Iterator;
import java.util.Vector;

public class ProfileDigesterContainer {
   
   Vector<ProfileSection> pss;
   String storedPavement = "road";
   
   public ProfileDigesterContainer() {
      // System.out.println("ProfileDigesterContainer()");
      pss = new Vector<ProfileSection>();
   }
   
   public void addProfileSection(ProfileSection ps) {
      // System.out.println("addProfileSection(ProfileSection ps)");
      ps.setType(storedPavement);
      pss.add(ps);
   }
   
   public Iterator iterator() {
       return pss.iterator();
   }
   
   public void setPavement(String pav) {
      // System.out.println("public void setPavement(String pav): " + pav);
      storedPavement = pav;
   }
}
