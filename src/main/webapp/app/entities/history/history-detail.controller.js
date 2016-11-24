(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HistoryDetailController', HistoryDetailController);

    HistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'History'];

    function HistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, History) {
        var vm = this;

        vm.history = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:historyUpdate', function(event, result) {
            vm.history = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
