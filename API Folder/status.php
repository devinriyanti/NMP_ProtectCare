<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    
    if ($connect->connect_errno) {
        echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
        die();
    }
    if(isset($_POST['username'])){
        $username = $_POST['username'];
        $sql = "SELECT s.username, l.name, s.checkin, s.checkout, s.doses_of_vaccine FROM status s INNER JOIN location l on s.idlocation = l.id WHERE s.username = '$username' ORDER BY s.checkin DESC LIMIT 1";
        $rslt = $connect->query($sql);
        $data = array();

        if($rslt->num_rows>0){
            while($row=$rslt->fetch_assoc()){
                if($row>0){
                    $data[]=$row;
                    echo json_encode(array("result"=>"ok","data"=>$data));
                }
            }
        }else {
            echo json_encode(array("result"=>"data kosong"));
        }
    }
    
?>