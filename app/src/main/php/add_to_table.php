<?php

#created by apyryt on 5/13/16

#getting the parameters from the PHP request
$eventName = $_REQUEST["eventName"];
if (!$eventName) {
	$eventName = "NULL";
}
$eventLocation = $_REQUEST["eventLocation"];
if (!$eventLocation) {
	$eventLocation = "NULL";
}
$eventDate = $_REQUEST["eventDate"];
if (!$eventDate) {
	$eventDate = "NULL";
}
$eventTime = $_REQUEST["eventTime"];
if (!$eventTime) {
	$eventTime = "NULL";
}
$eventLocation = $_REQUEST["eventLocation"];
if (!$eventLocation) {
	$eventLocation = "NULL";
}
$eventDescription = $_REQUEST["eventDescription"];
if (!$eventDescription) {
	$eventDescription = "NULL";
}
$eventCategory = $_REQUEST["eventCategory"];
if (!$eventCategory) {
	$eventCategory = "NULL";
}

#logging into database with the comm
$host = "mpss.csce.uark.edu";
$user = "crowdsource";
$password = "crowdsource123~";
$database = "dragonfly";
$con = mysql_connect($host, $user, $password);
if (!$con) {
    echo "Database not found" . mysql_error($con) . "\n";
}
else {
    echo "Connected succeeded " . "\n";
}

#picking our database
$result = mysql_select_db($database, $con);
if (!$result) {
     echo "selecting database failed " . mysql_erro(). "\n";
}

#create a table in the database if one does not exist:
$query = "select * from $database";
$result = mysql_query($query, $con);
if (!$result) {
	$query = "SHOW TABLES LIKE 'EVENTS'";
	$res = mysql_query($query, $con);
	if (!$res || mysql_num_rows($res) == 0) {

		#our table has 7 columns: an id, the event name, location, date, time, category, and description
		$query = "CREATE TABLE EVENTS(id INT(6) AUTO_INCREMENT PRIMARY KEY, eventName VARCHAR(100), eventLocation VARCHAR(100), eventDate DATE, eventTime TIME, eventCategory VARCHAR(20), eventDescription VARCHAR(500));";
   		$res = mysql_query($query, $con);
    	if (!$res) {
        	echo "Error creating a table " . mysqlerror() . "\n";
    	}
    }
}

#add a new row with the values from the request into the database
$query = "INSERT INTO EVENTS (eventName, eventLocation, eventDate, eventTime, eventCategory, eventDescription) values (\"$eventName\", \"$eventLocation\", $eventDate, $eventTime, \"$eventCategory\", \"$eventDescription\");";
$result = mysql_query($query, $con);
if (!$result) {
    echo "Error in inserting into the table " . mysql_error() . "\n";
}
mysql_close($con);

?>
