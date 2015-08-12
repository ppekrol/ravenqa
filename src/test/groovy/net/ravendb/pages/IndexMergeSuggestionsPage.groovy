package net.ravendb.pages

import geb.Page


class IndexMergeSuggestionsPage extends Page {

	static at = {
		header
	}

    static content = {
		header { $("h4", text:"Suggestions") }
    }

}
