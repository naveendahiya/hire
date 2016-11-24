(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('DataItemTypeDeleteController',DataItemTypeDeleteController);

    DataItemTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'DataItemType'];

    function DataItemTypeDeleteController($uibModalInstance, entity, DataItemType) {
        var vm = this;

        vm.dataItemType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DataItemType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
