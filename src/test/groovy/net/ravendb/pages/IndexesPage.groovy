package net.ravendb.pages

import geb.Page
import net.ravendb.modules.AlertTextModule
import net.ravendb.modules.DeleteResourceModalDialog
import net.ravendb.modules.TopNavigationBar


class IndexesPage extends Page {

	final static String INDEX_NAME_ORDERS_BY_COMPANY = "Orders/ByCompany"
	final static String INDEX_NAME_ORDERS_BY_COMPANY_LINK = "Orders%2FByCompany"
	final static String INDEX_NAME_ORDERS_TOTALS = "Orders/Totals"
	final static String INDEX_NAME_PRODUCT_SALES = "Product/Sales"

	final static String INDEX_TOGGLE_OPTION_DELETE = "Delete Index"
	final static String INDEX_TOGGLE_OPTION_DISABLED = "Disabled"

	final static String INDEX_DELETE_SUCCESS = "Deleted "
	final static String INDEX_SAVE_SUCCESS = "Saved "

	final static String TRASH_DROPDOWN_OPTION_DELETE_ALL_INDEXES = "Delete All Indexes"
	final static String TRASH_DROPDOWN_OPTION_DELETE_DISABLED_INDEXES = "Delete Disabled Indexes"
	final static String DELETE_ALL_INDEXES_SUCCESS = "Successfully deleted 3 indexes!"

    static at = {
        newIndexButton
    }

    static content = {
        topNavigation { module TopNavigationBar }
		deleteIndexModalDialog { module DeleteResourceModalDialog }
		alert { module AlertTextModule }

		//menu toolbar
		menuToolbar { $('.btn-toolbar') }
		trashButton { "i.fa-trash-o" }
		deleteDropdownMenu { "ul.dropdown-menu li" }
		collapseAllButton { $("a[title='Collapse all']") }
		expandAllButton { $("a[title='Expand all']") }

        newIndexButton { $("a[title='Add a new index (Alt+N)']") }

        indexesLinks(required:false) { $("a[href^='#databases/query/index/']") }

		indexRowContainer { $('.index-panel.panel.panel-default') }
		indexRowButtonSelector { "button" }
		indexRowLinkSelector { "li[role='presentation'] a" }
    }

    def getIndexLink(CharSequence name) {
        def link
        indexesLinks.each {
            if(it.@href.contains(name)) {
                link = it
            }
        }
        link
    }

	def getIndexContainer(CharSequence name) {
		def container
		indexRowContainer.each {
			if(it.getAttribute("innerHTML").contains(name)) {
				container = it
			}
		}
		container
	}

	def clickDropdownOption(CharSequence indexName, CharSequence optionName) {
		def container = getIndexContainer(indexName)
		container.find(indexRowButtonSelector).click()
		def link
		container.find(indexRowLinkSelector).each {
			if(it.getAttribute("innerHTML").contains(optionName)) {
				link = it
			}
		}
		assert link
		link.click()
	}

	def getTrashDropdownOption(CharSequence optionName) {
		def container = menuToolbar
		container.find(trashButton).click()
		def link
		container.find(deleteDropdownMenu).each {
			if(it.getAttribute("innerHTML").contains(optionName)) {
				link = it
			}
		}
		link
	}
}
