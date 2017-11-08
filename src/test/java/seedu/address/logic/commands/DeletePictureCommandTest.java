package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

public class DeletePictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeletePictureCommand deletePictureCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(DeletePictureCommand.MESSAGE_DELPIC_SUCCESS, personToUpdate.getName());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeProfilePicture(personToUpdate);

        assertCommandSuccess(deletePictureCommand, model, expectedMessage, expectedModel);
        assertTrue(personToUpdate.getAsText().equals(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getAsText()));
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeletePictureCommand deletePictureCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeletePictureCommand deletePictureCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deletePictureCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeletePictureCommand deletePictureFirstCommand = new DeletePictureCommand(INDEX_FIRST_PERSON);
        DeletePictureCommand deletePictureSecondCommand = new DeletePictureCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deletePictureFirstCommand.equals(deletePictureFirstCommand));

        // same values -> returns true
        DeletePictureCommand deletePictureFirstCommandCopy = new DeletePictureCommand(INDEX_FIRST_PERSON);
        assertTrue(deletePictureFirstCommandCopy.equals(deletePictureFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletePictureFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePictureFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deletePictureFirstCommand.equals(deletePictureSecondCommand));
    }

    /**
     * Returns a {@code DeletePictureCommand} with the parameter {@code index}.
     */
    private DeletePictureCommand prepareCommand(Index index) {
        DeletePictureCommand deletePictureCommand = new DeletePictureCommand(index);
        deletePictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletePictureCommand;
    }
}
