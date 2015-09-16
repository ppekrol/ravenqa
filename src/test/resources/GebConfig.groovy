/*
	This is the Geb configuration file.

	See: http://www.gebish.org/manual/current/configuration.html
*/
import java.nio.file.Files
import java.nio.file.Path

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities

waiting {
    timeout = 10
    retryInterval = 0.5 //second
}

baseUrl = "http://localhost:8080/studio/index.html"

tmpPath = Files.createTempDirectory("raven").toString()

driver = {
    DesiredCapabilities caps = DesiredCapabilities.chrome()

    Map<String, Object> prefs = new HashMap<String, Object>()
    prefs.put("download.default_directory", tmpPath)

    ChromeOptions options = new ChromeOptions()
    options.setExperimentalOption("prefs", prefs)
    options.addArguments(["--start-maximized"])
    caps.setCapability(ChromeOptions.CAPABILITY, options)

    new ChromeDriver(caps);
}

reportOnTestFailureOnly = true