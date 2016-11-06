<?php
    extract($_GET);

    header("Access-Control-Allow-Origin: *");
    header("Access-Control-Allow-Methods: POST");

    //Open a database connection
    $conn = mysql_connect('localhost','root','kuppam');
    if(!$conn)
    {
        die("Could not connect to database server..\n");
    }

    //Else, we have connected. We select the "test" database
    $db = mysql_select_db('healthcaredb');
    if(!$db)
    {
        die("Could not select database..\n");
    }

    $plot = array();
    $query = '';

    //Query Box Fill
    if($question)
    {
        //Query breakdown
        if($question == 'followup')
            $query = file_get_contents("followup_query.txt");

        elseif($question == 'morbid')
            $query = 'Select distinct name from `school`;';

        else
            die("Invalid Query");

        //We are ready now. Execute the query
        $results = mysql_query($query);
        if(!$results)
        {
            die("Some Issue in querying..\n");
        }

        //We have the data
        while($row = mysql_fetch_array($results,MYSQL_ASSOC))
            $plot[] = $row['name'];

    }

    //Plot points
    else
    {
        #Query for school mapping
        $query = file_get_contents("plot_query.txt");
        $query = str_replace('$school', $school, $query);

        //For School Location
        $query2 = "SELECT lat, lon
                    FROM `loc_school`
                    WHERE school_id
                    IN
                        (SELECT school_id
                        FROM `school`
                        WHERE name = '$school');";

        //We are ready now. Execute the query
        $results = mysql_query($query);
        $results2 = mysql_query($query2);

        //Many schools don't have locations
        if(!$results)
        {
            die("Some Issue in querying..\n");
        }

        //We have the data
        while($row2 = mysql_fetch_array($results2,MYSQL_ASSOC))
            $plot[] = array($row2['lat'],$row2['lon']);

        while($row = mysql_fetch_array($results,MYSQL_ASSOC))
            $plot[] = array($row['lat'],$row['lon']);
    }

    echo json_encode($plot);
?>