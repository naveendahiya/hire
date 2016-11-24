(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedSearchDialogController', SavedSearchDialogController);

    SavedSearchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SavedSearch'];

    function SavedSearchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SavedSearch) {
        var vm = this;

        vm.savedSearch = entity;
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
            if (vm.savedSearch.id !== null) {
                SavedSearch.update(vm.savedSearch, onSaveSuccess, onSaveError);
            } else {
                SavedSearch.save(vm.savedSearch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:savedSearchUpdate', result);
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
