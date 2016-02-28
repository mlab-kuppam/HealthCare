<?php	
	
	//include 'set_para.php';
	$str=$_POST['j'];	//accepting JSON String storing in J
	$str_json = trim($str);	//trimming the JSON 
	$tables = json_decode($str_json,true);	//getting the JSON Array back
	
	//opening the files to store errors and data
	$error_files= fopen(time()."Error.txt", "w") or die("Unable to open Error File");
	$data_files=fopen(time()."Data.txt", "w") or die("Unable to open data file");

	$json=json_encode($str_json, JSON_PRETTY_PRINT);

	fwrite($data_files,$json); //writing  the data into the file
	fclose($data_files); //closing data file

	$user='root';
	$pass='';
	$conn = mysqli_connect('localhost',$user,$pass,"healthcaredb");//connecting to the database
	if(!$conn)//killing task if connection not established
	{
		die("Connection Could not be Established ".mysqli_error($conn));
	}

	$ctr = true;
	mysqli_autocommit($conn,FALSE);
	mysqli_query($conn,'SET foreign_key_checks = 0');


	
	
	/*
		JSON String represents a 3D Array having tables, columns and values as its content.
		example of the JSON String
		j={'tablename':[{'column1':'value'},...],'tablename2':[{'column2':'value'},...]}
	*/

	//checking if child table is present inserting values from child table
	if(array_key_exists('child', $tables))
		insert($tables['child'],'child');//inserting the table contents
	foreach($tables as $key => $table)//loop to enter each table, key will store the tablename, table will store the array
	{
		if(strcmp($key, "child")!=0 && $ctr)
		insert($table,$key);//inserting contents
	}
	mysqli_query($conn,'SET foreign_key_checks = 1');
	if($ctr)//if the sync is successful
	{
		mysqli_commit($conn);//commiting all changes
		echo("Sync Status ". date("d/m/Y")." : ".date("h:i:sa"));
		echo(" Sync Successful");//printing sync successful
	}
	mysqli_close($conn);//closing the connection
	
	fclose($error_files);


	function insert($table,$key)
	{
		global $ctr,$conn,$error_files;
		$pid="child_id";//storing the name of primary key
		$pid_array=array();
		foreach ($table as $record)//loop to enter array
		{
			//inserting student_id first
			$id= $record[$pid];//storing the value of primary key
			if(!in_array($id, $pid_array))
			{
				array_push($pid_array, $id);
			}
			else
			{
				sleep(2);
			}
			$q="INSERT INTO ".$key." (".$pid.") VALUES ('".$id."');";
			if(mysqli_query($conn,$q))
			{
			}
			else
			{
				fwrite($error_files,time().' : '.mysqli_error($conn)."\n");
				$ctr=false;
				$res=mysqli_rollback($conn);
				echo("Sync Unsuccessful,primary key");
			}
			foreach($record as $k =>$v)//to enter each record, k is the name of column and v is its value
			{	
				//inserting other values
				if(strcmp($pid, $k)!=0 && $ctr)
				{
					$q="UPDATE ".$key." SET ".$k."='".$v."' WHERE ".$pid."='".$id."' ORDER BY timestamp DESC LIMIT 1";//update query
					if(mysqli_query($conn,$q))//performing the query
					{
					}
					else//query has failed
					{
						fwrite($error_files,time().' : '.mysqli_error($conn)."\n");//printing the error which has occured
						$ctr=false;//changing counter
						$res=mysqli_rollback($conn);//undoing any changes
						echo("Sync Unsuccessful");//printing sync unsuccessfull
					
						break 2;//breaking out of all loops if the sync is unsuccessful 
					}
				}		
			}
		}
	}

	?>
