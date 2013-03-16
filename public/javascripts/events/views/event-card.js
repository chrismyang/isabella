define(
  ['backbone'],
  function (Backbone) {
    return Backbone.View.extend({
      tagName : 'li',

      events : {
        'click' : 'select',
        'dblclick' : 'edit'
      },

      initialize : function () {
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        var html = '<h4>' + this.model.get('title') + '</h4>' + this.model.getAddress();

        this.$el.html(html);

        if (this.model.isSelected()) {
          this.$el.addClass('selected');
        }

        return this;
      },

      select : function () {
        Backbone.history.navigate('select/' + this.model.get('id'), { trigger : true });
      },

      edit : function () {
        Backbone.history.navigate('event/' + this.model.get('id'), { trigger : true });
      }
    })
  }
);