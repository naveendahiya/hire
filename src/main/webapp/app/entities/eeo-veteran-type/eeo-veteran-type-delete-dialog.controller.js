(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoVeteranTypeDeleteController',EeoVeteranTypeDeleteController);

    EeoVeteranTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EeoVeteranType'];

    function EeoVeteranTypeDeleteController($uibModalInstance, entity, EeoVeteranType) {
        var vm = this;

        vm.eeoVeteranType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EeoVeteranType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
