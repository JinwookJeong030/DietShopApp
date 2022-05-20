<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
    $c_seq= $_GET["c_seq"]; 
    $d_date = $_GET["d_date"];
    $query  =  "SELECT * FROM diet_log WHERE c_seq= ".$c_seq." AND DATE(d_date) = '".$d_date."' ;";
    $result= mysqli_query($conn,$query);

    $num = mysqli_num_rows($result);
$i =0;

if( $num>0){
  while ($row = mysqli_fetch_assoc($result)){
    $array[$i]=array(
        "d_seq"=> (int)$row['d_seq'],
         "c_seq" => (int)$row['c_seq'], 
         "d_date" => $row['d_date'], 
         "d_img" => $row['d_img'], 
   	"d_contents" => $row['d_contents'],      
    	"d_period" => $row['d_period'], 
         "d_meal" => $row['d_meal'], 
         "d_kcal" => (double)$row['d_kcal']
        
        );
    
  
    $i++;
  
 
}}
//   echo json_encode(array("num"=>$num), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);  
   echo json_encode($array, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);  

   // echo json_encode(array("response" => $error, JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE));
    
    //json 타입의 결과값을 return합니다.
   
   
    
    //DB 접속을 종료합니다.
    mysqli_close($conn);
    ?>