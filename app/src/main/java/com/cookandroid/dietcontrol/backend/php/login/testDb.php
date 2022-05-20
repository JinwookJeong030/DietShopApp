<?php
    $host = "localhost";
    $user = "root";
    $pw = "onlyroot";
    $dbName = "user";

    $conn = new mysqli($host, $user, $pw, $dbName);
    
    /* DB 연결 확인 */
    if($conn){ echo "Connection established"."<br>"; }
    else{ die( 'Could not connect: ' . mysqli_error($conn) ); }
    
    /* SELECT 예제 */
    $sql = "SELECT * FROM user";
    $result = mysqli_query($conn, $sql);
    while($row = mysqli_fetch_array($result)){
        echo $row['userID']." ".$row['userPassword']."<br>";
    }
    
    mysqli_close($conn);
?>