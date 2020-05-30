<?php

error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');


    $msg = $_POST['sql_msg']; //안드로이드에서 보낸 데이터 

    $stmt = $con->prepare($msg); 
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
    
            array_push($data, 
                array('bankname'=>$bankname,
                'productname'=>$productname,
                'rate'=>$rate,
                'description'=>$Description
            ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("results"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }


?>