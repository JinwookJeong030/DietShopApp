<?php
	inculde('../dbcon.php');
	header('Content-Type: application/json');
	$ev_title = $_GET['ev_title'];
	
	$qeury = "SELECT * FROM event_notice WHERE ev_title = $ev_title";	
	$result = mysqli_query($conn, $query);
	$num = mysqli_num_rows($result);
	
	if($num > 0 ) {
		$row = mysqli_fetch_assoc($result);
		$array = array (
			"ev_seq" => $row['ev_seq'],
			"m_seq" => $row['m_seq'],
			"ev_start" => $row['ev_start'],
			"ev_end" => $row['ev_end'],				
			"ev_title" => $row['ev_title'],
			"ev_contents" => $row['ev_contents'],
			"ev_img" => $row['ev_img']
		);
		echo json_encode($array, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
	}
	else {
		echo json_encode(array("response" => $error, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE));
	}
	mysqli_close($conn);	

?>