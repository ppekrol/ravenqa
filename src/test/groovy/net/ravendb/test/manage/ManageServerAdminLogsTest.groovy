package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerAdminLogsPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerAdminLogsTest extends TestBase {

    /**
     * User can view Admin Logs.
     * @Step Navigate to Manage Resources page.
     * @Step Click Admin Logs link.
     * @verification User can view and export logs.
     */
    @Test(groups="Smoke")
    void canViewAdminLogs() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.adminLogsLink.click()
        waitFor { at ManageServerAdminLogsPage }

        configureConnectionButton.click()
        waitFor { maxLogEntriesInput.displayed }

        maxLogEntriesInput = "100"
        def categoryInput = $(categoryInputSelector)[0]
        def levelSelect = $(levelSelectSelector)[0]
        categoryInput = "Raven."
        levelSelect.click()
        levelSelect.find("option").each {
            if(it.text().equals(ManageServerAdminLogsPage.LEVEL_DEBUG)) {
                it.click()
            }
        }

        addNewCategoryButton.click()
        $(categoryInputSelector).size() == 2

        $(removeButtonSelector)[1].click()
        $(categoryInputSelector).size() == 1

        okButton.click()
        waitFor { at ManageServerAdminLogsPage }
        waitFor { statusConnectedLabel.displayed }
        waitFor { logsContainer.children().size() > 0 }

        disconnectButton.click()
        waitFor { statusDisconnectedLabel.displayed }

        reconnectButton.click()
        waitFor { statusConnectedLabel.displayed }
    }
}
