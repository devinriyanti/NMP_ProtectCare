<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["idlocation"]) && isset($_POST["name"])){
        $idlocation = $_POST["idlocation"];
        $name = $_POST["name"];
        $sql = "SELECT * FROM location WHERE idlocation = $idlocation AND name=$name";
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
