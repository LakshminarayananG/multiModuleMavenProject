package browserDesign;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.html5.Location;

public interface Browser {

	/**
	 * This method will launch the Chrome browser and maximise the browser and set
	 * the wait for 30 seconds and load the url
	 * 
	 * @param url - This will load the specified url
	 * @author Testleaf
	 * @throws MalformedURLException
	 */
	public void startApp(String url);

	/**
	 * This method will close the active browser
	 * 
	 * @author Testleaf
	 */
	public void close();

	/**
	 * This method will close all the browsers
	 * 
	 * @author Testleaf
	 */
	public void quit();

	interface WebAlert {

		public void switchToAlert(WebElement element);

		public void acceptAlert(WebElement element);

		public void dismissAlert(WebElement element);

		public String getAlertText(WebElement element);

	}

	interface WebButton {
		public void click(WebElement element);

		public String getText(WebElement element);

		public boolean verifyText(WebElement element, String value);

		public String getColor(WebElement element);

		public boolean verifyColor(WebElement element, String value);

		public boolean isItEnabled(WebElement element);

		public boolean isItVisible(WebElement element);

	}

	interface WebCalendar {
		public String selectDay(WebElement element, String date);

		public String selectMonth(WebElement element, String month);

		public String selectYear(WebElement element, String year);

		public String selectDate(WebElement element, String date);

		public String getSelectedDate(WebElement element);

		public String verifySelectedDate(String date);

		public boolean verifyMinimumSelectableDate(String date);

		public boolean verifyMaximumSelectableDate(String date);

		public boolean verifyRangeSelectionOfDates(String fromDate, String toDate);

		public boolean verifySelectingDisabledDate(String date);

	}

	interface WebCheckbox {
		public String select(WebElement element);

		public String deSelect(WebElement element);

		public boolean isItSelected(WebElement element);

		public String getLabel(WebElement element);

		public boolean verifyLabel(WebElement element, String value);

		public String getColor(WebElement element);

		public boolean verifyColor(WebElement element, String value);

		public boolean isItEnabled(WebElement element);

		public boolean isItVisible(WebElement element);

	}

	interface WebEdit {
		public void type(WebElement element, String value);

		public String getTypedText(WebElement element);

		public void clear(WebElement element);

		public void append(WebElement element, String value);

		public boolean verifyText(WebElement element, String value);

		public void click(WebElement element);

		public String getColor(WebElement element);

		public boolean verifyColor(WebElement element, String value);

		public boolean isItEnabled(WebElement element);

		public boolean isItVisible(WebElement element);
		
		public WebElement locateElement(String locatorType,String value);

	}

	interface WebFile {

		public String downloadFile(WebElement element);

		public String uploadFile(WebElement element, String filePath);

		public String verifyFileFormat(String filepath);

		public boolean verifyFileSize(String filepath);

		public boolean verifyFileSecurity(String filepath);

	}

	interface WebFrame {

		public void SwitchToFrame(String frameIdOrName);

		public void SwitchToFrame(WebElement element);

		public void SwitchOutOfFrame();
	}

	interface Element {
		Location getPosition(WebElement element);

		boolean verifyPosition(WebElement element);

		Dimension getSize(WebElement element);

		boolean verifySize(int x, int y);

		String getColor(WebElement element);

		boolean verifyColor(String color);

		boolean isItEnabled(WebElement element);

		boolean isItVisible(WebElement element);

		String screenshot(WebElement element);
		
		public void append(WebElement ele, String data);
		
		public WebElement locateElement(String locatorType,String value);

	}

	interface WebImage {
		public String getImageName(WebElement element);

		public String getImageFormat(WebElement element);

		public String getImageSize(WebElement element);

		public String getImageAltText(WebElement element);

		public boolean verifyImageLoadingTime(WebElement element);

		public String verifyImageName(WebElement element, String name);

		public String verifyImageFormat(WebElement element, String format);

		public boolean verifyImageSize(WebElement element, int size);

		public boolean verifyImageAltText(WebElement element, String text);

		public boolean isItVisible(WebElement element);

	}

	interface WebLink {
		public void click(WebElement element);

		public String getText(WebElement element);

		public boolean verifyText(WebElement element, String value);

		

		

	}

	interface WebNavigation {
		public String toUrl(String url);

		public String back();

		public String forward();

		public String reload();

	}

	interface WebRadioBtn {

		public String click(WebElement element);

		public String getselected(WebElement element);

		public boolean isSelected(WebElement element);

		public String IsEnabled(WebElement element);

	}

	interface WebScreenshot {
		public String pageScreenshot();

		public String fullPageScreenshot();

		public String elementScreenshot(WebElement element);
	}

	interface WebSelect {
		public void selectDropDownUsingText(WebElement element, String value);

		public void selectDropDownUsingIndex(WebElement element, int value);

		public void selectDropDownUsingValue(WebElement element, String value);

		public String getAllSelectedOptions(WebElement element);

		public String GetAllOptions(WebElement element);

		public String Unselect(WebElement element, String value);
	}

	interface WebTable {
		public String getHeaders(WebElement element);

		public boolean verifyHeaders(WebElement element, String values);

		public boolean verifyTableHasThisData(WebElement element, String value);

		public String getRecordForUniqueId(WebElement element, String value);

		public String getNumberOfColumns(WebElement element);

		public String getNumberOfRows(WebElement element);

		public String getSpecificColumnForUniqueId(WebElement element, String value);

		public boolean verifyRecordForUniqueId(String uniqueId);

		public boolean verifyNumberOfColumns(int noOfColumns);

		public boolean verifyNumberOfRows(int noOfRows);

		public boolean verifySpecificColumnForUniqueId(String uniqueId, String columnName);

		public boolean verifyPaginationWithSelectedNoOfRecs(WebElement element, String value);

	}

	interface WebVideo {
		public String getVideoName(WebElement element);

		public String getVideoFormat(WebElement element);

		public String getVideoSizeInPage(WebElement element);

		public boolean verifyVideoName(WebElement element, String name);

		public boolean verifyVideoFormat(WebElement element, String format);

		public String verifyVideoSizeInPage(WebElement element, int size);

		public String isItVisible(WebElement element);
	}

	interface Windows {
		public String switchToWindow(int index);

		public String switchToWindow(String titleOrWindowId);

		public String getTitle();

		public boolean verifyTitle(String title);

		public String getAllWindows();

		public String getUrl();

		public String getPageSource();

		public String verifyPageHasText(String text);

		public boolean verifyUrl(String url);

		public String maximize();

		public String getWindowSize();

		public boolean verifyWIndowSize(int length, int width);

		public String getWindowPosition();

		public boolean verifyWindowPosition(int x, int y);

		public String close();

		public String quit();

	}

}
