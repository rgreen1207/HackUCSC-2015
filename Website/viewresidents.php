<html>
<head>
<script type="text/javascript" src="https://mandrillapp.com/api/docs/js/mandrill.js"></script>
<script type="text/javascript" src="send-email.js"></script>
<meta charset="utf-8">
<title>Elderly Integrated Care System</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>


<body>

<header>
  <div class="logo"> <img src="logo.png"> </div>
  <br>
  <div class="profile">
    <h1>Elderly Integrated Care Device</h1>
    <h3>UCSC HACKATHON 2015</h3>
    <hr>
    <h2>EICD Administrative Monitoring Page: View Resident Status</h2>
    <hr>
    <br>
	<br>
    <br>
	</div>
</header>

<div id="mainpage"> 
	<section id="menu">
    	<div id="commands"> 
			<div onclick="location.href='register.html';" class="link"><a href="register.html" title="ViewUsers">Register Residents</a></div>
			<br><br>
            <div onclick="location.href='viewresidents.php';" class="link"><a href="viewresidents.php" title="RegisterRes">View Users</a></div> 
            <br><br>
                                 
        </div>
                <span class="blankspace"></span>
        <div id="mainarea"> 
        <!--
			<div class = "goodinfo">Hello</div><br>
        	<div class = "badinfo">Hello2</div>
        -->            
            <?php
            $con = mysqli_connect('animanga.dot5hostingmysql.com', 'ucsc_hack', 'hack', 'ucsc_hack'); 
            if (mysqli_connect_errno($con))
            {
               echo "Failed to connect to MySQL: " . mysqli_connect_error();
            }

            $result = mysqli_query($con,"SELECT user_f_name, user_l_name, room, hasFallen FROM User");
            
            if (mysqli_num_rows($result) != 0) { 
                //output each row of data
                while ($row = mysqli_fetch_assoc($result)) { 
					if ($row["hasFallen"] == 1) {
						echo "<script type='text/javascript'>alert('". $row["user_f_name"] . " in Room Number " . $row["room"] . " has fallen! Please send assistance immediately.');</script>";
						/*echo '<script type="text/javascript">'
   , 'sendTheMail();'
   , '</script>'
;
						*/
				
                    echo "<div class = \"badinfo\">Name: " . $row["user_f_name"]. " " . $row["user_l_name"]. "</div><br>";
					} else { 
							                    echo "<div class = \"goodinfo\">Name: " . $row["user_f_name"]. " " . $row["user_l_name"]. "</div><br>";
					}
                    }
			}
                    else { 
                    	echo "No Residents In System";
					}
            mysqli_close($con);
            
            ?>
           	
            </li>
        </div>
    </section>
</div>
<br>
<br>
<br>
<hr>


</body>



</html>
