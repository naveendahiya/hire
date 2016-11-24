(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogDeleteController',HttpLogDeleteController);

    HttpLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'HttpLog'];

    function HttpLogDeleteController($uibModalInstance, entity, HttpLog) {
        var vm = this;

        vm.httpLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            HttpLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
