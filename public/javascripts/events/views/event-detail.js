define(
  ['backbone', 'collections/events'],
  function (Backbone, Events) {
    var ViewModel = Backbone.Model.extend({
      defaults : {
        "editMode" : false
      },

      initialize : function () {

      },

      getEvent : function () {
        return Events.getCurrentEvent().value;
      }
    });


    var EventDetail = Backbone.View.extend({
      initialize : function () {
        Events.getCurrentEvent().on('all', this.render, this);
        this.model.on('all', this.render, this);
        this.render();
      },

      render : function () {
        if (Events.getCurrentEvent().isEmpty) {
          this.$el.html('Click on the map or on a card to show details.');
        } else {
          if (this.model.get('editMode')) {
            var html = '<input type="text" value="' + this.model.getEvent().get('title') + '"/>' +
              '<input type="text" value="' + this.model.getEvent().get('location').text + '"/>';
            this.$el.html(html);
          } else {
            var html = '<h3>' + this.model.getEvent().get('title') + '</h3>' +
              '<p>' + this.model.getEvent().get('location').text + '</p>';

            this.$el.html(html);
          }
        }

        return this;
      },

      setEditMode : function (isEditMode) {
        if (!this.model.getEvent().isEmpty) {
          this.model.set('editMode', isEditMode);
        }
      }
    });

    return new EventDetail({ el :  '#event-detail', model : new ViewModel() });
  }
);