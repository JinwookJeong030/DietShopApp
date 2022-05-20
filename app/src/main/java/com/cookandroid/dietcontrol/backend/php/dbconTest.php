
<?php
 include ('./dbcon.php');
header('Content-Type: application/json');

 
    if($conn)
    echo json_encode(true, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    else
    echo json_encode(false, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    
mysqli_close($conn);
    
    
?>
