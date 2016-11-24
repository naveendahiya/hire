(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('JoborderDialogController', JoborderDialogController);

    JoborderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Joborder'];

    function JoborderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Joborder) {
        var vm = this;

        vm.joborder = entity;
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
            if (vm.joborder.id !== null) {
                Joborder.update(vm.joborder, onSaveSuccess, onSaveError);
            } else {
                Joborder.save(vm.joborder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:joborderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateModified = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
