<?php
	include ('../dbcon.php');
	header ('Content-Type: application/json');
	$c_id = $_POST['c_id'];
	$c_alias = $_POST['c_alias'];
	$c_tell = $_POST['c_tell'];
	$c_birth = $_POST['c_birth'];
	$c_email = $_POST['c_email'];
	$c_gender = $_POST['c_gender'];
	$c_agree_email = $_POST['c_agree_email'];
	$c_agree_sms = $_POST['c_agree_sms'];
	$ad_zipcode = $_POST['ad_zipcode'];
	$ad_address = $_POST['ad_address'];
	$ad_subaddress = $_POST['ad_subaddress'];
	
	$query = "UPDATE customer 
				SET c_alias = '".$c_alias."', 
					c_tell = '".$c_tell."', 
					c_birth = '".$c_birth."',
					c_email = '".$c_email."', 
					c_gender = '".$c_gender."', 
					c_agree_email = ".$c_agree_email.", 
					c_agree_sms = ".$c_agree_sms.", 
					ad_zipcode = '".$ad_zipcode."', 
					ad_address = '".$ad_address."', 
					ad_subaddress = '".$ad_subaddress."' 
				WHERE c_id = '".$c_id."';";

	mysqli_query($conn, $query);
	mysqli_close($conn);
?>