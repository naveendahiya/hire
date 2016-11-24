(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtensionStatisticsDeleteController',ExtensionStatisticsDeleteController);

    ExtensionStatisticsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExtensionStatistics'];

    function ExtensionStatisticsDeleteController($uibModalInstance, entity, ExtensionStatistics) {
        var vm = this;

        vm.extensionStatistics = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExtensionStatistics.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
