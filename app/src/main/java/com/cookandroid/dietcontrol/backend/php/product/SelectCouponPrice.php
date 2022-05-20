<?php
include ('../dbcon.php');

header('Content-Type: application/json');

$cp_name = $_GET['cp_name'];

$query = "SELECT cp_discount as P FROM coupon WHERE cp_name = '".$cp_name."'";

$results = mysqli_query($conn, $query);

$board = mysqli_fetch_assoc($results);

echo $board['P'];

mysqli_close($conn);
?>
