(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CompanyDepartmentDialogController', CompanyDepartmentDialogController);

    CompanyDepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CompanyDepartment'];

    function CompanyDepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CompanyDepartment) {
        var vm = this;

        vm.companyDepartment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.companyDepartment.id !== null) {
                CompanyDepartment.update(vm.companyDepartment, onSaveSuccess, onSaveError);
            } else {
                CompanyDepartment.save(vm.companyDepartment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:companyDepartmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
