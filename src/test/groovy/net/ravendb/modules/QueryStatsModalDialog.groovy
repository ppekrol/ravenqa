package net.ravendb.modules

import geb.Module


class QueryStatsModalDialog extends Module {

    static content = {
		header { $("h4", text:"Query Stats") }
		okButton { $("button", text:"OK") }
    }
}
