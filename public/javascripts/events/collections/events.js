define(
  ['backbone',
  'models/event',
  'utils/active-holder'],

  function (Backbone, Event, ActiveHolder) {
    'use strict';

    var EventList = Backbone.Collection.extend({

      model: Event,

      url: "events",

      initialize : function () {
        this._currentEvent = new ActiveHolder();
      },

      getCurrentEvent : function () {
        return this._currentEvent;
      },

      selectEvent : function (eventId) {
        this.each(function (e) { e.unselect(); });

        var event = this._findById(eventId);

        event.select();
        this._currentEvent.set(event);
      },

      _findById : function (eventId) {
        return this.find(function (event) { return event.get('id') == eventId; });
      },

      createNewEvent : function (onSuccess) {
        var createOptions = {
          wait : true,
          success : onSuccess
        };

        this.create({
          "title" : "New Event Title",
          "location" : {
            "text" : "1655 Mission St, San Francisco, CA"
          }
        }, createOptions);
      }
    });

    return new EventList();
  }
);