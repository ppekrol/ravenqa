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

        createButton { $("button", text:"Create") }
    }
}
