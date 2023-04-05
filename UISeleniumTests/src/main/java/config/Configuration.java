package config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
	"system:properties",
	"classpath:local.properties",
	"classpath:app.properties"})
public interface Configuration extends Config {

	@Key("target")
	String target();

	@Key("browser")
	String browser();

	@Key("headless")
	Boolean headless();

	@Key("url.api")
	String apiUrl();

	@Key("url.base")
	String baseUrl();
	
	@Key("timeout")
	int timeout();

	@Key("grid.url")
	String gridUrl();

	@Key("grid.port")
	String gridPort();

	@Key("faker.locale")
	String faker();

	@Key("app.username")
	String appUserName();

	@Key("app.password")
	String appPassword();

	


}
