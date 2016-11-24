(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('AccessLevelDeleteController',AccessLevelDeleteController);

    AccessLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'AccessLevel'];

    function AccessLevelDeleteController($uibModalInstance, entity, AccessLevel) {
        var vm = this;

        vm.accessLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AccessLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
