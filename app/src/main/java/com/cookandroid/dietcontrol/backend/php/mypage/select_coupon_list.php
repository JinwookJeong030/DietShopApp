<?php
	include ('../dbcon.php');
	header('Content-Type: application/json');
	
	$c_seq = $_GET["c_seq"];
	
	$query = "SELECT * FROM coupon WHERE c_seq= '".$c_seq."' ORDER BY cp_seq DESC;";
	$result = mysqli_query($conn, $query);
	$num = mysqli_num_rows($result);
	
	$i = 0;
	if($num > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$array[$i]=array(
				"cp_seq" => (int)$row['cp_seq'],
				"c_seq" => (int)$row['c_seq'],
				"cp_name" => $row['cp_name'],
				"cp_discount" => (int)$row['cp_discount'],
				"cp_date" => $row['cp_date'],
				"cp_useable" => $row['cp_useable'] == "1" ? true:false
			);
			$i++;
		}
		echo json_encode($array, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
	}

	mysqli_close($conn);
?>