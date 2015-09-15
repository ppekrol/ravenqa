package net.ravendb.pages

import geb.Page


class IndexTermsPage extends Page {

	final static String TERMS_NAME_COMPANY = "Company"
	final static String TERMS_NAME_COUNT = "Count"
	final static String TERMS_NAME_TOTAL = "Total"
	public final static int TERMS_COUNT = 280

	static at = {
		termsPanelLinks.size() > 0
	}

    static content = {
        termsPanelLinks { $("div#accordion a[data-parent='#accordion']") }
        termsItems { $("ul[data-bind='foreach: terms']") }
    }

	def clickTermsLink(CharSequence name) {
		def container
		termsPanelLinks.each {
			if(it.text().equals(name)) {
				container = it
			}
		}

		if(container) {
            container.click()
        }
	}
}
