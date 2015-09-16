package net.ravendb.test

import net.ravendb.pages.FileDetailsPage
import net.ravendb.pages.FileSystemPage
import net.ravendb.pages.ResourcesPage

import org.openqa.selenium.Keys
import org.testng.annotations.Test


class FileSystemTest extends TestBase {

    private final String FILE_METADATA = """
        {
            "Raven-Synchronization-Source": "111111111-1111-1111-1111-111111111111",
            "Raven-Synchronization-History": [],
            "Raven-Synchronization-Version": "111"
        }
    """.replaceAll("\\s", "")

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
        prepareFilesystem(fsName)

        String dirName = "testdir"
        createDirectory(dirName)

        File f = loadTestFile(SIMPLE_TEXT_FILENAME)
        uploadFile(f, dirName)
    }

    @Test(groups="Smoke")
    void canViewAndEditFileMetadata() {
        at ResourcesPage

        String fsName = "fs" + rand.nextInt()
        prepareFilesystem(fsName)

        String dirName = "testdir"
        createDirectory(dirName)

        File f = loadTestFile(SIMPLE_TEXT_FILENAME)
        uploadFile(f, dirName)

        clickFolder(dirName)
        clickFile(SIMPLE_TEXT_FILENAME)
        waitFor { at FileDetailsPage }

        assert fileMetadataContainer.value() != ""

        fileMetadataContainer.firstElement().sendKeys(Keys.chord(Keys.CONTROL, "a"));
        fileMetadataContainer.firstElement().sendKeys(Keys.BACK_SPACE);
        fileMetadataContainer.firstElement().sendKeys(FILE_METADATA)
        saveButton.click()
        alert.waitForMessage(FileDetailsPage.SAVED_MESSAGE + dirName + "/" + SIMPLE_TEXT_FILENAME)
    }

    private void prepareFilesystem(String name) {
        createResource(name, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        getResourceLink(name).click()
        waitFor { at FileSystemPage }
    }
}
