package browserActions;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;

import config.ConfigurationManager;

public class DriverFactory  {

	public static final ThreadLocal<Playwright> playwright = new ThreadLocal<Playwright>();
	public static final ThreadLocal<Browser> driver = new ThreadLocal<Browser>();
	public static final ThreadLocal<String> token = new ThreadLocal<String>();
	public static final ThreadLocal<BrowserContext> context = new ThreadLocal<BrowserContext>();
	public static final ThreadLocal<Page> page = new ThreadLocal<Page>();
	public static final ThreadLocal<Page> secondPage = new ThreadLocal<Page>();
	public static final ThreadLocal<FrameLocator> frameLocator = new ThreadLocal<FrameLocator>();

	/**
	 * Launches the preferred browser in the head(less) mode.
	 * @param browser The accepted browsers are chrome, edge, firefox, safari (webkit)
	 * @param headless Send true if you like to run in headless mode else false
	 * @author TestLeaf
	 */
	
	public void setDriver(String browser, boolean headless) {	
		System.setProperty("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
		playwright.set(Playwright.create());

		switch (browser) {
		case "chrome":
			driver.set(getPlaywright().chromium().launch(
					new BrowserType.LaunchOptions().setChannel("chrome")
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
			break;
		case "edge":
			driver.set(getPlaywright().chromium().launch(
					new BrowserType.LaunchOptions().setChannel("msedge")
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
			break;
		case "firefox":
			driver.set(getPlaywright().firefox().launch(
					new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
		case "safari":
			driver.set(getPlaywright().webkit().launch(
					new BrowserType.LaunchOptions()
					.setHeadless(headless)
					.setSlowMo(ConfigurationManager.configuration().slowMotion())));
		default:
			break;
		}
		
		// Login through API and set the OAuth Token
		setOauthToken();
		
	}
	
	public void setOauthToken() {

		// API request
		APIRequestContext request = getPlaywright().request().newContext(new APIRequest.NewContextOptions().setBaseURL(ConfigurationManager.configuration().oauthUrl()));

		// Request -> Post 
		APIResponse response = request.post("", 
				RequestOptions.create()
				.setForm(FormData.create()
						.set("grant_type", "password")
						.set("client_id", ConfigurationManager.configuration().clientId())
						.set("client_secret", ConfigurationManager.configuration().clientSecret())
						.set("username", ConfigurationManager.configuration().appUserName())
						.set("password",ConfigurationManager.configuration().appPassword())

						));

		// Parse the json
		token.set(new Gson().fromJson(response.text(), JsonElement.class).getAsJsonObject().get("access_token").getAsString());

	}
	
	public String getToken() {
		return token.get();
	}

	public Browser getDriver() {
		return driver.get();
	}

	public BrowserContext getContext() {
		return context.get();
	}

	public Page getPage() {
		return page.get();
	}
	
	public Page getSecondPage() {
		return secondPage.get();
	}
	
	public FrameLocator getFrameLocator() {
		return frameLocator.get();
	}

	public Playwright getPlaywright() {
		return playwright.get();
	}

}
