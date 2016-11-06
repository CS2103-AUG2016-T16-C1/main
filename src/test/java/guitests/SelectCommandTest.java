package guitests;

import org.junit.Test;

import hard2do.taskmanager.model.task.ReadOnlyTask;

import static org.junit.Assert.assertEquals;
//@@author A0141054W-reused
public class SelectCommandTest extends TaskManagerGuiTest {


    @Test
    public void testSelectTask_nonEmptyList_taskSelectedExpected() {

        assertSelectionInvalid(10); //invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); //first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); //last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); //a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); //invalid index
        //assertTaskSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void testSelectTask_emptyList_errorMessageExpected(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }
    
    //helper method
    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        //assertResultMessage("Selected Task: "+index);
        //assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        assertEquals(taskListPanel.getTask(index-1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
