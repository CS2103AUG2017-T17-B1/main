package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToNearbyListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using last displayed index from currently selected person's nearby list.
 */
public class NearbyCommand extends Command {

    public static final String COMMAND_WORD = "nearby";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the currently selected person's"
            + "nearby listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NEARBY_PERSON_SUCCESS = "Selected nearby Person: %1$s";

    private final Index targetIndex;

    public NearbyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToNearbyListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_NEARBY_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand // instanceof handles nulls
                && this.targetIndex.equals(((NearbyCommand) other).targetIndex)); // state check
    }
}
