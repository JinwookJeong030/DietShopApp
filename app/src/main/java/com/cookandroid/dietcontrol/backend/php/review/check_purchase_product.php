<?php
include ('../dbcon.php');

header('Content-Type: application/json');
$c_seq = $_GET['c_seq'];
$p_seq = $_GET['p_seq'];

$query ="SELECT * FROM `order` WHERE c_seq = '".$c_seq."' AND p_seq = '".$p_seq."';";
$num = mysqli_query($conn,$query);
if(mysqli_num_rows($num))
    $result=true;
else
    $result=false;

echo json_encode($result);
mysqli_close($conn);
?>
