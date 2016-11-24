(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtensionStatisticsDetailController', ExtensionStatisticsDetailController);

    ExtensionStatisticsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExtensionStatistics'];

    function ExtensionStatisticsDetailController($scope, $rootScope, $stateParams, previousState, entity, ExtensionStatistics) {
        var vm = this;

        vm.extensionStatistics = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:extensionStatisticsUpdate', function(event, result) {
            vm.extensionStatistics = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
