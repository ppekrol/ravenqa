package net.ravendb.modules

import geb.Module


class CreateResourceModalDialog extends Module {

    static content = {
        databaseIcon { $("label[title='Database']") }
        databaseNameInput(required:false) { $("input#databaseName") }
        filesystemIcon { $("label[title='File System']") }
        filesystemNameInput(required:false) { $("input#fileSystemName") }
        counterStorageIcon { $("label[title='Counter Storage']") }
        counterStorageNameInput(required:false) { $("input#counterStorageName") }
        timeSeriesIcon { $("label[title='Time Series']") }
        timeSeriesNameInput(required:false) { $("input#timeSeriesName") }

        databaseBundlesContainer { $("div[data-view='views/resources/createDatabase']") }
        compressionButton { databaseBundlesContainer.$("button[data-bind*='toggleCompressionBundle']") }
        encryptionButton { databaseBundlesContainer.$("button[data-bind*='toggleEncryptionBundle']") }
        expirationButton { databaseBundlesContainer.$("button[data-bind*='toggleExpirationBundle']") }
        periodicExportButton { databaseBundlesContainer.$("button[data-bind*='togglePeriodicExportBundle']") }
        quotasButton { databaseBundlesContainer.$("button[data-bind*='toggleQuotasBundle']") }
        replicationButton { databaseBundlesContainer.$("button[data-bind*='toggleReplicationBundle']") }
        scriptedIndexButton { databaseBundlesContainer.$("button[data-bind*='toggleScriptedIndexBundle']") }
        sqlReplicationButton { databaseBundlesContainer.$("button[data-bind*='toggleSqlReplicationBundle']") }
        versioningButton { databaseBundlesContainer.$("button[data-bind*='toggleVersioningBundle']") }

        filesystemBundlesContainer { $("div[data-view='views/resources/createFilesystem']") }
        filesystemEncryptionButton { filesystemBundlesContainer.$("button[data-bind*='toggleEncryptionBundle']") }
        filesystemVersioningButton { filesystemBundlesContainer.$("button[data-bind*='toggleVersioningBundle']") }

        createButton { $("button", text:"Create") }
    }
}
