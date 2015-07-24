package net.ravendb.test.manage

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerApiKeysPage
import net.ravendb.pages.manage.ManageServerBackupPage
import net.ravendb.pages.manage.ManageServerRestorePage;
import net.ravendb.test.DatabaseWithSampleDataTestBase

import org.testng.annotations.Test


class ManageServerBackupRestoreDatabaseTest extends DatabaseWithSampleDataTestBase {

    /**
     * User can backup and restore Northwind database.
     * @Step Navigate to resources page
     * @Step Create new database and create sampke data.
     * @Step Backup and restore databse.
     * @verification Database backed up and restored.
     */
    @Test(groups="Smoke")
    void canBackupAndRestoreDatabaseWithSampleData() {
        at DocumentsPage

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        manageYourServerButton.click()
        waitFor { at ManageServerApiKeysPage }

        menu.backupLink.click()
        waitFor { at ManageServerBackupPage }

        String location = backupBatabase(dbName)

        menu.restoreLink.click()
        waitFor { at ManageServerRestorePage }

        String restoredDbName = "db" + rand.nextInt()
        restoreDatabase(location, restoredDbName)

        topNavigation.switchToResources()
        waitFor { at ResourcesPage }

        getResourceLink(restoredDbName).click()
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
