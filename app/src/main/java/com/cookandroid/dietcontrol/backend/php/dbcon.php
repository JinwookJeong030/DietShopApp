
<?php

        $host = 'localhost'; 
        $user = 'root';
        $password = 'onlyroot'; 
        $db = 'hsdiet'; 
        
    $conn = mysqli_connect($host, $user, $password, $db);
 
    mysqli_query($conn, "set session character_set_connection=utf8;");
    mysqli_query($conn, "set session character_set_results=UTF8;");
    mysqli_query($conn, "set session character_set_client=utf8;");

?>
