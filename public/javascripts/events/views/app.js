define(
  ['backbone', 'views/map', 'collections/events'],
  function (Backbone, Map, Events) {
    return Backbone.View.extend({
      initialize : function () {
        var map = new Map({ el : '#map_canvas' });
        map.load();
        Events.fetch();
      }
    })
  }
);