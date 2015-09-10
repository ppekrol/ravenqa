package net.ravendb.modules

import geb.Module


class YesNoModalDialog extends Module {

    static content = {
        header { $("h4", text:"Sure?") }
        noButton { $("div.modal-footer button.btn-default") }
        yesButton { $("div.modal-footer button.btn-primary") }
    }
}
