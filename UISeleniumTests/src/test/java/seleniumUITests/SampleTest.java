package seleniumUITests;


import org.testng.annotations.Test;



import browserImpl.BrowserImplementation;
import fakerData.FakerDataFactory;

public class SampleTest extends BrowserImplementation {

	BrowserImplementation browser;

	
	
	@Test
	public void browserLaunch() {		
		browser = new BrowserImplementation();
		browser.startApp("https://www.google.com");
		
	}
	
	
	@Test
	public void typeSomething() {
		browser = new BrowserImplementation();
		browser.startApp("http://leaftaps.com/opentaps");
		browser.wEdit().type(wElement().locateElement("id", "username"), FakerDataFactory.getFirstName());
		browser.wEdit().type(wElement().locateElement("id", "password"), FakerDataFactory.getLastName());
		}


	

}
