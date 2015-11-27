package net.ravendb.modules

import geb.Module


class CreateEncryptionModalDialog extends Module {

    static content = {
        header { $("h4", text:"Create Encryption") }

        saveButton { $("button", text:"Save") }
    }
}
