<?php
include ('../dbcon.php');

header('Content-Type: application/json');

$p_seq = $_GET['p_seq'];

$query = "SELECT COUNT(re_seq) as P FROM review WHERE p_seq = '".$p_seq."'";

$results = mysqli_query($conn, $query);

$board = mysqli_fetch_assoc($results);

echo $board['P'];

mysqli_close($conn);
?>
