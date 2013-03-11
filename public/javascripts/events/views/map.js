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
          _this.addMarker(event.getPosition(), event.get('title'));
        });
      },

      _load : function () {
        var myLatlng = new google.maps.LatLng(37.7697361, -122.46613809999997);
        var mapOptions = {
          zoom: 13,
          center: myLatlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        this._gmap = new google.maps.Map(this.$el[0], mapOptions);
      },

      addMarker : function (position, title) {
        var map = this.getMap();
        var contentString = '<div id="content">'+
          '<h4>' + title + '</h4>' +
          '</div>';

        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });
        this._addInfoWindow(infowindow);

        var marker = new google.maps.Marker({
          position: position,
          map: map,
          title: 'Uluru (Ayers Rock)'
        });
        google.maps.event.addListener(marker, 'click', function() {
          _.each(this._infoWindows, function (iw) {
            iw.close();
          });
          infowindow.open(map,marker);
        });
      },

      _addInfoWindow : function (infoWindow) {
        this._infoWindows.push(infoWindow);
      },

      _infoWindows : []

    });

    return Map;
  }
);