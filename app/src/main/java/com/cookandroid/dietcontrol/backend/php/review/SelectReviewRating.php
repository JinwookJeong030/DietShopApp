<?php
include ('../dbcon.php');

header('Content-Type: application/json');

$p_seq = $_GET['p_seq'];

$query = "SELECT * FROM `review` WHERE p_seq = '".$p_seq."' ORDER BY re_rating DESC";

$results = mysqli_query($conn, $query);


echo "[";
$num = mysqli_num_rows($results);

while($row = mysqli_fetch_assoc($results)) {
    $array = array(
        "re_seq" => $row['re_seq'],
        "o_seq" => $row['o_seq'],
        "c_seq" => $row['c_seq'],
        "p_seq" => $row['p_seq'],
        "re_date" => $row['re_date'],
        "re_contents" => $row['re_contents'],
        "re_img" => $row['re_img'],
        "re_rating" => $row['re_rating']
    );
    echo json_encode($array, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    if($num > 1) {
        echo ",";
        $num--;
    }
}
echo "]";    
mysqli_close($conn);
?>
