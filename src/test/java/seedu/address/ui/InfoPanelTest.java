package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoDisplaysPerson;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InfoPanelHandle;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.ModelManager;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
public class InfoPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InfoPanel infoPanel;
    private InfoPanelHandle infoPanelHandle;

    private Logic logicStub;

    @Before
    public void setUp() {
        logicStub = new LogicManager(new ModelManager());
        ListObserver.init(new ModelManager());
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> infoPanel = new InfoPanel(logicStub));
        uiPartRule.setUiPart(infoPanel);

        infoPanelHandle = new InfoPanelHandle(infoPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default info panel
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getAddress());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getAddressField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDebt());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getTotalDebt());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDebtField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getTotalDebtField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getInterest());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getInterestField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPostalCode());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getPostalCodeField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getCluster());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getClusterField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getName());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getEmail());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getEmailField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getHandphone());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getHandphoneField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getHomePhone());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getHomePhoneField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getOfficePhone());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getOfficePhoneField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDateBorrow());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDateBorrowField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDeadline());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDeadlineField());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDateRepaid());
        assertEquals(MESSAGE_EMPTY_STRING, infoPanelHandle.getDateRepaidField());
        assertEquals(new ArrayList<>(), infoPanelHandle.getTags());
        infoPanelHandle.rememberSelectedPersonDetails();

        // associated info of a person
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, ALICE);
        assertTrue(infoPanelHandle.isSelectedPersonChanged());
        infoPanelHandle.rememberSelectedPersonDetails();

        // asserts that no change is registered when same person is clicked
        postNow(selectionChangedEventStub);
        assertInfoDisplay(infoPanel, ALICE);
        assertFalse(infoPanelHandle.isSelectedPersonChanged());

        // associated info of next person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 1));
        postNow(selectionChangedEventStub);
        assertTrue(infoPanelHandle.isSelectedPersonChanged());
        assertInfoDisplay(infoPanel, BENSON);
    }

    @Test
    public void equals() {
        infoPanel = new InfoPanel(logicStub);

        // test .equals() method for two same objects
        assertTrue(infoPanel.equals(infoPanel));

        // test .equals() method for an object of different type
        assertFalse(infoPanel.equals(infoPanelHandle));

        InfoPanel expectedInfoPanel = new InfoPanel(logicStub);

        assertTrue(infoPanel.equals(expectedInfoPanel));

        infoPanel.loadPersonInfo(ALICE);
        assertFalse(infoPanel.equals(expectedInfoPanel));

        expectedInfoPanel.loadPersonInfo(ALICE);
        assertTrue(infoPanel.equals(expectedInfoPanel));
    }

    /**
     * Asserts that {@code infoPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertInfoDisplay(InfoPanel infoPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        InfoPanelHandle personInfoHandle = new InfoPanelHandle(infoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoDisplaysPerson(expectedPerson, personInfoHandle);
    }
}
