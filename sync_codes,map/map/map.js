var map, heatmap, marker, data;

function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    zoom: 10,
    center: {lat: 12.774546, lng: 78.433523},
    mapTypeId: 'satellite'
  });

  //For sometimes, to reset localstorage
  localStorage.clear();
  tooltip = document.getElementById('tooltip');
  school = document.getElementById('school');
}


obj =
{
  xhr: new XMLHttpRequest(),

  Map: function()
  {
    //Clearing already existing map
    if(heatmap )
    {
      tooltip.style.visibility = 'hidden';
      heatmap.setMap(null);
    }

    if(marker)
      marker.setMap(null);

    data = school.value;

    //Get all options
    allschools = document.getElementsByTagName('option');
    for(i = 0; i < allschools.length; ++i)
    {
      if (allschools[i].value == data)
        break;
    }

    //If not among options
    if(data == "" || i == allschools.length)
      alert("Please Select School Name");

    else
    {
      urllink = "http://122.252.230.46/map/map.php?school="+ data;

      //Using Cache
      if(localStorage[urllink])
      {
        console.log('Saved In Cache');
        this.Heat(JSON.parse(localStorage[urllink]));
      }

      //Making New XHR Request
      else
      {
        this.xhr.onreadystatechange = this.Plot;
        this.xhr.open("GET", urllink, true);
        this.xhr.send();
      }
    }
  },

  Query: function(data)
  {
    urllink = 'http://122.252.230.46/map/map.php?question='+data;
    //Using Cache
    if(localStorage[urllink])
    {
      console.log('Saved In Cache');
      this.Fill(JSON.parse(localStorage[urllink]));
    }

    //Making New XHR Request
    else
    {
      this.xhr.onreadystatechange = this.Fetch;
      this.xhr.open("GET", urllink, true);
      this.xhr.send();
    }
  },

  Fetch: function()
  {
    if(obj.xhr.readyState == 4 && obj.xhr.status== 200)
    {
      queryans = JSON.parse(obj.xhr.responseText);

      //Saving in Cache
      localStorage[this.responseURL] = this.responseText

      obj.Fill(queryans);

      this.abort();
    }
  },

  Plot: function()
  {
    if(obj.xhr.readyState == 4 && obj.xhr.status== 200)
    {
      schoolPlot = JSON.parse(obj.xhr.responseText);

      //Saving in Cache
      localStorage[this.responseURL] = this.responseText

      obj.Heat(schoolPlot);

      this.abort();
    }
  },

  Fill: function(data)
  {
    //Filling Query Box with data received
    solution = document.getElementById('solution');
    solution.innerHTML = "";

    for(i = 0; i < data.length; ++i)
    {
      solution.innerHTML += "<br />" +'* '+ data[i];
    }
  },

  Heat: function(data)
  {
    //Just Plotting HeatMap
    heatmap = new google.maps.visualization.HeatmapLayer({
          data: this.getPoints(data),
          map: map
        });

    //Increasing Opacity
    heatmap.set('opacity', heatmap.get('opacity') ? null : 1)

    //Add a Marker
    temp = data[0];
    if(temp)
    {
        marker = new google.maps.Marker({
        position: new google.maps.LatLng(temp[0],temp[1]),
        map: map,
        title: school.value
    });
    }
  },

  getPoints: function(data) {

    var arr = new Array();
    for(i = 1; i < data.length; ++i)
    {
      temp = data[i];
      arr[i] = new google.maps.LatLng(temp[0], temp[1]);
    }

    tooltip.innerHTML = ''+data.length;
    tooltip.style.visibility = 'visible';

    return arr;
  }
}

//Few extra functions for Navbar
function openNav() {
    document.getElementById("mySidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("mySidenav").style.width = "0";
}

/*function toggleHeatmap() {
  heatmap.setMap(heatmap.getMap() ? null : map);
}

function changeRadius() {
  heatmap.set('radius', heatmap.get('radius') ? null : 20);
}

function changeOpacity() {
  heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
}*/