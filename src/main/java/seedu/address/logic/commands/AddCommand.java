package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.InferDateUtil;
import seedu.address.commons.util.InferTimeUtil;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Adds a task to the task manager.
 */
// @@author A0135787N-reused
public class AddCommand extends Command {

	public static final String COMMAND_WORD = "add";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. \n"
			+ "Parameters: CONTENT sd/DATE[dd-mm-yyyy] ed/DATE[dd-mm-yyyy] st/time[HH:mm] et/endTime[HH:mm] [#TAG]...\n"
			+ "Note: order and presence of parameters after CONTENT do not matter. \n" + "Example: " + COMMAND_WORD
			+ " do this task manager sd/20-10-2016 ed/20-10-2016 st/13:00 et/16:00 #shaglife #wheregottime";

	private static final String MESSAGE_SUCCESS = "New task added: %1$s";
	private static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
	private static final String MESSAGE_STARTENDDATE_CONSTRAINTS = "Start date must be added";
	private static final String MESSAGE_STARTENDTIME_CONSTRAINTS = "Start time must be added";
	private static final String MESSAGE_ENDDATETIME_CONSTRAINTS = "End date must have a corresponding end time, vice versa";

	private final ReadOnlyTask toAdd;

	private TaskDate dateToAdd;
	private TaskTime timeToAdd;
	private TaskDate enddateToAdd;
	private TaskTime endtimeToAdd;

	/**
	 * Convenience constructor using raw values.
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 * @throws ParseException
	 */

	public AddCommand(String content, String date, String endDate, String time, String endTime, Integer duration,
			Set<String> tags) throws IllegalValueException, ParseException {
		assert content != null;

		isValidTimeDate(date, endDate, time, endTime);

		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
			tagSet.add(new Tag(tagName));
		}

		// Infer date and time
		if (date != null) {
			dateToAdd = new TaskDate(date);
		} else if (date == null) {
			InferDateUtil idu = new InferDateUtil(content);
			if (idu.findDate()) {
				dateToAdd = new TaskDate(idu.getDate());
			} else {
				dateToAdd = new TaskDate();
			}
		}
		// check null for date and time
		if (endDate == null) {
			enddateToAdd = new TaskDate();
		} else
			enddateToAdd = new TaskDate(endDate);

		if (time != null) {
			timeToAdd = new TaskTime(time);
			if (endTime != null) {
				endtimeToAdd = new TaskTime(endTime);
			}
		} else if (time == null && endTime == null) {
			InferTimeUtil itu = new InferTimeUtil(content);
			if (itu.findTimeToTime()) {
				timeToAdd = new TaskTime(itu.getStartTime());
				endtimeToAdd = new TaskTime(itu.getEndTime());
			} else if (itu.findTime()) {
				timeToAdd = new TaskTime(itu.getTime());
				endtimeToAdd = new TaskTime();
			} else {
				timeToAdd = new TaskTime();
				endtimeToAdd = new TaskTime();
			}
		}

		if (duration != null) {
			this.toAdd = new RecurringTask(new Content(content), dateToAdd, timeToAdd, duration,
					new UniqueTagList(tagSet));
		} else if(endtimeToAdd != null || enddateToAdd != null) {
				this.toAdd = new Task(new Content(content), dateToAdd, enddateToAdd, timeToAdd, endtimeToAdd,
						new UniqueTagList(tagSet));
			}
		else {
			this.toAdd = new Task(new Content(content), dateToAdd, timeToAdd, new UniqueTagList(tagSet));
		}
	}

	@Override
	public CommandResult execute() {
		assert model != null;
		try {
			model.addTask(toAdd);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
		} catch (UniqueTaskList.DuplicateTaskException e) {
			return new CommandResult(MESSAGE_DUPLICATE_TASK);
		}

	}

	public static void isValidTimeDate(String startDate, String endDate, String startTime, String endTime)
			throws IllegalValueException {
		if ((endDate != null && endTime == null) || (endDate == null && endTime != null)) {
			throw new IllegalValueException(MESSAGE_ENDDATETIME_CONSTRAINTS);
		}

		else if (endDate != null && endTime != null) {
			hasStartDate(startDate);
			hasStartTime(startTime);
		}
	}

	public static void hasStartDate(String startDate) throws IllegalValueException {
		if (startDate == null)
			throw new IllegalValueException(MESSAGE_STARTENDDATE_CONSTRAINTS);
	}

	public static void hasStartTime(String startTime) throws IllegalValueException {
		if (startTime == null)
			throw new IllegalValueException(MESSAGE_STARTENDTIME_CONSTRAINTS);
	}
}
