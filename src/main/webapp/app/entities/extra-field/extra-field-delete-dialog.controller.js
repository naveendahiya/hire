(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldDeleteController',ExtraFieldDeleteController);

    ExtraFieldDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExtraField'];

    function ExtraFieldDeleteController($uibModalInstance, entity, ExtraField) {
        var vm = this;

        vm.extraField = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExtraField.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
