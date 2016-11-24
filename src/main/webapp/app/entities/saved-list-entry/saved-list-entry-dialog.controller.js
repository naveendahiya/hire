(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListEntryDialogController', SavedListEntryDialogController);

    SavedListEntryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SavedListEntry'];

    function SavedListEntryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SavedListEntry) {
        var vm = this;

        vm.savedListEntry = entity;
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
            if (vm.savedListEntry.id !== null) {
                SavedListEntry.update(vm.savedListEntry, onSaveSuccess, onSaveError);
            } else {
                SavedListEntry.save(vm.savedListEntry, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:savedListEntryUpdate', result);
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
