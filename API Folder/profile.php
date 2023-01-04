<?php
	error_reporting(E_ERROR | E_PARSE);
	$c = new mysqli("localhost","native_160419158","ubaya","native_160419158");

	if($c->connect_errno) {
		echo json_encode(array('result'=> 'ERROR', 'message' => 'Failed to connect DB'));
        die();
    }
    if(isset($_GET["username"])){
        $username= $_GET["username"];
        $sql = "SELECT full_name, doses_of_vaccine FROM `user` WHERE username = '$username'";
        $result = $c->query($sql);
        $array = array();
        if ($result->num_rows > 0) {
            if($obj = $result -> fetch_object()) {
                $array[] = $obj;
            }
            echo json_encode(array('result' => 'OK', 'data' => $array));
        } else {
            echo json_encode(array('result'=> 'ERROR', 'message' => 'No data found'));
            die();
        }
    }
