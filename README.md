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
Esc	          Clear Form
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
