<?php
include ('../dbcon.php');
 header('Content-Type: application/json');
    $c_seq= $_GET["c_seq"]; 
    $e_date = $_GET["e_date"];
    $query  =  "SELECT * FROM exercise_log WHERE c_seq= ".$c_seq." AND  DATE(e_date) = '".$e_date."'  ;";
    $result= mysqli_query($conn,$query);

    $num = mysqli_num_rows($result);
$i =0;

if($num>0){
  while ($row = mysqli_fetch_assoc($result)){
    $array[$i]=array(
        "e_seq"=> (int)$row['e_seq'],
         "c_seq" => (int)$row['c_seq'], 
         "e_date" => $row['e_date'], 
         "e_img" => $row['e_img'], 
   	"e_contents" => $row['e_contents'],      
    	"e_weight" => (double)$row['e_weight'], 
         "e_height" => (double)$row['e_height'], 
         "e_fat" => (double)$row['e_fat'],
        "e_part" => $row['e_part'],
        "e_minute" => (int)$row['e_minute']
        
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