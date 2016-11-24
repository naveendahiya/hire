(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CompanyDepartmentDeleteController',CompanyDepartmentDeleteController);

    CompanyDepartmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'CompanyDepartment'];

    function CompanyDepartmentDeleteController($uibModalInstance, entity, CompanyDepartment) {
        var vm = this;

        vm.companyDepartment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CompanyDepartment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
