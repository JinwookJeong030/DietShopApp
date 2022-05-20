<?php
session_start();
include ('.././dbcon.php');
header('Content-Type: application/json');

$c_seq = $_POST['c_seq'];
$p_seq = $_POST['p_seq'];
$o_cnt = $_POST['o_cnt'];
$o_person = $_POST['o_person'];
$o_zipcode = $_POST['o_zipcode'];
$o_address = $_POST['o_address'];
$o_subaddress = $_POST['o_subaddress'];
$o_tell = $_POST['o_tell'];
$o_price = $_POST['o_price'];
$o_pay = $_POST['o_pay'];
$o_card = $_POST['o_card'];
$o_point = $_POST['o_point'];
$o_coupon_name = $_POST['o_coupon_name'];

$query = "INSERT INTO `order` (c_seq, p_seq, o_cnt, o_person, o_zipcode, o_address, o_subaddress, o_tell, o_price, o_pay, o_card, o_point, o_coupon_name) VALUES "
        . "(".$c_seq.", ".$p_seq.", ".$o_cnt.", '".$o_person."', '".$o_zipcode."', '".$o_address."', '".$o_subaddress.
        "','".$o_tell."', ".$o_price.", '".$o_pay."', '".$o_card."', ".$o_point.", '".$o_coupon_name."');";

mysqli_query($conn, $query);
mysqli_close($conn); 

?>