<?php
include ('../dbcon.php');


header('Content-Type: application/json');
$c_id =$_GET['c_id'];
$query ="SELECT * FROM customer WHERE c_id = '".$c_id."';";
$num = mysqli_query($conn,$query);
if(mysqli_num_rows($num))
    $result=true;
else
    $result=false;


echo json_encode($result);
mysqli_close($conn);
?>
