//loading charts
google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);
//declaring variables
var process,query,array;
var entCount=new Array();//array to contain all counts
//array is the JSON array containing all results as JSON Objects plus one null JSON object
//main function
function main()
{
  //getting number of skin cases
  process=1;
  query="s=SELECT COUNT(distinct child_id) from ent where oe_r=1";
  getData();
  //getting number of skin cases
  process=2;
  query="s=SELECT COUNT(distinct child_id) from ent where oe_l=1";
  getData();
  //getting number of skin cases
  process=3;
  query="s=SELECT COUNT(distinct child_id) from ent where as_r=1";
  getData();
  //getting number of skin cases
  process=4;
  query="s=SELECT COUNT(distinct child_id) from ent where as_l=1";
  getData();
  //getting number of skin cases
  process=5;
  query="s=SELECT COUNT(distinct child_id) from ent where cs_r=1";
  getData();
  //getting number of skin cases
  process=6;
  query="s=SELECT COUNT(distinct child_id) from ent where cs_l=1";
  getData();
  //getting number of skin cases
  process=7;
  query="s=SELECT COUNT(distinct child_id) from ent where iw_r=1";
  getData();
  //getting number of skin cases
  process=8;
  query="s=SELECT COUNT(distinct child_id) from ent where iw_l=1";
  getData();
  //getting number of skin cases
  process=9;
  query="s=SELECT COUNT(distinct child_id) from ent where ih_r=1";
  getData();
  //getting number of skin cases
  process=10;
  query="s=SELECT COUNT(distinct child_id) from ent where ih_l=1";
  getData();
  //getting number of skin cases
  process=11;
  query="s=SELECT COUNT(distinct child_id) from ent where ep=1";
  getData();
  //getting number of skin cases
  process=12;
  query="s=SELECT COUNT(distinct child_id) from ent where ad=1";
  getData();
  //getting number of skin cases
  process=13;
  query="s=SELECT COUNT(distinct child_id) from ent where ph=1";
  getData();
  //getting number of skin cases
  process=14;
  query="s=SELECT COUNT(distinct child_id) from ent where ar=1";
  getData();
  //getting number of skin cases
  process=15;
  query="s=SELECT COUNT(distinct child_id) from ent where sd=1";
  getData();
  //getting number of skin cases
  process=16;
  query="s=SELECT COUNT(distinct child_id) from ent where urti=1";
  getData();
  //getting number of skin cases
  process=17;
  query="s=SELECT COUNT(distinct child_id) from ent where cleft=1";
  getData();
  //getting total number of cases
  process=18;
  query="s=SELECT COUNT(distinct child_id) from ent";
  getData();
  console.log(entCount);
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
             entCount["oe_r"]=getCount();
             break;
      case 2:console.log("PROCESS 2 executed");
             entCount["oe_l"]=getCount();
             break;
      case 3:console.log("PROCESS 3 executed");
             entCount["as_r"]=getCount();
             break;
      case 4:console.log("PROCESS 4 executed");
             entCount["as_l"]=getCount();
             break;
      case 5:console.log("PROCESS 5 executed");
             entCount["cs_r"]=getCount();
             break;
      case 6:console.log("PROCESS 6 executed");
             entCount["cs_l"]=getCount();
             break;
      case 7:console.log("PROCESS 7 executed");
             entCount["iw_r"]=getCount();
             break;
      case 8:console.log("PROCESS 8 executed");
             entCount["iw_l"]=getCount();
             break;
      case 9:console.log("PROCESS 9 executed");
             entCount["ih_r"]=getCount();
             break;
      case 10:console.log("PROCESS 10 executed");
             entCount["ih_l"]=getCount();
             break;
      case 11:console.log("PROCESS 11 executed");
             entCount["ep"]=getCount();
             break;
      case 12:console.log("PROCESS 12 executed");
             entCount["ad"]=getCount();
             break;
      case 13:console.log("PROCESS 12 executed");
             entCount["ph"]=getCount();
             break;
      case 14:console.log("PROCESS 12 executed");
             entCount["ar"]=getCount();
             break;
      case 15:console.log("PROCESS 12 executed");
             entCount["sd"]=getCount();
             break;
      case 16:console.log("PROCESS 12 executed");
             entCount["urti"]=getCount();
             break;
      case 17:console.log("PROCESS 12 executed");
             entCount["cleft"]=getCount();
             break;
      case 18:console.log("PROCESS 12 executed");
             entCount["Total"]=getCount();
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
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name of Disease');
    data.addColumn('number', 'Number of Cases');
    data.addRows([
        ['Otitis Externa - Right Ear',parseInt(entCount["oe_r"])],
        ['Otitis Externa - Left Ear',parseInt(entCount["oe_l"])],
        ['AOM - Right Ear',parseInt(entCount["as_r"])],
        ['AOM - Left Ear',parseInt(entCount["as_l"])],
        ['CSOM - Right Ear',parseInt(entCount["cs_r"])],
        ['CSOM - Left Ear',parseInt(entCount["cs_l"])],
        ['Wax - Right Ear',parseInt(entCount["iw_r"])],
        ['Wax - Left Ear',parseInt(entCount["iw_l"])],
        ['Impaired Hearing - Right Ear',parseInt(entCount["ih_r"])],
        ['Impaired Hearing - Left Ear',parseInt(entCount["ih_l"])],
        ['H/O Epistaxis',parseInt(entCount["ep"])],
        ['Tonsilitis',parseInt(entCount["ad"])],
        ['Pharyngitis',parseInt(entCount["ph"])],
        ['Rhinitis',parseInt(entCount["ar"])],
        ['Speech Defects',parseInt(entCount["sd"])],
        ['URTI',parseInt(entCount["urti"])],
        ['Cleft Lip',parseInt(entCount["cleft"])]
      ]);
   var options = {
    title: 'Distribution of ENT Diseases',
    sliceVisibilityThreshold: .01
  };

  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

  chart.draw(data, options)
}