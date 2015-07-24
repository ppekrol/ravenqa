package net.ravendb.test

import net.ravendb.pages.FileSystemPage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test


class FileSystemTest extends TestBase {

    /**
     * User can create new folder and upload file to file system.
     * @Step Navigate to resources page
     * @Step Create new file system and click on it.
     * @Step Create folder and upload file to that folder.
     * @verification Folder created and file uploaded.
     */
    @Test(groups="Smoke")
    void canCreateNewDirectoryAndUploadTextFile() {
        at ResourcesPage

        String fsName = "fs" + rand.nextInt()
        createResource(fsName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        getResourceLink(fsName).click()
        waitFor { at FileSystemPage }

        String dirName = "testdir"
        createDirectory(dirName)

        File f = loadTestFile(SIMPLE_TEXT_FILENAME)
        uploadFile(f, dirName)
    }
}
