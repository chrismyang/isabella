define(
  ['backbone'],
  function (Backbone) {
    return Backbone.Model.extend({
      defaults : {
        "id" : null,
        "title" : "",
        "location" : ""
      }
    });
  }
);