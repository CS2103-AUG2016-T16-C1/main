package hard2do.taskmanager.testutil;

import java.text.ParseException;
import java.util.ArrayList;

import hard2do.taskmanager.commons.exceptions.IllegalValueException;
import hard2do.taskmanager.model.tag.Tag;
import hard2do.taskmanager.model.tag.UniqueTagList;
import hard2do.taskmanager.model.tag.UniqueTagList.DuplicateTagException;
import hard2do.taskmanager.model.task.*;

/**
 * A mutable task object. For testing only.
 */
// @@author A0139523E-reused
public class TestTask implements ReadOnlyTask {

    private Content content;
    private TaskDate taskdate;
    private TaskDate enddate;
    private TaskTime tasktime;
    private TaskTime endtime;
    private boolean done;
    private boolean important;
    private Integer duration;

    private UniqueTagList tags;

    public TestTask() {
        tags = new UniqueTagList();
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setDate(TaskDate taskdate) {
        this.taskdate = taskdate;
    }

    public void setEndDate(TaskDate enddate) {
        this.enddate = enddate;
    }

    public void setTime(TaskTime tasktime) {
        this.tasktime = tasktime;
    }

    public void setEndTime(TaskTime endtime) {
        this.endtime = endtime;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public Content getContent() {
        return content;
    }

    @Override
    public TaskDate getDate() {
        return taskdate;
    }

    @Override
    public TaskDate getEndDate() {
        return enddate;
    }

    @Override
    public TaskTime getTime() {
        return tasktime;
    }

    @Override
    public TaskTime getEndTime() {
        return endtime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return getAsText0();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getContent().value + " ");
        sb.append("sd/" + this.getDate().dateString + " ");
        sb.append("ed/" + this.getEndDate().dateString + " ");
        sb.append("st/" + this.getTime().timeString + " ");
        sb.append("et/" + this.getEndTime().timeString + " ");
        if (this.getDuration() != null) sb.append("r/" + this.getDuration()+" ");
        this.getTags().getInternalList().stream().forEach(s -> sb.append("#" + s.tagName + " "));
        return sb.toString();
    }

    @Override
    public boolean getDone() {
        return done;
    }

    @Override
    public boolean setDone() {
        if (!done)
            done = true;
        else
            return false;
        return true;
    }

    @Override
    public Integer getDuration() {
        return duration;
    }
    //@@author A0141054W
    @Override
    public boolean addTags(ArrayList<String> tagsToAdd) throws DuplicateTagException, IllegalValueException {
        UniqueTagList newList = new UniqueTagList();
        for (String t : tagsToAdd) {
            newList.add(new Tag(t));
        }
        newList.mergeFrom(tags);
        setTags(newList);
        return true;
    }

    @Override
    public boolean deleteTags(ArrayList<String> tagsToDelete) throws DuplicateTagException, IllegalValueException {
        UniqueTagList newList = new UniqueTagList(tags);
        for(String t : tagsToDelete){
            newList.remove(new Tag(t));
        }
        setTags(newList);
        return true;
    }
    //@@author

    /**
     * Replaces this task's tags with the tags in the argument tag list.
     */
    public void setTags(UniqueTagList replacement) {
        tags.setTags(replacement);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean setUndone() {
        if (done)
            done = false;
        else
            return false;
        return true;
    }

    @Override
    public boolean setImportant() {
        if (!important) important = true;
        return true;
    }
    
    @Override
    public boolean setUnimportant() {
        if (important) important = false;
        else return false;
        return true;
    }
    
    @Override
    public boolean getImportant() {
        return important;
    }

    @Override
    public boolean setNext() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean setDate(String date) throws IllegalValueException, ParseException{
        this.getDate().dateString = date;
        this.taskdate = new TaskDate(date);
        return true;
    }
    @Override
    public boolean setEndDate(String newDate) throws IllegalValueException, ParseException{
        this.enddate = new TaskDate(newDate);
        return true;
    }
    @Override
    public boolean setTime(String time) throws IllegalValueException, ParseException{
        this.tasktime = new TaskTime(time);
        return true;
    }
    @Override
    public boolean setEndTime(String time) throws IllegalValueException, ParseException{
        this.endtime = new TaskTime(time);
        return true;
    }
    @Override
    public boolean setContent(String newContent) throws IllegalValueException{
        this.content = new Content(newContent);
        return true;
    }

}
