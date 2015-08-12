package net.ravendb.modules

import geb.Module


class CopyIndexModalDialog extends Module {

    static content = {
		header { $("h4", text:"Copy Index") }
		closeButton { $("button", text:"Close") }
    }
}
