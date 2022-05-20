<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
 
       $c_seq=$_POST['c_seq'];    
       $e_date=$_POST['e_date'];
       $e_img = $_POST['e_img'];
       $e_contents =$_POST['e_contents']  ;
       $e_weight =$_POST['e_weight'];
       $e_height=$_POST['e_height'];
       $e_fat=$_POST['e_fat'];
       $e_part=$_POST['e_part'];
       $e_minute=$_POST['e_minute'];
       
   
       
       $query ="INSERT INTO exercise_log(c_seq ,e_date, e_img, e_contents, e_weight, e_height, e_fat, e_part, e_minute) "
               . "VALUES( ".$c_seq.",'".$e_date."','".$e_img."','".$e_contents."',".$e_weight.",".$e_height.",".$e_fat.",'".$e_part."',".$e_minute." );";

       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>

