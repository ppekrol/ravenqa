package net.ravendb.modules

import geb.Module


class AlertTextModule extends Module {

    static content = {
        alert { $("div.studio-alerts") }
    }
}
