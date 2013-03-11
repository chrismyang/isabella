require(
  ['jquery', 'views/map', 'collections/events'],

  function ($, Map, Events) {
    $(function () {
      var map = new Map({ el : '#map_canvas' });
      map.load();
      Events.fetch();
    });
  }
);