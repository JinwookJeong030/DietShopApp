<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $c_seq=$_POST['c_seq'];    
       $d_date=$_POST['d_date'];
       $d_img = $_POST['d_img'];
       $d_contents =$_POST['d_contents']  ;
       $d_period =$_POST['d_period'];
       $d_meal=$_POST['d_meal'];
       $d_kcal=$_POST['d_kcal'];
       

       
       $query ="INSERT INTO diet_log(c_seq ,d_date, d_img, d_contents, d_period, d_meal, d_kcal) "
               . "VALUES( ".$c_seq.",'".$d_date."' ,'".$d_img."' ,'".$d_contents."','".$d_period."','".$d_meal."',".$d_kcal.");";

       
      
    if(mysqli_query($conn,$query)) {
        $response = true;
    
        
    } else {
    	$response = false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>

