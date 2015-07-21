package net.ravendb

import geb.testng.GebReportingTest

import java.text.SimpleDateFormat

import net.ravendb.pages.LandingPage
import net.ravendb.pages.ResourcesPage

import org.openqa.selenium.JavascriptExecutor
import org.testng.ITestContext
import org.testng.annotations.BeforeGroups
import org.testng.annotations.BeforeMethod

/**
 * Base class for all tests.
 */
abstract class TestBase extends GebReportingTest {

    protected Random rand = new Random()

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

    protected makeElementVisible(def element) {
        // make file input visible (needs to be more than 10px x 10px for IE web driver)
        String js = "arguments[0].style.height='20px'; arguments[0].style.width='20px'; arguments[0].style.visibility='visible'; arguments[0].style.display='block';arguments[0].style.opacity=1;"
        ((JavascriptExecutor) browser.driver).executeScript(js, element.firstElement());
    }
}
