package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerGlobalConfigurationPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerVersioningTest extends TestBase {

    /**
     * User can create and delete Global Configuration for Versioning.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Versioning configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteVersioningConfiguration() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        versioningTab.click()
        waitFor { versioning.createGlobalConfigurationForVersioningButton.displayed }

        versioning.createGlobalConfigurationForVersioningButton.click()
        versioning.save()
        versioning.remove()
    }
}
