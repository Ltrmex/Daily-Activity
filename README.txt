========================= Requirements =========================
1. Register with the system - completed
2. Log-in to the fitness system	from the client	application to the server application - completed
3. Add	a fitness record - completed
4. Add	a meal record - completed
5. View	the last ten records on	their record - completed
6. View	the last ten fitness records on	their record - completed
7. Delete a record using the record number shown in menu item 6 - attempted, but not completed
	- Left code as it was there, however it doesn't delete(work). Decided to leave it as it doesn't affect the application

========================= NOTE =========================
- Most of the requirements met
- All files if non existent, will be created automatically
- No data, value, input validation done
	- No max 100 value for the meal description
	- Only allowed one word strings to input, otherwise it messes up the file storage
- Database of all users exists as a file
	- Each user has it's own line with their data
- Meal Records and Fitness Records are stored separately
	- Each user has it's own file for meal dn fitness records
		- The way it's done is by user name followed by word 'fitness' or 'meal' depending on the type of record
	- When user is trying to input their record for first time their record file is created automatically

========================= References =========================
- Operating Systems Lab Resources
- https://codereview.stackexchange.com/questions/79039/get-the-tail-of-a-file-the-last-10-lines
- https://stackoverflow.com/questions/16265693/how-to-use-buffered-reader-in-java
- https://stackoverflow.com/questions/9620683/java-fileoutputstream-create-file-if-not-exists