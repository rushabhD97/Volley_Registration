<?php
    
    require_once 'conn.php';

    $response=array();
    if(isset($_GET['api_call'])){
        switch($_GET['api_call']){
            case 'login':   
                            if(areParametersAvailable(array('email_id','password'))){
                                $emailid=$_POST['email_id'];
                                $password=$_POST['password'];
                                
                    
                                $stmt=$conn->prepare("SELECT id,username,password,mobile_no,gender,email_id,first_name,middle_name,last_name,created_on FROM authentication WHERE email_id=? ");
                                $stmt->bind_param("s",$emailid);
                                $stmt->execute();
                                $stmt->store_result();
                    
                                if($stmt->num_rows > 1){
                                    $response['error']=true;
                                    $response['message']="Contact System Admin!!";
                                }else{
                                    $stmt->bind_result($id,$username,$hashedPassword,$mobile_number,$gender,$emailid,$first_name,$last_name,$middle_name,$created_on);
                                    $stmt->fetch();
                                    if(password_verify($password,$hashedPassword)){
                                        $stmt->prepare("UPDATE authentication SET last_signin=NOW() WHERE id=?");
                                        $stmt->bind_param("i",$id);
                                        $stmt->execute();
                                        $user=array(
   
                                            'id'=>$id,
                                            'username'=>$username,
                                            'emailid'=>$emailid,
                                            'mobilenumber'=>$mobile_number,
                                            'gender'=>$gender,
                                            'first_name'=>$first_name,
                                            'middle_name'=>$middle_name,
                                            'last_name'=>$last_name,
                                            "created_on"=>"$created_on"                                         
                                        );
                                        $response['error']=false;
                                        $response['message']="Logged In";
                                        $response['user']=$user;
                                    }else{
                                        $response['error']=true;
                                        $response['message']="Password did not match";
                                    }
                                }
                                
                    
                            }else{
                                $response['error']=true;
                                $response['message']="Parameters Insufficient";
                            }
                    


            
            
                            break;
            case 'signup':  

                            if(areParametersAvailable(array('email_id','username','password','first_name','last_name','middle_name','mobile_number','gender'))){
                                $emailid=$_POST['email_id'];
                                $username=$_POST['username'];
                                $password=password_hash($_POST['password'],PASSWORD_DEFAULT);
                                $first_name=$_POST['first_name'];
                                $last_name=$_POST['last_name'];
                                $middle_name=$_POST['middle_name'];
                                $mobile_number=$_POST['mobile_number'];
                                $gender=$_POST['gender'];
                                
                    
                                $stmt=$conn->prepare("SELECT id FROM authentication WHERE email_id=? OR username=?");
                                $stmt->bind_param("ss",$emailid,$username);
                                $stmt->execute();
                                $stmt->store_result();
                    
                                if($stmt->num_rows > 0){
                                    $response['error']=true;
                                    $response['message']="User Already Registered!!";
                                }else{
                                    $stmt = $conn->prepare("INSERT INTO authentication (username, email_id, password, created_on, last_signin, first_name, middle_name, last_name, mobile_no, gender) VALUES (?, ?, ?, NOW() , NOW(), ?, ?, ?, ?, ?)");

                                    $stmt->bind_param("ssssssss", $username, $emailid, $password, $first_name,$middle_name,$last_name,$mobile_number,$gender);
                                    
                                    if($stmt->execute()){
                                        $stmt=$conn->prepare("SELECT id,username,email_id,mobile_no,gender,created_on,first_name,middle_name,last_name FROM authentication WHERE username = ?");
                                        $stmt->bind_param("s",$username);
                                        $stmt->execute();
                                        $stmt->bind_result($id,$username,$emailid,$mobile_number,$gender,$created_on,$first_name,$middle_name,$last_name);
                                        $stmt->fetch();
                                        
                    
                                        $user=array(
                                            'id'=>$id,
                                            'username'=>$username,
                                            'emailid'=>$emailid,
                                            'mobilenumber'=>$mobile_number,
                                            'gender'=>$gender,
                                            "created_on"=>"$created_on",
                                            "first_name"=>$first_name,
                                            "middle_name"=>$middle_name,
                                            "last_name"=>$last_name

                                        );
                    
                                        $stmt->close();
                    
                                        $response['error']=false;
                                        $response['message']="User Registered Successfully";
                                        $response['user']=$user;
                                    }
                                }
                    
                            }else{
                                $response['error']=true;
                                $response['message']="Parameters Insufficient";
                            }
                    

                            break;
            default:        
                            $response['error']=true;
                            $response['message']="Invalid Operation!!";
        }
    }else{
        $response['error']=true;
        $response['message']="Not an API call";


    }

    echo json_encode($response);



    function areParametersAvailable($params){
        foreach($params as $param){
            if(!isset($_POST[$param])){
                return false; 
            }
        }
        return true; 
    }



?>
