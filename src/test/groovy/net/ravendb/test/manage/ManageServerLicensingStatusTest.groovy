package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerLicenseInformationPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerLicensingStatusTest extends TestBase {

    /**
     * User can view License status.
     * @Step Navigate to Manage Resources page.
     * @Step Licensing Information link.
     * @verification User can view license information.
     */
    @Test(groups="Smoke")
    void canViewLicenseStatus() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.licenseInformationLink.click()
        waitFor { at ManageServerLicenseInformationPage }

        forceLicenseUpdateButton.click()
        waitFor { licensingStatusHeader.displayed }
    }
}
