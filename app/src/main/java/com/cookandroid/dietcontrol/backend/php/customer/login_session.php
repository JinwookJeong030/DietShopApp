<?php
session_cache_expire(2);
session_start();


echo json_encode($_SESSION["c_seq"], JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);

?>