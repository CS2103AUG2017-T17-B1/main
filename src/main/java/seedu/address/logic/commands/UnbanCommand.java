package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author jaivigneshvenugopal
/**
 * Removes a person identified using it's last displayed index from the blacklist.
 */
public class UnbanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unbans the currently selected person or the person identified by the index number used in the last "
            + "person listing from blacklist.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNBAN_PERSON_SUCCESS = "Removed %1$s from BLACKLIST";
    public static final String MESSAGE_UNBAN_PERSON_FAILURE = "%1$s is not BLACKLISTED!";

    private final Index targetIndex;

    public UnbanCommand() {
        this.targetIndex = null;
    }

    public UnbanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        String messageToDisplay = MESSAGE_UNBAN_PERSON_SUCCESS;

        ReadOnlyPerson personToUnban = selectPerson(targetIndex);

        try {
            if (personToUnban.isBlacklisted()) {
                model.removeBlacklistedPerson(personToUnban);
            } else {
                messageToDisplay = MESSAGE_UNBAN_PERSON_FAILURE;
            }
        } catch (PersonNotFoundException e) {
            assert false : "The target person is not in blacklist";
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, personToUnban.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnbanCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((UnbanCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((UnbanCommand) other).targetIndex))); // state check
    }
}
