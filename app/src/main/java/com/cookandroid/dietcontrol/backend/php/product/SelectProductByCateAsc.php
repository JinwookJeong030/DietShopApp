<?php

include ('.././dbcon.php');
    header('Content-Type: application/json');

    $p_category = $_GET['p_category'];
 
    if($p_category == "전체")
    $query = "SELECT * FROM product ORDER BY p_price ASC;";
    else
    $query = "SELECT * FROM product WHERE p_category='".$p_category."' ORDER BY p_price ASC;";

    $results = mysqli_query($conn, $query);
    
    echo "[";
    $num = mysqli_num_rows($results);

    while($row = mysqli_fetch_assoc($results)) {
        $array = array(
            "p_seq" => $row['p_seq'],
            "p_name" => $row['p_name'],
            "p_price" => $row['p_price'],
            "p_brand" => $row['p_brand'],
            "p_sales" => $row['p_sales'],
            "p_rating" => $row['p_rating'],
            "p_discount" => $row['p_discount'],
            "p_date" => $row['p_date'],
            "p_stock" => $row['p_stock'],
            "p_category" => $row['p_category'],
            "p_content" => $row['p_content'],
            "p_img" => $row['p_img']
        );
        echo json_encode($array, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        if($num > 1) {
            echo ",";
            $num--;
        }
    }
    echo "]";    
    mysqli_close($conn);
?>