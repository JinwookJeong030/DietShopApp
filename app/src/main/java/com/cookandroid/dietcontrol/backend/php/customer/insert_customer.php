<?php
   include ('../dbcon.php');
   header('Content-Type: application/json');
       $c_id =$_POST['c_id'];
       $c_pw =$_POST['c_pw'];
       $c_name=$_POST['c_name'];
       $c_alias=$_POST['c_alias'];
       $c_tell=$_POST['c_tell'];
       $c_birth=$_POST['c_birth'];
       $c_email=$_POST['c_email'];
       $c_gender=$_POST['c_gender'];
       $c_agree_email=$_POST['c_agree_email'];
       $c_agree_sms=$_POST['c_agree_sms'];
       
       

      
       
       
       $query ="INSERT INTO customer(c_id, c_pw, c_name, c_alias, c_tell,c_email,c_birth, c_gender, c_agree_email, c_agree_sms, c_useable_coupon) "
               . "VALUES('".$c_id."','".$c_pw."','".$c_name."','".$c_alias."','".$c_tell."','".$c_email."','".$c_birth."','".$c_gender."',".$c_agree_email.",".$c_agree_sms.", 1)";
       
       
       
       
mysqli_query($conn,$query);       

       $queryCSeq ="SELECT c_seq FROM customer WHERE c_id= '".$c_id."';";
       
       
        $resultCSeq=mysqli_query($conn,$queryCSeq);     
             $num = mysqli_num_rows($resultCSeq);

    $row = mysqli_fetch_assoc($resultCSeq); 


 $queryCoupon ="INSERT INTO coupon(c_seq, cp_name, cp_discount, cp_date)VALUES(".$row['c_seq'].",'회원가입 기념', 1000 , '2059-12-25' ) ";
       mysqli_query($conn,$queryCoupon);   

mysqli_close($conn);
       

?>