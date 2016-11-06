package seedu.address.ui;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.Task;

//@@author A0141054W

/**
 * The task details on the right panel
 * Able to do click and edit
 */
public class TaskDetail extends UiPart {
    private final Logger logger = LogsCenter.getLogger(TaskDetail.class);
    private static final String FXML = "TaskDetail.fxml";
    private Logic logic;
    private String newContent;
    private String formattedString;
    private int index;
    private ReadOnlyTask task;
    private LocalDate newDate;
    private LocalTime newTime;
    private ResultDisplay resultDisplay;
    private CommandResult mostRecentResult;

    private AnchorPane placeHolderPane;
    private AnchorPane taskDetailPane;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private VBox detailView;
    @FXML
    private JFXTextField content;
    @FXML
    private Label tags;
    @FXML
    private JFXDatePicker startTimePicker;
    @FXML
    private JFXDatePicker endTimePicker;

    public TaskDetail() {
    }

    public static TaskDetail load(Stage primaryStage, AnchorPane placeHolder, ResultDisplay resultDisplay,
            Logic logic) {
        TaskDetail detail = UiPartLoader.loadUiPart(primaryStage, placeHolder, new TaskDetail());
        detail.configure(resultDisplay, logic);
        detail.addToPlaceHolder();
        detail.initializeTextField();
        return detail;
    }

    private void configure(ResultDisplay resultDisplay, Logic logic) {
        this.logic = logic;

        this.resultDisplay = resultDisplay;
    }

    private void initializeTextField() {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        content.getValidators().add(validator);
        validator.setMessage("No Input Given");
        content.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    content.validate();
                }
            }
        });
        startTimePicker.timeProperty().addListener(new ChangeListener<LocalTime>() {

            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue,
                    LocalTime newValue) {
                try {
                    handleTimeChanged();
                } catch (ParseException e) {
                }
            }
                
        });
    }

    public void loadTaskDetail(ReadOnlyTask task, int index) {
        this.index = index + 1;
        this.task = task;
        this.fillTaskDetail();
    }
    
    
    public void fillTaskDetail() {
        content.setText(task.getContent().toString());
        
        if (DateTimeUtil.changeDateToLocalDate(task.getDate().getValue()) != null) {
            startDatePicker.setValue(DateTimeUtil.changeDateToLocalDate(task.getDate().getValue()));
        } else {
            startDatePicker.setValue(null);
        }
        
        if (DateTimeUtil.changeDateToLocalTime(task.getTime().getValue()) != null) {
            startTimePicker.setTime(DateTimeUtil.changeDateToLocalTime(task.getTime().getValue()));
        } else {
            startTimePicker.setTime(LocalTime.MIN);
        }
        
        if (DateTimeUtil.changeDateToLocalDate(task.getEndDate().getValue()) != null) {
            endDatePicker.setValue(DateTimeUtil.changeDateToLocalDate(task.getEndDate().getValue()));
        } else {
            endDatePicker.setValue(null);
        }
        
        if (DateTimeUtil.changeDateToLocalTime(task.getEndTime().getValue()) != null) {
            endTimePicker.setTime(DateTimeUtil.changeDateToLocalTime(task.getEndTime().getValue()));
        } else {
            endTimePicker.setTime(LocalTime.MIN);
        }
        tags.setText(task.tagsString());
    }
    
    @FXML
    private void handleContentChanged() throws ParseException {
        newContent = content.getText();
        mostRecentResult = logic.execute("edit " + index + " c/" + newContent);
        resultDisplay.postMessage(mostRecentResult.feedbackToUser);
        logger.info("Result: " + mostRecentResult.feedbackToUser);
    }

    @FXML
    private void handleDateChanged() throws ParseException {
        newDate = startDatePicker.getValue();
        formattedString = DateTimeUtil.changeLocalDateToFormattedString(newDate);
        if (formattedString.compareTo(task.getDate().toString()) != 0) {
            mostRecentResult = logic.execute("edit " + index + " sd/" + formattedString);
            resultDisplay.postMessage(mostRecentResult.feedbackToUser);
            logger.info("Result: " + mostRecentResult.feedbackToUser);
        }
    }
    
    @FXML
    private void handleTimeChanged() throws ParseException {
        newTime = startTimePicker.getTime();
        formattedString = DateTimeUtil.changeLocalTimeToFormattedString(newTime);
        System.out.println(formattedString + " " + task.getTime().toString()); 
        if (formattedString.compareTo(task.getTime().toString()) != 0) {
            mostRecentResult = logic.execute("edit " + index + " st/" + formattedString);
            resultDisplay.postMessage(mostRecentResult.feedbackToUser);
            logger.info("Result: " + mostRecentResult.feedbackToUser);
        }
    }

    private void addToPlaceHolder() {
        placeHolderPane.getChildren().add(detailView);
    }

    @Override
    public void setNode(Node node) {
        taskDetailPane = (AnchorPane) node;
    }

    @Override
    public void setPlaceholder(AnchorPane pane) {
        this.placeHolderPane = pane;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }

}
