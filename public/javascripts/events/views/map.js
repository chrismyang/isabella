define(['jquery', 'backbone', 'collections/events'],
  function ($, Backbone, Events) {
    var Map = Backbone.View.extend({

      initialize : function () {
        Events.on('reset', this.resetMarkers, this);
        Events.on('add', this.addEvent, this);
        Events.getCurrentEvent().on('change:value', this._showSelectedEvent, this);
        this._load();
      },

      _gmap : null,

      getMap : function () {
        return this._gmap;
      },

      resetMarkers : function () {
        Events.each(_.bind(this.addEvent, this));
      },

      addEvent : function (event) {
        this.addMarker(event.getPosition(), event.get('title'), event.get('id'));
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

      addMarker : function (position, title, id) {
        var map = this.getMap();
        var contentString = '<div id="content"><h4>' + title + '</h4></div>';

        var infowindow = new google.maps.InfoWindow({
          content: contentString
        });
        this._addInfoWindow(id, infowindow);

        var marker = new google.maps.Marker({
          position: position,
          map: map,
          title: 'Uluru (Ayers Rock)'
        });

        this._addMarker(id, marker);

        google.maps.event.addListener(marker, 'click', _.bind(this._showEventInMap), this);
      },

      _showEventInMap : function (eventId) {
        for (var id in this._infoWindows) {
          this._getInfoWindow(id).close();
        }

        var _marker = this._getMarker(eventId);
        this._getInfoWindow(eventId).open(this.getMap(),_marker);
      },

      _addMarker : function (eventId, marker) {
        this._marker[eventId] = marker;
      },

      _addInfoWindow : function (eventId, infoWindow) {
        this._infoWindows[eventId] = infoWindow;
      },

      _infoWindows : {},

      _getInfoWindow : function (eventId) {
        return this._infoWindows[eventId];
      },

      _showSelectedEvent : function (oldEvent, selectedEvent) {
        if (!Events.getCurrentEvent().isEmpty) {
          this._showEventInMap(selectedEvent.get('id'));
        } else {

        }
      },

      _marker : {},

      _getMarker : function (eventId) {
        return this._marker[eventId];
      }
    });

    return Map;
  }
);