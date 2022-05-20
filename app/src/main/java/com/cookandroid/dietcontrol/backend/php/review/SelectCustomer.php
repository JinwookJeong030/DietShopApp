<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');
    
    $query = "SELECT * FROM customer";
    $results = mysqli_query($conn, $query);

    $num = mysqli_num_rows($results);
    echo "[";
    while($row = mysqli_fetch_assoc($results)) {
        $array = array(
            "c_seq"=> $row['c_seq'],
            "c_id" => $row['c_id'], 
            "c_pw" => $row['c_pw'], 
            "c_name" => $row['c_name'], 
            "c_alias" => $row['c_alias'], 
            "c_tell" => $row['c_tell'], 
            "c_email" => $row['c_email'], 
            "c_birth" => $row['c_birth'], 
            "c_gender"=> $row['c_gender'],
            "c_point" => $row['c_point'], 
            "c_agree_email" => $row['c_agree_email'], 
            "c_agree_sms" => $row['c_agree_sms'], 
            "ad_zipcode" => $row['ad_zipcode'], 
            "ad_address" => $row['ad_address'], 
            "ad_subaddress" => $row['ad_subaddress'], 
            "c_useable_coupon" => $row['c_useable_coupon']
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