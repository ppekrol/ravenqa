package net.ravendb.test.manage

import net.ravendb.pages.FileSystemPage
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerApiKeysPage
import net.ravendb.pages.manage.ManageServerBackupPage
import net.ravendb.pages.manage.ManageServerRestorePage;
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerBackupRestoreFileSystem extends TestBase {

    /**
     * User can backup and restore simple file system.
     * @Step Navigate to resources page
     * @Step Create new file system with single folder and file.
     * @Step Backup and restore file system.
     * @verification File system backed up and restored.
     */
    @Test(groups="Smoke")
    void canBackupAndRestoreSimpleFileSystem() {
        at ResourcesPage

        String fsName = "fs" + rand.nextInt()
        createResource(fsName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        getResourceLink(fsName).click()
        waitFor { at FileSystemPage }

        String dirName = "testdir"
        createDirectory(dirName)

        File f = loadTestFile(SIMPLE_TEXT_FILENAME)
        uploadFile(f, dirName)

        topNavigation.resourcesLink.click()
        waitFor { at ResourcesPage }

        manageYourServerButton.click()
        waitFor { at ManageServerApiKeysPage }

        menu.backupLink.click()
        waitFor { at ManageServerBackupPage }

        String location = backupFileSystem(fsName)

        menu.restoreLink.click()
        waitFor { at ManageServerRestorePage }

        String restoredFsName = "fs" + rand.nextInt()
        restoreFileSystem(location, restoredFsName)
    }
}
