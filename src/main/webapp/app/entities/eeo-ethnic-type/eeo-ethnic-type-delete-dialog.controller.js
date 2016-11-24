(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoEthnicTypeDeleteController',EeoEthnicTypeDeleteController);

    EeoEthnicTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'EeoEthnicType'];

    function EeoEthnicTypeDeleteController($uibModalInstance, entity, EeoEthnicType) {
        var vm = this;

        vm.eeoEthnicType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EeoEthnicType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
