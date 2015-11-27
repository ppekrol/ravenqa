package net.ravendb.modules

import geb.Module


class ResetIndexModalDialog extends Module {

    static content = {
        header { $("h4", text:"Reset index?") }
        confirmButton { $("button", text:"Yep, reset") }
    }
}
