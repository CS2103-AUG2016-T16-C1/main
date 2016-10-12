package seedu.address.model.person;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import seedu.address.commons.exceptions.IllegalValueException;

public class TaskDate {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Task date should only follow this format dd-mm-yyyy";
    public static final String TASKDATE_VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";

    public final Date value;
    public final String dateString;
    

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     * @throws ParseException 
     */
    public TaskDate(String dateString) throws IllegalValueException, ParseException {
        assert dateString != null;
        this.dateString = dateString.trim();
        if (!isValidTaskDate(dateString)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = simpleDateFormat.parse(dateString);
        this.value = date;

    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidTaskDate(String test) {
        return test.matches(TASKDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        //return value.toString();
    	return dateString;
    	
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instance of handles nulls
                && this.value.equals(((TaskDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
