define(
  ['backbone', 'views/templates'],
  function (Backbone, Templates) {
    return Backbone.View.extend({
      tagName : 'li',

      template:_.template(Templates.eventCard),

      events : {
        'click' : 'select',
        'dblclick' : 'edit'
      },

      initialize : function () {
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        var modelJson = this.model.toJSON();
        modelJson.location = this.model.getAddress();
        modelJson.tags = "food, bar";

        this.$el.html(this.template(modelJson));
        this.$el.addClass('card');

        if (this.model.isSelected()) {
          this.$el.addClass('selected');
        }

        return this;
      },

      select : function () {
        Backbone.history.navigate('select/' + this.model.get('id'), { trigger : true });
      },

      edit : function () {
        Backbone.history.navigate('edit/' + this.model.get('id'), { trigger : true });
      }
    })
  }
);