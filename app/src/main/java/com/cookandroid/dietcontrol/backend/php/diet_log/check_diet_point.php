<?php
include ('../dbcon.php');
 header('Content-Type: application/json');

 
$c_seq=$_GET['c_seq'];

$query ="SELECT pt_seq FROM point WHERE   c_seq= ".$c_seq." AND DATE_FORMAT(pt_date, '%Y-%m-%d') =DATE_FORMAT(NOW(),'%Y-%m-%d')  AND  pt_name ='다이어트 일기 작성' ;";
$result=mysqli_query($conn,$query);
$num = mysqli_num_rows($result);



  if($num>0) {
        $response=true;
    
        
    } else {
    	$response=false;
    
    }
    echo json_encode($response, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);

?>
