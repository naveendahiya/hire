(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogTypesDeleteController',HttpLogTypesDeleteController);

    HttpLogTypesDeleteController.$inject = ['$uibModalInstance', 'entity', 'HttpLogTypes'];

    function HttpLogTypesDeleteController($uibModalInstance, entity, HttpLogTypes) {
        var vm = this;

        vm.httpLogTypes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HttpLogTypes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
