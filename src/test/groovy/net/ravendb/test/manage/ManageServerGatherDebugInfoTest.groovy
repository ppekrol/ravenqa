package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerGatherDebugInfoPage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.testng.annotations.Test


class ManageServerGatherDebugInfoTest extends TestBase {

    /**
     * User can gather debug info.
     * @Step Navigate to Manage Resources page.
     * @Step Click Gather Debug Info link.
     * @verification User can gather debug info.
     */
    @Test(groups="Smoke")
    void canGatherDebugInfo() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.gatherDebugInfoLink.click()
        waitFor { at ManageServerGatherDebugInfoPage }

        createInfoPackageButton.click()
        waitFor { parallelStacksContainer.children().size() > 0 }
    }
}
