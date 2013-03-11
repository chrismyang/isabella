require([
  'backbone',
  'views/app',
  'jquery',
  'router'],

  function (Backbone, App, $, Router) {
    $(function () {
      new App();
      new Router();
      Backbone.history.start({pushState: true, root: "/helloWorld"})
    })
  }
);