(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ActivityTypeDetailController', ActivityTypeDetailController);

    ActivityTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ActivityType'];

    function ActivityTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, ActivityType) {
        var vm = this;

        vm.activityType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:activityTypeUpdate', function(event, result) {
            vm.activityType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
