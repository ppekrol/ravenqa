package net.ravendb.test.manage

import java.awt.Robot
import java.awt.event.KeyEvent

import net.ravendb.modules.manage.ManageServeSQLReplication;
import net.ravendb.modules.manage.ManageServerPeriodicExport;
import net.ravendb.modules.manage.ManageServerReplication;
import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerAdminLogsPage;
import net.ravendb.pages.manage.ManageServerAdministratorJsConsolePage;
import net.ravendb.pages.manage.ManageServerApiKeysPage;
import net.ravendb.pages.manage.ManageServerBackupPage;
import net.ravendb.pages.manage.ManageServerCompactPage;
import net.ravendb.pages.manage.ManageServerGatherDebugInfoPage;
import net.ravendb.pages.manage.ManageServerGlobalConfigurationPage;
import net.ravendb.pages.manage.ManageServerIoTestPage;
import net.ravendb.pages.manage.ManageServerLicenseInformationPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.pages.manage.ManageServerRestorePage;
import net.ravendb.pages.manage.ManageServerServerSmugglingPage;
import net.ravendb.pages.manage.ManageServerTrafficWatchPage;
import net.ravendb.pages.manage.ManageServerWindowsAuthenticationPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerTest extends TestBase {

    /**
     * User can click all the links available on Manage Server page.
     * @Step Click Manage Server button on Resources page.
     * @Step Click all the links available on left panel.
     * @verification All pages displayed with no errors.
     */
    @Test(groups="Smoke")
    void canClickAllManageServerLinks() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.apiKeysLink.click()
        waitFor { at ManageServerApiKeysPage }

        menu.windowsAuthenticationLink.click()
        waitFor {at ManageServerWindowsAuthenticationPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        menu.serverSmugglingLink.click()
        waitFor { at ManageServerServerSmugglingPage }

        menu.backupLink.click()
        waitFor { at ManageServerBackupPage }

        menu.compactLink.click()
        waitFor { at ManageServerCompactPage }

        menu.restoreLink.click()
        waitFor { at ManageServerRestorePage }

        Robot robot = new Robot()
        robot.keyPress(KeyEvent.VK_PAGE_DOWN)
        robot.keyRelease(KeyEvent.VK_PAGE_DOWN)

        menu.adminLogsLink.click()
        waitFor { at ManageServerAdminLogsPage }

        menu.trafficWatchLink.click()
        waitFor { at ManageServerTrafficWatchPage }

        menu.licenseInformationLink.click()
        waitFor { at ManageServerLicenseInformationPage }

        menu.gatherDebugInfoLink.click()
        waitFor { at ManageServerGatherDebugInfoPage }

        menu.ioTestLink.click()
        waitFor { at ManageServerIoTestPage }

        menu.administratorJsConsole.click()
        waitFor { at ManageServerAdministratorJsConsolePage }

        //TODO not sure what is happening here
        //menu.studioConfigLink.click()
        //waitFor { at ManageServerStudioConfigPage }

        menu.toSystemDatabaseLink.click()
        waitFor { menu.areYouSureModalDialog.okButton.displayed }
        menu.areYouSureModalDialog.okButton.click()
        waitFor { at DocumentsPage }
    }

}
