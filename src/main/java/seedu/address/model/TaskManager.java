package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Task;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.History.StateNotFoundException;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.person.UniqueTaskList.TaskNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**

 * Duplicates are not allowed (by .equals comparison)
 */
public class TaskManager implements ReadOnlyTaskManager {

    private UniqueTaskList tasks;
    private UniqueTagList tags;
    private History history;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
        history = new History();
    }

    public TaskManager() {}

    /**
     * Tasks and Tags are copied into this TaskManager 
     */
    public TaskManager(ReadOnlyTaskManager toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this task manager
     */
    public TaskManager(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
    }

    public static ReadOnlyTaskManager getEmptyTaskManager() {
        return new TaskManager();
    }

//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyTaskManager newData) throws IllegalValueException, ParseException {
        resetData(newData.getTaskList(), newData.getTagList());
    }
    
    public void save(String commandType) throws IllegalValueException, ParseException{
    	history.save(tasks.getInternalList(), tags.getInternalList(), commandType);
    } 
    
    public void undo() throws StateNotFoundException{
    	history.undo();
    	setTasks(history.getPreviousTasks());
    	setTags(history.getPreviousTags());
    }
//// task-level operations

    /**
     * Adds a task to Hard2Do.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
    	syncTagsWithMasterList(t);
        tasks.add(t);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task task) {
        final UniqueTagList taskTags = task.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        task.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
    	
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean editTask(int targetIndex, String newDate, String newTime, String newContent) 
    		throws UniqueTaskList.TaskNotFoundException, ParseException {
    	
        if (tasks.edit(targetIndex, newDate, newTime, newContent)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public boolean markTaskAsDone(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.done(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    public History getHistory(){
    	return history;
    }

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }
    
    public boolean addTags(ReadOnlyTask target, ArrayList<String> newTags) 
    		throws IllegalValueException, TaskNotFoundException{
    	
    	if(tasks.addTags( target, newTags)){
    		return true;
    	} else {
    		throw new UniqueTaskList.TaskNotFoundException();
    	}
		
	}
    
    public boolean deleteTags(ReadOnlyTask target, ArrayList<String> newTags) 
    		throws IllegalValueException, TaskNotFoundException{
    	
    	if(tasks.deleteTags( target, newTags)){
    		return true;
    	} else {
    		throw new UniqueTaskList.TaskNotFoundException();
    	}
		
	}

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskManager // instanceof handles nulls
                && this.tasks.equals(((TaskManager) other).tasks)
                && this.tags.equals(((TaskManager) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }

	
    

}
