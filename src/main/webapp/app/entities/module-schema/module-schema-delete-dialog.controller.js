(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ModuleSchemaDeleteController',ModuleSchemaDeleteController);

    ModuleSchemaDeleteController.$inject = ['$uibModalInstance', 'entity', 'ModuleSchema'];

    function ModuleSchemaDeleteController($uibModalInstance, entity, ModuleSchema) {
        var vm = this;

        vm.moduleSchema = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ModuleSchema.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
