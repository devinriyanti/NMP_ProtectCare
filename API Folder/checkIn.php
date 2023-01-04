<?php 
extract($_POST);
error_reporting(E_ERROR | E_PARSE);
$connect = new mysqli("localhost","native_160419158","ubaya","native_160419158");
if($connect->connect_errno){
    $arr = array('result'=> 'ERROR', 'message' => 'Failed to connect DB');
    die(json_encode($arr));
}

$doses;
$idlocation;
$idstatus;

if(isset($_POST['username'])&& isset($_POST['location'])&& isset($_POST['checkInDate'])&&isset($_POST["unique_code"])){
     $username = $_POST['username'];
     $location = $_POST['location'];
     $checkin = $_POST['checkInDate'];
     $code = $_POST['unique_code'];

     $sql = "SELECT * FROM user WHERE username = '$username'";
     $rslt = $connect->query($sql);
     while ($row=$rslt->fetch_assoc()) {
         $doses = $row['doses_of_vaccine'];
     }

     $sql2 = "SELECT * FROM location WHERE name = '$location'";
     $rslt2 = $connect->query($sql2);
     while ($row2=$rslt2->fetch_assoc()) {
         $idlocation = $row2['id'];
     }

     $sqlIDStatus = "SELECT MAX(idstatus) as idstatus FROM status";
     $res = $connect->query($sqlIDStatus);
     if ($rowStatus=$res->fetch_assoc()) {
         $idstatus = $rowStatus['idstatus'];
     }

     $sql3 = "SELECT * FROM location WHERE name = '$location' AND unique_code = '$code'";
     $rslt3 = $connect->query($sql3);
     if($row3 = mysqli_fetch_array($rslt3)){
         $sqlHistory = "INSERT INTO status (idlocation, username, checkin, doses_of_vaccine, idstatus) VALUES ('$idlocation','$username','$checkin',$doses,$idstatus+1)";
         $connect->prepare($sqlHistory);
         if(mysqli_query($connect,$sqlHistory)){
             echo json_encode(array("result"=>"ok"));
         }else {
             echo json_encode(array("result"=>"data gagal ditambahkan"));
         }
     }else {
         echo json_encode(array("result"=>"Kode yang dimasukkan tidak terdaftar dalam database!"));
     }
}
?>