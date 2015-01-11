<!-- //accessing information through browser requests for Android application
//base code adapted from http://www.tutorialspoint.com/android/android_php_mysql.htm
-->
<?php
$con = mysqli_connect('animanga.dot5hostingmysql.com', 'ucsc_hack', 'hack', 'ucsc_hack'); 
//$con=mysqli_connect("","username","password","database name");
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

//Uncomment for production
//$username = $_POST['username'];
//$password = $_POST['password'];

//Comment for production
$username = "Test";
$password = "Test2";

$result = mysqli_query($con,"SELECT username FROM User where username='$username' and password='$password'");

$row = mysqli_fetch_array($result);

$data = $row[0];

if($data){
//echo $data;
echo "True";
} else {
echo "False";
}

mysqli_close($con);

?>