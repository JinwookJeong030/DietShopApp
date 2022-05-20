<?php
	include ('../dbcon.php');
	 header('Content-Type: application/json');
		$query ="SELECT * FROM event_notice";
		$result = mysqli_query($conn ,$query);
		$num = mysqli_num_rows($result);
	
	$i = 0;
		if($num > 0) {
			while ($row = mysqli_fetch_assoc($result)){
				$array[$i] = array (
					"ev_seq" => $row['ev_seq'],
					"m_seq" => $row['m_seq'],
					"ev_start" => $row['ev_start'],
					"ev_end" => $row['ev_end'],
					"ev_title" => $row['ev_title'],
					"ev_contents" => $row['ev_contents'],
					"ev_img" => $row['ev_img']
				);
				$i++;
			}
			echo json_encode($array, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
		}
		else {
			echo json_encode(array("response" => $error, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE));
		}
	mysqli_close($conn);
?>