(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CompanyDepartmentController', CompanyDepartmentController);

    CompanyDepartmentController.$inject = ['$scope', '$state', 'CompanyDepartment'];

    function CompanyDepartmentController ($scope, $state, CompanyDepartment) {
        var vm = this;

        vm.companyDepartments = [];

        loadAll();

        function loadAll() {
            CompanyDepartment.query(function(result) {
                vm.companyDepartments = result;
            });
        }
    }
})();
