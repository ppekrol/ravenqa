package net.ravendb

import net.ravendb.pages.DatabasePage
import net.ravendb.pages.DocumentsPage;
import net.ravendb.pages.TasksCreateSampleDataPage
import net.ravendb.pages.TasksImportDatabasePage

import org.testng.annotations.Test


class TasksTest extends DatabaseTestBase {

    /**
     * User can run Create Sample Data task.
     * @Step Navigate to Tasks page.
     * @Step Click Create Sample Data link.
     * @verification Sample data created.
     */
    @Test(groups="Smoke")
    void canCreateSampleData() {
        at DatabasePage

        topNavigation.tasksLink.click()
        waitFor { at TasksImportDatabasePage }

        menu.createSampleDataLink.click()
        waitFor { at TasksCreateSampleDataPage }

        createSampleDataButton.click()
        waitFor(10, 0.1) { progressBar.displayed }
        waitFor(10, 0.1) { !progressBar.displayed }

        topNavigation.documentsLink.click()
        waitFor { at DocumentsPage }

        collectionsList.size() == 10

        waitFor { selectAllDocumentsCheckbox.displayed }
        selectAllDocumentsCheckbox.click()
        waitFor { selectAllDocumentsInfoAllDocumentsCountContainer.text() == "1059" }
    }
}
