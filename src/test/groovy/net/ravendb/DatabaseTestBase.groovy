package net.ravendb

import net.ravendb.pages.DatabasePage
import net.ravendb.pages.ResourcesPage

import org.testng.ITestContext
import org.testng.annotations.BeforeMethod


abstract class DatabaseTestBase extends TestBase {

    protected String dbName

    @BeforeMethod(alwaysRun=true)
    def setup(ITestContext context) {
        super.setup(context)

        String dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        waitFor(message: "Database "+dbName+" not found on the list.") {
            getResourceLink(dbName)
        }

        getResourceLink(dbName).click()
        waitFor { at DatabasePage }
    }
}
