package net.ravendb.pages

import geb.Page
import net.ravendb.modules.TopNavigationBar


class IndexMergeSuggestionsPage extends Page {

	static at = {
		header
	}

    static content = {
		topNavigation { module TopNavigationBar }

		header { $("h4", text:"Suggestions") }
        collapseUnmergableLink { $("a[href^='#collapseUnmergable']") }
        unmergablePanel { $("div.panel-collapse.collapse.in#collapseUnmergable") }
    }
}
