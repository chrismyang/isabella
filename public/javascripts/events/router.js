define([
  'backbone',
  'views/app',
  'models/scoreDetails',
  'common/collections/questions',
  'common/views/question'
  ],

  function (Backbone, CandidatesAppView, ScoreDetails, Questions, QuestionView) {
    'use strict';

    var CandidatesWorkspace = Backbone.Router.extend({

      appView : new CandidatesAppView(),

      routes : {
        "scoreDetails/:userId" : 'showDetails'
      },

      showDetails : function(userId) {
        console.log(userId);
        this.loadAttributeScores(userId);
        this.loadFullResponse(userId);
        this.highlightUserRow(userId);
      },

      highlightUserRow: function (userId) {
        $('#table').find('tr').removeClass('user-row');
        $('#' + userId).addClass('user-row');
      },


      loadAttributeScores : function(userId) {
        var self = this;
        $.get( "/admin/scoreDetails/" + userId,
          function(data) {
            self.appView.scoreDetailsView.model.set(data);
          }).error(function(data) { alert(data.responseText);});
      },


      loadFullResponse : function(userId) {
        Questions.url = "fullResponse/" + userId;
        var $el = $('#indicator-detail');
        $el.html('');

        return Questions.fetch({
          success: function(collection, response, options) {
            collection.each(function (questionModel) {
              if (questionModel.has('answer')) {
                var view = new QuestionView({ model: questionModel });
                var $questionEl = $('<div></div>');
                $el.append($questionEl);
                $el.append($('<hr />'));
                view.setElement($questionEl).render(true);
              }
            });
          }
        });
      }
    });

    return CandidatesWorkspace;
  });

