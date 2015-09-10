package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerAdminLogsPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.pages.manage.ManageServerTrafficWatchPage;
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerTrafficWatchTest extends TestBase {

    /**
     * User can view Traffic Watch.
     * @Step Navigate to Manage Resources page.
     * @Step Click Traffic Watch link.
     * @verification User can view logs.
     */
    @Test(groups="Smoke")
    void canViewTrafficWatch() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.trafficWatchLink.click()
        waitFor { at ManageServerTrafficWatchPage }

        configureConnectionButton.click()
        waitFor { maxEntriesInput.displayed }

        maxEntriesInput = "100"
        resourceNameInput = ManageServerTrafficWatchPage.RESOURCE_SYSTEM_DATABASE
        connectButton.click()

        waitFor { at ManageServerTrafficWatchPage }
        waitFor { statusConnectedLabel.displayed }
        waitFor { logsContainer.children().size() > 0 }

        disconnectConnectionButton.click()
        waitFor { statusDisconnectedLabel.displayed }

        reconnectConnectionButton.click()
        waitFor { statusConnectedLabel.displayed }
    }
}
