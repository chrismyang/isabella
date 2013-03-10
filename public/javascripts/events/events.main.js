require(
  ['jquery', 'views/map'],

  function ($, Map) {
    $(function () {
      Map.setElement('#map_canvas');
      Map.load();
    });
  }
);