(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ImportedDeleteController',ImportedDeleteController);

    ImportedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Imported'];

    function ImportedDeleteController($uibModalInstance, entity, Imported) {
        var vm = this;

        vm.imported = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Imported.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
