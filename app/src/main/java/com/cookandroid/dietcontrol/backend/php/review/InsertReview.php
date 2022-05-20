<?php
include ('.././dbcon.php');
header('Content-Type: application/json');

$o_seq = $_POST['o_seq'];
$c_seq = $_POST['c_seq'];
$p_seq = $_POST['p_seq'];
$re_contents = $_POST['re_contents'];
$re_img = $_POST['re_img'];
$re_rating = $_POST['re_rating'];

$query = "INSERT INTO `review` (o_seq, c_seq, p_seq, re_contents, re_img, re_rating) VALUES (".$o_seq.",".$c_seq.", ".$p_seq.", '".$re_contents."', '".$re_img."', ".$re_rating.");";
mysqli_query($conn, $query);
$queryTemp= "SELECT COUNT(re_seq) FROM review WHERE p_seq = ".$p_seq.";";
$reviewCnt = mysqli_fetch_assoc(mysqli_query($conn,$queryTemp));

$queryTemp2 ="UPDATE product SET p_rating = (p_rating *(".$reviewCnt['COUNT(re_seq)']."-1) + ".$re_rating.")/(".$reviewCnt['COUNT(re_seq)'].")  WHERE p_seq = ".$p_seq.";";
$queryTemp2 = mysqli_query($conn,$queryTemp2);



mysqli_close($conn); 

?>