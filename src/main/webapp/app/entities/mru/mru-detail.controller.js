(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('MruDetailController', MruDetailController);

    MruDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Mru'];

    function MruDetailController($scope, $rootScope, $stateParams, previousState, entity, Mru) {
        var vm = this;

        vm.mru = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:mruUpdate', function(event, result) {
            vm.mru = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
