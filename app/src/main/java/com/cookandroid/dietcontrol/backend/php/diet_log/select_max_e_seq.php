<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
       $query ="SELECT MAX(e_seq)+1 FROM exercise_log;";
       $result=mysqli_query($conn,$query);
       $row = mysqli_fetch_row($result);
    echo json_encode($row[0], JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
 mysqli_close($conn);
       
?>

