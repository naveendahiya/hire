(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('FeedbackController', FeedbackController);

    FeedbackController.$inject = ['$scope', '$state', 'Feedback'];

    function FeedbackController ($scope, $state, Feedback) {
        var vm = this;

        vm.feedbacks = [];

        loadAll();

        function loadAll() {
            Feedback.query(function(result) {
                vm.feedbacks = result;
            });
        }
    }
})();
