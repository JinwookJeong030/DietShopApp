<?php
include ('../dbcon.php');

header('Content-Type: application/json');
$c_seq = $_GET['c_seq'];
$p_seq = $_GET['p_seq'];

$query = "SELECT * FROM `order` WHERE c_seq = '".$c_seq."' AND p_seq = '".$p_seq."'";

$results = mysqli_query($conn, $query);


echo "[";
$num = mysqli_num_rows($results);

while($row = mysqli_fetch_assoc($results)) {
    $array = array(
        "o_seq" => $row['o_seq'],
        "c_seq" => $row['c_seq'],
        "p_seq" => $row['p_seq'],
        "o_cnt" => $row['o_cnt'],
        "o_person" => $row['o_person'],
        "o_zipcode" => $row['o_zipcode'],
        "o_address" => $row['o_address'],
        "o_subaddress" => $row['o_subaddress'],
        "o_tell" => $row['o_tell'],
        "o_price" => $row['o_price'],
        "o_pay" => $row['o_pay'],
        "o_card" => $row['o_card'],
        "o_point" => $row['o_point'],
        "o_coupon_name" => $row['o_coupon_name']
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
