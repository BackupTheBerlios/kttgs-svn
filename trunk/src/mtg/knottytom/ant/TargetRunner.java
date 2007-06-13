/*
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package mtg.knottytom.ant;
//import console.*;

import java.io.*;
// import java.lang.annotation.Target;
//import javax.swing.*;
import org.apache.tools.ant.*;
import org.apache.tools.ant.helper.*;

//import org.gjt.sp.jedit.*;
//import org.gjt.sp.jedit.gui.*;
//import org.gjt.sp.util.*;

public class TargetRunner extends Thread {
    
    PrintStream _out = System.out;
    Project runner = new Project();
    Target runAnt = new Target();
    PrintStream _err = System.err;
    DefaultLogger _buildLogger = new DefaultLogger();
    Throwable _error = null;
    
    Target _target;
    Project _project = new Project();
    File _buildFile;
    // View _view;
    // Output _output;
    
    PrintStream _consoleOut = System.out;
    PrintStream _consoleErr = System.err;
    
    static public void main(String[] args) {
        Target __target = new Target();
        __target.setName("make");
        __target.setProject(new Project());
        //_project.addTarget(__target);
        //
        ProjectHelperImpl ph = new ProjectHelperImpl();
        Project p = new Project();
        ph.parse(p, new File("./build.xml"));
        
        TargetRunner tr = new TargetRunner(p, new File("./build.xml"));
        // tr.run();
    }
    
    
    public TargetRunner( Target target, File buildFile) {
        init( target, buildFile);
    }
    
    
    public TargetRunner( Project project, File buildFile) {
        Target target = (Target) project.getTargets().get( project.getDefaultTarget() );
        init( target, buildFile);
    }
    
    
    public void run() {
        //AntBrowserPlugin.getErrorSource().clear();
        
        //boolean useSameJvm
        //	 = jEdit.getBooleanProperty( AntBrowserPlugin.OPTION_PREFIX + "use-same-jvm" );
        boolean useSameJvm = true;
        String antCommand = "command";
        
        //if ( !useSameJvm && antCommand == null )
        //	promptForAntCommand();
        
        // useSameJvm = jEdit.getBooleanProperty( AntBrowserPlugin.OPTION_PREFIX + "use-same-jvm" );
        
        // assume we have the console open at this point since it called us.
        //Console console =
        //	(Console) _view.getDockableWindowManager().getDockableWindow( "console" );
        
        if ( useSameJvm ) {
            setOutputStreams();
            try {
                _project.addBuildListener( _buildLogger );
                
                //fireBuildStarted();
                _project.executeTarget( _target.getName() );
            } catch ( RuntimeException exc ) {
                _error = exc;
            } catch ( Error e ) {
                _error = e;
            } finally {
                // fireBuildFinished();
                resetLogging();
            }
        } else {
            String command = " -buildfile ";
            command += "\"";
            command += _buildFile.getAbsolutePath();
            command += "\"";
            command += " " + _target.getName();
            //runAntCommand( command );
        }
        //_output.commandDone();
    }
    
    
    private void setOutputStreams() {
        System.setOut( _consoleOut );
        System.setErr( _consoleErr );
    }
    
    
    private void init( Target target, File buildFile ) {
        _target = target;
        _project = _target.getProject();
        _buildFile = buildFile;
        //_view = view;
        //_output = output;
        
        //_consoleErr = new AntPrintStream( System.out, view);
        //_consoleOut = new AntPrintStream( System.out, view);
        
        // set so jikes prints emacs style errors
        // _project.setProperty( "build.compiler.emacs", "true" );
        
        configureBuildLogger();
        
        // fire it up
        this.start();
    }
    
    
        /*
        private void runAntCommand( String args )
        {
                String command = "command";
                if ( command == null || command.equals( "" ) )
                        //Log.log( Log.WARNING, this, "Please set the path to the Ant script you wish to use." );
         
                if ( command != null ) {
                        command = "\"" + command + "\"";
                        if (
                                jEdit.getBooleanProperty( AntBrowserPlugin.OPTION_PREFIX + "output-emacs" )
                                 ) {
                                command += " -emacs ";
                        }
                        command += args;
                        Console console = AntBrowserPlugin.getConsole( _view );
                        console.run( ConsolePlugin.SYSTEM_SHELL, console, command );
                }
        }
         */
    
    
    
    private void configureBuildLogger() {
        //_buildLogger.setEmacsMode(
        //	jEdit.getBooleanProperty( AntBrowserPlugin.OPTION_PREFIX + "output-emacs" )
        //	 );
        _buildLogger.setOutputPrintStream( _consoleOut );
        _buildLogger.setErrorPrintStream( _consoleErr );
        _buildLogger.setMessageOutputLevel( Project.MSG_INFO );
    }
    
    
    private void resetLogging() {
        _consoleErr.flush();
        _consoleOut.flush();
        System.setOut( _out );
        System.setErr( _err );
        // _project.removeBuildListener( _buildLogger );
    }
    
    
        /*
        private void fireBuildStarted()
        {
                BuildEvent event = new BuildEvent( _project );
                event.setMessage( "Running target: " + _target, Project.MSG_INFO );
                _buildLogger.buildStarted( event );
                _buildLogger.messageLogged( event );
        }
         
         
        private void fireBuildFinished()
        {
                BuildEvent event = new BuildEvent( _project );
                event.setException( _error );
                _buildLogger.buildFinished( event );
        }
         */
}

