package net.ravendb

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.TasksCreateSampleDataPage
import net.ravendb.pages.TasksImportDatabasePage

import org.testng.annotations.Test


class TasksTest extends EmptyDatabaseTestBase {

    /**
     * User can run Create Sample Data task.
     * @Step Navigate to Tasks page.
     * @Step Click Create Sample Data link.
     * @verification Sample data created.
     */
    @Test(groups="Smoke")
    void canCreateSampleData() {
        at DocumentsPage

        topNavigation.tasksLink.click()
        waitFor { at TasksImportDatabasePage }

        menu.createSampleDataLink.click()
        waitFor { at TasksCreateSampleDataPage }

        createSampleData()

        topNavigation.documentsLink.click()
        waitFor { at DocumentsPage }
        waitFor(30) { collectionsList.size() == 10 }
        waitFor(30) { selectAllDocumentsCheckbox.displayed }

        waitFor {
            selectAllDocumentsCheckbox.click()
            if(selectAllDocumentsInfoAllDocumentsCountContainer.text() == "1059") {
                return true
            } else {
                selectAllDocumentsCheckbox.click()
                return false
            }
        }
    }
}
