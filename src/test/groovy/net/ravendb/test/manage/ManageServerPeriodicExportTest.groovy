package net.ravendb.test.manage

import net.ravendb.modules.manage.ManageServerPeriodicExport;
import net.ravendb.pages.ResourcesPage
import net.ravendb.pages.manage.ManageServerGlobalConfigurationPage;
import net.ravendb.pages.manage.ManageServerPage;
import net.ravendb.test.TestBase;

import org.testng.annotations.Test


class ManageServerPeriodicExportTest extends TestBase {

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

        periodicExport.createPeriodicExportToFilesystemConfiguration(
            true,
            "test",
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to Glacier.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToGlacier() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_GLACIER,
            "test-glacier",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to S3.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToS3() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_S3,
            "test-s3",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

    /**
     * User can create and delete Global Configuration for Periodic Export to Azure.
     * @Step Click Manage Server button on Resources page.
     * @Step Choose Global Configuration from left menu.
     * @Step Create Periodic Export configuration.
     * @verification Configuration created and deleted.
     */
    @Test(groups="Smoke")
    void canCreateAndDeleteGlobalConfigurationForPeriodicExportToAzure() {
        at ResourcesPage

        manageYourServerButton.click()
        waitFor { at ManageServerPage }

        menu.globalConfigurationLink.click()
        waitFor { at ManageServerGlobalConfigurationPage }

        periodicExport.createPeriodicExportToRemoteServerConfiguration(
            true,
            ManageServerPeriodicExport.REMOTE_SERVER_AZURE,
            "test-azure",
            "AWSAccount",
            "AWSKey",
            ManageServerPeriodicExport.AWS_REGION_US_EAST_1,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_MINUTES,
            1,
            ManageServerPeriodicExport.INTERVAL_OPTION_DAYS
        )

        periodicExport.deletePeriodicExportGlobalConfiuration()
    }

}
