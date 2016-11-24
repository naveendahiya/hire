(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CompanyDepartmentDetailController', CompanyDepartmentDetailController);

    CompanyDepartmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CompanyDepartment'];

    function CompanyDepartmentDetailController($scope, $rootScope, $stateParams, previousState, entity, CompanyDepartment) {
        var vm = this;

        vm.companyDepartment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:companyDepartmentUpdate', function(event, result) {
            vm.companyDepartment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
