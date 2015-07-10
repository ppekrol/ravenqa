/*
	This is the Geb configuration file.

	See: http://www.gebish.org/manual/current/configuration.html
*/
import org.openqa.selenium.chrome.ChromeDriver

waiting {
    timeout = 10
    retryInterval = 0.5 //second
}

baseUrl = "http://localhost:8080/studio/index.html"

driver = { new ChromeDriver() }

reportOnTestFailureOnly = true