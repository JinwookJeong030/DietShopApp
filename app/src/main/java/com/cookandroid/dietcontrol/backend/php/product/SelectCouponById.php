<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $c_seq = $_GET['c_seq'];
    
    $query = "SELECT * FROM coupon WHERE c_seq=".$c_seq." AND cp_useable = 1;";
    $results = mysqli_query($conn, $query);

    echo "[";
    $num = mysqli_num_rows($results);

    while($row = mysqli_fetch_assoc($results)) {
        $array = array(
            "cp_seq" => $row['cp_seq'],
            "c_seq" => $row['c_seq'],
            "cp_name" => $row['cp_name'],
            "cp_discount" => $row['cp_discount'],
            "cp_date" => $row['cp_date'],
            "cp_useable" => $row['cp_useable']
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