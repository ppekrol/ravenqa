/*
	This is the Geb configuration file.

	See: http://www.gebish.org/manual/current/configuration.html
*/
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

waiting {
    timeout = 10
    retryInterval = 0.5 //second
}

baseUrl = "http://localhost:8080/studio/index.html"

driver = {
    def options = new ChromeOptions()
    options.addArguments(["--start-maximized"])
    new ChromeDriver(options)
}

reportOnTestFailureOnly = true