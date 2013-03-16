define(
  ['backbone'],
  function (Backbone) {
    return Backbone.View.extend({
      initialize : function () {
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        if (this.model.isEmpty) {
          this.$el.html('Click on the map or on a card to show details.');
        } else {
          var html = '<h3>' + this.model.value.get('title') + '</h3>' +
            '<p>' + this.model.value.get('location').text + '</p>';

          this.$el.html(html);
        }

        return this;
      }
    })
  }
);