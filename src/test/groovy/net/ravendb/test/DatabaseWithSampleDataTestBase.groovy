package net.ravendb.test

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.TasksCreateSampleDataPage
import net.ravendb.pages.TasksImportDatabasePage

import org.testng.ITestContext
import org.testng.annotations.BeforeMethod


abstract class DatabaseWithSampleDataTestBase extends EmptyDatabaseTestBase {

    @BeforeMethod(alwaysRun=true)
    def setup(ITestContext context) {
        super.setup(context)

        at DocumentsPage

        topNavigation.tasksLink.click()
        waitFor { at TasksImportDatabasePage }

        menu.createSampleDataLink.click()
        waitFor { at TasksCreateSampleDataPage }

        createSampleData()

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        getResourceLink(dbName).click()
        waitFor { at DocumentsPage }
    }
}
