(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('DataItemTypeDetailController', DataItemTypeDetailController);

    DataItemTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DataItemType'];

    function DataItemTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, DataItemType) {
        var vm = this;

        vm.dataItemType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:dataItemTypeUpdate', function(event, result) {
            vm.dataItemType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
