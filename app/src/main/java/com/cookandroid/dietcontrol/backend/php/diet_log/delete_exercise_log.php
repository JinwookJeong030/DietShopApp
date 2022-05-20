<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $e_seq=$_POST['e_seq'];    
  
     
       
       $query ="DELETE FROM exercise_log WHERE e_seq= ".$e_seq.";";

       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>