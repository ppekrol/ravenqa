package net.ravendb.modules

import geb.Module


class DeleteDatabaseModalDialog extends Module {

    static content = {
        header { $("h4", text:"Sure?") }
        confirmButton { $("button", text:"Yep, delete") }
    }
}
