package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size()
                + ", number of blacklisted persons " + data.getBlacklistedPersonList().size()
                + ", number of whitelisted persons " + data.getWhitelistedPersonList().size()
                + ", number of overdue persons " + data.getOverduePersonList().size()
                + ", number of tags " + data.getTagList().size();
    }
}
