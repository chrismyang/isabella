define(
  ['backbone', 'views/event-card', 'collections/events'],
  function (Backbone, EventCard, Events) {
    return Backbone.View.extend({
      tagName : 'ul',

      events : {
        'click button' : 'addNewEvent'
      },

      initialize : function () {
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        this.$el.html('');

        var fnRenderEvent = _.bind(function (event) {
          var card = new EventCard({ model : event });
          this.$el.append(card.render().$el);
        }, this);

        this.model.each(fnRenderEvent);

        this.$el.append('<button>New Event</button>');

        return this;
      },

      addNewEvent : function () {
        Events.createNewEvent(function (newEvent) {
          Backbone.history.navigate('edit/' + newEvent.get('id'), { trigger : true });
        });
      }
    })
  }
);