//loading charts
google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);
//declaring variables
var process,query,array;
var eyeCount=new Array();//array to contain all counts
//array is the JSON array containing all results as JSON Objects plus one null JSON object
//main function
function main()
{
  //getting number of skin cases
  process=1;
  query="s=SELECT COUNT(distinct child_id) from eye where cv_r=0";
  getData();
  //getting number of skin cases
  process=2;
  query="s=SELECT COUNT(distinct child_id) from eye where cv_l=0";
  getData();
  //getting number of skin cases
  process=3;
  query="s=SELECT COUNT(distinct child_id) from eye where bs_r=1";
  getData();
  //getting number of skin cases
  process=4;
  query="s=SELECT COUNT(distinct child_id) from eye where bs_l=1";
  getData();
  //getting number of skin cases
  process=5;
  query="s=SELECT COUNT(distinct child_id) from eye where ac_r=1";
  getData();
  //getting number of skin cases
  process=6;
  query="s=SELECT COUNT(distinct child_id) from eye where ac_l=1";
  getData();
  //getting number of skin cases
  process=7;
  query="s=SELECT COUNT(distinct child_id) from eye where nb=1";
  getData();
  //getting number of skin cases
  process=8;
  query="s=SELECT COUNT(distinct child_id) from eye where cp_r=1";
  getData();
  //getting number of skin cases
  process=9;
  query="s=SELECT COUNT(distinct child_id) from eye where cp_l=1";
  getData();
  //getting number of skin cases
  process=10;
  query="s=SELECT COUNT(distinct child_id) from eye where cdc_r=1";
  getData();
  //getting number of skin cases
  process=11;
  query="s=SELECT COUNT(distinct child_id) from eye where cdc_l=1";
  getData();
  //getting number of skin cases
  process=12;
  query="s=SELECT COUNT(distinct child_id) from eye where am_r=1";
  getData();
  //getting number of skin cases
  process=13;
  query="s=SELECT COUNT(distinct child_id) from eye where am_l=1";
  getData();
  //getting number of skin cases
  process=14;
  query="s=SELECT COUNT(distinct child_id) from eye where ny_r=1";
  getData();
  //getting number of skin cases
  process=15;
  query="s=SELECT COUNT(distinct child_id) from eye where ny_l=1";
  getData();
  //getting number of skin cases
  process=16;
  query="s=SELECT COUNT(distinct child_id) from eye where fe_r=0";
  getData();
  //getting number of skin cases
  process=17;
  query="s=SELECT COUNT(distinct child_id) from eye where fe_l=0";
  getData();
  //getting total number of cases
  process=18;
  query="s=SELECT COUNT(distinct child_id) from eye";
  getData();
  console.log(eyeCount);
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
             eyeCount["cv_r"]=getCount();
             break;
      case 2:console.log("PROCESS 2 executed");
             eyeCount["cv_l"]=getCount();
             break;
      case 3:console.log("PROCESS 3 executed");
             eyeCount["bs_r"]=getCount();
             break;
      case 4:console.log("PROCESS 4 executed");
             eyeCount["bs_l"]=getCount();
             break;
      case 5:console.log("PROCESS 5 executed");
             eyeCount["ac_r"]=getCount();
             break;
      case 6:console.log("PROCESS 6 executed");
             eyeCount["ac_l"]=getCount();
             break;
      case 7:console.log("PROCESS 7 executed");
             eyeCount["nb"]=getCount();
             break;
      case 8:console.log("PROCESS 8 executed");
             eyeCount["cp_r"]=getCount();
             break;
      case 9:console.log("PROCESS 9 executed");
             eyeCount["cp_l"]=getCount();
             break;
      case 10:console.log("PROCESS 10 executed");
             eyeCount["cdc_r"]=getCount();
             break;
      case 11:console.log("PROCESS 11 executed");
             eyeCount["cdc_l"]=getCount();
             break;
      case 12:console.log("PROCESS 12 executed");
             eyeCount["am_r"]=getCount();
             break;
      case 13:console.log("PROCESS 13 executed");
             eyeCount["am_l"]=getCount();
             break;
      case 14:console.log("PROCESS 14 executed");
             eyeCount["ny_r"]=getCount();
             break;
      case 15:console.log("PROCESS 15 executed");
             eyeCount["ny_l"]=getCount();
             break;
      case 16:console.log("PROCESS 16 executed");
             eyeCount["fe_r"]=getCount();
             break;
      case 17:console.log("PROCESS 17 executed");
             eyeCount["fe_l"]=getCount();
             break;
      case 18:console.log("PROCESS 18 executed");
             eyeCount["Total"]=getCount();
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
        ['No Colour Vision - Right Eye',parseInt(eyeCount["cv_r"])],
        ['No Colour Vision - Left Eye',parseInt(eyeCount["cv_l"])],
        ['Bitot\'s Spots - Right Eye',parseInt(eyeCount["bs_r"])],
        ['Bitot\'s Spots - Left Eye',parseInt(eyeCount["bs_l"])],
        ['Allergic Conjunctivitis - Right Eye',parseInt(eyeCount["ac_r"])],
        ['Allergic Conjunctivitis - Left Eye',parseInt(eyeCount["ac_l"])],
        ['Night Blindness',parseInt(eyeCount["nb"])],
        ['Congenital Ptosis - Right Eye',parseInt(eyeCount["cp_r"])],
        ['Congenital Ptosis - Left Eye',parseInt(eyeCount["cp_l"])],
        ['Congenital Developmental Cataract - Right Eye',parseInt(eyeCount["cdc_r"])],
        ['Congenital Developmental Cataract - Left Eye',parseInt(eyeCount["cdc_l"])],
        ['Amblyopia - Right Eye',parseInt(eyeCount["am_r"])],
        ['Amblyopia - Left Eye',parseInt(eyeCount["am_l"])],
        ['Nystagmus - Right Eye',parseInt(eyeCount["ny_r"])],
        ['Nystagmus - Left Eye',parseInt(eyeCount["ny_l"])],
        ['Fundus Examination - Right Eye',parseInt(eyeCount["fe_r"])],
        ['Abnormal Fundus Examination - Left Eye',parseInt(eyeCount["fe_l"])]
      ]);
   var options = {
    title: 'Distribution of Eye Diseases',
    sliceVisibilityThreshold: .01
  };

  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

  chart.draw(data, options)
}