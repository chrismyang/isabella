define(
  ['backbone', 'views/map', 'collections/events', 'views/events-overview', 'views/event-detail'],
  function (Backbone, Map, Events, EventsOverview, EventDetail) {
    return Backbone.View.extend({
      initialize : function () {
        var map = new Map({ el : '#map_canvas' });
        Events.fetch();
        new EventsOverview({ el : '#events-overview', model : Events });
        new EventDetail({ el : '#event-detail', model : Events.getCurrentEvent() });
      }
    })
  }
);