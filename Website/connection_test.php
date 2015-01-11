 <?php 
$link = mysql_connect('animanga.dot5hostingmysql.com', 'ucsc_hack', 'hack'); 
if (!$link) { 
    die('Could not connect: ' . mysql_error()); 
} 
echo 'Connected successfully'; 
mysql_select_db(ucsc_hack); 
?> 
