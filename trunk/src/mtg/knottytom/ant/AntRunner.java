/*
 * AntRunner.java
 *
 * Created on Amrch 5, 2007, 18:07
 *
 */

package mtg.knottytom.ant;

import org.apache.tools.ant.helper.ProjectHelper2;
import org.apache.tools.ant.helper.ProjectHelperImpl;
import org.apache.tools.ant.listener.AnsiColorLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import java.io.File;

public class AntRunner {

    public AntRunner() {
    }

    public void run() {
       try {
	       Project pro = new Project();
	       System.out.println("Running...");
	       ProjectHelperImpl ph = new ProjectHelperImpl();
	       ph.parse(pro, new File("./build.xml"));
	       pro.init();
	       DefaultLogger dl = new DefaultLogger();
	       // AnsiColorLogger dl = new AnsiColorLogger();
	       dl.setOutputPrintStream(System.out);
	       dl.setMessageOutputLevel(Project.MSG_WARN);
	       pro.addBuildListener(dl);
	       pro.executeTarget("gen-tour-html");
       } catch(Exception e) {
	       e.printStackTrace();
       }
    
    }
}