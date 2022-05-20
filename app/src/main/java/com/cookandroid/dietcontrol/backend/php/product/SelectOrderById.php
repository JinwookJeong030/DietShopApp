<?php
    session_start();
    include ('.././dbcon.php');
    header('Content-Type: application/json');

    $o_seq = $_GET['o_seq'];
    
    $query = "SELECT * FROM `order` JOIN `product` ON product.p_seq =order.p_seq WHERE order.o_seq = ".$o_seq.";";
    $results = mysqli_query($conn, $query);

    $num = mysqli_num_rows($results);

    if($row = mysqli_fetch_assoc($results)) {
        $array = array(
            "o_seq" => (int)$row['o_seq'],
            "c_seq" => (int)$row['c_seq'],
            "p_seq" => (int)$row['p_seq'],
            "o_cnt" => (int)$row['o_cnt'],
            "o_person" => $row['o_person'],
            "o_zipcode" => $row['o_zipcode'],
            "o_address" => $row['o_address'],
            "o_subaddress" => $row['o_subaddress'],
            "o_tell" => $row['o_tell'],
            "o_price" => (int)$row['o_price'],
            "o_pay" => $row['o_pay'],
            "o_card" => $row['o_card'],
            "o_point" => (int)$row['o_point'],
            "o_coupon_name" => $row['o_coupon_name'],
            "o_date" => $row['o_date']
    );
        
    }
        echo json_encode($array, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        
    mysqli_close($conn);
?>