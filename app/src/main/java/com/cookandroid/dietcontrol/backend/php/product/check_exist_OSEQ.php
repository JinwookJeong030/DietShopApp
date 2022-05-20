<?php
include ('../dbcon.php');

header('Content-Type: application/json');
$o_seq =$_GET['o_seq'];
$query ="SELECT * FROM `order` WHERE o_seq = '".$o_seq."';";
$num = mysqli_query($conn,$query);
if(mysqli_num_rows($num))
    $result=true;
else
    $result=false;

echo json_encode($result);
mysqli_close($conn);
?>
