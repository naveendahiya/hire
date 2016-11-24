(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ActivityDetailController', ActivityDetailController);

    ActivityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Activity'];

    function ActivityDetailController($scope, $rootScope, $stateParams, previousState, entity, Activity) {
        var vm = this;

        vm.activity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:activityUpdate', function(event, result) {
            vm.activity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
