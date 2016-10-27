package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.parser.Parser;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.storage.Storage;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * The main LogicManager of the app.
 */
//@@author A0139523E-reused
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Parser parser;
    private final Config config;

    public LogicManager(Model model, Storage storage, Config config) {
        this.model = model;
        this.parser = new Parser();
        this.config = config;
    }

    @Override
    public CommandResult execute(String commandText) throws ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parser.parseCommand(commandText);
        command.setData(model, config);
        return command.execute();
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }
}
