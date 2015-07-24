package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerCompactPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerCompactTest extends TestBase {

    /**
     * User can compact database.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Compact from left menu.
     * @Step Compact database.
     * @verification Database compacted.
     */
    @Test(groups="Smoke")
    void canCompactDatabase() {
        at ResourcesPage

        def dbName = "db" + rand.nextInt()
        createResource(dbName, ResourcesPage.RESOURCE_TYPE_DATABASE)

        waitFor { getResourceLink(dbName) }

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.compactLink.click()
        waitFor { at ManageServerCompactPage }

        compactDatabase(dbName)
    }

    /**
     * User can compact file system.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Compact from left menu.
     * @Step Compact file system.
     * @verification File system compacted.
     */
    @Test(groups="Smoke")
    void canCompactFileSystem() {
        at ResourcesPage

        def fsName = "fs" + rand.nextInt()
        createResource(fsName, ResourcesPage.RESOURCE_TYPE_FILESYSTEM)

        waitFor { getResourceLink(fsName) }

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.compactLink.click()
        waitFor { at ManageServerCompactPage }

        fileSystemTab.click()

        compactFileSystem(fsName)
    }
}
