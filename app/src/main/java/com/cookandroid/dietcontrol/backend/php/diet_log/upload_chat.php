<?php

header("Content-Type:text/html;charset=utf-8");
$data=$_POST["uploaded_file"];

$uploads_dir="./newImage";
$file_path = "./";

$basename = basename( $_FILES['uploaded_file']['name']);

$file_path = $file_path . $basename;
if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'],"../$uploads_dir/$file_path")) {
    $result =array("result" => "success");
    echo "success";
} else{
    $result = array("result" => "error");
    echo "error";
}
?>