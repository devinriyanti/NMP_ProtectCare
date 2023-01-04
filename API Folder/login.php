<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["username"]) && isset($_POST["password"])){
        $username = $_POST["username"];
        $password = $_POST["password"];
        $sql = "SELECT * FROM user WHERE username = '$username' AND password='$password'";
		$result = $connect->query($sql);
	        $array = array();

        if ($result->num_rows > 0) {
            echo json_encode('Success');
        } else {
            echo json_encode('Failed');
            die();
        }
    }
?>
