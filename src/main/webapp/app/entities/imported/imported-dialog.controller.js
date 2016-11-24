(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ImportedDialogController', ImportedDialogController);

    ImportedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Imported'];

    function ImportedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Imported) {
        var vm = this;

        vm.imported = entity;
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
            if (vm.imported.id !== null) {
                Imported.update(vm.imported, onSaveSuccess, onSaveError);
            } else {
                Imported.save(vm.imported, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:importedUpdate', result);
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
