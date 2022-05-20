<?php
    include ('../dbcon.php');
    header('Content-Type: application/json');
    $c_seq=$_GET['c_seq'];    
    
    $query = "SELECT MAX(o_seq) FROM `order` WHERE c_seq =".$c_seq.";";
    $result = mysqli_query($conn, $query);
   
   $row = mysqli_fetch_row($result);
    echo json_encode($row[0], JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
  
    mysqli_close($conn);
?>