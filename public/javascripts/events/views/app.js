define(
  ['backbone', 'views/map', 'collections/events', 'views/events-overview'],
  function (Backbone, Map, Events, EventsOverview) {
    return Backbone.View.extend({
      initialize : function () {
        var map = new Map({ el : '#map_canvas' });
        Events.fetch();
        new EventsOverview({ el : '#events-overview', model : Events });
      }
    })
  }
);