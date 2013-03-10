define(
  ['backbone',
  'models/event'],

  function (Backbone, Event) {
    'use strict';

    var EventList = Backbone.Collection.extend({

      model: Event,

      url: "events"

    });

    return new EventList();
  }
);