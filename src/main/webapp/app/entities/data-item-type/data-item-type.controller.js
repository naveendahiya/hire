(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('DataItemTypeController', DataItemTypeController);

    DataItemTypeController.$inject = ['$scope', '$state', 'DataItemType'];

    function DataItemTypeController ($scope, $state, DataItemType) {
        var vm = this;

        vm.dataItemTypes = [];

        loadAll();

        function loadAll() {
            DataItemType.query(function(result) {
                vm.dataItemTypes = result;
            });
        }
    }
})();
