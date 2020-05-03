
/*Server file to start the socket connection once the UI on*/
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class SystemServer extends Thread {
	/*Record Server logs*/
	static Logger serverLogger = Logger.getLogger(UserInterface.class);

	public void run(){};
	/*Simple XML Parser*/
	 static Patient xmlParse(String xmlData){ //takes File outputs patient object
	        String pid, a, m, ts, in, nc, dm, b, bq, i, ct, rid, sip, rip; //declare variables
	        pid=a=m=ts=in=nc=dm=b=bq=i=ct=rid=sip=rip=""; //initialize variables
	      
	        
	        pid = getBetween(xmlData, "<patient patientID=\"", "\">");
	        a = getBetween(xmlData, "<age>", "</age>");
	        m = getBetween(xmlData, "<menopause>", "</menopause>");
	        ts = getBetween(xmlData, "<tumor-size>", "</tumor-size>");
	        in = getBetween(xmlData, "<inv-nodes>", "</inv-nodes>");
	        nc = getBetween(xmlData, "<node-caps>", "</node-caps>");
	        dm = getBetween(xmlData, "<deg-malig>", "</deg-malig>");
	        b = getBetween(xmlData, "<breast>", "</breast>");
	        bq = getBetween(xmlData, "<breast-quad>", "</breast-quad>");
	        i = getBetween(xmlData, "<irradiat>", "</irradiat>");
	        ct = getBetween(xmlData, "<Class>", "</Class>");
	        rid = getBetween(xmlData, "<request requestID=\"", "\"");
	        sip = getBetween(xmlData, "senderIPAddress=\"", "\"");
	        rip = getBetween(xmlData, "receiverIPAddress=\"", "\"");

	        Patient patient = new Patient(pid, a, m, ts, in, nc, dm, b, bq, i, ct, rid, sip, rip); //create patient using constructor
	        return patient;
	}
	 static Document convertStringToXMLDocument(String xmlString) 
	    {
		     System.out.print(xmlString);
		     xmlString = xmlString.replace("\n", "");
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = null;
	        try
	        {
	            builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
	            return doc;
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	
	  static String[] XMLArray(String xmlinput)
	    {
	    	String lines[] = xmlinput.split("\\r?\\n");

	    	
			return lines;
	    	
	    }
	    static String getBetween(String input, String starttoken, String endtoken)
	    {
	        String output = "";
	        String regexString = starttoken + "(.*?)" + endtoken; 
	        Pattern pattern = Pattern.compile(regexString);
	        Matcher matcher = pattern.matcher(input);
	        if (matcher.find()) {
	             output = matcher.group(1); 
	            }
	            return output;
	    }
	    static String generateResult()
	    {
	    	Random r = new Random();
	    	int random = r.nextInt(1000);
	    	if(random%2 == 1)
	    	{
	    		return "recurrence-events";
	    	}
	    	else
	    	{
	    		return "no-recurrence-events";
	    	}
	    	
	    }
	    public static  String ResponseText(Patient newpatient,String result)
   	 {
               String outputtext = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";
               outputtext += "	<response requestID=\"" +newpatient.requestID+"\"";
               outputtext += " \n		responseID=\"Group8\" \n		senderIPAddress=\""+newpatient.senderIPAddress+"\"";
               outputtext += " \n		receiverIPAddress=\""+newpatient.receiverIPAddress+"\" \n		xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"";
               outputtext += " \n		xsi:noNamespaceSchemaLocation=\"response.xsd\">\n	<diagnosis patientID=\""+newpatient.patientid+"\">";
               outputtext += "  \n	<opinion>"+result+"</opinion>\n</diagnosis>\n</response>"; 
	    	return outputtext;
   		 
   	 }
    public static void main(String[] args) throws IOException {
    	
    	 int port = 2020;
    	 try (ServerSocket serverSocket = new ServerSocket(port)) {
    		 serverLogger.debug("Server is listening on port " + port);
             while (true) {
                 Socket socket = serverSocket.accept();
                 InputStream input = socket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(input));                 
                 OutputStream output = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(output, true);
                 String text="";
                 String outputtext = "";
                	do
                		{
                		text = reader.readLine();
               		    serverLogger.debug("server with port 2020 is receving request: (String)" + text );

                		outputtext += "\n" + text;
                        if (text.contains("</request>"))
    	   	               {
                            Patient newpatient = xmlParse(outputtext);
                            Document doc = convertStringToXMLDocument(outputtext);
                            System.out.println(doc.getFirstChild().getNodeName());
                   		    serverLogger.debug("Stub Diagnoser in port 2020 is analyzing.");
                            String result = generateResult();
                             outputtext = ResponseText(newpatient,result);
                             String[] responsexml = XMLArray(outputtext);
                             //writer.println(outputtext);
                    		    serverLogger.debug("server with port 2020 is providing response");
                             for(int t =0;t<responsexml.length;t++)
                             {                            	
                                 writer.println(responsexml[t]);
                             }
    	   	               }                      
                		} while (!text.trim().equals("</request>"));               
                 socket.close();
     		    serverLogger.debug("server 2020:Socket connection is closed");

             }

         } catch (IOException ex) {
             System.out.println("Server exception: " + ex.getMessage());
             serverLogger.error("Server exception: " + ex.getMessage());
             ex.printStackTrace();
         }
    }
}