<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $e_seq=$_POST['e_seq'];    
       $e_date=$_POST['e_date'];
       $e_img = $_POST['e_img'];
       $e_contents =$_POST['e_contents']  ;
       $e_weight =$_POST['e_weight'];
       $e_height=$_POST['e_height'];
       $e_fat=$_POST['e_fat'];
       $e_part=$_POST['e_part'];
       $e_minute=$_POST['e_minute'];
      
       $query ="UPDATE exercise_log "
               . "SET e_date = '".$e_date."', e_contents = '".$e_contents."' , e_weight= ".$e_weight." "
               . ", e_height = ".$e_height." , e_fat = ".$e_fat." , e_part ='".$e_part."' , e_minute=".$e_minute." "
               . "WHERE e_seq = ".$e_seq.";";
       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>
