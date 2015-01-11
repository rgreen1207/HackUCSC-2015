Connection Info: 
un: ucsc_hack
pw: hack

Database Schema

Table:
User

Fields:
Username (username)
Password (password)
User Id (userid)
Sign Up Date (sign_up_date)
First Name (user_f_name)
Last Name (user_l_name)
Birth Date (user_b_date)
Email (user_email)
User Phone Number (user_phone)
Emergency Contact First Name (eme_f_name)
Emergency Contact Last Name (eme_l_name)
Emergency Number (eme_phone)
Emergency Email (eme_email)

Creation:

CREATE TABLE  `User` (
 `username` CHAR( 30 ) NOT NULL COMMENT  'Username',
 `password` CHAR( 30 ) NOT NULL ,
 `userid` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY ,
 `sign_up_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
 `user_f_name` CHAR( 30 ) NOT NULL ,
 `user_l_name` CHAR( 30 ) NOT NULL ,
 `user_b_date` DATE NOT NULL ,
 `user_email` CHAR( 100 ) NOT NULL ,
 `user_phone` CHAR( 10 ) NOT NULL ,
 `eme_f_name` CHAR( 30 ) NOT NULL COMMENT  'Emergency Contact First Name',
 `eme_l_name` CHAR( 30 ) NOT NULL COMMENT  'Emergency Contact Last Name',
 `eme_phone` CHAR( 10 ) NOT NULL ,
 `eme_email` CHAR( 100 ) NOT NULL ,
UNIQUE (
`username`
)
) ENGINE = MYISAM COMMENT =  'Database for the users who sign up for the service. ';

Insert:

Test 1: 

INSERT INTO  `User` (  `username` ,  `password` ,  `userid` ,  `sign_up_date` ,  `user_f_name` ,  `user_l_name` ,  `user_b_date` ,  `user_email` ,  `user_phone` ,  `eme_f_name` ,  `eme_l_name` , `eme_phone` ,  `eme_email` ) 
VALUES (
'Test',  'Test', NULL , 
CURRENT_TIMESTAMP ,  'Test',  'Test',  '2015-01-09',  '',  '',  '',  '',  '',  ''
);

$sql = 'INSERT INTO `User` (`username`, `password`, `userid`, `sign_up_date`, `user_f_name`, `user_l_name`, `user_b_date`, `user_email`, `user_phone`, `eme_f_name`, `eme_l_name`, `eme_phone`, `eme_email`) VALUES (\'Test\', \'Test\', NULL, CURRENT_TIMESTAMP, \'Test\', \'Test\', \'2015-01-09\', \'\', \'\', \'\', \'\', \'\', \'\');';

INSERT INTO  `User` (  `username` ,  `password` ,  `userid` ,  `sign_up_date` ,  `user_f_name` ,  `user_l_name` ,  `user_b_date` ,  `user_email` ,  `user_phone` ,  `eme_f_name` ,  `eme_l_name` , `eme_phone` ,  `eme_email` ) 
VALUES (
'Test2',  'Test2', NULL , 
CURRENT_TIMESTAMP ,  'Test2',  'Test2',  '',  '',  '',  '',  '',  '',  ''
);

