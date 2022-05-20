<?php
include ('.././dbcon.php');
    header('Content-Type: application/json');

    $p_seq = $_GET["p_seq"];
 
        $query = "SELECT * FROM product WHERE p_seq='".$p_seq."';";

    $results = mysqli_query($conn, $query);
    
    
    if($row = mysqli_fetch_assoc($results)) 
        $array = array(
            "p_seq" => (int)$row['p_seq'],
            "p_name" => $row['p_name'],
            "p_price" => (int)$row['p_price'],
            "p_brand" => $row['p_brand'],
            "p_sales" => (int)$row['p_sales'],
            "p_rating" => (double)$row['p_rating'],
            "p_discount" => (int)$row['p_discount'],
            "p_date" => $row['p_date'],
            "p_stock" => (int)$row['p_stock'],
            "p_category" => $row['p_category'],
            "p_content" => $row['p_content'],
            "p_img" => $row['p_img']
        );
        echo json_encode($array, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        
 
    mysqli_close($conn);


?>
