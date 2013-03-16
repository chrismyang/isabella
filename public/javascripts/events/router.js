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
        "edit/:eventId" : 'editEvent'
      },

      selectEvent : function (eventId) {
        Events.selectEvent(eventId);
        EventDetailView.setEditMode(false);
      },

      editEvent : function (eventId) {
        this.selectEvent(eventId);
        EventDetailView.setEditMode(true);
      }
    });
  });

