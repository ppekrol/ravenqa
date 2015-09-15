package net.ravendb.pages

import geb.Page


class IndexTermsPage extends Page {

	final static String TERMS_NAME_COMPANY = "Company"
	final static String TERMS_NAME_COUNT = "Count"
	final static String TERMS_NAME_TOTAL = "Total"
	public final static int TERMS_COUNT = 280

	static at = {
		termsPanel
	}

    static content = {
		termsPanel { $('.panel.panel-default a') }
		termsItemsCount { $('ul.list-group .list-group-item') }
    }

	def getTermsLink(CharSequence name) {
		def container
		termsPanel.each {
			if(it.getAttribute("innerHTML").contains(name)) {
				container = it
			}
		}
		container
	}

	int getRowsCount() {
		int rowsCount = 0
		termsItemsCount.each {
			if(!it.@style.contains("display: none")) {
				rowsCount += 1
			}
		}

		return rowsCount
	}
}
