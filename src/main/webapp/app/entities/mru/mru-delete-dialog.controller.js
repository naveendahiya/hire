(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('MruDeleteController',MruDeleteController);

    MruDeleteController.$inject = ['$uibModalInstance', 'entity', 'Mru'];

    function MruDeleteController($uibModalInstance, entity, Mru) {
        var vm = this;

        vm.mru = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Mru.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
