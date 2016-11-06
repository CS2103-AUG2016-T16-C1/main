package guitests;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hard2do.taskmanager.commons.core.Messages;
import hard2do.taskmanager.commons.exceptions.IllegalValueException;
import hard2do.taskmanager.logic.commands.DoneCommand;
import hard2do.taskmanager.logic.commands.ImportantCommand;
import hard2do.taskmanager.logic.commands.NotdoneCommand;
import hard2do.taskmanager.model.tag.UniqueTagList.DuplicateTagException;
import hard2do.taskmanager.testutil.TestTask;

//@@author A0141054W
public class DoneCommandTest extends TaskManagerGuiTest {

    @Test
    public void testDone_differentIndex_updatedDoneListExpected() {
        TestTask[] currentList = td.getTypicalTasks();
        //done first item
        int targetIndex = 1;
        assertDoneTaskSuccess(targetIndex, currentList);
        
        //done a middle item
        commandBox.runCommand("list all");
        targetIndex = 4;
        assertDoneTaskSuccess(targetIndex, currentList);
        
        //undone first item
        commandBox.runCommand("list all");
        targetIndex = 4;
        assertUndoneTaskSuccess(targetIndex, currentList);
        
        //done already done item
        commandBox.runCommand("list all");
        assertDoneTaskSuccess(targetIndex, currentList);
        
    }
    
    @Test
    public void testDone_invalidIndex_errorMessageExpected() {
        //done a invalid index
        commandBox.runCommand("done " + "100");    
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
    }
    
    //helper method for main test
    private void assertDoneTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) throws IllegalStateException {
        TestTask taskToDone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        taskToDone.setDone();
        commandBox.runCommand("done " + targetIndexOneIndexed);

        //confirm marking the task as done, item is not in the undone list
        assertNull(taskListPanel.navigateToTask(taskToDone.getContent().value));
        //confirm the result message is correct
        assertResultMessage(String.format(DoneCommand.MESSAGE_DONE_TASK_SUCCESS, taskToDone));
        
        //confirm task is in the whole list
        //commandBox.runCommand("list");
        //assertNotNull(taskListPanel.navigateToTask(taskToDone.getContent().value));


    }
    
    //@@author A0147989B
    private void assertUndoneTaskSuccess(int targetIndexOneIndexed, final TestTask[] currentList) throws IllegalStateException {
        TestTask taskToUndone = currentList[targetIndexOneIndexed-1]; //-1 because array uses zero indexing
        taskToUndone.setUndone();
        commandBox.runCommand("notdone " + targetIndexOneIndexed);

        //confirm marking the task as not done
        assertEquals(taskToUndone.getDone(),false);
        
        //confirm the result message is correct
        assertResultMessage(String.format(NotdoneCommand.MESSAGE_NOTDONE_TASK_SUCCESS, taskToUndone));
        
    }
}
