<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $c_seq = $_GET['c_seq'];

    $c_point = $_POST['c_point'];
    $c_useable_coupon = $_POST['c_useable_coupon'];

    $query ="UPDATE customer SET c_point = '".$c_point."', c_useable_coupon = '".$c_useable_coupon."' WHERE c_seq = '".$c_seq."';";
    
    mysqli_query($conn,$query);       
    mysqli_close($conn);
?>