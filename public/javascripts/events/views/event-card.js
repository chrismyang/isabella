define(
  ['backbone'],
  function (Backbone) {
    return Backbone.View.extend({
      tagName : 'li',

      initialize : function () {
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        var html = '<h4>' + this.model.get('title') + '</h4>' + this.model.getAddress();

        this.$el.html(html);
        return this;
      }
    })
  }
);