package net.ravendb.modules

import geb.Module


class DisableEnableResourceModalDialog extends Module {

    static content = {
        header { $("h4", text:"Sure?") }
        disableButton { $("button", text:"Yep, disable") }
        enableButton { $("button", text:"Yep, enable") }
    }
}
