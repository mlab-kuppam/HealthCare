<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "healthcaredb";

// Create connection
$conn = mysqli_connect($servername, $username, $password, $dbname);
// Check connection
if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
}

$sql = "SELECT child_id,name,gender,father_name FROM child";
$result = mysqli_query($conn, $sql);

$all=array();

if (mysqli_num_rows($result) > 0) {
    // output data of each row
    while($row = mysqli_fetch_assoc($result)) {
        $all[$row['child_id']]=$row;
    }
    echo(json_encode($all));
} else {
    echo "0 results";
}

mysqli_close($conn);
?>