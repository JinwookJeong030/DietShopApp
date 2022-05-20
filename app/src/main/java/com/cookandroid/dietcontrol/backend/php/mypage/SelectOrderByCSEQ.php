<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $c_seq = $_GET['c_seq'];
    
    $query = "SELECT order.o_seq, product.p_name, order.o_price, product.p_img, order.o_date FROM `order` join `product` on product.p_seq = order.p_seq WHERE order.c_seq = '".$c_seq."' ORDER BY order.o_seq DESC;";
    $results = mysqli_query($conn, $query);

    echo "[";
    $num = mysqli_num_rows($results);

    while($row = mysqli_fetch_assoc($results)) {
        $array = array(
            "o_seq" => $row['o_seq'],
            "p_name" => $row['p_name'],
            "o_price" => $row['o_price'],
            "p_img" => $row['p_img'],
            "o_date" => $row['o_date']
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