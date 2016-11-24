(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoVeteranTypeDetailController', EeoVeteranTypeDetailController);

    EeoVeteranTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EeoVeteranType'];

    function EeoVeteranTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, EeoVeteranType) {
        var vm = this;

        vm.eeoVeteranType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:eeoVeteranTypeUpdate', function(event, result) {
            vm.eeoVeteranType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
