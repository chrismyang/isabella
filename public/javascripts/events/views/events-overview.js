define(
  ['backbone', 'views/event-card'],
  function (Backbone, EventCard) {
    return Backbone.View.extend({
      tagName : 'ul',

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

        return this;
      }
    })
  }
);