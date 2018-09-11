package yukecm.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlReader{
    private String	filePath;
    private ConfigReader config;
    
	
	public XmlReader(String filePath, ConfigReader config){
		this.filePath = filePath;
		this.config = config;
	}
	
	public void parse() throws ParserConfigurationException, SAXException, IOException {
		BufferedReader reader = null;	
		InputStream inputStream = null;
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		try {
			if (new File(filePath).exists())
				inputStream = new FileInputStream(new File(filePath).getAbsolutePath());
			else
				inputStream = XmlReader.class.getClassLoader().getResourceAsStream(filePath);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			parser.parse(new InputSource(reader), config);
		//reader = new BufferedReader(new InputStreamReader(inputStream, "Unicode"));
		//parser.parse(new InputSource(reader), config);
		}
		finally {
			if (reader != null) 
				reader.close();
			if(inputStream != null)
				inputStream.close();
		}
	}
}
