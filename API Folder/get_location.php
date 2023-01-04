<?php
    error_reporting(E_ERROR | E_PARSE);
    $connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
    if($connect->connect_errno){
        $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
        die(json_encode($arr));
    }

    $query ="SELECT * FROM location";
    $result = $connect->query($query);
    $data =array();

    if($result->num_rows > 0){
        while($obj = $result->fetch_assoc()){
            $data[] =$obj;
        }
        echo json_encode(array('result' =>'ok','data'=>$data));
    }else{
        die (json_encode(array("result"=>"ERROR","message"=>"No data found")));
    }

?>