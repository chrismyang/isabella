define([
  'backbone',
  'collections/events',
  'views/event-detail'
  ],

  function (Backbone, Events, EventDetailView) {
    'use strict';

    return Backbone.Router.extend({
      routes : {
        "select/:eventId" : 'selectEvent',
        "event/:eventId" : 'editEvent'
      },

      selectEvent : function (eventId) {
        Events.selectEvent(eventId);
      },

      editEvent : function (eventId) {
        this.selectEvent(eventId);
        EventDetailView.enableEditMode();
      }
    });
  });

