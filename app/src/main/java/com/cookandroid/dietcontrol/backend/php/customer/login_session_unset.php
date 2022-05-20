<?php
session_start();

unset($_SESSION['c_seq']);

if(!isset($_SESSION['c_seq'])){
    
echo json_encode(true, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    
}
else
{
    echo json_encode(false, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
}




?>