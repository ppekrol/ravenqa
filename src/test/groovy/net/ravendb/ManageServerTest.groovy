package net.ravendb

import java.awt.Robot
import java.awt.event.KeyEvent

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.ManageServerAdminLogsPage
import net.ravendb.pages.ManageServerAdministratorJsConsolePage
import net.ravendb.pages.ManageServerApiKeysPage
import net.ravendb.pages.ManageServerBackupPage
import net.ravendb.pages.ManageServerCompactPage
import net.ravendb.pages.ManageServerGatherDebugInfoPage
import net.ravendb.pages.ManageServerGlobalConfigurationPage
import net.ravendb.pages.ManageServerIoTestPage
import net.ravendb.pages.ManageServerLicenseInformationPage
import net.ravendb.pages.ManageServerPage
import net.ravendb.pages.ManageServerRestorePage
import net.ravendb.pages.ManageServerServerSmugglingPage
import net.ravendb.pages.ManageServerTrafficWatchPage
import net.ravendb.pages.ManageServerWindowsAuthenticationPage
import net.ravendb.pages.ResourcesPage

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


    /**
     * User can create and delete Global Configuration for Periodic Export.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToFilesystem() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        createPeriodicExportToFilesystemConfiguration(
            true,
            true,
            "test",
            1,
            ManageServerGlobalConfigurationPage.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerGlobalConfigurationPage.INTERVAL_OPTION_DAYS
        )

        deletePeriodicExportGlobalConfiuration()
    }
}
