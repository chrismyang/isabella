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
      events : {
        "click .save" : "save"
      },

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
            var html = '<input id="inputTitle" type="text" value="' + this.model.getEvent().get('title') + '"/>' +
              '<input id="inputLocation" type="text" value="' + this.model.getEvent().get('location').text + '"/>' +
              '<button class="save">Save</button>';

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
      },

      save : function () {
        if (this.model.get('editMode')) {
          var newData = {
            "title" : this.$('#inputTitle').val()
          };

          var id = this.model.getEvent().get('id');
          var event = Events.get(id);
          event.set(newData);
          event.save();
        }
      }
    });

    return new EventDetail({ el :  '#event-detail', model : new ViewModel() });
  }
);