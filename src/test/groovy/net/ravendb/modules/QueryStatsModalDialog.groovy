package net.ravendb.modules

import geb.Module


class QueryStatsModalDialog extends Module {

	final static String QUERY_STATS_TOTAL_RESULTS = "89"
	final static String QUERY_STATS_STATUS = "Up to date"

    static content = {
		header { $("h4", text:"Query Stats") }
		okButton { $("button", text:"OK") }

		queryStatsContainer { $('span.col-sm-9') }
    }

	def getQueryStatsData(CharSequence name) {
		def container
		queryStatsContainer.each {
			if(it.getAttribute("innerHTML").contains(name)) {
				container = it
			}
		}
		container
	}
}
