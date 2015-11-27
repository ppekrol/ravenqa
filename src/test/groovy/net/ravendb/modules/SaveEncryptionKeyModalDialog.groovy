package net.ravendb.modules

import geb.Module

class SaveEncryptionKeyModalDialog extends Module {

    static content = {
        header { $("h3", text:"Save Your Encryption Key") }

        okButton { $("button", text:"OK") }
    }
}
