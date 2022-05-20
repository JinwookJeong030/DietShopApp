<?php

   include ('../dbcon.php');
    header('Content-Type: application/json');
 
    $c_seq= $_POST["c_seq"]; 
    $pt_name = $_POST["pt_name"];
    $pt_point = $_POST["pt_point"];
    
    $query1  =  "UPDATE customer SET c_point = c_point + ".$pt_point." WHERE c_seq ='".$c_seq."'; ";
    $query2  =  "INSERT INTO point(c_seq , pt_name , pt_point) VALUES(".$c_seq.", '".$pt_name."', ".$pt_point."); ";
  
     if(mysqli_query($conn,$query1) &&mysqli_query($conn,$query2)) {
        $response = true;
    
        
    } else {
    	$response = false;
   
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
     mysqli_close($conn);
?>