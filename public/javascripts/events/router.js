define([
  'backbone',
  'collections/events'
  ],

  function (Backbone, Events) {
    'use strict';

    return Backbone.Router.extend({
      routes : {
        "event/:eventId" : 'selectEvent'
      },

      selectEvent : function (eventId) {
        Events.selectEvent(eventId);
      }
    });
  });

