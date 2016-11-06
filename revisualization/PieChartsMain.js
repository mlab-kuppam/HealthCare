//loading charts
google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);
//declaring variables
var process,query,array;
var organCount=new Array();//array to contain all
var totalOrganCount=new Array();
//array is the JSON array containing all results as JSON Objects plus one null JSON object
//main function
function main()
{
  //getting number of skin cases
  process=1;
  query="s=SELECT COUNT(distinct child_id) from skin where referal=1";
  getData();
  //getting total number of skin cases
  process=2;
  query="s=SELECT COUNT(distinct child_id) from skin";
  getData();
  //getting number of ent cases
  process=3;
  query="s=SELECT COUNT(distinct child_id) from ent where referal=1";
  getData();
  //getting total number of ent cases
  process=4;
  query="s=SELECT COUNT(distinct child_id) from ent";
  getData();
  //getting number of eye cases
  process=5;
  query="s=SELECT COUNT(distinct child_id) from eye where referal=1";
  getData();
  //getting total number of eye cases
  process=6;
  query="s=SELECT COUNT(distinct child_id) from eye";
  getData();
  //getting number of health cases
  process=7;
  query="s=SELECT COUNT(distinct child_id) from health2 where referal=1";
  getData();
  //getting total number of health cases
  process=8;
  query="s=SELECT COUNT(distinct child_id) from health2";
  getData();
  //getting number of Oral cases
  process=9;
  query="s=SELECT COUNT(distinct child_id) from health1 where oe_referal=1";
  getData();
  //getting total number of Oral cases
  process=10;
  query="s=SELECT COUNT(distinct child_id) from health1";
  getData();
  console.log(organCount);
  console.log(totalOrganCount);
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
             organCount["skin"]=getCount();
             break;
      case 2:console.log("PROCESS 2 executed");
             totalOrganCount["skin"]=getCount();
             break;
      case 3:console.log("PROCESS 3 executed");
             organCount["ent"]=getCount();
             break;
      case 4:console.log("PROCESS 4 executed");
             totalOrganCount["ent"]=getCount();
             break;
      case 5:console.log("PROCESS 5 executed");
             organCount["eye"]=getCount();
             break;
      case 6:console.log("PROCESS 6 executed");
             totalOrganCount["eye"]=getCount();
             break;
      case 7:console.log("PROCESS 7 executed");
             organCount["health"]=getCount();
             break;
      case 8:console.log("PROCESS 8 executed");
             totalOrganCount["health"]=getCount();
             break;
      case 9:console.log("PROCESS 9 executed");
             organCount["oral"]=getCount();
             break;
      case 10:console.log("PROCESS 10 executed");
             totalOrganCount["oral"]=getCount();
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
function drawChart() 
{
  var data = google.visualization.arrayToDataTable([
          ['Organ System', 'Referal Percentage in Percent'],
          ['Skin',parseInt(100*organCount["skin"]/totalOrganCount["skin"])],
          ['ENT',parseInt(100*organCount["ent"]/totalOrganCount["ent"])],
          ['Eye',parseInt(100*organCount["eye"]/totalOrganCount["eye"])],
          ['General Health',parseInt(100*organCount["health"]/totalOrganCount["health"])],
          ['Oral',parseInt(100*organCount["oral"]/totalOrganCount["oral"])]
        ]);
  var options = {
    title: 'Relative Widespread of Diseases Based on Organ System'
  };

  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

  chart.draw(data, options)
}