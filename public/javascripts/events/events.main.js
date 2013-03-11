require([
  'backbone',
  'views/app'],

  function (Backbone, App) {
    new App();
    Backbone.history.start();
  }
);