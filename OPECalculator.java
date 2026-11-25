/**
 * @author: Ashley Strong
 * @date:11/19/2026
 * @version: 1.1
 * 
 * Last date of update: 11/25/2026
 * 
OPE Calculator v1.1 - Project Summary

********************************* Overview ********************************

OPE Calculator v1.1 is a JavaFX desktop application designed to calculate and track Overall Production Efficiency (OPE) 
at the shift level within a manufacturing environment. The application combines production calculations, historical record 
keeping, downtime tracking, and reporting features into a single user-friendly interface.

Originally conceived as a simple OPE calculator, the application evolved into a shift performance tracking system capable of 
collecting operational data for long-term analysis and continuous improvement initiatives.

********************************* Purpose *********************************

The application allows production personnel, supervisors, and team leaders to:

Calculate shift-level OPE metrics
Track Availability, Performance, and Quality
Record downtime events and root causes
Store historical shift data
Export records for Excel analysis
Review performance trends over time
Maintain production records in a standardized format

***** Technology Stack *****
 * Language *
Java
* Framework *
JavaFX
* File Storage *
CSV (Comma-Separated Values)
* Platform *
Windows Desktop
* Development Concepts Implemented *
Object-Oriented Programming
Event-Driven Programming
Exception Handling
File Input/Output
Data Validation
UI Design and Layout
Keyboard Accelerators
Dynamic File Management
Helper Method Refactoring
************************************************ Core Features **********************************************
***** Shift Information *****

* Users can document:

Shift Date
Shift (1st, 2nd, or 3rd)
Supervisor
Department

* Supported Departments:

3100
3500
3600
3700
6100
6500
7100
7500
7600
7700
8810
9400
9450
9600
9700

Department values are maintained using a centralized array for easy expansion.

***** Production Inputs *****

Users enter:

Scheduled Production Time
Planned Breaks
Downtime
Target Production
Actual Production
Rejected Units
Automated Calculations

The application calculates:

* Availability *

(Planned Production Time - Downtime) ÷ Planned Production Time

* Performance *

Actual Production ÷ Target Production

* Quality *

Good Units ÷ Total Units Produced

* OPE *

Availability × Performance × Quality

***** Result Visualization *****

Results are displayed using a KPI dashboard.

Color-coded OPE results provide immediate visual feedback:

OPE	Color
90%+	Green
80%-89.99%	Orange
Below 80%	Red

************************************* Downtime Tracking ******************************************

Version 1.1 introduced downtime documentation features.

***** Downtime Reasons *****

Users can select from:

Mechanical Failure
Electrical Failure
Material Shortage
Tool Change
Preventive Maintenance
Quality Hold
Operator Shortage
Training
Changeover
Other
Downtime Notes

A dedicated notes section allows users to record:

Specific failures
Corrective actions
Additional context
Shift observations

Downtime controls automatically enable only when downtime exceeds zero.

************************************************* Data Validation ***********************************************

The application performs extensive validation before calculations occur.

Examples include:

***** Numeric Validation *****

Rejects:

Negative values
Production Validation

Rejects:

Target Production <= 0

***** Time Validation *****

Rejects:

Planned Breaks >= Scheduled Time

and

Downtime > Planned Production Time

***** Save Validation *****

Prevents saving:

Blank Supervisor Name
Uncalculated Records
Invalid OPE Results

********************************************* Historical Record Keeping **********************************************

***** Save Shift Record *****

Appends completed records to:

ope_history.csv

Each save operation stores:

Date
Shift
Supervisor
Department
Timestamp
Production Inputs
Downtime Information
Availability
Performance
Quality
OPE

************************************************ Storage Location **********************************************

Records are stored automatically within the user's Documents folder:

Documents
└─ OPE Calculator
└─ ope_history.csv

The storage location is determined dynamically using:

System.getProperty("user.home")

allowing the application to work correctly for any user.

************************************************ History Management *********************************************
***** Open History *****

Opens:

ope_history.csv

using the system-default spreadsheet application (typically Microsoft Excel).

***** Open History Folder *****

Launches File Explorer and opens the application's data folder.

***** Export History *****

Allows users to create date-stamped exports such as:

OPE_History_2026-11-24.csv

using a FileChooser dialog.

***** Clear History *****

Deletes the historical data file after confirmation.

************************************************* CSV Protection *************************************************

Version 1.1 includes CSV escaping functionality to properly handle:

Commas
Quotation marks
Free-form notes

Example:

Ran RTR-2 before lunch, RTR-3 after lunch due to "emergency phoe calls"

is safely exported without corrupting column alignment in Excel.

******************************************* User Experience Features *********************************************

Keyboard Shortcuts

Shortcut	    Action
Enter	        Calculate
Esc	            Clear Form
Ctrl + S	    Save Shift
Ctrl + H	    Open History
Ctrl + D	    Open History Folder
Ctrl + E	    Export History
Ctrl + Q	    Exit

*********************************************** Tool Tips *************************************************

Interactive tooltips provide field-level guidance for all major input controls.

***** User Guide *****

Integrated application documentation explains:

Feature usage
Data storage
Export functionality
History management
Keyboard shortcuts

**************************************** Technical Improvements in Version 1.1 ***********************************

Major architectural improvements include:

***** Helper Method Refactoring *****

Business functions extracted into dedicated methods:

calculateOPE()
saveShiftRecord()
openHistory()
openHistoryFolder()
exportHistory()
clearHistory()
clearInputs()
showAppInfo()
showOPEInfo()
showUserGuide()
showError()

Result:

Cleaner code
Easier maintenance
Greater reusability
Improved readability

********************************************** Future Roadmap ***********************************************

Potential enhancements for Version 1.2+ include:

***** Reporting & Analytics *****
Average OPE by Department
Average OPE by Shift
Average OPE by Supervisor
Downtime Frequency Reports

***** Trend Analysis *****
JavaFX Charts
Historical OPE Graphs
Weekly and Monthly Summaries

***** Data Management *****
Native Excel (.xlsx) Support
Import Existing History
Archived Historical Data

***** Deployment *****
Windows EXE Packaging using jpackage
Runtime Bundling
Desktop Installer Distribution
Project Status

Version 1.1 Complete

The application has evolved from a simple educational JavaFX project into a functional manufacturing performance tracking tool 
capable of supporting real-world shift reporting, historical data analysis, and continuous improvement efforts. It demonstrates 
practical application of Java, JavaFX, file management, business logic implementation, and desktop application development 
principles.

Version History
*
* v1.0
* - OPE calculations
* - CSV history
* - Export functionality
*
* v1.1
* - Shift tracking
* - Department tracking
* - Downtime reason tracking
* - Open History Folder
* - User Guide
* - Keyboard shortcuts
*
*/

package com.ashley.javafx;

//All the import statements:
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class OPECalculator extends Application {

    //Create a final variable to hold the version number:
    private static final String VERSION = "1.1";

    //Create the HISTORY_FILE object for use in all IO methods:
    private static final String HISTORY_FILE =
    System.getProperty("user.home")
    + "\\Documents\\OPE Calculator\\ope_history.csv";

    //Create the CSV_Header to define the header for file IO methods:
    private static final String CSV_HEADER =
    "Date,Shift,Supervisor,Department," 
    + "Time,ScheduledTime,PlannedBreaks,"
    + "Downtime,TargetProduction,"
    + "ActualProduction,RejectedUnits,"
    + "DowntimeReason,DowntimeNotes,"
    + "Availability,Performance,"
    + "Quality,OPE";

    //List of departments, easy to add/update later:
    private static final String[] DEPARTMENTS = {
    "3100",
    "3500",
    "3600",
    "3700",
    "6100",
    "6500",
    "7100",
    "7500",
    "7600",
    "7700",
    "8810",
    "9400",
    "9450",
    "9600",
    "9700"
    };

    private static final String[] DOWNTIME_REASONS = {
        "Mechanical Failure",
        "Electrical Failure",
        "Material Shortage",
        "Tool Change",
        "Preventive Maintenance",
        "Quality Hold",
        "Operator Shortage",
        "Training",
        "Changeover",
        "Other"
    };

    //Create the labels and text fields for the inputs:
    private Label shiftDateLabel =
        new Label("Shift Date:");

    private DatePicker shiftDatePicker =
        new DatePicker(LocalDate.now());

    private Label shiftLabel =
        new Label("Shift:");

    private ComboBox<String> shiftComboBox =
        new ComboBox<>();

    private Label supervisorLabel =
        new Label("Supervisor:");

    private TextField supervisorTextField =
        new TextField();

    private Label departmentLabel =
        new Label("Department:");

    private ComboBox<String> departmentComboBox =
        new ComboBox<>();    

    private Label scheduledPTLabel = new Label("Scheduled Production Time (in minutes): ");
    private TextField scheduledPTTextField = new TextField();
    
    private Label plannedBreaksLabel = new Label("Planned Breaks (in minutes): ");
    private TextField plannedBreaksTextField = new TextField();

    private Label downtimeLabel = new Label("Downtime (in minutes): ");
    private TextField downtimeTextField = new TextField();

    private Label targetProductionLabel = new Label("Target Production (units): ");
    private TextField targetProductionTextField = new TextField();

    private Label actualProductionLabel = new Label("Actual Production (units): ");
    private TextField actualProductionTextField = new TextField();

    private Label rejectedUnitsLabel = new Label("Rejected Units: ");
    private TextField rejectedUnitsTextField = new TextField();

    //Create the "Save Shift" button:
    private Button saveButton = new Button("Save Shift Record");

    //Create the labels for the results:
    private Label metricsTitle = new Label("Performance Metrics");

    private Label availabilityLabel = new Label("Availability: ");
    private Label availabilityResult = new Label("--.--%");

    private Label performanceLabel = new Label("Performance: ");
    private Label performanceResult = new Label("--.--%");

    private Label qualityLabel = new Label("Quality: ");
    private Label qualityResult = new Label("--.--%");
        
    private Label OPELabel = new Label("OPE: ");
    private Label OPEResult = new Label("--.--%");

    //Create the Downtime ComboBox and textAreas:
    private Label downtimeReasonLabel = new Label("Downtime Reason: ");
    private ComboBox<String> downtimeReasonComboBox = new ComboBox<>();
    private Label downtimeNotesLabel = new Label("Notes:");
    private TextArea downtimeNotesTextArea = new TextArea();

    /* 
    ********************************** Create helper method for "Calculate" button: *****************************
    */
    private void calculateOPE() {
        try {

            //Convert the inputs to double data types:
            double scheduledPT = Double.parseDouble(scheduledPTTextField.getText().trim());
            double downtime = Double.parseDouble(downtimeTextField.getText().trim());
            double plannedBreaks = Double.parseDouble(plannedBreaksTextField.getText().trim());
            double targetProduction = Double.parseDouble(targetProductionTextField.getText().trim());
            double actualProduction = Double.parseDouble(actualProductionTextField.getText().trim());
            double rejects = Double.parseDouble(rejectedUnitsTextField.getText().trim()); 

            //Input validation, throw IllegalFormatException if values are less than zero:
            if (scheduledPT <= 0) {
                throw new IllegalArgumentException(
                    "Scheduled Production Time must be greater than zero."
                );
            }
            if (downtime < 0) {
                throw new IllegalArgumentException(
                    "Downtime cannot be negative."
                );
            }
            if (plannedBreaks < 0) {
                throw new IllegalArgumentException(
                    "Planned Breaks cannot be negative."
                );
            }
            if (targetProduction <= 0) {
                throw new IllegalArgumentException(
                    "Target Production must be greater than zero."
                );
            }
            if (actualProduction < 0) {
                throw new IllegalArgumentException(
                    "Actual Production cannot be negative."
                );
            }
            if (rejects < 0) {
                throw new IllegalArgumentException(
                    "Rejected Units cannot be negative."
                );
            }
            
            //Input validation, plannedBreaks cannot be larger than scheduledPT:
            if (plannedBreaks >= scheduledPT) {
                throw new IllegalArgumentException(
                    "Planned Breaks cannot exceed Scheduled Production Time."
                );
            }

            //Calculate planned production time, which is total scheduled time minus planned breaks:
            double plannedPT = scheduledPT - plannedBreaks;
            double runTime = plannedPT - downtime;

            //Input validation, in case a line is down the entire shift:
            if (runTime < 0) { 
                runTime = 0; 
            }

            //Input validation, downtime cannot be larger than plannedPT:
            if (downtime > plannedPT) {
                throw new IllegalArgumentException(
                    "Downtime cannot exceed Planned Production Time."
                );
            }

            //Calculate the three performance metrics:
            double availability = runTime / plannedPT;
            double performance = actualProduction / targetProduction;
            double quality;

            //Input validation, in case a shift ran 0 and produced 0 rejects:
            if (actualProduction == 0 && rejects == 0 ) {
                quality = 1.0; }
            else {
                quality = actualProduction / (actualProduction + rejects);
            }

            //Calculate OPE:
            double ope = availability * performance * quality;

            //Input validation, in case of Infinite or NaN result:
            if (Double.isNaN(ope) || Double.isInfinite(ope)) {
                throw new IllegalArgumentException(
                    "Calculation produced an invalid result, please check your inputs."
                );
            }

            //Set the result labels to show the calculated results:
            availabilityResult.setText(String.format(
                "%.2f%%", availability * 100));
            performanceResult.setText(String.format(
                "%.2f%%", performance * 100));
            qualityResult.setText(String.format(
                "%.2f%%", quality * 100));

            OPEResult.setText(String.format("%.2f%%", ope * 100));

            //OPEResult Label changes color depending on the result:
            if (ope >= 0.90) {
                    OPEResult.setStyle("-fx-text-fill: green;" + 
                    "-fx-font-size: 42px;" + 
                    "-fx-font-weight: bold;"
            );}
            else if (ope >= 0.80) {
                    OPEResult.setStyle("-fx-text-fill: orange;" + 
                    "-fx-font-size: 42px;" + 
                    "-fx-font-weight: bold;"
            );}
            else {
                    OPEResult.setStyle("-fx-text-fill: red;" + 
                    "-fx-font-size: 42px;" + 
                    "-fx-font-weight: bold;"
            );} 

            //Once a calculation is successful, enable the "Save Shift" button:
            saveButton.setDisable(false);

        }

            catch (NumberFormatException ex) {

                showError("Please enter positive numbers in all fields.");

            }

            catch (IllegalArgumentException iaex) {

               showError(iaex.getMessage());

            }
        }

    /* 
    ********************************** Create helper method for "Save Shift Record" button: *****************************
    */
    private void saveShiftRecord() {
        try {

            //Validation, if downtime > 0, forces user to select a reason in comboBox:
            if (Double.parseDouble(downtimeTextField.getText()) > 0
                && downtimeReasonComboBox.getValue() == null)
            {
                showError(
                    "Please select a downtime reason."
                );

                return;
            }

            //Validation, if supervisor textField is empty, forces user to name a supervisor:
            if (supervisorTextField.getText().trim().isEmpty()) {
                showError(
                    "Please enter a supervisor name."
                );
                return;
            }

            //Validation, forces user to calculate an OPE result before saving a shift record:
            if (OPEResult.getText().equals("--.--%")) {

                showError(
                    "Please calculate OPE before saving a shift record."
                );

                return;
            }

            File file = new File(HISTORY_FILE);

            file.getParentFile().mkdirs();

            boolean newFile = !file.exists();

            try (FileWriter writer =
                new FileWriter(file, true)) {

                if (newFile) {

                    writer.write(
                        CSV_HEADER + "\n"
                    );
                }

                writer.write(
                    shiftDatePicker.getValue() + "," +

                    shiftComboBox.getValue() + "," +

                    escapeCSV(supervisorTextField.getText().trim()) + "," +

                    departmentComboBox.getValue() + "," +

                    LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                    ) + "," +

                    scheduledPTTextField.getText().trim() + "," +
                    plannedBreaksTextField.getText().trim() + "," +
                    downtimeTextField.getText().trim() + "," +

                    targetProductionTextField.getText().trim() + "," +
                    actualProductionTextField.getText().trim() + "," +
                    rejectedUnitsTextField.getText().trim() + "," +

                    downtimeReasonComboBox.getValue() + "," +
                    escapeCSV(downtimeNotesTextArea.getText().trim()) + "," +

                    availabilityResult.getText() + "," +
                    performanceResult.getText() + "," +
                    qualityResult.getText() + "," +
                    OPEResult.getText() +

                    "\n"
                );
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(
                "Shift record saved successfully."
            );

            alert.showAndWait();
        }
        catch(IOException ex) {

            showError("Unable to save shift record.");
        }
    }

    /*
    ************************* Create helper method for "Open History" menuItem: ***********************************
    */
    private void openHistory() {

        try {

            File historyFile =
                new File(HISTORY_FILE);

            if(!historyFile.exists()) {
                
             showError(
                    "No history file exists yet."
                );
                return;
            }

            if (historyFile.length() == 0) {
                    showError(
                        "No history data exists yet."
                    );

                    return;
                }

            Desktop.getDesktop().open(historyFile);
        }
        catch(IOException ex) {

            showError(
                "Unable to open history file."
            );
        }
    }

    /*
    ************************* Create helper method for the "Export History" menuItem: ***********************************
    */
        private void exportHistory(Stage stage) {

        try {

            File historyFile = new File(HISTORY_FILE);

            if (!historyFile.exists()) {

                showError(
                    "No history file exists. Save a shift record first.");

                return;
            }

            List<String> lines =
            Files.readAllLines(historyFile.toPath());

            if (lines.isEmpty()) {

                showError(
                    "History file is empty."
                );

                return;
            }

            if (lines.size() == 1) {

                showError(
                    "No shift records have been saved yet."
                );

                return;
            }

            if (!lines.get(0).trim().equals(CSV_HEADER)) {

                showError(
                    "History file format is invalid."
                );

                return;
            }

            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Export History");

            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                    "CSV Files",
                    "*.csv"
                )
            );

            fileChooser.setInitialFileName(
                "OPE_History_" +
                    LocalDate.now() +
                    ".csv"
            );

            File exportFile =
                fileChooser.showSaveDialog(stage);

            if (exportFile == null) {

                return;
            }

            Files.copy(
                historyFile.toPath(),
                exportFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );

            Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Export Complete");
            alert.setHeaderText(null);
            alert.setContentText(
                "History exported successfully."
            );

            alert.showAndWait();

        }
        catch (IOException ex) {

            showError(
                "Unable to export history."
            );

        }
    }

     /*
    ************************* Create helper method for the "Clear History" menuItem: ***********************************
    */
        private void clearHistory() {

        Alert confirm =
            new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Clear History");
        confirm.setHeaderText(null);
        confirm.setContentText(
            "Delete all saved history?"
        );

        Optional<ButtonType> result =
            confirm.showAndWait();

        if(result.isPresent()
        && result.get() == ButtonType.OK) {

            File historyFile = new File(HISTORY_FILE);

            if (!historyFile.exists()) {
                showError(
                    "No history file exists."
                );
                return;
            }    
            
            if (!historyFile.delete()) {

                showError(
                    "Unable to delete history file."
                );
            } else {
                Alert confirmDelete =
                    new Alert(Alert.AlertType.INFORMATION);
                confirmDelete.setTitle("History Cleared");
                confirmDelete.setHeaderText(null);
                confirmDelete.setContentText(
                    "All history cleared."
                );
                confirmDelete.showAndWait();
            }
        }
    }

    /*
    ****** Create helper method for the "About" menuItem showing the alert message giving info about the application: *******
    */
    private void showAppInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OPE Calculator Information");
        alert.setHeaderText("OPE Calculator");
        alert.setContentText("Version " + VERSION + 
            "\n\nCreated by Ashley Strong\n\nJavaFX Desktop Application\n\n" +
            " *****Designed for shift-level OPE calculations*****\n\n" + "© 2026");

        alert.showAndWait();                
    }

    /*
    ****** Create helper method for the "OPE Formula" menuItem showing the alert message explaining the OPE formula: *******
    */
    private void showOPEInfo() {

        Label title = new Label("OPE Formula");
        title.setStyle(
            "-fx-font-size: 18px;" +
            "-fx-font-weight: bold;"
        );

        Label line1 = new Label(
            "Availability = (Planned Production Time - Downtime) / Planned Production Time"
        );
        line1.setStyle("-fx-font-size: 16px;");

        Label line2 = new Label(
            "Performance = Actual Production / Target Production"
        );
        line2.setStyle("-fx-font-size: 16px;");

        Label line3 = new Label(
            "Quality = Good Units / Total Units Produced"
        );
        line3.setStyle("-fx-font-size: 16px;");

        Label line4 = new Label(
            "OPE = Availability x Performance x Quality"
        );
        line4.setStyle(
            "-fx-font-size: 18px;" + 
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #0056A4;"
        );

        VBox content = new VBox(15, title, line1, line2, line3, line4);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OPE Formula");
        alert.setHeaderText("OPE is calculated as follows:");

        alert.getDialogPane().setContent(content);
        alert.getDialogPane().setPrefWidth(600);
        alert.getDialogPane().setPrefHeight(315);  
        alert.setResizable(true);              

        alert.showAndWait();
    }

    /*
    ****** Create helper method for the "User Guide" menuItem: *******
    */
    private void showUserGuide() {

        TextArea guideText = new TextArea(

            "OPE CALCULATOR USER GUIDE\n\n"

            + "PURPOSE\n"
            + "-------\n"
            + "This application calculates Overall Production "
            + "Efficiency (OPE) using Availability, "
            + "Performance, and Quality metrics.\n\n"

            + "CALCULATE\n"
            + "---------\n"
            + "Calculates Availability, Performance, "
            + "Quality, and OPE from the data entered.\n\n"

            + "SAVE SHIFT RECORD\n"
            + "----------\n"
            + "Saves the current shift record to the "
            + "history file.\n\n"

            + "OPEN HISTORY\n"
            + "------------\n"
            + "Opens the history CSV file in Excel.\n\n"

            + "OPEN HISTORY FOLDER\n"
            + "-------------------\n"
            + "Opens the folder where history and "
            + "exported files are stored.\n\n"

            + "EXPORT HISTORY\n"
            + "--------------\n"
            + "Creates a copy of the history file in "
            + "a location selected by the user.\n\n"

            + "CLEAR HISTORY\n"
            + "-------------\n"
            + "Permanently deletes all saved history.\n\n"

            + "FILE STORAGE LOCATION\n"
            + "---------------------\n"
            + HISTORY_FILE + "\n\n"

            + "KEYBOARD SHORTCUTS\n"
            + "------------------\n"
            + "Enter      - Calculate\n"
            + "Esc        - Clear Form\n"
            + "Ctrl + S   - Save Shift\n"
            + "Ctrl + H   - Open History\n"
            + "Ctrl + D   - Open History Folder\n"
            + "Ctrl + E   - Export History\n"
            + "Ctrl + Q   - Exit Application\n\n"

            + "DOWNTIME TRACKING\n"
            + "-----------------\n"
            + "If downtime is greater than zero,\n"
            + "select a downtime reason and enter\n"
            + "optional notes describing the event.\n\n"
                    );

        guideText.setEditable(false);
        guideText.setWrapText(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("User Guide");
        alert.setHeaderText("OPE Calculator User Guide");

        alert.getDialogPane().setContent(
            guideText
        );

        alert.getDialogPane().setPrefWidth(700);
        alert.getDialogPane().setPrefHeight(500);

        alert.setResizable(true);

        alert.showAndWait();
    }

    /*
    ************************** Create helper method showing an alert message in case of Exceptions: ***********************
    */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(message);

            alert.showAndWait();
        }

    /*
    ************************** Create helper method for clearing the inputs in the textFields: ***********************
    */   
    private void clearInputs() {
        saveButton.setDisable(true);
        shiftDatePicker.setValue(LocalDate.now());
        shiftComboBox.setValue(
            "1st Shift"
        );
        supervisorTextField.clear();
        departmentComboBox.setValue(
            DEPARTMENTS[0]
        );

        scheduledPTTextField.clear();
        plannedBreaksTextField.clear();
        downtimeTextField.clear();
        targetProductionTextField.clear();
        actualProductionTextField.clear();
        rejectedUnitsTextField.clear();

        availabilityResult.setText("--.--%");
        performanceResult.setText("--.--%");
        qualityResult.setText("--.--%");
        OPEResult.setText("--.--%");
        OPEResult.setStyle("-fx-font-size: 42px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #004B87;");  
            
        downtimeReasonComboBox.getSelectionModel().clearSelection();
        downtimeReasonComboBox.setDisable(true);

        downtimeNotesTextArea.clear();
        downtimeNotesTextArea.setDisable(true);    
    }  
    
    /*
    ********** Helper method for IO issues caused by user inputting commas, quotation marks, or multi-line text ***********
    */
    private String escapeCSV(String value) {

        if (value == null) {
            return "";
        }

        return "\"" +
            value.replace("\"", "\"\"") +
            "\"";
    }

    /*
    ************************* Create helper method for "Open History Folder" menuItem: ***********************************
    */
    private void openHistoryFolder() {

        try {

            File historyFolder = new File(
                System.getProperty("user.home")
                + "\\Documents\\OPE Calculator"
            );

            if (!historyFolder.exists()) {

                historyFolder.mkdirs();
            }

            Desktop.getDesktop().open(historyFolder);

        }
        catch (IOException ex) {

            showError(
                "Unable to open history folder."
            );

        }
    }

    /*
    ************************************************** Overridden start() method: **********************************************
     */
    @Override
    public void start(Stage stage) { 

        //Configure date picker and shift picker:
        shiftComboBox.getItems().addAll(
            "1st Shift",
            "2nd Shift",
            "3rd Shift"
        );

        shiftComboBox.setValue("1st Shift");

        shiftDatePicker.setTooltip(
            new Tooltip("Date the shift occurred")
        );

        shiftComboBox.setTooltip(
            new Tooltip("Select the shift")
        );

        shiftDatePicker.setMaxWidth(150);
        shiftComboBox.setMaxWidth(150);

        //Configure supervisor textField and department comboBox:
        supervisorTextField.setMaxWidth(150);
        supervisorTextField.setTooltip(
            new Tooltip("Enter supervisor name")
        );

        departmentComboBox.getItems().addAll(
            DEPARTMENTS
        );
        departmentComboBox.setValue(
            DEPARTMENTS[0]
        );
        departmentComboBox.setMaxWidth(150);
        departmentComboBox.setTooltip(
            new Tooltip("Select department")
        );

        //Configure textField and Label objects:
        scheduledPTTextField.setMaxWidth(150.0);
        scheduledPTTextField.setTooltip(new Tooltip(
            "Start of shift to end of shift in minutes"));

        plannedBreaksTextField.setMaxWidth(150.0);
        plannedBreaksTextField.setTooltip(new Tooltip(
            "Total breaks and lunch in minutes"));

        downtimeTextField.setMaxWidth(150.00);
        downtimeTextField.setTooltip(new Tooltip(
            "Total downtime, including breakdowns and microstops, in minutes"));

        targetProductionTextField.setMaxWidth(150.00);
        targetProductionTextField.setTooltip(new Tooltip(
            "Target rate for the shift, in units"));

        actualProductionTextField.setMaxWidth(150.00);
        actualProductionTextField.setTooltip(new Tooltip(
            "Actual rate hit by end of shift, in units"));

        rejectedUnitsTextField.setMaxWidth(150.00);
        rejectedUnitsTextField.setTooltip(new Tooltip(
            "Total number of rejects, in units"));

        metricsTitle.setStyle("-fx-font-size: 20px;" +
            "-fx-font-family: 'Segoe UI Semibold';" +
            "-fx-font-weight: bold;" + 
            "-fx-text-fill: #003366;");
        metricsTitle.setAlignment(Pos.CENTER);

        availabilityResult.setAlignment(Pos.CENTER_RIGHT);
        availabilityResult.setMinWidth(80);

        performanceResult.setAlignment(Pos.CENTER_RIGHT);
        performanceResult.setMinWidth(80);

        qualityResult.setAlignment(Pos.CENTER_RIGHT);
        qualityResult.setMinWidth(80);

        OPELabel.setStyle("-fx-font-size: 30px;" 
        + "-fx-font-weight: bold;" 
        + "-fx-text-fill: #0056A4;");

        OPEResult.setAlignment(Pos.CENTER_RIGHT);
        OPEResult.setMinWidth(150);
        OPEResult.setStyle("-fx-font-size: 42px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #004B87;"
        );

        //Configure the Downtime ComboBox and textArea:
        downtimeReasonComboBox.getItems().addAll(
            DOWNTIME_REASONS
        );

        downtimeReasonComboBox.setPromptText(
            "Select Reason"
        );

        downtimeReasonComboBox.setDisable(true);
        downtimeNotesTextArea.setDisable(true);
        downtimeNotesTextArea.setPrefRowCount(2);
        downtimeNotesTextArea.setWrapText(true);

        //Validation checks (blank and downtime > 0) for Downtime comboBox and textArea:
        downtimeTextField.textProperty().addListener(
            (observable, oldValue, newValue) -> {

                if (newValue.trim().isEmpty()) {
                    downtimeReasonComboBox.setDisable(true);
                    downtimeNotesTextArea.setDisable(true);
                    return;
                }

                try {
                    double downtime =
                        Double.parseDouble(newValue);
                    boolean hasDowntime =
                        downtime > 0;
                    downtimeReasonComboBox.setDisable(
                        !hasDowntime
                    );

                    downtimeNotesTextArea.setDisable(
                        !hasDowntime
                    );

                }
                catch (NumberFormatException ex) {
                    downtimeReasonComboBox.setDisable(true);
                    downtimeNotesTextArea.setDisable(true);

                }
            }
        );

        //Create the BorderPane:
        BorderPane root = new BorderPane();

        //Create the MenuBar:
        MenuBar menubar = new MenuBar();

        //Create Menus:
        Menu file = new Menu("File");
        Menu shift = new Menu("Shift");
        Menu history = new Menu("History");
        Menu help = new Menu("Help");

        //Add Menus to MenuBar:
        menubar.getMenus().addAll(file, shift, history, help);

        //Create Exit MenuItem, add to File Menu:
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(exit);

        //Create saveShiftRecord and clear MenuItems, add to Shift Menu:
        MenuItem saveShiftRecord = new MenuItem("Save Shift Record");
        MenuItem clear = new MenuItem("Clear Form");
        shift.getItems().addAll(saveShiftRecord, clear);

        //Create openHistory, exportHistory, and clearHistory MenuItems, add to History Menu:
        MenuItem openHistory = new MenuItem("Open History");
        MenuItem openHistoryFolder = new MenuItem("Open History Folder");
        MenuItem exportHistory = new MenuItem("Export History");
        MenuItem clearHistory = new MenuItem("Clear History");
        history.getItems().addAll(openHistory, openHistoryFolder, exportHistory, clearHistory);

        //Create about and opeFormula MenuItems, add to Help menu:
        MenuItem about = new MenuItem("About");
        MenuItem opeFormula = new MenuItem("OPE Formula");
        MenuItem userGuide = new MenuItem("User Guide");
        help.getItems().addAll(about, opeFormula, userGuide);


        //Create main title for the window:
        Label OPEtitle = new Label("OPE Calculator");
        OPEtitle.setAlignment(Pos.CENTER);
        OPEtitle.setStyle("-fx-font-size: 38px;" +
                        "-fx-font-family: 'Segoe UI Semibold';" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #004B87;");

        //Create the Calculate button:
        Button calculateButton = new Button("Calculate");
        calculateButton.setStyle("-fx-background-color: #0078D4;" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;"
        );
        calculateButton.setPrefWidth(100);
        calculateButton.setPrefHeight(35);

        //Key Code functionality, pressing the Enter button is the same as pressing Calculate button:
        calculateButton.setDefaultButton(true);

        //Configure the Save button:
        saveButton.setStyle(
            "-fx-background-color: #28A745;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;"
        );

        saveButton.setPrefWidth(130);
        saveButton.setPrefHeight(35);
        saveButton.setDisable(true);

        //Create the Reset button:
        Button clearButton = new Button("Clear");
        clearButton.setStyle("-fx-background-color: #808080;" +
                        "-fx-text-fill: white;"
        );
        clearButton.setPrefWidth(80);
        clearButton.setPrefHeight(35);

        //Key Code functionality, pressing the Esc button is the same as pressing Clear button:
        clearButton.setCancelButton(true);

        //Set the Calculate button event handler:
        calculateButton.setOnAction(e->{
            calculateOPE();
        });

        //Set the Clear button action event handler:
        clearButton.setOnAction(e-> {
            clearInputs();
        });

        //Set the Save button action event handler:
        saveButton.setOnAction(e -> {
            saveShiftRecord();
        });

        //Set the "Exit" MenuItem action event handler:
        exit.setOnAction(e->{
            stage.close();
        });

        //Set the "Save Shift Record" menuItem action event handler:
        saveShiftRecord.setOnAction(e -> {
            saveShiftRecord();
        });

        //Set the "Clear Form" MenuItem action event handler:
        clear.setOnAction(e->{
            clearInputs();
        });

        //Set the "Open History" MenuItem action event handler:
        openHistory.setOnAction(e -> {
            openHistory();
        });

        //Set the "Open History Folder" MenuItem action event handler:
        openHistoryFolder.setOnAction(e -> {
            openHistoryFolder();
        });

        //Set the "Export History" MenuItem action event handler:
        exportHistory.setOnAction(e -> {
            exportHistory(stage);
        });

        //Set the "Clear History" MenuItem action event handler:
        clearHistory.setOnAction(e-> {
            clearHistory();
        });

        //Set the "About" MenuItem action event handler:
        about.setOnAction(e->{
            showAppInfo();
        });

        //Set the "OPE Formula" MenuItem action event handler:
        opeFormula.setOnAction(e->{
            showOPEInfo();
        });

        //Set the "User Guide" MenuItem action event handler:
        userGuide.setOnAction(e -> {
            showUserGuide();
        });

        //Create an HBox to hold the main window title, align and pad it:
        HBox titleBox = new HBox(OPEtitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 20, 0));

        //Create a spacer to help the layout of the buttons look nicer:
        Region spacer = new Region();
        spacer.setMinHeight(15);

        //Create an HBox for the button layout:
        HBox buttonBox = new HBox(50,
            calculateButton,
            saveButton,
            clearButton);

        //Create a VBox for the downtime comboBox, textArea, and labels, and configure it:
        VBox downtimeBox = new VBox(
            10,

            downtimeReasonLabel,
            downtimeReasonComboBox,

            downtimeNotesLabel,
            downtimeNotesTextArea
        );   
        downtimeBox.setPadding(
            new Insets(15)
        );

        downtimeBox.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #d0d0d0;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        ); 

        //Create a GridPane for the inputs:
        GridPane inputGrid = new GridPane();

        inputGrid.setHgap(10);
        inputGrid.setVgap(15);

        inputGrid.setPadding(new Insets(20));

        inputGrid.add(shiftDateLabel, 0, 0);
        inputGrid.add(shiftDatePicker, 1, 0);

        inputGrid.add(shiftLabel, 0, 1);
        inputGrid.add(shiftComboBox, 1, 1);

        inputGrid.add(supervisorLabel, 0, 2);
        inputGrid.add(supervisorTextField, 1, 2);

        inputGrid.add(departmentLabel, 0, 3);
        inputGrid.add(departmentComboBox, 1, 3);

        inputGrid.add(scheduledPTLabel,      0, 4);
        inputGrid.add(scheduledPTTextField,  1, 4);

        inputGrid.add(plannedBreaksLabel,    0, 5);
        inputGrid.add(plannedBreaksTextField,1, 5);

        inputGrid.add(downtimeLabel,         0, 6);
        inputGrid.add(downtimeTextField,     1, 6);

        inputGrid.add(targetProductionLabel,       0, 7);
        inputGrid.add(targetProductionTextField,   1, 7);

        inputGrid.add(actualProductionLabel,       0, 8);
        inputGrid.add(actualProductionTextField,   1, 8);

        inputGrid.add(rejectedUnitsLabel,    0, 9);
        inputGrid.add(rejectedUnitsTextField,1, 9);

        inputGrid.add(spacer,                0, 10);

        inputGrid.add(buttonBox, 0, 11, 2, 1);

        //Create a VBox for the inputs GridPane:
        VBox inputsBox = new VBox(inputGrid);

        //Create an HBox for the results header for layout in GridPane:
        HBox metricsHeader = new HBox(metricsTitle);
        metricsHeader.setAlignment(Pos.CENTER);
        metricsHeader.setPadding(new Insets(0, 0, 10, 0));

        //Create Results GridPane for layout:
        GridPane resultsGrid = new GridPane();

        resultsGrid.setHgap(30);
        resultsGrid.setVgap(25);

        resultsGrid.setMinWidth(300);

        resultsGrid.setPadding(new Insets(20));

        resultsGrid.setStyle("-fx-background-color: white;" + 
            "-fx-border-color: #d0d0d0;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;"
        );

        resultsGrid.add(metricsHeader, 0, 0, 2, 1);

        resultsGrid.add(availabilityLabel, 0, 1);
        resultsGrid.add(availabilityResult, 1, 1);

        resultsGrid.add(performanceLabel, 0, 2);
        resultsGrid.add(performanceResult, 1, 2);

        resultsGrid.add(qualityLabel, 0, 3);
        resultsGrid.add(qualityResult, 1, 3);

        //Create serarator line for layout:
        Separator separator = new Separator();

        resultsGrid.add(separator, 0, 4, 2, 1);
        resultsGrid.add(OPELabel, 0, 5);
        resultsGrid.add(OPEResult, 1, 5);

    
        //Create a VBox for the result GridPane:
        VBox resultsBox = new VBox(20, resultsGrid, downtimeBox);
        resultsBox.setMaxWidth(300);

        //Create another HBox for both inputs and results, so they align horizontally in the window:
        HBox child = new HBox(40, inputsBox, resultsBox);

        //Create one VBox for everything:
        VBox parent = new VBox(30, titleBox, child);
        parent.setPadding(new Insets(20));

        //Set the menuBar at the top and the final VBox in the center:
        root.setTop(menubar);
        root.setCenter(parent);
        root.setStyle("-fx-background-color: #d1dbeb;");

        //Create the scene and set the size of the window:
        Scene scene = new Scene(root, 800, 675);

        //Add KeyCodeCombination functionality, pressing Ctrl + S is the same as pressing "Save Shift" button:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.S,
                KeyCombination.CONTROL_DOWN),
            () -> saveShiftRecord()
        );

        //Add KeyCodeCombination functionality, pressing Ctrl + L is the same as pressing "Clear" button:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.L,
                KeyCombination.CONTROL_DOWN),
            () -> clearInputs()
        );

        //Add KeyCodeCombination functionality, pressing Ctrl + H is the same as selecting "Open History" MenuItem:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.H,
                KeyCombination.CONTROL_DOWN),
            () -> openHistory()
        );

        //Add KeyCodeCombination functionality, pressing Ctrl + D is the same as selecting "Open History Folder" MenuItem:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.D,
                KeyCombination.CONTROL_DOWN),
            () -> openHistoryFolder()
        );

        //Add KeyCodeCombination functionality, pressing Ctrl + E is the same as selecting "Export History" MenuItem:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.E,
                KeyCombination.CONTROL_DOWN),
            () -> exportHistory(stage)
        );

        //Add KeyCodeCombination functionality, pressing Ctrl + Q is the same as selecting "Exit" MenuItem:
        scene.getAccelerators().put(
            new KeyCodeCombination(
                KeyCode.Q,
                KeyCombination.CONTROL_DOWN),
            () -> stage.close()
        );

        //Title the window, and show it:
        stage.setTitle("OPE Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}