<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $p_seq = $_GET['p_seq'];
    $p_stock = $_POST['p_stock'];

    $query = "UPDATE product SET p_sales = p_sales+1  WHERE p_seq = ".$p_seq.";";
    
    mysqli_query($conn,$query);       
    mysqli_close($conn);
?>