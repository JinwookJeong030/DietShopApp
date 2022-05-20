<?php
	include ('../dbcon.php');
	header('Content-Type: application/json');
	
	$c_seq = $_GET["c_seq"];
	
	$query = "SELECT * FROM point WHERE c_seq= '".$c_seq."' ORDER BY pt_seq DESC;";
	$result = mysqli_query($conn, $query);
	$num = mysqli_num_rows($result);
	
	$i = 0;
	if($num > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$array[$i]=array(
				"pt_seq" => (int)$row['pt_seq'],
				"c_seq" => (int)$row['c_seq'],
				"pt_name" => $row['pt_name'],
				"pt_point" => $row['pt_point'],
				"pt_date" => $row['pt_date']
			);
			$i++;
		}
		echo json_encode($array, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
	}

	mysqli_close($conn);
?>