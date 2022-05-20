
<?php
        $host = 'http://hsdiet.dothome.co.kr/'; 
        $user = 'hsdiet';
        $password = 'thisis*password0'; 
        $db = 'hsdiet'; 
        
    $conn = mysqli_connect($host, $user, $password, $db);
 
    mysqli_query($conn, "set session character_set_connection=utf8;");
    mysqli_query($conn, "set session character_set_results=UTF8;");
    mysqli_query($conn, "set session character_set_client=utf8;");

?>
