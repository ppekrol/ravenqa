package net.ravendb.test

import net.ravendb.pages.DocumentsPage
import net.ravendb.pages.IndexesPage
import net.ravendb.pages.NewTransformerPage
import net.ravendb.pages.TransformersPage

import org.testng.annotations.Test


class TransformersTest extends DatabaseWithSampleDataTestBase {

    /**
     * User can create, edit and delete simple transformer.
     * @Step Navigate to Indexes page.
     * @Step Navigate to Transformers page.
     * @Step Create, edit and delete.
     * @verification Transformer created, edited and deleted.
     */
    @Test(groups="Smoke")
    void canCreateEditDeleteSimpleTransformer() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }

        newTransformerButton.click()
        waitFor { at NewTransformerPage }

        // create transformer
        String transformerName = "i" + rand.nextInt()
        def transform = [
            """
            from result in results
            let category = LoadDocument(result.Category)
            select new {result.Name,result.PricePerUnit,Category = category.Name,CategoryDescription = category.Description}
            """.toString()
        ]
        createAndSaveTransformer(transformerName, transform)

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }
        waitFor { getTransformerLink(transformerName) }

        // edit transformer
        getTransformerLink(transformerName).click()
        waitFor { at NewTransformerPage }

        formatTransformer(transformerName)

        // delete transformer
        deleteTransformerButton.click()
        waitFor { deleteTransformerModalDialog.confirmButton.displayed }

        deleteTransformerModalDialog.confirmButton.click()
        messagesContainer.waitForMessage(NewTransformerPage.TRANSFORMER_DELETE_SUCCESS + transformerName)

        waitFor { at TransformersPage }
        waitFor { !getTransformerLink(transformerName) }
    }

    /**
     * User can paste transformer from JSON.
     * @Step Navigate to Indexes page.
     * @Step Navigate to Transformers page.
     * @Step Paste and save transformer from JSON.
     * @verification Transformer created properly.
     */
    @Test(groups="Smoke")
    void canPasteTransformerFromJson() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }

        String transformerName = "Testing"
        def json =
        """
            {
                "Transformer": {
                    "TransformResults": "from result in results\r\nselect result.Company",
                    "TransfomerId": 1,
                    "Temporary": false,
                    "Name": "Testing",
                    "LockMode": "Unlock"
                }
            }
        """.replaceAll("\\s"," ")
        createAndSaveTransformerFromJson(transformerName, json)
        waitFor { at NewTransformerPage }

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }
        waitFor { getTransformerLink(transformerName) }
    }

    /**
     * User can lock/unlock transformer.
     * @Step Navigate to Indexes page.
     * @Step Navigate to Transformers page.
     * @Step Lock transformer.
     * @Step Unlock transformer.
     * @verification Transformer locked and unlocked properly.
     */
    @Test(groups="Smoke")
    void canLockAndUnlockTransformer() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }

        // lock
        clickLockUnlockButton(TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY)
        waitFor { getLockIcon(TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY).displayed }
        messagesContainer.waitForMessage(TransformersPage.TRANSFORMER_SAVE_SUCCESS + TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY)

        // unlock
        clickLockUnlockButton(TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY)
        waitFor { !getLockIcon(TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY).displayed }
        messagesContainer.waitForMessage(TransformersPage.TRANSFORMER_SAVE_SUCCESS + TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY)
    }

    /**
     * User can copy transformer.
     * @Step Navigate to Indexes page.
     * @Step Navigate to Transformers page.
     * @Step Copy transformer.
     * @verification Transformer copied properly.
     */
    @Test(groups="Smoke")
    void canCopyTransformer() {
        at DocumentsPage

        topNavigation.indexesLink.click()
        waitFor { at IndexesPage }

        topNavigation.switchToTransformers()
        waitFor { at TransformersPage }

        copyTransformer(TransformersPage.TRANSFORMER_NAME_ORDERS_COMPANY)
        waitFor { at TransformersPage }
    }
}
