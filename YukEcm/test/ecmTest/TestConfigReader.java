package ecmTest;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import yukecm.config.ConfigReader;
import yukecm.config.XmlReader;

public abstract class TestConfigReader {
	public TestConfigReader() throws ParserConfigurationException, SAXException, IOException {
		XmlReader reader = new XmlReader("config/Config.Xml",new ConfigReader());
		reader.parse();
	}
}
