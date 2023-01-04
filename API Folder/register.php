<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }
    if(isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["full_name"])){
        $username= $_POST["username"];
        $password = $_POST["password"];
        $full_name = $_POST["full_name"];
        $checkUsername = "SELECT * FROM user WHERE username = $username";
		$result = $connect->query($checkUsername);
        if ($result->num_rows > 0) {
            echo json_encode(array("result"=>"failed"));
            die();
        } else {
            $sql = "INSERT INTO `user` (`username`, `password`, `full_name`) VALUES ('$username', '$password', '$full_name')";
		    $res=$connect->prepare($sql);
            if(mysqli_query($connect,$sql)){
                echo json_encode(array("result"=>"ok"));
            }else {
                echo json_encode(array("result"=>"gagal"));
            }
        }
    }
?>
