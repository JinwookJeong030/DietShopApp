<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $cp_useable = $_POST['cp_useable'];

    $c_seq = $_GET['c_seq'];
    $cp_name = $_GET['cp_name'];

    $query ="UPDATE coupon SET cp_useable = '".$cp_useable."' WHERE c_seq = '".$c_seq."' AND cp_name = '".$cp_name."';";
    
    mysqli_query($conn,$query);       
    mysqli_close($conn);
?>