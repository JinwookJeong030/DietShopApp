<?php
	include ('../dbcon.php');
	header('Content-Type: application/json');
	$c_id = $_POST['c_id'];
	$c_pw = $_POST['c_pw'];
	
	$query = "UPDATE customer SET c_pw = '".$c_pw."' WHERE c_id = '".$c_id."';";
	$num = mysqli_query($conn, $query);
	mysqli_close($conn);
?>
