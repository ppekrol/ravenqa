package net.ravendb.pages

import sun.security.ssl.ServerNameExtension.ServerName;
import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.ManageServerMenu
import net.ravendb.modules.YesNoModalDialog


class ManageServerGlobalConfigurationPage extends Page {

    final static String INTERVAL_OPTION_MINUTES = "minutes"
    final static String INTERVAL_OPTION_HOURS = "hours"
    final static String INTERVAL_OPTION_DAYS = "days"

    final static String REMOTE_SERVER_GLACIER = "glacierVault"
    final static String REMOTE_SERVER_S3 = "s3bucket"
    final static String REMOTE_SERVER_AZURE = "azureStorage"

    final static String AWS_REGION_US_EAST_1 = "us-east-1"
    final static String AWS_REGION_US_WEST_1 = "us-west-1"
    final static String AWS_REGION_US_WEST_2 = "us-west-2"
    final static String AWS_REGION_EU_WEST_1 = "eu-west-1"
    final static String AWS_REGION_AP_NORTHEAST_1 = "ap-northeast-1"
    final static String AWS_REGION_AP_SOUTHEASTT_1 = "ap-southeast-1"
    final static String AWS_REGION_SA_EAST_1 = "sa-east-1"

    static at = {
        periodicExportTab
        menu
    }

    static content = {
        menu { module ManageServerMenu }
        messagesContainer { module AlertTextModule }
        yesNoDialog { module YesNoModalDialog }

        // tabs
        periodicExportTab { $("a", text:"Periodic export") }

        // tool bar
        saveButton { $("button[title='Save Changes(Alt+S)']") }

        // periodic export
        createGlobalConfigurationForPeriodicExportButton(required:false) { $("button", text:"Create global configuration for Periodic Export") }
        removeGlobalConfigurationForPeriodicExportButton(required:false) { $("button", text:"Remove global configuration for Periodic Export") }
        periodicExportEnabledButton(required:false) { $("label", text:"Enabled:").siblings()[0].$("button") }
        periodicExportEnabledYes(required:false) { periodicExportEnabledButton.siblings()[0].$("a", text:"Yes") }
        periodicExportEnabledNo(required:false) { periodicExportEnabledButton.siblings()[0].$("a", text:"No") }
        periodicExportOnDiskExportButton(required:false) { $("label", text:"On disk export:").siblings()[0].$("button") }
        periodicExportOnDiskExportYes(required:false) { periodicExportOnDiskExportButton.siblings()[0].$("a", text:"Yes") }
        periodicExportOnDiskExportNo(required:false) { periodicExportOnDiskExportButton.siblings()[0].$("a", text:"No") }
        periodicExportOnDiskExportFolder(required:false) { $("input#onDiskLocation") }
        periodicExportUploadToServerButton(required:false) { $("label", text:"Upload to remote server:").siblings()[0].$("button") }
        periodicExportUploadToServerYes(required:false) { periodicExportUploadToServerButton.siblings()[0].$("a", text:"Yes") }
        periodicExportUploadToServerNo(required:false) { periodicExportUploadToServerButton.siblings()[0].$("a", text:"No") }
        periodicExportIncrementalBackupIntervalInput(required:false) { $("input#incrementalBackupInterval") }
        periodicExportIncrementalBackupIntervalSelect { $("label", text:"Incremental Backup Interval:").siblings()[1].$("select") }
        periodicExportFullBackupIntervalInput(required:false) { $("input#fullBackupInterval") }
        periodicExportFullBackupIntervalSelect(required:false) { $("label", text:"Full Backup Interval:").siblings()[1].$("select") }
        periodicExportRemoteServerName(required:false) { $("div[data-bind='if: remoteUploadEnabled, visible: remoteUploadEnabled']").$("select") }
        periodicExportRemoteServerResourceNameInput(required:false) { $("div[data-bind='if: remoteUploadEnabled, visible: remoteUploadEnabled']").$("input")[0] }
        periodicExportAwsAccessKeyInput(required:false) { $("input#awsAccessKey") }
        periodicExportAwsSecretKeyInput(required:false) { $("input#awsSecretKey") }
        periodicExportAwsRegionSelect(required:false) { $("select#awsRegionEndpoint") }
    }

    def createPeriodicExportToFilesystemConfiguration(
        boolean enabled,
        String onDiskFolder,
        int incrementalBackupInterval,
        String incrementalBackupIntervalUnit,
        int fullBackupInterval,
        String fullBackupIntervalUnit
        ) {
        createGlobalConfigurationForPeriodicExportButton.click()
        waitFor { periodicExportEnabledButton.displayed }

        fillForm(
            enabled,
            true,
            onDiskFolder,
            false,
            null,
            null,
            null,
            null,
            null,
            incrementalBackupInterval,
            incrementalBackupIntervalUnit,
            fullBackupInterval,
            fullBackupIntervalUnit
            )

        waitFor { saveButton.displayed }
        saveButton.click()

        waitFor {
            saveButton.@disabled == "true"
            removeGlobalConfigurationForPeriodicExportButton.displayed
        }
    }

    def createPeriodicExportToRemoteServerConfiguration(
        boolean enabled,
        String serverName,
        String resourceName,
        String account,
        String secretKey,
        String awsRegion,
        int incrementalBackupInterval,
        String incrementalBackupIntervalUnit,
        int fullBackupInterval,
        String fullBackupIntervalUnit
        ) {
        createGlobalConfigurationForPeriodicExportButton.click()
        waitFor { periodicExportEnabledButton.displayed }

        fillForm(
            enabled,
            false,
            null,
            true,
            serverName,
            resourceName,
            account,
            secretKey,
            awsRegion,
            incrementalBackupInterval,
            incrementalBackupIntervalUnit,
            fullBackupInterval,
            fullBackupIntervalUnit
            )

        waitFor { saveButton.displayed }
        saveButton.click()

        waitFor {
            saveButton.@disabled == "true"
            removeGlobalConfigurationForPeriodicExportButton.displayed
        }
    }

    def deletePeriodicExportGlobalConfiuration() {
        removeGlobalConfigurationForPeriodicExportButton.click()
        waitFor { yesNoDialog.yesButton.displayed }

        yesNoDialog.yesButton.click()
        waitFor { createGlobalConfigurationForPeriodicExportButton.displayed }
    }

    private fillForm(
        boolean enabled,
        boolean onDiskExport,
        String onDiskFolder,
        boolean uploadToServer,
        String serverName,
        String resourceName,
        String account,
        String secretKey,
        String awsRegion,
        int incrementalBackupInterval,
        String incrementalBackupIntervalUnit,
        int fullBackupInterval,
        String fullBackupIntervalUnit
        ) {
        periodicExportEnabledButton.click()
        enabled ? periodicExportEnabledYes.click() : periodicExportEnabledNo.click()
        periodicExportOnDiskExportButton.click()

        onDiskExport ? periodicExportOnDiskExportYes.click() : periodicExportOnDiskExportNo.click()
        onDiskExport ? periodicExportOnDiskExportFolder = onDiskFolder : null

        periodicExportUploadToServerButton.click()
        if(uploadToServer) {
            periodicExportUploadToServerYes.click()
            periodicExportRemoteServerName = serverName
            periodicExportRemoteServerResourceNameInput = resourceName
            switch(serverName) {
                case this.REMOTE_SERVER_GLACIER:
                    periodicExportAwsAccessKeyInput = account
                    periodicExportAwsSecretKeyInput = secretKey
                    periodicExportAwsRegionSelect = awsRegion
                    break
            }
        }

        periodicExportIncrementalBackupIntervalInput = incrementalBackupInterval
        periodicExportIncrementalBackupIntervalSelect = incrementalBackupIntervalUnit

        periodicExportFullBackupIntervalInput = fullBackupInterval
        periodicExportFullBackupIntervalSelect = fullBackupIntervalUnit
    }
}
