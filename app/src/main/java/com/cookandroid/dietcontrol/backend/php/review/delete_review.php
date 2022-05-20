<?php
include ('.././dbcon.php');
header('Content-Type: application/json');

//리뷰 번호를 받으면
$re_seq = $_POST['re_seq'];
$p_seq = $_POST['p_seq'];
$re_contents = $_POST['re_contents'];
$re_img = $_POST['re_img'];
$re_rating = $_POST['re_rating'];


$queryTemp= "SELECT COUNT(re_seq) FROM review WHERE re_seq = ".$re_seq.";";
$reviewCnt = mysqli_fetch_assoc(mysqli_query($conn,$queryTemp));

$queryTemp2 ="UPDATE product SET p_rating = (p_rating *(".$reviewCnt['COUNT(re_seq)'].") - ".$re_rating.")/(".$reviewCnt['COUNT(re_seq)']."-1)  WHERE p_seq = ".$p_seq.";";
$queryTemp2 = mysqli_query($conn,$queryTemp2);

$query = "DELETE  FROM review WHERE  re_seq = ".$re_seq." ;";
mysqli_query($conn, $query);


mysqli_close($conn); 

?>