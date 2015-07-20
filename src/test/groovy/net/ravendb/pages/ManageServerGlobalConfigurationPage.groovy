package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.ManageServerMenu
import net.ravendb.modules.YesNoModalDialog


class ManageServerGlobalConfigurationPage extends Page {

    final static String INTERVAL_OPTION_MINUTES = "minutes"
    final static String INTERVAL_OPTION_HOURS = "hours"
    final static String INTERVAL_OPTION_DAYS = "days"

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
        periodicExportFullBackupIntervalSelect { $("label", text:"Full Backup Interval:").siblings()[1].$("select") }
    }

    def createPeriodicExportToFilesystemConfiguration(
        boolean enabled,
        boolean onDiskExport,
        String onDiskFolder,
        int incrementalBackupInterval,
        String incrementalBackupIntervalUnit,
        int fullBackupInterval,
        String fullBackupIntervalUnit
        ) {
        createGlobalConfigurationForPeriodicExportButton.click()
        waitFor { periodicExportEnabledButton.displayed }

        periodicExportEnabledButton.click()
        enabled ? periodicExportEnabledYes.click() : periodicExportEnabledNo.click()
        periodicExportOnDiskExportButton.click()
        onDiskExport ? periodicExportOnDiskExportYes.click() : periodicExportOnDiskExportNo.click()
        periodicExportOnDiskExportFolder = onDiskFolder

        periodicExportIncrementalBackupIntervalInput = incrementalBackupInterval
        periodicExportIncrementalBackupIntervalSelect = incrementalBackupIntervalUnit

        periodicExportFullBackupIntervalInput = fullBackupInterval
        periodicExportFullBackupIntervalSelect = fullBackupIntervalUnit

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
}
