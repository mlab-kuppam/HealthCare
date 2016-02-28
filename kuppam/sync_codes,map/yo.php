<!DOCTYPE html>
<?php

mysql_connect('localhost','root','') or die(mysql_error());
mysql_select_db('loc');
$ch;
if($_POST['sub']=='Visualize')
{
	$check = $_POST['check'];
	global $str;
	foreach ($check as $ch){
		$str = $str.$ch;
	}
}
$select = mysql_query('SELECT `lat` FROM `location` WHERE'.substr($str,4));
$select1 = mysql_query('SELECT `lon` FROM `location` WHERE '.substr($str,4));
$rows = array();
$rows1 = array();
     while($row = mysql_fetch_assoc($select))
      {
		  $row1 = mysql_fetch_assoc($select1);
              $rows[] = $row['lat'];
			  $rows1[] = $row1['lon'];
      }  
?>

<html>
  <head>
    <meta charset="utf-8">
    <title>Heatmaps</title>
    <style>
      html, body, #map-canvas {
        height: 100%;
        margin: 0px;
        padding: 0px
      }
	  #yolo
	  {
		  z-index: 5;
		position: absolute;
		font-weight:bold;
	  }
	  #yolo:hover
	  {
		  color:red;
		  transition: ease-in all 300ms;
	  }
	   .button
	  {
		margin-left:560px;
		height:24px;
		width: 95px;
        border: 1px solid #999;
	  }
	  button:hover 
	  {
		color:blue;
	   transition: ease-in all 300ms;
      }
	  #panel {
        position: absolute;
        top: 5px;
        left: 50%;
        margin-left: -150px;
        z-index: 5;
        background-color: #fff;
        padding: 5px;
        border: 1px solid #999;
      }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=visualization"></script>
    <script>
// Adding 500 Data Points
var map, pointarray, heatmap;

var str = <?php echo json_encode($rows);?>;
var str1 = <?php echo json_encode($rows1);?>;
//document.write(str);
//document.write(str1);
for(var i=0; i<str.length;i++)
{
	str[i] = parseFloat(str[i]);
	str1[i] = parseFloat(str1[i]);
}

var taxiData = new Array();
var markers = new Array();

for(var j=0; j<str.length;j++)
{
	taxiData[j] = new google.maps.LatLng(str[j],str1[j]);	
}

function initialize() {
  var mapOptions = {
    zoom: 9,
    center: new google.maps.LatLng(12.774546, 78.433523),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };


  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

  var pointArray = new google.maps.MVCArray(taxiData);

  heatmap = new google.maps.visualization.HeatmapLayer({
    data: pointArray
  });
  var v=document.getElementById('please');
  
   if(document.getElementById('panel')!="Visualize")
  {	  
	  toggleHeatmap() ;  
  } 
}

   var contentString = '<a href="markers.html"><div>Click here!</div></a>';

  var infowindow = new google.maps.InfoWindow({
      content: contentString
  });
for(var i=0;i<taxiData.length;i++)
{
  var marker = new google.maps.Marker({
      position:taxiData[i],
      map: map,
      title: 'Uluru (Ayers Rock)'
  });
  google.maps.event.addListener(marker, 'click', function() {
    infowindow.open(map,marker);
  });
}
  
function toggleHeatmap() 
{
      heatmap.setMap(heatmap.getMap() ? null : map);	
}
function drop() {
  clearMarkers();
  for (var i = 0; i < taxiData.length; i++) {
    addMarkerWithTimeout(taxiData[i], i * 200);
  }
}

function addMarkerWithTimeout(position, timeout) {
  window.setTimeout(function() {
    markers.push(new google.maps.Marker({
      position: position,
      map: map,
      animation: google.maps.Animation.DROP
    }));
  }, timeout);
}

function clearMarkers() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
}

google.maps.event.addDomListener(window, 'load', initialize);

    </script>
  </head>

  <body>
  <div id = "yolo">
  <form action="yo.php" method="post">
   <div id="up"><input type = "submit" name = "sub" id="button" value = "Visualize" class="button"/></div>
  <input type = "checkbox" name = "check[]" value = " and `cholera` = 1"><span title="254 Cases">Cholera</span></input><br>
  <input type = "checkbox" name = "check[]" value = " and `malaria` = 1"><span title="248 Cases">Malaria</span></input><br>
  <input type = "checkbox" name = "check[]" value = " and `typhoid` = 1"><span title="239 Cases">Typhoid</span></input><br>
  <input type = "checkbox" name = "check[]" value = " and `dengue` = 1"><span title="242 Cases">Dengue</span></input><br>
  <input type = "checkbox" name = "check[]" value = " and `flu` = 1"><span title="245 Cases">Flu</span></input><br>
  </form>
  </div>
  <div id="button" style="margin-left:660px;
		height:21px;
		width: 95px;
        border: 1px solid #999;z-index:5;">
      <button id="drop" onclick="drop()">Drop Markers</button>
     </div>
    <div id="map-canvas"></div>
  </body>
</html>