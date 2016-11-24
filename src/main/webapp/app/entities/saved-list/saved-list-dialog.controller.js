(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListDialogController', SavedListDialogController);

    SavedListDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SavedList'];

    function SavedListDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SavedList) {
        var vm = this;

        vm.savedList = entity;
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
            if (vm.savedList.id !== null) {
                SavedList.update(vm.savedList, onSaveSuccess, onSaveError);
            } else {
                SavedList.save(vm.savedList, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:savedListUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateModified = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
