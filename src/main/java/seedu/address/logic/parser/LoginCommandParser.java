package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.ui.LoginView.isShowingLoginView;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author jelneo
/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";
    public static final String EMPTY_USERNAME_MESSAGE = "Username cannot be empty";
    public static final String EMPTY_PASSWORD_MESSAGE = "Password cannot be empty";

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns a LoginCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (isShowingLoginView()) {
            return parseForGui(args);
        }

        try {
            String[] argsList = trimmedArgs.split(ONE_OR_MORE_SPACES_REGEX);
            // if incorrect number of arguments are supplied, throw ArrayOutOfBoundsException
            if (argsList.length != 2) {
                throw new ArrayIndexOutOfBoundsException();
            }
            Username username = ParserUtil.parseUsername(argsList[0]);
            Password password = ParserUtil.parsePassword(argsList[1]);
            return new LoginCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        } catch (ArrayIndexOutOfBoundsException aiofoe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }
    }

    /**
     * This method is used to parse arguments for GUI login.
     * A separate {@code parse} method is needed to check if either username or password field is empty and display
     * the correct error message.
     * Parses the given {@code String} of arguments obtained from login GUI in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LoginCommand parseForGui(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (argMultimap.getValue(PREFIX_USERNAME).get().isEmpty()) {
            throw new ParseException(EMPTY_USERNAME_MESSAGE);
        }
        if (argMultimap.getValue(PREFIX_PASSWORD).get().isEmpty()) {
            throw new ParseException(EMPTY_PASSWORD_MESSAGE);
        }
        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
            return new LoginCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage());
        }
    }
}
