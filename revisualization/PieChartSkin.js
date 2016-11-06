//loading charts
google.charts.load("current", {packages:["corechart"]});
google.charts.setOnLoadCallback(drawChart);
//declaring variables
var process,query,array;
var skinCount=new Array();//array to contain all counts
//array is the JSON array containing all results as JSON Objects plus one null JSON object
//main function
function main()
{
  //getting number of skin cases
  process=1;
  query="s=SELECT COUNT(distinct child_id) from skin where sc=1";
  getData();
  //getting number of skin cases
  process=2;
  query="s=SELECT COUNT(distinct child_id) from skin where pi=1";
  getData();
  //getting number of skin cases
  process=3;
  query="s=SELECT COUNT(distinct child_id) from skin where ph=1";
  getData();
  //getting number of skin cases
  process=4;
  query="s=SELECT COUNT(distinct child_id) from skin where pe=1";
  getData();
  //getting number of skin cases
  process=5;
  query="s=SELECT COUNT(distinct child_id) from skin where pity=1";
  getData();
  //getting number of skin cases
  process=6;
  query="s=SELECT COUNT(distinct child_id) from skin where im=1";
  getData();
  //getting number of skin cases
  process=7;
  query="s=SELECT COUNT(distinct child_id) from skin where pap=1";
  getData();
  //getting number of skin cases
  process=8;
  query="s=SELECT COUNT(distinct child_id) from skin where tc=1";
  getData();
  //getting number of skin cases
  process=9;
  query="s=SELECT COUNT(distinct child_id) from skin where mi=1";
  getData();
  //getting number of skin cases
  process=10;
  query="s=SELECT COUNT(distinct child_id) from skin where vi=1";
  getData();
  //getting number of skin cases
  process=11;
  query="s=SELECT COUNT(distinct child_id) from skin where xerosis=1";
  getData();
  //getting total number of skin cases
  process=12;
  query="s=SELECT COUNT(distinct child_id) from skin";
  getData();
  console.log(skinCount);
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
             skinCount["Scabies"]=getCount();
             break;
      case 2:console.log("PROCESS 2 executed");
             skinCount["PityriasisAlba"]=getCount();
             break;
      case 3:console.log("PROCESS 3 executed");
             skinCount["Phrynoderma"]=getCount();
             break;
      case 4:console.log("PROCESS 4 executed");
             skinCount["Pediculosis"]=getCount();
             break;
      case 5:console.log("PROCESS 5 executed");
             skinCount["Pityriasis Versicolor"]=getCount();
             break;
      case 6:console.log("PROCESS 6 executed");
             skinCount["Impetigo"]=getCount();
             break;
      case 7:console.log("PROCESS 7 executed");
             skinCount["Papularurticaria"]=getCount();
             break;
      case 8:console.log("PROCESS 8 executed");
             skinCount["Tinea Cruris"]=getCount();
             break;
      case 9:console.log("PROCESS 9 executed");
             skinCount["Miliaria"]=getCount();
             break;
      case 10:console.log("PROCESS 10 executed");
             skinCount["Viral Warts"]=getCount();
             break;
      case 11:console.log("PROCESS 11 executed");
             skinCount["Xerosis"]=getCount();
             break;
      case 12:console.log("PROCESS 12 executed");
             skinCount["Total"]=getCount();
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
  /*
  var data = new google.visualization.DataTable();
      data.addColumn('string', 'Name of Disease');
      data.addColumn('number', 'Percentage of Cases');
      data.addRows([
          ['Scabies',parseInt(100000*skinCount["Scabies"]/skinCount["Total"])],
          ['PityriasisAlba',parseInt(100000*skinCount["PityriasisAlba"]/skinCount["Total"])],
          ['Phrynoderma',parseInt(100000*skinCount["Phrynoderma"]/skinCount["Total"])],
          ['Pediculosis',parseInt(100000*skinCount["Pediculosis"]/skinCount["Total"])],
          ['Pityriasis Versicolor',parseInt(10000*skinCount["Pityriasis Versicolor"]/skinCount["Total"])],
          ['Impetigo',parseInt(100000*skinCount["Impetigo"]/skinCount["Total"])],
          ['Papularurticaria',parseInt(100000*skinCount["Papularurticaria"]/skinCount["Total"])],
          ['Tinea Cruris/Corporis',parseInt(100000*skinCount["Tinea Cruris"]/skinCount["Total"])],
          ['Miliaria',parseInt(100000*skinCount["Miliaria"]/skinCount["Total"])],
          ['Viral Warts',parseInt(100000*skinCount["Viral Warts"]/skinCount["Total"])],
          ['Xerosis',parseInt(100000*skinCount["Xerosis"]/skinCount["Total"])]
      ]);
  var data = google.visualization.arrayToDataTable([
          ['Skin Disease', 'Percentage of Cases'],
          ['Scabies',parseInt(100*skinCount["Scabies"]/skinCount["Total"])],
          ['PityriasisAlba',parseInt(100*skinCount["PityriasisAlba"]/skinCount["Total"])],
          ['Phrynoderma',parseInt(100*skinCount["Phrynoderma"]/skinCount["Total"])],
          ['Pediculosis',parseInt(100*skinCount["Pediculosis"]/skinCount["Total"])],
          ['Pityriasis Versicolor',parseInt(100*skinCount["Pityriasis Versicolor"]/skinCount["Total"])],
          ['Impetigo',parseInt(100*skinCount["Impetigo"]/skinCount["Total"])],
          ['Papularurticaria',parseInt(100*skinCount["Papularurticaria"]/skinCount["Total"])],
          ['Tinea Cruris/Corporis',parseInt(100*skinCount["Tinea Cruris"]/skinCount["Total"])],
          ['Miliaria',parseInt(100*skinCount["Miliaria"]/skinCount["Total"])],
          ['Viral Warts',parseInt(100*skinCount["Viral Warts"]/skinCount["Total"])],
          ['Xerosis',parseInt(100*skinCount["Xerosis"]/skinCount["Total"])]
          ]);*/
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name of Disease');
    data.addColumn('number', 'Number of Cases');
    data.addRows([
        ['Scabies',parseInt(skinCount["Scabies"])],
        ['PityriasisAlba',parseInt(skinCount["PityriasisAlba"])],
        ['Phrynoderma',parseInt(skinCount["Phrynoderma"])],
        ['Pediculosis',parseInt(skinCount["Pediculosis"])],
        ['Pityriasis Versicolor',parseInt(skinCount["Pityriasis Versicolor"])],
        ['Impetigo',parseInt(skinCount["Impetigo"])],
        ['Papularurticaria',parseInt(skinCount["Papularurticaria"])],
        ['Tinea Cruris/Corporis',parseInt(skinCount["Tinea Cruris"])],
        ['Miliaria',parseInt(skinCount["Miliaria"])],
        ['Viral Warts',parseInt(skinCount["Viral Warts"])],
        ['Xerosis',parseInt(skinCount["Xerosis"])]
      ]);
   var options = {
    title: 'Popularity of Skin Diseases',
    sliceVisibilityThreshold: .005,
     legend: {title: 'Diseases'}
  };

  var chart = new google.visualization.PieChart(document.getElementById('chart_div'));

  chart.draw(data, options)
}