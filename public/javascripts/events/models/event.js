define(
  ['backbone'],
  function (Backbone) {
    return Backbone.Model.extend({
      defaults : {
        "id" : null,
        "title" : "",
        "location" : {
          "text" : "",
          "latitude" : 0.0,
          "longitude" : 0.0
        },

        selected : false
      },

      getPosition : function () {
        var latitude = this.get('location').latitude;
        var longitude = this.get('location').longitude;
        return new google.maps.LatLng(latitude, longitude);
      },

      getAddress : function () {
        return this.get('location').text;
      },

      select : function () {
        this.set('selected', true);
      },

      unselect : function () {
        this.set('selected', false);
      },

      isSelected : function () {
        return this.get('selected');
      }


    });
  }
);