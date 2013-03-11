define(['jquery', 'backbone', 'collections/events'],
  function ($, Backbone, Events) {
    var Map = Backbone.View.extend({

      initialize : function () {
        Events.on('reset', this.resetMarkers, this);
        this._load();
      },

      _gmap : null,

      getMap : function () {
        return this._gmap;
      },

      resetMarkers : function () {
        var _this = this;
        Events.each(function (event) {
          _this.addMarker(event.getPosition());
        });
      },

      _load : function () {
        var myLatlng = new google.maps.LatLng(-25.363882,131.044922);
        var mapOptions = {
          zoom: 4,
          center: myLatlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        this._gmap = new google.maps.Map(this.$el[0], mapOptions);
      },

      addMarker : function (position) {
        var map = this.getMap();
        var contentString = '<div id="content">'+
          '<div id="siteNotice">'+
          '</div>'+
          '<h1 id="firstHeading" class="firstHeading">Uluru</h1>'+
          '<div id="bodyContent">'+
          '<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
          'sandstone rock formation in the southern part of the '+
          'Northern Territory, central Australia. It lies 335&#160;km (208&#160;mi) '+
          'south west of the nearest large town, Alice Springs; 450&#160;km '+
          '(280&#160;mi) by road. Kata Tjuta and Uluru are the two major '+
          'features of the Uluru - Kata Tjuta National Park. Uluru is '+
          'sacred to the Pitjantjatjara and Yankunytjatjara, the '+
          'Aboriginal people of the area. It has many springs, waterholes, '+
          'rock caves and ancient paintings. Uluru is listed as a World '+
          'Heritage Site.</p>'+
          '<p>Attribution: Uluru, <a href="http://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">'+
          'http://en.wikipedia.org/w/index.php?title=Uluru</a> '+
          '(last visited June 22, 2009).</p>'+
          '</div>'+
          '</div>';

        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });

        var marker = new google.maps.Marker({
          position: position,
          map: map,
          title: 'Uluru (Ayers Rock)'
        });
        google.maps.event.addListener(marker, 'click', function() {
          infowindow.open(map,marker);
        });
      }
    });

    return Map;
  }
);