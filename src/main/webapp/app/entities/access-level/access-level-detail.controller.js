(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('AccessLevelDetailController', AccessLevelDetailController);

    AccessLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AccessLevel'];

    function AccessLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, AccessLevel) {
        var vm = this;

        vm.accessLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:accessLevelUpdate', function(event, result) {
            vm.accessLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
