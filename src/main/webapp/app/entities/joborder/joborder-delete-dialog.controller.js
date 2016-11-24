(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('JoborderDeleteController',JoborderDeleteController);

    JoborderDeleteController.$inject = ['$uibModalInstance', 'entity', 'Joborder'];

    function JoborderDeleteController($uibModalInstance, entity, Joborder) {
        var vm = this;

        vm.joborder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Joborder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
