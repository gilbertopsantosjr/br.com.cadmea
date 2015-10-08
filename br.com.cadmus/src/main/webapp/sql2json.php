<?php 


echo get_json();

function get_json(){
    $response['code'] = 0;
    $response['data'] = null;
 
    $data = json_decode(file_get_contents("php://input"));
     
    if (isset($data) && !empty($data)){
        $response['code'] = 0;
        $response['data'] = create_json ( $data->query, $data->foreignkey ) ;
    } else {
        $response['code'] = 1; //Bad Request
        $response['data'] = "looks this sql ". $_POST["query"] ." is invalid";
    }     
           
    return deliver_response('json', $response);
}
   
function create_json($query, $foreignkey){ 
    
    $conn = mysql_connect("localhost", "root", "123") or die("Connection failed: " . mysql_error() );
    $link = mysql_select_db( "M_3_8_0_A", $conn) or die( 'Error:' . mysql_error() );   
    
    $data_sql = mysql_query($query, $conn) or die( "sql invalid: " . mysql_errno($conn) . ": " . mysql_error($conn) . "" ) ;
    $json_str = "";
   
    if($total = mysql_num_rows($data_sql)) { 
        $json_str .= "[\n";
        
        $row_count = 0;    
        while($data = mysql_fetch_assoc($data_sql)) {
            if(count($data) > 1) 
                $json_str .= "{\n";

            $count = 0;
            if($foreignkey){
                foreach($data as $key => $value) {
                    if ( endsWith($key, "_id") ) {
                       unset($data[$key]);                        
                    }
                }
            }
            
            foreach($data as $key => $value) {
                if(count($data) > 1) 
                    $json_str .= isUid($key) . ":" . is_value_numeric($value);
                else 
                    $json_str .= is_value_numeric($value);
                $count++;    
                if($count < count($data)) 
                    $json_str .= ",\n";
            }
            
            $row_count++;
            if(count($data) > 1) 
                $json_str .= "}\n";

            if($row_count < $total) 
                $json_str .= ",\n";
        }

        $json_str .= "]\n";
    } 

    $json_str = str_replace("\n","",$json_str);

    return $json_str;
}

function isUid($key){
    return startsWith($key, "id") ? "\"uid\"" : "\"$key\"" ;  
}

function is_value_numeric($value){
    return !is_numeric ($value) ? "\"$value\"" : $value; 
} 

function startsWith($haystack, $needle)
{
     $length = strlen($needle);
     return (substr($haystack, 0, $length) === $needle);
}

function endsWith($haystack, $needle)
{
    $length = strlen($needle);
    if ($length == 0) {
        return true;
    }

    return (substr($haystack, -$length) === $needle);
}


function deliver_response($format, $api_response){
     // Define HTTP responses
    $http_response_code = array(
        200 => 'OK',
        400 => 'Bad Request',
        401 => 'Unauthorized',
        403 => 'Forbidden',
        404 => 'Not Found'
    );
 
    // Set HTTP Response
    header('HTTP/1.1 '.$api_response['status'].' '.$http_response_code[ $api_response['status'] ]);
 
    // Process different content types
    if( strcasecmp($format,'json') == 0 ){
        // Set HTTP Response Content Type
        header('Content-Type: application/json; charset=utf-8');
        // Format data into a JSON response
        $json_response = json_encode($api_response['data']);
        // Deliver formatted data
        echo $json_response;
    }
    exit;
} 

?>
