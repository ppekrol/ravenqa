package net.ravendb.modules

import geb.Module


class CreateDatabaseModalDialog extends Module {

    static content = {
        databaseIcon { $("label[title='Database']") }
        databaseNameInput(required:false) { $("input#databaseName") }

        createButton { $("button", text:"Create") }
    }
}
