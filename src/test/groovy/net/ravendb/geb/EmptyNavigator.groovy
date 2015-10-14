package net.ravendb.geb

import geb.Browser


class EmptyNavigator extends geb.navigator.EmptyNavigator {
    EmptyNavigator(Browser browser) {
        super(browser)
        assert false
    }
}
