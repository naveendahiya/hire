(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateDialogController', CandidateDialogController);

    CandidateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Candidate'];

    function CandidateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Candidate) {
        var vm = this;

        vm.candidate = entity;
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
            if (vm.candidate.id !== null) {
                Candidate.update(vm.candidate, onSaveSuccess, onSaveError);
            } else {
                Candidate.save(vm.candidate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateAvailable = false;
        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateModified = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
