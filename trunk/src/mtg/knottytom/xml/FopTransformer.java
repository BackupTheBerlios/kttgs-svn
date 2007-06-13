/*
 * FopTransformer.java
 *
 * Created on 4. Mai 2007, 21:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package mtg.knottytom.xml;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import noNamespace.CrosspointDocument;
import noNamespace.CrosspointsDocument;
import noNamespace.CrosspointDocument.Crosspoint;
import noNamespace.TrackInfoDocument.TrackInfo;
import noNamespace.GeneralDocument;
import noNamespace.TourGuideDocument;
import noNamespace.TrackInfoDocument;
import org.apache.xmlbeans.*;
import org.w3c.dom.Node;

/**
 *
 * @author tom
 */
public class FopTransformer {
    
    private TourGuideDocument doc;
    private String fileName;
    
    
    private boolean withAnecdotes = true;
    private boolean withMap = false;
    private StringBuffer pdfXml;
    
    public static void main(String[] argv) {
        System.out.println("File: [" + argv[0] +"]");
        FopTransformer ft = new FopTransformer(argv[0]);
        if (ft.transform()) {
            if(argv[1] != null) {
                ft.saveToFile(argv[1]);
            }
            System.exit(0);
        } else {
            System.exit(1);
        }
        
    }
    
    /** Creates a new instance of FopTransformer */
    public FopTransformer(String file) {
        fileName = file;
        pdfXml = new StringBuffer();
    }
    
    public void saveToFile(String file) {
        File f = new File(file);
        FileWriter fw;
        try {
            fw = new FileWriter(f);
            fw.write(pdfXml.toString());
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   
    public boolean transform() {
        File file;
        boolean ret = false;
        
        try {
            file = new File(fileName);
            XmlOptions parseOptionsObj = new XmlOptions();
            parseOptionsObj.setLoadLineNumbers();
            parseOptionsObj.setLoadLineNumbers(XmlOptions.LOAD_LINE_NUMBERS_END_ELEMENT);
            doc = TourGuideDocument.Factory.parse(file, parseOptionsObj);
            ret = true;
        } catch (XmlException e) {
            System.out.println("Error line: " + e.getError().getLine());
            System.out.println("Error column: " + e.getError().getColumn());
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // doc = null;
        }
        // System.err.println("transform(): " + doc);
        
        pdfXml.append("<pdf>\n");
        // make overview page
        pdfXml.append("   <overview>\n");
        transformOverview(doc.getTourGuide().getGeneral());
        pdfXml.append("   </overview>\n");
        
        // make way points
        pdfXml.append("   <crosspoints>\n");
        transformCrosspoints(doc.getTourGuide().getCrosspoints());
        pdfXml.append("   </crosspoints>\n");
        
        // may way point descs
        pdfXml.append("   <descs>\n");
        transformDescs(doc.getTourGuide().getCrosspoints());
        pdfXml.append("   </descs>\n");
        
        pdfXml.append("</pdf>\n");
        // System.out.println("pdfXml: \n" + pdfXml);
        return ret;
    }
    
    
    private void transformOverview(GeneralDocument.General general) {
        String indent = "      ";
        
        String dataOne;
        String dataTwo;
        
        pdfXml.append(indent + "<data name=\"id\">[NONE]</data>\n");
        
        dataOne = general.getDistance().getUnit();
        dataTwo = ""+general.getDistance().newCursor().getTextValue();
        pdfXml.append(indent + "<data name=\"distance\">"+ dataTwo + dataOne +"</data>\n");
        
        dataOne = ""+general.getName().newCursor().getTextValue();
        pdfXml.append(indent + "<data name=\"name\">"+ dataOne +"</data>\n");
        
        dataOne = general.getDuration();
        pdfXml.append(indent + "<data name=\"duration\">"+ dataOne +"</data>\n");
        
        dataOne = general.getTotalUphill();
        pdfXml.append(indent + "<data name=\"total-uphill\">"+ dataOne +"</data>\n");
        
        String desc = general.getDesc().toString();
        pdfXml.append(indent + "<desc>\n");
        pdfXml.append(indent + indent + desc);
        pdfXml.append(indent + "</desc>\n");
    }
    
    private void transformCrosspoints(CrosspointsDocument.Crosspoints crosspoints) {
        String indent = "      ";
        Crosspoint cp;
        TrackInfo ti;
        
        TrackInfo tis[] = crosspoints.getTrackInfoArray();
        Crosspoint cps[] = crosspoints.getCrosspointArray();
        int points = cps.length;
        int lastTrackInfo = points - 1 - 1; // we have n-1 trackinfo tags!
        int totalDist = 0;
        String marks = "";
        String pavement = "";
        
        System.out.println("points: " + points);
        
        for(int i=0; i < points; i++) {
            pdfXml.append(indent + "<crosspoint>\n");
            cp = cps[i];
            
            String dist = cp.getDistance();
            String direction = cp.getDirection().toString();
            String elevation = cp.getElevation();
            
            // track info
            marks = "";
            pavement = "";
            if(i > lastTrackInfo) {
                // nothing to do here
            } else {
                ti = tis[i];
                pavement = ti.getPavement().toString();
                marks += ti.getEatndrinkArray().length > 0 ? "E" : "";
                marks += ti.getPoiArray().length > 0 ? "P" : "";
                marks += ti.getAnecdoteArray().length > 0 ? "A" : "";
            }
            
            pdfXml.append(indent + indent + "<cpdata name=\"num\">"+i+"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"dist\">"+dist+"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"total_dist\">"+totalDist+"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"direction\">"+direction+"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"elevation\">"+elevation+"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"pavement\">"+ pavement +"</cpdata>\n");
            pdfXml.append(indent + indent + "<cpdata name=\"marks\">"+ marks +"</cpdata>\n");
            pdfXml.append(indent + "</crosspoint>\n");
            
            totalDist += new Integer(dist);
        }
    }
    
    private void transformDescs(CrosspointsDocument.Crosspoints crosspoints) {
        String indent = "      ";
        Crosspoint cp;
        TrackInfo ti;
        String txt;
        
        TrackInfo tis[] = crosspoints.getTrackInfoArray();
        Crosspoint cps[] = crosspoints.getCrosspointArray();
        int points = cps.length;
        
        for(int i=0; i < points; i++) {
            pdfXml.append(indent + "<info num=\""+i+"\">\n");
            cp = cps[i];
            txt = cp.getDesc().toString(); // .newCursor().getTextValue();
            pdfXml.append(indent + indent + txt);
            pdfXml.append(indent + "</info>\n");
        }
        
        
    }
    
    
}
