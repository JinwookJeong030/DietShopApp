
<?php

 header('Content-Type: application/json');
 
$location =$_POST["location"];



$basename =basename($_FILES['uploaded_file']['name']);


    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'],$location)){
        $result = true;
    }
    else{
        $result = false;
    }

  echo json_encode($result, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
?>
