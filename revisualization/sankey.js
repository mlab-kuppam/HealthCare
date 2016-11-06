//loading charts
  google.charts.load('current', {'packages':['sankey']});
  google.charts.setOnLoadCallback(drawChart);
//declaring variables
var process,query,array;
var just_Count=new Array(); //array to contain all counts

//array is the JSON array containing all results as JSON Objects plus one null JSON object
//main function

function main()
{
  //getting number of regular bathing and xerosis
  process=1;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM skin WHERE xerosis =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ph_b =1)";
  getData();

  //getting number of irregular bathing and xerosis
  process=2;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM skin WHERE xerosis =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ph_b =0)";
  getData();

  //getting number of regular bathing and Phrynoderma
  process=3;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM skin WHERE ph =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ph_b =1)";
  getData();

  //getting number of regular bathing and Phrynoderma
  process=4;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM skin WHERE ph =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ph_b =0)";
  getData();  

  //getting number of Both parents working and Over night food
  process=5;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM health1 WHERE both_parents = 1 and over_night_food > 0";
  getData();
  console.log(just_Count);

  //getting number of Both parents not working and Over night food
  process=6;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM health1 WHERE both_parents = 0 and over_night_food > 0";
  getData();

  //getting number of Borewell and worm infestation
  process=7;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE water=0 AND child_id IN ( SELECT DISTINCT child_id FROM health2 WHERE gs_wi =1)";
  getData();

  //getting number of Panchayat water and worm infestataion
  process=8;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE water=1 AND child_id IN ( SELECT DISTINCT child_id FROM health2 WHERE gs_wi =1)";
  getData();

  console.log(just_Count);
}
//function to get data
function getData()
{
    var xhttp,data1;
    if (window.XMLHttpRequest) 
      xhttp = new XMLHttpRequest();
    else 
      xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    xhttp.onreadystatechange = function () 
    {
      if(xhttp.readyState==4 && xhttp.status==200)
      {
        data1=xhttp.responseText;
        //console.log(data1);
        if(!data1.includes("Unsuccessful"))
          array=JSON.parse(data1);
        else
          console.error("Unsuccessful retrieval")
        if(data1.localeCompare(""))
          {
            callback();
          }
        else
          console.log("No Data");
      }
    };
    xhttp.open("POST","http://122.252.230.46/query.php",false);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log("Query: "+query);
    xhttp.send(query.trim());
}
//function with code to be performed after data is recieved
function callback()
{
  switch(process)
  {
      case 1:console.log("PROCESS 1 executed");
             just_Count["p1"]=getCount();
             break;
      case 2:console.log("PROCESS 2 executed");
             just_Count["p2"]=getCount();
             break;
      case 3:console.log("PROCESS 3 executed");
             just_Count["p3"]=getCount();
             break;
      case 4:console.log("PROCESS 4 executed");
             just_Count["p4"]=getCount();
             break;
      case 5:console.log("PROCESS 5 executed");
             just_Count["p5"]=getCount();
             break;
      case 6:console.log("PROCESS 6 executed");
             just_Count["p6"]=getCount();
             break;
      case 7:console.log("PROCESS 7 executed");
             just_Count["p7"]=getCount();
             break;
      case 8:console.log("PROCESS 8 executed");
             just_Count["p8"]=getCount();
             break;
      default:console.error("Unidentified process");
  }
}
//function to retrieve the query results
function getCount()
{
  for(key in array[0])
     {
       return(array[0][key]);
     }
}
//function to draw school chart
function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'From');
        data.addColumn('string', 'To');
        data.addColumn('number', 'Weight');
        data.addRows([
          [ 'Regular Bathing', 'Xerosis', parseInt(just_Count["p1"]) ],
          [ 'Irregular Bathing', 'Xerosis', parseInt(just_Count["p2"]) ],
          [ 'Regular Bathing', 'Phrynoderma', parseInt(just_Count["p3"]) ],
          [ 'Irregular Bathing', 'Phrynoderma', parseInt(just_Count["p4"]) ],
          [ 'Both Parents Working', 'Over Night Food', parseInt(just_Count["p5"]) ],
          [ 'Both Parents Not Working', 'Over Night Food', parseInt(just_Count["p6"]) ],
          [ 'Borewell Water', 'Worm Infestation', parseInt(just_Count["p7"]) ],
          [ 'Panchayat Water', 'Worm Infestation', parseInt(just_Count["p8"]) ]
        ]);

        // Sets chart options.
        var options = {
          lable: 'Parameters Comparison and Inferencing (Sankey)',
          width: 800,
          height: 600
        };

        // Instantiates and draws our chart, passing in some options.
        var chart = new google.visualization.Sankey(document.getElementById('chart_div'));
        chart.draw(data, options)
      }