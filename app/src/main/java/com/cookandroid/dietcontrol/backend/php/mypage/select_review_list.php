<?php
	include ('../dbcon.php');
	header('Content-Type: application/json');
	
	$c_seq = $_GET["c_seq"];
	
	$query = "SELECT re_seq, re_date,re_contents,re_rating,p_name
	FROM review, product WHERE review.p_seq = product.p_seq  AND c_seq= '".$c_seq."' ORDER BY re_seq DESC;";
	$result = mysqli_query($conn, $query);
	$num = mysqli_num_rows($result);
	
	$i = 0;
	if($num > 0) {
		while ($row = mysqli_fetch_assoc($result)) {
			$array[$i]=array(
                                "re_seq" => $row['re_seq'],
				"re_date" => $row['re_date'],
				"re_contents" => $row['re_contents'],
				"re_rating" => (int)$row['re_rating'],
				"p_name" => $row['p_name']
			);
			$i++;
		}
		echo json_encode($array, JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
	}

	mysqli_close($conn);
?>