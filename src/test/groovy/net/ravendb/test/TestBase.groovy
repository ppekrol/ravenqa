package net.ravendb.test

import geb.testng.GebReportingTest

import java.text.SimpleDateFormat

import net.ravendb.pages.LandingPage
import net.ravendb.pages.ResourcesPage

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.testng.ITestContext
import org.testng.annotations.BeforeGroups
import org.testng.annotations.BeforeMethod

/**
 * Base class for all tests.
 */
abstract class TestBase extends GebReportingTest {

    protected Random rand = new Random()

    protected final static SIMPLE_TEXT_FILENAME = "simple-text-file.txt"

    @BeforeGroups(groups="Smoke")
    def setupGroup() {
        go browser.baseUrl

        // dismiss build reminder
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        String date = formatter.format(new Date())
        ((JavascriptExecutor)browser.driver).executeScript("window.localStorage.setItem('LastServerBuildReminder', '${date}')")
        ((JavascriptExecutor)browser.driver).executeScript("window.localStorage.setItem('ChangesApiWarnDisabled', true)")

        try {
            waitFor { at LandingPage }

            createDbLink.click()
            waitFor { createDatabaseModalDialog.createButton.displayed }

            createDatabaseModalDialog.databaseIcon.click()
            createDatabaseModalDialog.databaseNameInput = "doNotRemove"
            createDatabaseModalDialog.createButton.click()
        } catch(Exception e) {
            // empty by design
        } finally {
            waitFor { at ResourcesPage }
        }
    }

    @BeforeMethod(alwaysRun=true)
    def setup(ITestContext context) {
        go browser.baseUrl
        waitFor {at ResourcesPage }
    }

    protected loadTestFile(String filename) {
        URL sampleDataFileUrl = this.getClass().getClassLoader().getResource("test-data/"+filename)
        assert sampleDataFileUrl
        new File(sampleDataFileUrl.toURI())
    }
}
