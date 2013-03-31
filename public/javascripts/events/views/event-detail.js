define(
  ['backbone', 'collections/events', 'views/templates'],
  function (Backbone, Events, Templates) {
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
      defaultTemplate: _.template(Templates.defaultDetailCard),

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
            var html =
              '<input id="inputTitle" type="text" placeholder="title" value="' + this.model.getEvent().get('title') + '"/>' +
              '<input id="inputLocation" type="text" placeholder="location" value="' + this.model.getEvent().get('location').text + '"/>' +
              '<input id="inputImageUrl" type="text" placeholder="imageUrl" value="' + this.model.getEvent().get('imageUrl') + '"/>' +
              '<input id="inputTags" type="text" placeholder="tags" value="' + this.model.getEvent().get('tags') + '"/>' +
              '<input id="inputNotes" type="text" placeholder="notes" value="' + this.model.getEvent().get('notes') + '"/>' +
              '<button class="save">Save</button>';

            this.$el.html(html);
          } else {
            var json = this.model.getEvent().toJSON();

            this.$el.html(this.defaultTemplate(json));
            this.$el.addClass('detail-card');

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
          var location = this.model.getEvent().get('location');
          location.text = this.$('#inputLocation').val();

          var newData = {
            "title" : this.$('#inputTitle').val(),
            "location" : location,
            "imageUrl" : this.$('#inputImageUrl').val(),
            "tags" : this.$('#inputTags').val(),
            "notes" : this.$('#inputNotes').val()
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