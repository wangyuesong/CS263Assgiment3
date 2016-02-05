package login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.offbytwo.jenkins.JenkinsServer;

/**
 * @Project: base
 * @Title: JobBuilder.java
 * @Package login
 * @Description: TODO
 * @author YuesongWang
 * @date Feb 3, 2016 11:01:26 PM
 * @version V1.0
 */
public class JobBuilder {
    public static void main(String[] args) {
        JenkinsServer jenkins = null;
        try {
            jenkins = new JenkinsServer(new URI("http://130.211.189.253"), "", "");
            jenkins.createJob("ProgramaticJob", inputStream2String(new FileInputStream(new File("config.xml"))));
        } catch (URISyntaxException | IOException  e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//
//        try {
//
//            // jenkins.createJob(jobName, jobXml);
//            Map<String, Job> jobs = jenkins.getJobs();
//            for (String s : jobs.keySet()) {
//                // System.out.println(s);
//                System.out.println(jobs.get(s).details().details());
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

    }

    public static String inputStream2String(InputStream is) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null){
          buffer.append(line);
        }
        return buffer.toString();
     }

    public void createDom() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        String filePath = "maven_template.xml";
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(filePath);

        Node projectUrl = doc.getElementsByTagName("projectUrl").item(0);
        projectUrl.setTextContent("https://github.com/YuesongWang/TestJenkins/");

        Node url = doc.getElementsByTagName("url").item(0);
        url.setTextContent("git@github.com:YuesongWang/TestJenkins.git");

        Node credentialsId = doc.getElementsByTagName("credentialsId").item(0);
        credentialsId.setTextContent("c56c7063-12a8-4a0f-b772-83b16732f6bf");

        Node targets = doc.getElementsByTagName("targets").item(0);
        targets.setTextContent("clean install");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("config.xml"));
        transformer.transform(source, result);

    }
}
