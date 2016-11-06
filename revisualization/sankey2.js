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
  //getting number of Overcrowding and anaemia
  process=1;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE r_overcrowding =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ca>1)";
  getData();

  //getting number of No Overcrowding and anaemia
  process=2;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE r_overcrowding =0 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE ca>1)";
  getData();

  //getting number of Overcrowding and adventitious sound
  process=3;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE r_overcrowding =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE rs_sounds =1)";
  getData();

  //getting number of Cross Ventilation and adventitious sound
  process=4;
  query="s=SELECT COUNT( DISTINCT child_id ) FROM socio_demographic WHERE cross_ventilation =1 AND child_id IN ( SELECT DISTINCT child_id FROM health1 WHERE rs_sounds =1)";
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
          [ 'Overcrowding','Anaemia', parseInt(just_Count["p1"]) ],
          [ 'No-Overcrowding', 'Anaemia', parseInt(just_Count["p2"]) ],
          [ 'Overcrowding', 'Adventitious Sound', parseInt(just_Count["p3"]) ],
          [ 'Bad Cross Ventilation', 'Adventitious Sound', parseInt(just_Count["p4"]) ]
        ]);

        // Sets chart options.
        var options = {
              //title="Relations between",
              width: 800,
              height: 400
        };

        // Instantiates and draws our chart, passing in some options.
        var chart = new google.visualization.Sankey(document.getElementById('chart_div'));
        chart.draw(data, options)
      }