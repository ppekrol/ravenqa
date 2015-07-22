package net.ravendb

import net.ravendb.pages.ManageServerGlobalConfigurationPage
import net.ravendb.pages.ManageServerPage
import net.ravendb.pages.ResourcesPage

import org.testng.annotations.Test


class ManageServerQuotasTest extends TestBase {

    /**
     * User can create and delete Global Configuration for Quotas.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Quotas configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteQuotasConfiguration() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        quotasTab.click()
        waitFor { quotas.createGlobalConfigurationForQuotasButton.displayed }
        quotas.createGlobalConfigurationForQuotasButton.click()

        quotas.addConfiguration("1", "2", "1", "2")

        quotas.save()

        quotas.remove()
    }
}
