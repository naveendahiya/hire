(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtensionStatisticsController', ExtensionStatisticsController);

    ExtensionStatisticsController.$inject = ['$scope', '$state', 'ExtensionStatistics'];

    function ExtensionStatisticsController ($scope, $state, ExtensionStatistics) {
        var vm = this;

        vm.extensionStatistics = [];

        loadAll();

        function loadAll() {
            ExtensionStatistics.query(function(result) {
                vm.extensionStatistics = result;
            });
        }
    }
})();
