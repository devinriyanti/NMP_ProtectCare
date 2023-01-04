<?php
    extract($_POST);
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }

    if(isset($_POST["username"]) && isset($_POST["checkOut"])){
        $username = $_POST["username"];
        $checkOut = $_POST["checkOut"];

        $sql = "SELECT * FROM status WHERE username = '$username' ORDER BY idstatus DESC LIMIT 1";
        $rslt = $connect->query($sql);
        while($row=$rslt->fetch_assoc()){
            $sql2 = "UPDATE status SET checkout = '$checkOut' WHERE username = '$username'";
            $rslt2 = $connect->prepare($sql2);
            if(mysqli_query($connect, $sql2)){
                echo json_encode(array("result"=>"ok"));
            }else {
                echo json_encode(array("result"=>"Gagal diupdate"));
            }
        }
    }
?>