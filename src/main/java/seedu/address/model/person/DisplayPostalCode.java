package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

//@@author khooroko
/**
 * Represents a displayable version of a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDisplayPostalCode(String)}
 */
public class DisplayPostalCode {

    public static final String DISPLAY_POSTAL_CODE_VALIDATION_REGEX = "^S\\d{6}";
    public static final String MESSAGE_DISPLAY_POSTAL_CODE_CONSTRAINTS = "Display postal code must be an S followed"
            + "by 6 digits";
    public static final String DISPLAY_PREFIX_POSTAL_CODE = "S";

    public final String value;

    /**
     * Creates a display only postal code from the given postal code.
     */
    public DisplayPostalCode(PostalCode postalCode) {
        requireNonNull(postalCode);
        String displayPostalCode = DISPLAY_PREFIX_POSTAL_CODE + postalCode.toString();
        if (isValidDisplayPostalCode(displayPostalCode)) {
            throw new AssertionError(MESSAGE_DISPLAY_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = displayPostalCode;
    }

    /**
     * Returns true if a given string is a valid person postal code.
     */
    public static boolean isValidDisplayPostalCode(String test) {
        return test.matches(DISPLAY_POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
