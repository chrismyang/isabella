define(
  function () {
    'use strict';

    var templates = {};

    templates.eventCard = '\
      <div class="card-container"> \
        <div class="image-container" style="background-image: url(<%= imageUrl %>);"></div> \
        <div class="card-body-container"> \
          <h1><%= title %></h1> \
          <p class="location"><i class="icon-map-marker"></i> <%= location.text %></p> \
          <p class="tags"><i class="icon-tags"></i> <%= tags %></p> \
        </div> \
      </div>';

    templates.defaultDetailCard = '\
      <div class="card-container"> \
        <div class="image-container" style="background-image: url(<%= imageUrl %>);"></div> \
        <div class="card-body-container"> \
          <h1><%= title %></h1> \
          <p class="location"><i class="icon-map-marker"></i> <%= location.text %></p> \
          <p class="tags"><i class="icon-tags"></i> <%= tags %></p> \
          <p class="notes"><%= notes %></p>\
        </div> \
      </div>';

    return templates;
  }
);