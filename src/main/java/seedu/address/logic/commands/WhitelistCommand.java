package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WHITELISTED_PERSONS;

//@@author jaivigneshvenugopal
/**
 * Lists all persons who have cleared their debts.
 */
public class WhitelistCommand extends Command {

    public static final String COMMAND_WORD = "whitelist";
    public static final String COMMAND_WORD_ALIAS = "wl";

    public static final String MESSAGE_SUCCESS = "Listed all debtors "
            + "who have cleared their debts";


    @Override
    public CommandResult execute() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        String currentList = listObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }
}
