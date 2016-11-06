package guitests;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
//@@author A0135787N-reused
public class ClearCommandTest extends TaskManagerGuiTest {

    @Test
    public void testClear_nonEmptyList_listCleared() {
        //verify a non-empty list can be cleared
        assertTrue(taskListPanel.isListMatching(td.getTypicalTasks()));
        assertClearCommandSuccess();
    }
    
    @Test
    public void testClear_afterCommands_listCleared() {
        //verify other commands can work after a clear command
        commandBox.runCommand(td.homework.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.homework));
        commandBox.runCommand("delete 1");
        assertListSize(0);
    }
    
    @Test
    public void testClear_emptyList_listCleared() {
        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertResultMessage("Task Manager has been cleared!");
    }
}
