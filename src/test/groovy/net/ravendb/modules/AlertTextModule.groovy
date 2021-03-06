package net.ravendb.modules

import geb.Module


class AlertTextModule extends Module {

    static content = {
        alert(required:false) { $("div.studio-alerts") }
    }

    def containsMessage(CharSequence message) {
        alert.firstElement().getAttribute("innerHTML").contains(message)
    }

    def waitForMessage(CharSequence message) {
        waitFor(10, 0.1) {
            containsMessage(message)
        }
    }
}
