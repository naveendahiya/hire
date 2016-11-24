(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ActivityTypeController', ActivityTypeController);

    ActivityTypeController.$inject = ['$scope', '$state', 'ActivityType'];

    function ActivityTypeController ($scope, $state, ActivityType) {
        var vm = this;

        vm.activityTypes = [];

        loadAll();

        function loadAll() {
            ActivityType.query(function(result) {
                vm.activityTypes = result;
            });
        }
    }
})();
