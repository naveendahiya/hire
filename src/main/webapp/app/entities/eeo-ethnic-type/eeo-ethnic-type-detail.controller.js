(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoEthnicTypeDetailController', EeoEthnicTypeDetailController);

    EeoEthnicTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EeoEthnicType'];

    function EeoEthnicTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, EeoEthnicType) {
        var vm = this;

        vm.eeoEthnicType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:eeoEthnicTypeUpdate', function(event, result) {
            vm.eeoEthnicType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
