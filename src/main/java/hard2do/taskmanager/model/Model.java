package hard2do.taskmanager.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Set;

import hard2do.taskmanager.commons.core.UnmodifiableObservableList;
import hard2do.taskmanager.commons.exceptions.IllegalValueException;
import hard2do.taskmanager.model.History.StateNotFoundException;
import hard2do.taskmanager.model.tag.Tag;
import hard2do.taskmanager.model.task.ReadOnlyTask;
import hard2do.taskmanager.model.task.Task;
import hard2do.taskmanager.model.task.UniqueTaskList;
import hard2do.taskmanager.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * The API of the Model component.
 */
//@@author A0139523E-reused
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. 
     * @throws ParseException 
     * @throws IllegalValueException */
    void resetData(ReadOnlyTaskManager newData) throws IllegalValueException, ParseException;

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Adds the given task */
    void addTask(ReadOnlyTask task) throws UniqueTaskList.DuplicateTaskException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);
    
    /** Updates the filter of the filtered task list to filter by the closest edit distance to given string input*/
    void updateFilteredTaskList(String toFind);
    
    /** Updates the filter of the filtered task list to filter by the given Tag*/
    void updateFilteredTaskList(Tag tagToFind);
    
    /** Edit the given task. 
     * @throws ParseException 
     * @throws IllegalValueException */
	void editTask(ReadOnlyTask target, String newDate, String newEndDate, String newTime, String newEndTime, String newContent) 
			throws TaskNotFoundException, ParseException, IllegalValueException;

    //@@author A0147989B
    /** Fetch the next date of a recurring task. */
    void nextTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;

    /** Mark the given task as done. */
    void doneTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Mark the given task as undone. */
    void undoneTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Mark the given task as important. */
    void importantTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    
    /** Mark the given task as unimportant. */
    void unimportantTask(ReadOnlyTask target) throws UniqueTaskList.TaskNotFoundException;
    //@@author 
    
	void save(String commandType) throws IllegalValueException, ParseException;

	void undo() throws StateNotFoundException;

	History getHistory();

	void updateFilteredListToShowDone();
	
	void updateFilteredListToShowUndone();
	
	void updateFilteredListToShowImportant();
	
	void updateFilteredListToShowUnimportant();


	void addTags(ReadOnlyTask target, ArrayList<String> newTag) 
			throws TaskNotFoundException, ParseException, IllegalValueException;

	void deleteTags(ReadOnlyTask taskToDelTags, ArrayList<String> tagsToDel) 
			throws TaskNotFoundException, ParseException, IllegalValueException;

	

}
