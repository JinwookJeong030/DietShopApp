<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $re_seq=$_POST['re_seq'];    
  
     
       
       $query ="DELETE FROM review WHERE re_seq= ".$re_seq.";";

       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>