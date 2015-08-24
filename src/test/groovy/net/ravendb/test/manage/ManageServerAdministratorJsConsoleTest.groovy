package net.ravendb.test.manage

import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerAdministratorJsConsolePage
import net.ravendb.pages.manage.ManageServerPage
import net.ravendb.test.TestBase

import org.openqa.selenium.Keys
import org.testng.annotations.Test


class ManageServerAdministratorJsConsoleTest extends TestBase {

    /**
     * User can administrator js console script.
     * @Step Navigate to Manage Resources page.
     * @Step Click Administrator JS Console link.
     * @verification User can run JS script in console.
     */
    @Test(groups="Smoke")
    void canExecuteJsScriptInConsole() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.administratorJsConsole.click()
        waitFor { at ManageServerAdministratorJsConsolePage }

        targetDatabaseInput = ManageServerAdministratorJsConsolePage.TARGET_DB_SYSTEM
        targetDatabaseInput.firstElement().sendKeys(Keys.RETURN)
        selectOperation(ManageServerAdministratorJsConsolePage.OPERATION_DB_STATS)
        executeCommandButton.click()
        alert.waitForMessage(ManageServerAdministratorJsConsolePage.SUCCESS_MESSAGE)
        waitFor { resultsContainerLines.size() > 1 }
    }
}
