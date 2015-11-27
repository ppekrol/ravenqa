package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerApiKeysPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerApiKeysTest extends TestBase {

    /**
     * User can create a new API Key.
     * @Step Navigate to Manage Resources page.
     * @Step Click API Keys link.
     * @Step Add new API Key.
     * @Step Delete API Key.
     * @verification User can create a new API Key and then delete it.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteNewApiKey() {
        at ResourcesPage

        def dbNameFirst = "db" + rand.nextInt()
        createResource(dbNameFirst, ResourcesPage.RESOURCE_TYPE_DATABASE)
        waitFor { getResourceLink(dbNameFirst) }

        def dbNameSecond = "db" + rand.nextInt()
        createResource(dbNameSecond, ResourcesPage.RESOURCE_TYPE_DATABASE)
        waitFor { getResourceLink(dbNameSecond) }

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.apiKeysLink.click()
        waitFor { at ManageServerApiKeysPage }

        createNewApiKey("test" + dbNameFirst, dbNameFirst, dbNameSecond)

        deleteApiKey()
    }
}
