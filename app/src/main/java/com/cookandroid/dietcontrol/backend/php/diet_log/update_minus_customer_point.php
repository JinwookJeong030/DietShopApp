<?php

include ('../dbcon.php');
    header('Content-Type: application/json');
 
    $c_seq= $_POST["c_seq"]; 
    $c_point = $_POST["c_point"];
    
    $query  =  "UPDATE customer SET c_point = c_point - ".$c_point." WHERE c_seq ='".$c_seq."'; ";

  
     if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
   
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
     mysqli_close($conn);
?>