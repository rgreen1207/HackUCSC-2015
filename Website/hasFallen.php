<?php
$con = mysqli_connect('animanga.dot5hostingmysql.com', 'ucsc_hack', 'hack', 'ucsc_hack'); 
//$con=mysqli_connect("","username","password","database name");
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$result = mysqli_query($con,"SELECT hasFallen FROM User");
$row = mysqli_fetch_array($result);

$tmp = 0; 

foreach ($row as &$value) {
    if ($value == 1) { 
		echo 1;
		$tmp = 1;
		break;
	}
}

if ($tmp != 1) { 
	return 0;
}

mysqli_close($con);

?>