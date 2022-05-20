<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $d_seq=$_POST['d_seq'];    
  
     
       
       $query ="DELETE FROM diet_log WHERE d_seq= ".$d_seq.";";

       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>