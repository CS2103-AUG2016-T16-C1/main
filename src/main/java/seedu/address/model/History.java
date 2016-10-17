package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.Content;
import seedu.address.model.person.Task;
import seedu.address.model.person.TaskDate;
import seedu.address.model.person.TaskTime;
import seedu.address.model.person.UniqueTaskList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

import java.text.ParseException;
import java.util.*;

/**
 * A list of states that records previous states of the task manager before changes.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */


public class History {
	
	
	public static class StateNotFoundException extends Exception {} 
	
	
	private Stack<List<Task>> taskStates;
	private Stack<Collection<Tag>> tagStates;
	private Stack <String> messages;
	private List<Task> tasksState;
	private Collection<Tag> tagsState;
	
	public final String UNDO_ADD_MESSAGE = "Added Task has been deleted";
	public final String UNDO_DELETE_MESSAGE = "Removed Task has been re-added";
	public final String EMPTY_HISTORY_MESSAGE = "No available commands can be undone";
	public final String UNDO_SORT_MESSAGE = "Sorting has been undone";
	
	
	History(){
		taskStates =  new Stack <List<Task>>(); 
		messages = new Stack<String>();
		tagStates = new Stack <Collection<Tag>>();
		tagsState = FXCollections.observableArrayList();
	}
	
	
	public void save(ObservableList<Task> stateToSave, ObservableList<Tag> tagsToSave, String commandType) throws IllegalValueException, ParseException{
		
		if (stateToSave.isEmpty())
			return;
		
		ObservableList<Task> newState = FXCollections.observableArrayList();
		
		//Create deep copy of tasks
		for(Task t : stateToSave){
			
			Set<Tag> tagSet = new HashSet<>();
	        for (Tag tag : t.getTags().toSet()) {
	            tagSet.add(new Tag(tag.tagName));
	        }
			
			
			newState.add( new Task( new Content(t.getContent().value), 
					new TaskDate(t.getDate().dateString),
					new TaskTime(t.getTime().timeString), 
					new UniqueTagList(tagSet))
					);
			if(t.getDone())
				newState.get(newState.size() - 1).setDone();
		}
		taskStates.push(newState);
		messages.push(commandType);
		
		if(!tagsToSave.isEmpty()){
			Collection <Tag> newTags = FXCollections.observableArrayList();
			for(Tag g : tagsToSave){
				newTags.add(new Tag(g.tagName));
			}
			tagStates.push(newTags);
		}
	}
	
	public void undo() throws StateNotFoundException{
		
		if(taskStates.isEmpty()) 
			throw new StateNotFoundException();
		
		tasksState = taskStates.pop();
		messages.pop();
		tagsState = tagStates.pop();
	}
	
	public List<Task> getPreviousTasks(){
		return tasksState;
	}
	
	public Collection<Tag> getPreviousTags(){
		return tagsState;
	}
}
