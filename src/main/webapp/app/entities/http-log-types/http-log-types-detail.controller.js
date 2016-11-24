(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogTypesDetailController', HttpLogTypesDetailController);

    HttpLogTypesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HttpLogTypes'];

    function HttpLogTypesDetailController($scope, $rootScope, $stateParams, previousState, entity, HttpLogTypes) {
        var vm = this;

        vm.httpLogTypes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:httpLogTypesUpdate', function(event, result) {
            vm.httpLogTypes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
