package net.ravendb.modules

import geb.Module


class AreYouSureModalDialog extends Module {

    static content = {
        cancelButton { $("button", text:"Cancel") }
        okButton { $("button", text:"OK") }
    }
}
