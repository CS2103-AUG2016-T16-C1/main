# User Guide

* [Quick Start](#quick-start)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.

1. Download the latest `Hard2Name.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Hard2Do list.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks to do in the future
   * **`add`**` Go to tutorial d/10.6 t/1200 c/take the laptop ` :
     adds a task of `Go to tutorial` to the Hard2Do.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
   * **`edit`**` 3 t/1300 ` :
      updates the time in the 3rd task shown in the current list
6. Refer to the [Features](#features) section below for details of each command.<br>


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of parameters is fixed.

#### Viewing help : `help`
Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

#### Adding a person: `add`
Adds a task to the to-do list <br>
Format: `add c/task d/DD-MM-YYYY t/HH:MM #tag`

Examples:
* `add c/Go to CS tutorial d/10-6-2016 t/12:00 #takethelaptop`
* `add c/Watch movie d/10-06-2016 t/12:01 #gotmoney`

#### Listing all tasks : `list`
Shows a list of all tasks in the future.<br>
Format: `list`

#### Finding all tasks containing any keyword in their content: `find`
Finds tasks whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `cs` will not match `CS`
> * The order of the keywords does not matter. e.g. `CS tut` will match `tut CS`
> * Only the content of the task is searched.
> * Only full words will be matched e.g. `CS` will not match `CSS`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `CS` will match `CS tutorial`

Examples:
* `find tutorial`<br>
  Returns `Go to CS tutorial` but not `tutorial`
* `find CS EE CEG`<br>
  Returns ANY tasks having content `CS`, `EE`, or `CEG`

#### Deleting a task : `delete`
Deletes the specified task from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`.
  The index refers to the index number shown in the current listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the address book.
* `find tutorial`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

#### Select a task : `select`
Selects the task identified by the index number used in the last task listing.<br>
Format: `select INDEX`

> Selects the task and loads the detail of the task at the specified `INDEX`.
  The index refers to the index number shown in the current listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `select 2`<br>
  Selects the 2nd task in the to-do list.
* `find CS` <br>
  `select 1`<br>
  Selects the 1st task in the results of the `find` command.

#### Edit a task : `edit`
Edits the current selected task.<br>
Format: `edit DETAIL/NEW_DETAIL_INFO [OTHER_DETAILS/OTHER_NEW_DETAILS]`

> Edits the current selected task and updates the relevant `DETAIL`. <br>
  The detail refers to a specific detail of the selected task.<br>
  The detail **must be a valid detail** d/, t/, c/ . <br>
  Repetition of detail type is not allowed..

Examples:
* `list`<br>
  `select 2` <br>
  `edit /t1800`<br>
  Updates the time in the task selected by the `select` command to 1800.
* `list` <br>
  `select 3`<br>
  `edit d/2016.10.07 t/1800 c/update details`
  Updates all details in the task selected by the `select` command.

#### Clearing all entries : `clear`
Clears all entries from the address book.<br>
Format: `clear`  

#### Exiting the program : `exit`
Exits the program.<br>
Format: `exit`  

#### Saving the data
Hard2Do list data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Hard2Do list folder.

## Command Summary

Command | Format  
-------- | :--------
Add | `add TASK d/YYYY.MM.DD t/HHRR c/BALABALA.`
Clear | `clear`
Delete | `delete INDEX`
Find | `find KEYWORD [MORE_KEYWORDS]`
List | `list`
Help | `help`
Select | `select INDEX`
Edit | `edit DETAIL/NEW_DETAIL [OTHER_DETAILS/OTHER_NEW_DETAILS]`
