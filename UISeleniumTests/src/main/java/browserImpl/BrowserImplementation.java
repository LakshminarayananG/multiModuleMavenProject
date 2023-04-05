package browserImpl;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import browserDesign.Browser;



public class BrowserImplementation implements Browser {
	
	private static final ThreadLocal<RemoteWebDriver> remoteWebDriver = new ThreadLocal<RemoteWebDriver>();
	private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<WebDriverWait>();
	
	public void setDriver(String browser) {
		switch (browser) {
		case "chrome":
		    ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			options.addArguments("--remote-allow-origins=*");
		    remoteWebDriver.set(new ChromeDriver(options));
			break;
		case "firefox":
			remoteWebDriver.set(new FirefoxDriver());
			break;
		case "ie":
			remoteWebDriver.set(new InternetExplorerDriver());
		default:
			break;
		}
	}
	
	
	
	public RemoteWebDriver getDriver() {
		return remoteWebDriver.get();
	}
	
	public void setWait() {
		wait.set(new WebDriverWait(getDriver(), Duration.ofSeconds(30)));
	}

	public WebDriverWait getWait() {
		return wait.get();
	}

	@Override
	public void startApp(String url) {
		try {
			setDriver("chrome");
			setWait();
			getDriver().navigate().to(url);
			getDriver().manage().window().maximize();
			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		
	}

	@Override
	public void close() {
		getDriver().close();
	}

	@Override
	public void quit() {
		getDriver().quit();
	}
	
	
	
	public Browser.WebAlert wAlert() {
        return new CWebAlert();
    }
	
	public Browser.WebButton wButton(){
		return new CWebButton();
	}
	
	public Browser.WebCalendar wCalendar(){
		return new CWebCalendar();
	}
	
	public Browser.WebCheckbox wCheckBox(){
		return new CWebCheckbox();
	}
	
	
	public Browser.WebEdit wEdit(){
		return new CWebEdit();
	} 
	
	public Browser.WebFile wFile(){
		return new CWebFile();
	}
	
	public Browser.WebFrame wFrame(){
		return new CWebFrame();
	}
	
	public Browser.Element wElement(){
		return new CElement();
	}
	
	public Browser.WebImage wImage(){
		return new CWebImage();
	}
	
	
	public Browser.WebLink cWebLink(){
		return new CWebLink();
	}
	
	
	public Browser.WebNavigation cWebNavigation(){
		return new CWebNavigation();
	}
	
	public Browser.WebRadioBtn cWebRadioBtn(){
		return new CWebRadioBtn();
	} 
	
	
	public Browser.WebScreenshot cWebScreenShot(){
		return new CWebScreenshot();
	}
	
	
	
	public Browser.WebSelect cWebSelect(){
		return new CWebSelect();	
		
	}
	
	
	public Browser.WebTable cWebTable(){
		return new CWebTable();
	}
	
	
	public Browser.WebVideo cWebVideo(){
		return new CWebVideo();
	}
	
	public Browser.Windows cWindows(){
		return new CWindows();
	}
	
	
	
 	class CWebAlert implements Browser.WebAlert {

		@Override
		public void switchToAlert(WebElement element) {
			getDriver().switchTo().alert();			
		}

		@Override
		public void acceptAlert(WebElement element) {
			try {
				getWait().until(ExpectedConditions.alertIsPresent());
				Alert alert = getDriver().switchTo().alert();
				alert.accept();
			} catch (NoAlertPresentException e) {
				System.out.println("AlertNotFound Exception : "+e.getMessage());
			} catch (WebDriverException e) {
				System.out.println("WebDriverException : "+e.getMessage());
			}
		}

		@Override
		public void dismissAlert(WebElement element) {
			String text = "";		
			try {
				Alert alert = getDriver().switchTo().alert();
				text = alert.getText();
				alert.dismiss();
				System.out.println("The alert "+text+" is accepted.");
			} catch (NoAlertPresentException e) {
				System.out.println("There is no alert present.");
			} catch (WebDriverException e) {
				System.out.println("WebDriverException : "+e.getMessage());
			}  
		}

		@Override
		public String getAlertText(WebElement element) {
			String text = "";		
			try {
				Alert alert = getDriver().switchTo().alert();
				text = alert.getText();
			} catch (NoAlertPresentException e) {
				System.out.println("There is no alert present.");
			} catch (WebDriverException e) {
				System.out.println("WebDriverException : "+e.getMessage());
			} 
			return text;
			
		}
		
	}
	
	
	class CWebButton implements WebButton {

		@Override
		public void click(WebElement element) {
			int counter = 0;
			String text="";
			try {
				wait.get().until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				element.click();
				 
			} catch (StaleElementReferenceException e) {
				if(counter++ < 3) {
					click(element);
				}else {
					
					throw new RuntimeException();
				}
			} 
		}

		@Override
		public String getText(WebElement element) {
			return element.getText();
		}

		@Override
		public boolean verifyText(WebElement element, String value) {
			try {
				if(element.getText().equals(value)) {
					return true;
				}else {
					System.out.println("The expected text doesnt contain the actual "+value);
				}
			} catch (WebDriverException e) {
				System.out.println("Unknown exception occured while verifying the Text");
			} 

			return false;
		}

		@Override
		public String getColor(WebElement element) {
			return element.getCssValue("color");
		}

		@Override
		public boolean verifyColor(WebElement element, String value) {
			try {
				if(element.getCssValue("color").equals(value)) {
					return true;
				}else {
					System.out.println("The expected colour doesnt contain the actual "+value);
				}
			} catch (WebDriverException e) {
				System.out.println("Unknown exception occured while verifying the color");
			} 

			return false;
		}

		@Override
		public boolean isItEnabled(WebElement element) {
			return element.isEnabled();
		}

		@Override
		public boolean isItVisible(WebElement element) {
			return element.isDisplayed();
		}
		
	}
	
	
	class CWebCalendar implements WebCalendar {

		@Override
		public String selectDay(WebElement element, String date) {
			
			return null;
		}

		@Override
		public String selectMonth(WebElement element, String month) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String selectYear(WebElement element, String year) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String selectDate(WebElement element, String date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getSelectedDate(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String verifySelectedDate(String date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyMinimumSelectableDate(String date) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyMaximumSelectableDate(String date) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyRangeSelectionOfDates(String fromDate, String toDate) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifySelectingDisabledDate(String date) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	class CWebCheckbox implements WebCheckbox {

		@Override
		public String select(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String deSelect(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isItSelected(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getLabel(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyLabel(WebElement element, String value) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getColor(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyColor(WebElement element, String value) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItEnabled(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItVisible(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	class CWebEdit implements WebEdit {

		@Override
		public void type(WebElement element, String value) {
			try {
				element.clear();
				element.sendKeys(value);
				System.out.println("The Data :"+value+" entered Successfully");
			} catch (ElementNotInteractableException e) {
				System.out.println("The Element "+element+" is not Interactable");
				throw new RuntimeException();
			}
		}
		
		@Override
		public WebElement locateElement(String locatorType, String value) {
			try {
				switch(locatorType.toLowerCase()) {
				case "id": return getDriver().findElement(By.id(value));
				case "name": return getDriver().findElement(By.name(value));
				case "class": return getDriver().findElement(By.className(value));
				case "link": return getDriver().findElement(By.linkText(value));
				case "xpath": return getDriver().findElement(By.xpath(value));
				}
			} catch (NoSuchElementException e) {
				System.out.println("The Element with locator:"+locatorType+" Not Found with value: "+value);
				throw new RuntimeException();
			}catch (Exception e) {
				System.out.println("The Element with locator:"+locatorType+" Not Found with value: "+value);
			}
			return null;
		}

		@Override
		public String getTypedText(WebElement element) {
			return element.getAttribute("value");
		}

		@Override
		public void clear(WebElement element) {
			try {
				element.clear();
				System.out.println("The field is cleared Successfully");
			} catch (ElementNotInteractableException e) {
				System.out.println("The field is not Interactable");
				throw new RuntimeException();
			}
		}

		@Override
		public void append(WebElement element, String value) {
			element.sendKeys(value);
		}

		@Override
		public boolean verifyText(WebElement element, String value) {
			try {
				if(element.getText().equals(value)) {
					return true;
				}else {
					System.out.println("The expected text doesnt contain the actual "+value);
				}
			} catch (WebDriverException e) {
				System.out.println("Unknown exception occured while verifying the Text");
			} 

			return false;
		}

		@Override
		public void click(WebElement element) {
			int counter = 0;
			String text="";
			try {
				wait.get().until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				element.click();
				 
			} catch (StaleElementReferenceException e) {
				if(counter++ < 3) {
					click(element);
				}else {
					
					throw new RuntimeException();
				}
			} 
		}

		@Override
		public String getColor(WebElement element) {
			return element.getCssValue("color");
		}

		@Override
		public boolean verifyColor(WebElement element, String value) {
			try {
				if(element.getCssValue("color").equals(value)) {
					return true;
				}else {
					System.out.println("The expected colour doesnt contain the actual "+value);
				}
			} catch (WebDriverException e) {
				System.out.println("Unknown exception occured while verifying the color");
			} 

			return false;
		}

		@Override
		public boolean isItEnabled(WebElement element) {
			return element.isEnabled();
		}

		@Override
		public boolean isItVisible(WebElement element) {
			return element.isDisplayed();
		}
		
	}
	
	
	class CWebFile implements WebFile {

		@Override
		public String downloadFile(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String uploadFile(WebElement element, String filePath) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String verifyFileFormat(String filepath) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyFileSize(String filepath) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyFileSecurity(String filepath) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	class CWebFrame implements WebFrame {

		@Override
		public void SwitchToFrame(String frameIdOrName) {
			getDriver().switchTo().frame(frameIdOrName);
		}

		@Override
		public void SwitchToFrame(WebElement element) {
			getDriver().switchTo().frame(element);
		}

		@Override
		public void SwitchOutOfFrame() {
			getDriver().switchTo().defaultContent();
		}
		
	}
	
	
	class CElement implements Element {

		@Override
		public Location getPosition(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public WebElement locateElement(String locatorType, String value) {
			try {
				switch(locatorType.toLowerCase()) {
				case "id": return getDriver().findElement(By.id(value));
				case "name": return getDriver().findElement(By.name(value));
				case "class": return getDriver().findElement(By.className(value));
				case "link": return getDriver().findElement(By.linkText(value));
				case "xpath": return getDriver().findElement(By.xpath(value));
				}
			} catch (NoSuchElementException e) {
				System.out.println("The Element with locator:"+locatorType+" Not Found with value: "+value);
				throw new RuntimeException();
			}catch (Exception e) {
				System.out.println("The Element with locator:"+locatorType+" Not Found with value: "+value);
			}
			return null;
		}
		
		

		@Override
		public boolean verifyPosition(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Dimension getSize(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifySize(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getColor(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyColor(String color) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItEnabled(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItVisible(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String screenshot(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void append(WebElement ele, String data) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	class CWebImage implements WebImage {

		@Override
		public String getImageName(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getImageFormat(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getImageSize(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getImageAltText(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyImageLoadingTime(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String verifyImageName(WebElement element, String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String verifyImageFormat(WebElement element, String format) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyImageSize(WebElement element, int size) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyImageAltText(WebElement element, String text) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isItVisible(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	class CWebLink implements WebLink {

		@Override
		public void click(WebElement element) {
			int counter = 0;
			String text="";
			try {
				wait.get().until(ExpectedConditions.elementToBeClickable(element));
				text = element.getText();
				element.click();
				 
			} catch (StaleElementReferenceException e) {
				if(counter++ < 3) {
					click(element);
				}else {
					
					throw new RuntimeException();
				}
			} 
		}

		@Override
		public String getText(WebElement element) {
			return element.getText();
		}

		@Override
		public boolean verifyText(WebElement element, String value) {
			try {
				if(element.getText().equals(value)) {
					return true;
				}else {
					System.out.println("The expected text doesnt contain the actual "+value);
				}
			} catch (WebDriverException e) {
				System.out.println("Unknown exception occured while verifying the Text");
			} 

			return false;
		}
		
	}
	
	
	class CWebNavigation implements WebNavigation {

		@Override
		public String toUrl(String url) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String back() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String forward() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String reload() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	class CWebRadioBtn implements WebRadioBtn {

		@Override
		public String click(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getselected(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSelected(WebElement element) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String IsEnabled(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	class CWebScreenshot implements WebScreenshot {

		@Override
		public String pageScreenshot() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String fullPageScreenshot() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String elementScreenshot(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	class CWebSelect implements WebSelect {

		@Override
		public void selectDropDownUsingText(WebElement ele, String value) {
			if(ele.getTagName().equalsIgnoreCase("select"))
				new Select(ele).selectByVisibleText(value);
			else {
				ele.click();
				ele.findElement(By.xpath("//../following::*[text()='"+value+"']")).click();
			}
		}

		@Override
		public void selectDropDownUsingIndex(WebElement element, int value) {
			new Select(element)
			.selectByIndex(value);
		}

		@Override
		public void selectDropDownUsingValue(WebElement ele, String value) {
			new Select(ele)
			.selectByValue(value);
		}

		@Override
		public String getAllSelectedOptions(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String GetAllOptions(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String Unselect(WebElement element, String value) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	private class CWebTable implements WebTable {

		@Override
		public String getHeaders(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyHeaders(WebElement element, String values) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyTableHasThisData(WebElement element, String value) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getRecordForUniqueId(WebElement element, String value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getNumberOfColumns(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getNumberOfRows(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getSpecificColumnForUniqueId(WebElement element, String value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyRecordForUniqueId(String uniqueId) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyNumberOfColumns(int noOfColumns) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyNumberOfRows(int noOfRows) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifySpecificColumnForUniqueId(String uniqueId, String columnName) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyPaginationWithSelectedNoOfRecs(WebElement element, String value) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	private class CWebVideo implements WebVideo {

		@Override
		public String getVideoName(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getVideoFormat(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getVideoSizeInPage(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyVideoName(WebElement element, String name) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean verifyVideoFormat(WebElement element, String format) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String verifyVideoSizeInPage(WebElement element, int size) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String isItVisible(WebElement element) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	private class CWindows implements Windows {

		@Override
		public String switchToWindow(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String switchToWindow(String titleOrWindowId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyTitle(String title) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getAllWindows() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getUrl() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPageSource() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String verifyPageHasText(String text) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyUrl(String url) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String maximize() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getWindowSize() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyWIndowSize(int length, int width) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String getWindowPosition() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean verifyWindowPosition(int x, int y) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String close() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String quit() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	
	

}
