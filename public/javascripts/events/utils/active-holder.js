define(['backbone'],
  function (Backbone) {
    /*
     ActiveHolder is a holder for an object.  It can be empty.  It also
     can be listened to for changes to whether or not it is empty and if
     the identity of the held object has changed.

     Used as the base implementation of "current"-type thing -- if, say,
     you are looking at a list of questions and the user can select one
     of them (or none of them) you need a holder to wrap that.
     */
    var ActiveHolder = function ActiveHolder() { };

    ActiveHolder.prototype.isEmpty = true;

    ActiveHolder.prototype.value = true;

    ActiveHolder.prototype.clear = function () {
      this.isEmpty = true;
      this.value = null;
      this.trigger('change:isEmpty', { isEmpty: true });
    };

    ActiveHolder.prototype.set = function (newValue) {
      this.isEmpty = false;
      var oldValue = this.value;
      this.value = newValue;
      this.trigger('change:value', oldValue, newValue);
      this.trigger('change:isEmpty', { isEmpty : false });
    };

    _.extend(ActiveHolder.prototype, Backbone.Events);

    return ActiveHolder;
  }
);

