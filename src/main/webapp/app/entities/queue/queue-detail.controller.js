(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('QueueDetailController', QueueDetailController);

    QueueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Queue'];

    function QueueDetailController($scope, $rootScope, $stateParams, previousState, entity, Queue) {
        var vm = this;

        vm.queue = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:queueUpdate', function(event, result) {
            vm.queue = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
