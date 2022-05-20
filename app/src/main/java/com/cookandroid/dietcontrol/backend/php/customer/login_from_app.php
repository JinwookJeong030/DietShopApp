<?php
session_cache_expire(2);
session_start();
include ('../dbcon.php');
 header('Content-Type: application/json');
 $c_seq= $_GET['c_seq']; 
 



$_SESSION["c_seq"] =null;

 $query  =  "SELECT c_seq FROM customer WHERE c_seq=".$c_seq.";";
 $result= mysqli_query($conn,$query);
 $row = mysqli_fetch_assoc($result);



//session_cache_expire(60);
$_SESSION["c_seq"]=$row['c_seq'];

echo json_encode($_SESSION["c_seq"], JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    
mysqli_close($conn);


?>